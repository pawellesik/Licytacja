package com.example.licytacja.moje.BridgeBidder;

import com.example.licytacja.moje.BridgeBidder.Constraints.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * KLASA BAZOWA DLA SYSTEMÓW LICYTACYJNYCH.
 * Zawiera zestaw narzędzi (tzw. constraints) do budowania reguł licytacji AI.
 */
public abstract class Bidder {

    // =================================================================================
    // --- SEKCJA 1: DEFINIOWANIE ODZYWEK (Fundamentalne) ---
    // =================================================================================

    /**
     * GŁÓWNA METODA SYSTEMU. Tworzy regułę: "Zalicytuj to, JEŚLI spełniasz te warunki".
     * @param call Odzywka (np. Bid._1H, Call.PASS).
     * @param constraints Lista warunków (np. points(12,17), shape(5,10)).
     */
    public static BidRule shows(Call call, Constraint... constraints) {
        return new BidRule(call, constraints);
    }

    /**
     * DEFINIUJE CECHY ODZYWKI I ODPOWIEDZI. Najmocniejsze narzędzie w klasie.
     * Pozwala ustawić wymuszenie, uzgodnić atut i przypisać metodę licytacji dla partnera.
     */
    public static CallFeatureGroup properties(Call call, PositionCallsFactory partnerBids, 
                                              boolean forcing1Round, boolean forcingToGame, 
                                              boolean agreeTrump, Suit trump,
                                              String alert, String announce, String convention,
                                              StaticConstraint onlyIf) {
        CallFeatureGroup group = new CallFeatureGroup();
        group.getFeatures().add(new CallProperties(call, partnerBids, forcing1Round, forcingToGame, agreeTrump, trump, onlyIf));
        if (alert != null) group.getFeatures().add(alert(call, alert, onlyIf));
        if (announce != null) group.getFeatures().add(announce(call, announce, onlyIf));
        if (convention != null) group.getFeatures().add(convention(call, convention, onlyIf));
        return group;
    }

    /** Wersja metody properties obsługująca tablicę odzywek naraz. */
    public static CallFeatureGroup properties(Call[] calls, PositionCallsFactory partnerBids, boolean forcing1Round, boolean forcingToGame, boolean agreeTrump, Suit trump, String alert, String announce, String convention, StaticConstraint onlyIf) {
        CallFeatureGroup group = new CallFeatureGroup();
        for (Call call : calls) {
            group.getFeatures().add(properties(call, partnerBids, forcing1Round, forcingToGame, agreeTrump, trump, alert, announce, convention, onlyIf));
        }
        return group;
    }

    /** Szybkie przypisanie partnerowi metody odpowiedzi bez ustawiania flag wymuszenia. */
    public static CallProperties partnerBids(Call call, PositionCallsFactory pcf) {
        return new CallProperties(call, pcf, false, false, false, null);
    }

    // =================================================================================
    // --- SEKCJA 2: WYCENA RĘKI (Punkty i Siła) ---
    // =================================================================================

    /**
     * PUNKTY PARY (SUMA). Twoje punkty + punkty obiecane publicznie przez partnera.
     * @param suit Jeśli podasz kolor, silnik doliczy punkty za krótkości (Dummy Points).
     */
    public static HandConstraint pairPoints(Suit suit, int min, int max) {
        return new PairPoints.PairShowsPoints(suit, min, max, false);
    }

    public static HandConstraint pairPoints(int min, int max) {
        return new PairPoints.PairShowsPoints(null, min, max, false);
    }

    /** HCP + DŁUGOŚĆ. Standardowa metoda sprawdzania siły na otwarcie (1 pkt za każdą kartę > 4). */
    public static HandConstraint points(int min, int max) {
        return new Points.ShowsPoints(null, min, max, Points.PointType.Starting);
    }

    /** PUNKTY HONOROWE (HCP). Tylko figury (A=4, K=3, D=2, W=1). Używane głównie do 1NT. */
    public static HandConstraint highCardPoints(int min, int max) {
        return new Points.ShowsPoints(null, min, max, Points.PointType.HighCard);
    }

    /** PUNKTY DZIADKA. HCP + bonusy za krótkości przy ficie z partnerem. */
    public static HandConstraint dummyPoints(int min, int max) {
        return new Points.ShowsPoints(null, min, max, Points.PointType.Dummy);
    }

    // =================================================================================
    // --- SEKCJA 3: SKŁAD I RELACJE MIĘDZY KOLORAMI ---
    // =================================================================================

    /** Liczba kart w licytowanym kolorze. Przykład: shape(5,13) */
    public static HandConstraint shape(int min, int max) {
        return new Shape.ShowsShape(null, min, max);
    }

    /** Liczba kart w konkretnym kolorze. */
    public static HandConstraint shape(Suit suit, int count) {
        return new Shape.ShowsShape(suit, count, count);
    }

    /** FIT (WSPÓLNY KOLOR). Sprawdza czy Ty + Partner macie łącznie min. 'count' kart. */
    public static HandConstraint fit(int count) {
        return new PairMinShape.PairShowsMinShape(null, count, true);
    }

    public static final HandConstraint FIT_8_PLUS = fit(8);

    /** Sprawdza, czy licytowany kolor jest NAJDŁUŻSZYM kolorem w Twojej ręce. */
    public static final HandConstraint LONGEST_SUIT = new LongestSuit.ShowsLongestSuit(null);

    /** Ręka zrównoważona (brak singli, brak renonsów, max jeden dubleton). */
    public static final HandConstraint BALANCED = new Balanced.ShowsBalanced(true);

    /** Licytowany kolor jest co najmniej tak długi jak wskazany kolor 'worse'. */
    public static HandConstraint longerOrEqual(Suit better, Suit worse) {
        return new BetterSuit.ShowsBetterSuit(better, worse, better, true);
    }

    // =================================================================================
    // --- SEKCJA 4: ASY I KRÓLE (Szlemy) ---
    // =================================================================================

    /** Łączna liczba asów w rękach obu partnerów (0-4). */
    public static Constraint pairAces(int... count) {
        return new PairAces(count);
    }

    /** Liczba asów tylko w Twojej ręce. */
    public static Constraint aces(int... count) {
        return new Aces(count);
    }

    /** Kluczowe karty (RKCB). Liczy Asy + Króla atutowego oraz Damę atutową. */
    public static HandConstraint pairKeyCards(Suit suit, Boolean hasQueen, int... count) {
        return new PairKeyCards(suit, hasQueen, count);
    }

    // =================================================================================
    // --- SEKCJA 5: SYTUACJA PRZY STOLE (Constrainty Statyczne) ---
    // =================================================================================

    /** Sprawdza pozycję gracza (1, 2, 3 lub 4 ręka). */
    public static StaticConstraint isSeat(int... seats) {
        return new SimpleStaticConstraint((call, ps) -> {
            for (int s : seats) if (ps.getSeat() == s) return true;
            return false;
        }, (call, ps) -> "seat " + Arrays.toString(seats));
    }

    /** Przeskok o 1 poziom (np. 1H - 2S). */
    public static final StaticConstraint IS_SINGLE_JUMP = new JumpBid(1);
    /** Brak przeskoku (najniżej jak się da). */
    public static final StaticConstraint IS_NON_JUMP = new JumpBid(0);
    /** Dowolny przeskok (1, 2 lub 3 poziomy). */
    public static final StaticConstraint IS_ANY_JUMP = new JumpBid(1, 2, 3);

    /** True, jeśli gracz przed podjęciem obecnej decyzji raz już spasował. */
    public static StaticConstraint isPassedHand() {
        return new SimpleStaticConstraint((call, ps) -> ps.isPassedHand(), "passed hand");
    }

    /** Sprawdza, czy gracz licytował JUŻ TEN SAM kolor wcześniej. */
    public static final StaticConstraint IS_REBID = new BidHistory(0, null);

    // =================================================================================
    // --- SEKCJA 6: LOGIKA POMOCNICZA I ADNOTACJE ---
    // =================================================================================

    public static Constraint and(Constraint... constraints) { return new ConstraintGroup(constraints); }
    public static StaticConstraint staticAnd(StaticConstraint... constraints) { return new ConstraintGroup(constraints); }
    
    public static StaticConstraint not(StaticConstraint c) {
        return new SimpleStaticConstraint((call, ps) -> !c.conforms(call, ps), (call, ps) -> {
            if (c instanceof IDescribeConstraint) {
                String desc = ((IDescribeConstraint) c).describe(call, ps);
                return (desc == null || desc.isEmpty()) ? null : "not " + desc;
            }
            return null;
        });
    }

    public static StaticConstraint partner(Constraint constraint) { return new PositionProxy(PositionProxy.RelativePosition.Partner, constraint); }
    public static Constraint note(String text) { return new Note(text); }

    public static CallFeature alert(Call call, String text, StaticConstraint... constraints) { return new CallAnnotation(call, CallAnnotation.AnnotationType.Alert, text, constraints); }
    public static CallFeature announce(Call call, String text, StaticConstraint... constraints) { return new CallAnnotation(call, CallAnnotation.AnnotationType.Announce, text, constraints); }
    public static CallFeature convention(Call call, String text, StaticConstraint... constraints) { return new CallAnnotation(call, CallAnnotation.AnnotationType.Convention, text, constraints); }
    public static CallFeature convention(String text, StaticConstraint... constraints) { return new CallAnnotation(null, CallAnnotation.AnnotationType.Convention, text, constraints); }

    public static CallFeatureGroup propertiesAgreeTrump(Call[] calls, PositionCallsFactory partnerBids, boolean agreeTrump) { 
        return properties(calls, partnerBids, false, false, agreeTrump, null, null, null, null, null); 
    }

    public static final StaticConstraint IS_VUL = new SimpleStaticConstraint((call, ps) -> ps.isVulnerable(), "vul");
    public static final StaticConstraint IS_NOT_VUL = not(IS_VUL);
    public static final StaticConstraint IS_PARTNERS_SUIT = partner(new HasShownSuit(null, false));
    public static final StaticConstraint CONTRACT_IS_AGREED_STRAIN = new SimpleStaticConstraint((call, ps) -> {
        Call contractBid = ps.getBiddingState().getContract().getBid();
        if (contractBid instanceof Bid) {
            Bid bid = (Bid) contractBid;
            return ps.getBiddingState().getContract().isOurs(ps.getDirection()) && bid.getSuit() == ps.getPairState().getLastShownSuit();
        }
        return false;
    });

    public static class Note extends StaticConstraint implements IDescribeConstraint {
        private final String text;
        public Note(String text) { this.text = text; }
        @Override public boolean conforms(Call call, PositionState ps) { return true; }
        @Override public String describe(Call call, PositionState ps) { return text; }
    }
}
