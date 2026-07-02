package com.example.licytacja.moje.BridgeBidder;

import com.example.licytacja.moje.BridgeBidder.Constraints.*;
import java.util.Arrays;

public abstract class Bidder {
    public static BidRule shows(Call call, Constraint... constraints) {
        return new BidRule(call, constraints);
    }

    public static CallFeature alert(Call call, String text, StaticConstraint... constraints) {
        return new CallAnnotation(call, CallAnnotation.AnnotationType.Alert, text, constraints);
    }

    public static CallFeature announce(Call call, String text, StaticConstraint... constraints) {
        return new CallAnnotation(call, CallAnnotation.AnnotationType.Announce, text, constraints);
    }

    public static CallFeature convention(Call call, String text, StaticConstraint... constraints) {
        return new CallAnnotation(call, CallAnnotation.AnnotationType.Convention, text, constraints);
    }

    public static CallProperties partnerBids(Call call, PositionCallsFactory pcf) {
        return new CallProperties(call, pcf, false, false, false, null);
    }

    public static CallProperties partnerBids(PositionCallsFactory pcf) {
        return new CallProperties(null, pcf, false, false, false, null);
    }

    public static CallFeatureGroup properties(Call call, PositionCallsFactory partnerBids, boolean forcing1Round, boolean forcingToGame, boolean agreeTrump, Suit trump,
                                              String alert, String announce, String convention,
                                              StaticConstraint onlyIf) {
        CallFeatureGroup group = new CallFeatureGroup();
        group.getFeatures().add(new CallProperties(call, partnerBids, forcing1Round, forcingToGame, agreeTrump, trump, onlyIf));
        if (alert != null) group.getFeatures().add(alert(call, alert, onlyIf));
        if (announce != null) group.getFeatures().add(announce(call, announce, onlyIf));
        if (convention != null) group.getFeatures().add(convention(call, convention, onlyIf));
        return group;
    }

    public static CallFeatureGroup properties(Call[] calls, PositionCallsFactory partnerBids, boolean forcing1Round, boolean forcingToGame, boolean agreeTrump, Suit trump,
                                              String alert, String announce, String convention,
                                              StaticConstraint onlyIf) {
        CallFeatureGroup group = new CallFeatureGroup();
        for (Call call : calls) {
            group.getFeatures().add(properties(call, partnerBids, forcing1Round, forcingToGame, agreeTrump, trump, alert, announce, convention, onlyIf));
        }
        return group;
    }

    public static CallFeatureGroup properties(Call call, PositionCallsFactory partnerBids, boolean forcing1Round, String convention) {
        return properties(call, partnerBids, forcing1Round, false, false, null, null, null, convention, null);
    }

    public static CallFeatureGroup propertiesForcingToGame(Call[] calls, PositionCallsFactory partnerBids, boolean forcingToGame) {
        return properties(calls, partnerBids, false, forcingToGame, false, null, null, null, null, null);
    }

    public static CallFeatureGroup propertiesAgreeTrump(Call[] calls, PositionCallsFactory partnerBids, boolean agreeTrump) {
        return properties(calls, partnerBids, false, false, agreeTrump, null, null, null, null, null);
    }
    
    public static CallFeatureGroup properties(Call call, PositionCallsFactory partnerBids, boolean forcing1Round) {
        return properties(call, partnerBids, forcing1Round, false, false, null, null, null, null, null);
    }

    public static CallFeatureGroup properties(Call call, String alert) {
        return properties(call, null, false, false, false, null, alert, null, null, null);
    }

    // Static Constraints
    public static StaticConstraint isSeat(int... seats) {
        return new SimpleStaticConstraint(
                (call, ps) -> {
                    for (int s : seats) {
                        if (ps.getSeat() == s) return true;
                    }
                    return false;
                },
                (call, ps) -> "seat " + Arrays.toString(seats)
        );
    }

    public static StaticConstraint isLastBid(Call call) {
        return new BidHistory(0, call);
    }

    public static StaticConstraint isLastBid(int level, Suit suit) {
        return new BidHistory(0, new Bid(level, suit));
    }

    public static StaticConstraint isLastBid(int level, Strain strain) {
        return new BidHistory(0, new Bid(level, strain));
    }

    public static StaticConstraint isOpeningBid(Bid bid) {
        return new SimpleStaticConstraint((call, ps) -> ps.getBiddingState().getOpeningBid().equals(bid));
    }

    public static final StaticConstraint IS_REBID = new BidHistory(0, null);
    public static final StaticConstraint IS_NOT_REBID = not(IS_REBID);

    public static StaticConstraint id(String id) {
        return new LogID(id);
    }
    public static final StaticConstraint IS_CUE_BID = new IsCueBid(null);
    public static final StaticConstraint IS_NOT_CUE_BID = not(IS_CUE_BID);

    public static StaticConstraint staticAnd(StaticConstraint... constraints) {
        return new ConstraintGroup(constraints);
    }

    public static final StaticConstraint IS_NEW_SUIT = staticAnd(IS_NOT_CUE_BID, new NewSuit(null));

    public static StaticConstraint not(StaticConstraint c) {
        return new SimpleStaticConstraint(
                (call, ps) -> !c.conforms(call, ps),
                (call, ps) -> {
                    if (c instanceof IDescribeConstraint) {
                        String desc = ((IDescribeConstraint) c).describe(call, ps);
                        return (desc == null || desc.isEmpty()) ? null : "not " + desc;
                    }
                    return null;
                }
        );
    }

    public static StaticConstraint isJump(int... jumpLevels) {
        return new JumpBid(jumpLevels);
    }

    public static final StaticConstraint IS_NON_JUMP = isJump(0);
    public static final StaticConstraint IS_SINGLE_JUMP = isJump(1);

    public static final StaticConstraint IS_VUL = new SimpleStaticConstraint((call, ps) -> ps.isVulnerable(), "vul");
    public static final StaticConstraint IS_NOT_VUL = new SimpleStaticConstraint((call, ps) -> !ps.isVulnerable(), "not vul");

    public static StaticConstraint partner(Constraint constraint) {
        return new PositionProxy(PositionProxy.RelativePosition.Partner, constraint);
    }

    public static StaticConstraint rho(Constraint constraint) {
        return new PositionProxy(PositionProxy.RelativePosition.RHO, constraint);
    }

    // Hand Constraints
    public static HandConstraint highCardPoints(int min, int max) {
        return new Points.ShowsPoints(null, min, max, Points.PointType.HighCard);
    }

    public static HandConstraint points(int min, int max) {
        return new Points.ShowsPoints(null, min, max, Points.PointType.Starting);
    }

    public static HandConstraint dummyPoints(int min, int max) {
        return new Points.ShowsPoints(null, min, max, Points.PointType.Dummy);
    }

    public static HandConstraint shape(int count) {
        return new Shape.ShowsShape(null, count, count);
    }

    public static HandConstraint shape(int min, int max) {
        return new Shape.ShowsShape(null, min, max);
    }

    public static Constraint aces(int... count) {
        return new KeyCards(null, null, count);
    }

    public static HandConstraint shape(Suit suit, int min, int max) {
        return new Shape.ShowsShape(suit, min, max);
    }

    public static final HandConstraint BALANCED = new Balanced.ShowsBalanced(true);
    
    public static HandConstraint longerThan(Suit worse) {
        return new BetterSuit.ShowsBetterSuit(null, worse, worse, true);
    }

    public static HandConstraint longerOrEqualTo(Suit worse) {
        return new BetterSuit.ShowsBetterSuit(null, worse, null, true);
    }

    public static HandConstraint longer(Suit better, Suit worse) {
        return new BetterSuit.ShowsBetterSuit(better, worse, worse, true);
    }

    public static HandConstraint longerOrEqual(Suit better, Suit worse) {
        return new BetterSuit.ShowsBetterSuit(better, worse, better, true);
    }

    public static HandConstraint betterThan(Suit worse) {
        return new BetterSuit.ShowsBetterSuit(null, worse, worse, false);
    }

    public static HandConstraint betterOrEqualTo(Suit worse) {
        return new BetterSuit.ShowsBetterSuit(null, worse, null, false);
    }

    public static Constraint and(Constraint... constraints) {
        return new ConstraintGroup(constraints);
    }

    public static Constraint longestMajor(int max) {
        return and(shape(Suit.Hearts, 0, max), shape(Suit.Spades, 0, max));
    }

    public static final HandConstraint LONGEST_SUIT = new LongestSuit.ShowsLongestSuit(null);

    public static HandConstraint pairKeyCards(Suit suit, Boolean hasQueen, int... count) {
        return new PairKeyCards(suit, hasQueen, count);
    }

    public static Constraint kings(int... count) {
        return new Kings(count);
    }

    public static Constraint pairKings(int... count) {
        return new PairKings(count);
    }

    public static HandConstraint fit(int count, Suit suit, boolean desiredValue) {
        return new PairMinShape.PairShowsMinShape(suit, count, desiredValue);
    }

    public static HandConstraint fit(int count, Suit suit) {
        return fit(count, suit, true);
    }

    public static HandConstraint fit(int count) {
        return fit(count, null, true);
    }

    public static final HandConstraint FIT_8_PLUS = fit(8);

    public static HandConstraint pairPoints(Suit suit, int min, int max) {
        return new PairPoints.PairShowsPoints(suit, min, max);
    }

    public static HandConstraint pairPoints(int min, int max) {
        return new PairPoints.PairShowsPoints(min, max);
    }

    public static final StaticConstraint CONTRACT_IS_AGREED_STRAIN = new SimpleStaticConstraint(
            (call, ps) -> {
                ContractState contract = ps.getBiddingState().getContract();
                return contract.getBid() != null &&
                        contract.isOurs(ps.getDirection()) &&
                        contract.getBid().getSuit() == ps.getPairState().getLastShownSuit();
            }
    );
}
