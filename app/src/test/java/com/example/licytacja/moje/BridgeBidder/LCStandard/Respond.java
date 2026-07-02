package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.NegativeDouble;
import java.util.ArrayList;
import java.util.List;

public class Respond extends LCStandard {
    protected static final Range RESPOND_PASS = new Range(0, 5);
    protected static final Range RESPOND_1_LEVEL = new Range(6, 40);
    protected static final Range RAISE_1 = new Range(6, 10);
    protected static final Range LIMIT_RAISE = new Range(11, 12);
    protected static final Range NEW_SUIT_2_LEVEL = new Range(13, 40);
    protected static final Range RESPOND_1NT_OVER_MAJOR = new Range(6, 12);
    protected static final Range MINIMUM_HAND = new Range(6, 10);
    protected static final Range MEDIUM_HAND = new Range(11, 13);
    protected static final Range RAISE_TO_3NT = new Range(13, 16);
    protected static final Range RAISE_TO_4M = new Range(13, 16);
    protected static final Range LIMIT_RAISE_OR_BETTER = new Range(11, 40);
    protected static final Range WEAK_4_LEVEL = new Range(0, 10);
    protected static final Range WEAK_5_LEVEL = new Range(0, 10);
    protected static final Range GAME_OR_BETTER = new Range(13, 40);
    protected static final Range MAX_PASSED = new Range(10, 11);

    public static PositionCalls oneClub(PositionState ps) {
        if (!ps.getRHO().isPassed()) {
            return oppsInterferred(ps, Suit.Clubs);
        }
        PositionCalls choices = new PositionCalls(ps);
        if (ps.isPassedHand()) {
            choices.addRules(
                partnerBids(OpenBid2::responderChangedSuits),
                shows(Bid._1D, points(RESPOND_1_LEVEL), shape(5, 10), longestMajor(3)),
                shows(Bid._1H, points(RESPOND_1_LEVEL), shape(4), shape(Suit.Spades, 0, 4)),
                shows(Bid._1H, points(RESPOND_1_LEVEL), shape(5, 10), longerThan(Suit.Spades)),
                shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), longerOrEqual(Suit.Spades, Suit.Hearts)),
                propertiesAgreeTrump(new Call[]{Bid._2C, Bid._3C, Bid._4C, Bid._5C}, OpenBid2::responderRaisedMinor, true),
                shows(Bid._2C, points(RAISE_1), shape(5), longestMajor(3)),
                shows(Bid._3C, points(LIMIT_RAISE), shape(5), longestMajor(3)),
                shows(Bid._5C, points(WEAK_5_LEVEL), shape(7, 10)),
                shows(Bid._4C, points(WEAK_4_LEVEL), shape(6))
            );
        } else {
            choices.addRules(SolidSuit.BIDS(ps));
            choices.addRules(
                properties(new Call[]{Bid._1D, Bid._1H, Bid._1S}, OpenBid2::responderChangedSuits, true),
                shows(Bid._1D, points(RESPOND_1_LEVEL), shape(5, 10), longestMajor(3)),
                shows(Bid._1D, points(LIMIT_RAISE_OR_BETTER), shape(5, 10), longerThan(Suit.Hearts), longerThan(Suit.Spades)),
                shows(Bid._1H, points(RESPOND_1_LEVEL), shape(4), shape(Suit.Spades, 0, 4)),
                shows(Bid._1H, points(RESPOND_1_LEVEL), shape(5, 10), longerThan(Suit.Spades)),
                shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), longerOrEqual(Suit.Spades, Suit.Hearts)),
                propertiesAgreeTrump(new Call[]{Bid._2C, Bid._3C, Bid._4C, Bid._5C}, OpenBid2::responderRaisedMinor, true),
                shows(Bid._2C, points(RAISE_1), shape(5), longestMajor(3)),
                shows(Bid._3C, points(LIMIT_RAISE), shape(5), longestMajor(3)),
                shows(Bid._5C, points(WEAK_5_LEVEL), shape(7, 10)),
                shows(Bid._4C, points(WEAK_4_LEVEL), shape(6))
            );
        }
        // choices.addRules(noTrumpResponsesToMinor(Suit.Clubs));
        choices.addRules(weakJumpShift(Suit.Clubs));
        choices.addPassRule(points(RESPOND_PASS));
        return choices;
    }

    public static PositionCalls oneDiamond(PositionState ps) {
        if (!ps.getRHO().isPassed()) {
            return oppsInterferred(ps, Suit.Diamonds);
        }
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(SolidSuit.BIDS(ps));
        choices.addRules(
            propertiesAgreeTrump(new Call[]{Bid._2D, Bid._3D, Bid._4D, Bid._5D}, OpenBid2::responderRaisedMinor, true),
            properties(Bid._2C, OpenBid2::twoOverOne, false, true, false, null, null, null, null, null), // forcingToGame
            properties(new Call[]{Bid._1H, Bid._1S}, OpenBid2::responderChangedSuits, true),
            shows(Bid._2C, points(GAME_OR_BETTER), longestMajor(4)),
            shows(Bid._1H, points(RESPOND_1_LEVEL), shape(4), shape(Suit.Spades, 0, 4)),
            shows(Bid._1H, points(RESPOND_1_LEVEL), shape(5, 10), longerThan(Suit.Spades)),
            shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), longerOrEqual(Suit.Spades, Suit.Hearts)),
            shows(Bid._2D, points(RAISE_1), shape(5), longestMajor(3)),
            shows(Bid._3D, points(LIMIT_RAISE), shape(5), longestMajor(3)),
            shows(Bid._5D, points(WEAK_5_LEVEL), shape(7, 10)),
            shows(Bid._4D, points(WEAK_4_LEVEL), shape(6))
        );
        // choices.addRules(noTrumpResponsesToMinor(Suit.Diamonds));
        choices.addRules(weakJumpShift(Suit.Diamonds));
        choices.addPassRule(points(RESPOND_PASS));
        return choices;
    }

    public static PositionCalls oneHeart(PositionState ps) {
        if (!ps.getRHO().isPassed()) {
            return oppsInterferred(ps, Suit.Hearts);
        }
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(SolidSuit.BIDS(ps));
        choices.addRules(
            partnerBids(OpenBid2::responderChangedSuits),
            propertiesAgreeTrump(new Call[]{Bid._2H, Bid._3H, Bid._4H}, OpenBid2::responderRaisedMajor, true),
            shows(Bid._2H, dummyPoints(RAISE_1), shape(3, 5)),
            shows(Bid._3H, dummyPoints(MEDIUM_HAND), shape(4, 5)),
            shows(Bid._4H, points(WEAK_4_LEVEL), shape(5, 10)),
            shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), shape(Suit.Hearts, 0, 3)),
            shows(Bid._1NT, points(RESPOND_1NT_OVER_MAJOR), shape(Suit.Hearts, 0, 3), shape(Suit.Spades, 0, 3)),
            shows(Bid._3NT, FLAT, points(RAISE_TO_3NT))
        );
        choices.addRules(weakJumpShift(Suit.Hearts));
        choices.addPassRule(points(RESPOND_PASS));
        return choices;
    }

    public static PositionCalls oneSpade(PositionState ps) {
        if (!ps.getRHO().isPassed()) {
            return oppsInterferred(ps, Suit.Spades);
        }
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(SolidSuit.BIDS(ps));
        choices.addRules(
            partnerBids(OpenBid2::responderChangedSuits),
            propertiesAgreeTrump(new Call[]{Bid._2S, Bid._3S, Bid._4S}, OpenBid2::responderRaisedMajor, true),
            shows(Bid._2S, dummyPoints(RAISE_1), shape(3, 5)),
            shows(Bid._3S, dummyPoints(MEDIUM_HAND), shape(4, 5)),
            shows(Bid._4S, points(WEAK_4_LEVEL), shape(5, 10)),
            shows(Bid._1NT, points(RESPOND_1NT_OVER_MAJOR), shape(Suit.Spades, 0, 3)),
            shows(Bid._3NT, FLAT, points(RAISE_TO_3NT))
        );
        choices.addRules(weakJumpShift(Suit.Spades));
        choices.addPassRule(points(RESPOND_PASS));
        return choices;
    }

    private static PositionCalls oppsInterferred(PositionState ps, Suit openSuit) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(NegativeDouble.initiateConvention(ps));
        return choices;
    }

    public static Iterable<CallFeature> weakOpen(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(shows(Bid._4H, FIT_8_PLUS, ruleOf17()));
        bids.add(shows(Bid._4H, fit(10)));
        bids.add(shows(Bid._4S, FIT_8_PLUS, ruleOf17()));
        bids.add(shows(Bid._4S, fit(10)));
        bids.add(shows(Bid._3D, fit(9)));
        bids.add(shows(Bid._3H, fit(9)));
        bids.add(shows(Bid._3S, fit(9)));
        bids.add(shows(Call.PASS));
        return bids;
    }

    public static Iterable<CallFeature> weakJumpShift(Suit openSuit) {
        List<CallFeature> bids = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            if (suit != openSuit) {
                bids.add(shows(new Bid(2, suit), IS_SINGLE_JUMP, points(0, 5), shape(6, 10), DECENT_PLUS_SUIT));
                bids.add(shows(new Bid(3, suit), IS_SINGLE_JUMP, points(0, 5), shape(6, 10), DECENT_PLUS_SUIT));
            }
        }
        return bids;
    }
}
