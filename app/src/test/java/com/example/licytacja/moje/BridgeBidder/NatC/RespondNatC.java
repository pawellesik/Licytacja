package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.AcesAsk;
import com.example.licytacja.moje.BridgeBidder.Conventions.Jacoby2NT;
import com.example.licytacja.moje.BridgeBidder.Conventions.NegativeDouble;
import com.example.licytacja.moje.BridgeBidder.Conventions.Blackwood;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;

import java.util.ArrayList;
import java.util.List;

public class RespondNatC extends NatC {
    public static final Range RESPOND_PASS = new Range(0, 5);
    public static final Range RESPOND_1_LEVEL = new Range(6, 10);
    public static final Range RAISE_1 = new Range(6, 10);
    public static final Range LIMIT_RAISE = new Range(11, 12);
    public static final Range NEW_SUIT_2_LEVEL = new Range(13, 40);
    public static final Range RESPOND_1NT_OVER_MAJOR = new Range(6, 12);
    public static final Range MINIMUM_HAND = new Range(6, 10);
    public static final Range MEDIUM_HAND = new Range(11, 13);
    public static final Range RAISE_TO_3NT = new Range(13, 16);
    public static final Range RAISE_TO_4M = new Range(13, 16);
    public static final Range LIMIT_RAISE_OR_BETTER = new Range(11, 40);
    public static final Range WEAK_4_LEVEL = new Range(0, 10);
    public static final Range WEAK_5_LEVEL = new Range(0, 10);
    public static final Range GAME_OR_BETTER = new Range(13, 40);
    public static final Range MAX_PASSED = new Range(10, 11);
    protected static final Range WEAK_JUMP_SHIFT_POINTS = new Range(0, 5);


    public static PositionCalls oneClub(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(Blackwood.initiateConvention(ps));
        if (ps.isPassedHand()) {
            choices.addRules(
                    partnerBids(OpenBid2NatC::responderChangedSuits),
                    shows(Bid._1D, points(RESPOND_1_LEVEL), shape(5, 10), longestMajor(3)),
                    shows(Bid._1H, points(RESPOND_1_LEVEL), shape(4), shape(Suit.Spades, 0, 4)),
                    shows(Bid._1H, points(RESPOND_1_LEVEL), shape(5, 10), longerThan(Suit.Spades)),
                    shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), longerOrEqualTo(Suit.Hearts)),
                    propertiesAgreeTrump(new Call[]{Bid._2C, Bid._3C, Bid._4C, Bid._5C}, OpenBid2NatC::responderRaisedMinor, true),
                    shows(Bid._2C, points(RAISE_1), shape(5), longestMajor(3)),
                    shows(Bid._3C, points(LIMIT_RAISE), shape(5), longestMajor(3)),
                    shows(Bid._5C, points(WEAK_5_LEVEL), shape(7, 10)),
                    shows(Bid._4C, points(WEAK_4_LEVEL), shape(6))
            );
        } else {
            choices.addRules(SolidSuitNatC.BIDS(ps));
            choices.addRules(
                    properties(new Call[]{Bid._1D, Bid._1H, Bid._1S}, OpenBid2NatC::responderChangedSuits, true),
                    shows(Bid._1D, points(RESPOND_1_LEVEL), shape(5, 10), longestMajor(3)),
                    shows(Bid._1D, points(LIMIT_RAISE_OR_BETTER), shape(5, 10), longerThan(Suit.Hearts), longerThan(Suit.Spades)),
                    shows(Bid._1H, points(RESPOND_1_LEVEL), shape(4), shape(Suit.Spades, 0, 4)),
                    shows(Bid._1H, points(RESPOND_1_LEVEL), shape(5, 10), longerThan(Suit.Spades)),
                    shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), longerOrEqualTo(Suit.Hearts)),
                    propertiesAgreeTrump(new Call[]{Bid._2C, Bid._3C, Bid._4C, Bid._5C}, OpenBid2NatC::responderRaisedMinor, true),
                    shows(Bid._2C, points(RAISE_1), shape(5), longestMajor(3)),
                    shows(Bid._3C, points(LIMIT_RAISE), shape(5), longestMajor(3)),
                    shows(Bid._5C, points(WEAK_5_LEVEL), shape(7, 10)),
                    shows(Bid._4C, points(WEAK_4_LEVEL), shape(6))
            );
        }
        choices.addRules(weakJumpShift(Suit.Clubs));
        choices.addPassRule(points(RESPOND_PASS));
        return choices;
    }

    public static PositionCalls oneDiamond(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(SolidSuitNatC.BIDS(ps));
        choices.addRules(Blackwood.initiateConvention(ps));
        choices.addRules(
                propertiesAgreeTrump(new Call[]{Bid._2D, Bid._3D, Bid._4D, Bid._5D}, OpenBid2NatC::responderRaisedMinor, true),
                properties(new Call[]{Bid._1H, Bid._1S}, OpenBid2NatC::responderChangedSuits, true),
                shows(Bid._2C, points(GAME_OR_BETTER), longestMajor(4)),
                shows(Bid._1H, points(RESPOND_1_LEVEL), shape(4), shape(Suit.Spades, 0, 4)),
                shows(Bid._1H, points(RESPOND_1_LEVEL), shape(5, 10), longerThan(Suit.Spades)),
                shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), longerOrEqualTo(Suit.Hearts)),
                shows(Bid._2D, points(RAISE_1), shape(5), longestMajor(3)),
                shows(Bid._3D, points(LIMIT_RAISE), shape(5), longestMajor(3)),
                shows(Bid._5D, points(WEAK_5_LEVEL), shape(7, 10)),
                shows(Bid._4D, points(WEAK_4_LEVEL), shape(6))
        );
        choices.addRules(weakJumpShift(Suit.Diamonds));
        choices.addPassRule(points(RESPOND_PASS));
        return choices;
    }

    public static PositionCalls oneHeart(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(Blackwood.initiateConvention(ps));
        Call[] raises = new Call[]{Bid._2H, Bid._3H, Bid._4H};
        if (ps.isPassedHand()) {
            choices.addRules(
                    partnerBids(OpenBid2NatC::responderChangedSuits),
                    propertiesAgreeTrump(raises, OpenBid2NatC::responderRaisedMajor, true),
                    shows(Bid._2H, dummyPoints(RAISE_1), shape(3, 5)),
                    shows(Bid._3H, dummyPoints(MEDIUM_HAND), shape(3, 5)),
                    shows(Bid._4H, points(WEAK_4_LEVEL), shape(5, 10)),
                    shows(Bid._1S, shape(4, 10), points(RESPOND_1_LEVEL), shape(Suit.Hearts, 0, 3)),
                    shows(Bid._2C, points(MAX_PASSED), shape(5, 10)),
                    shows(Bid._2D, points(MAX_PASSED), shape(5, 10)),
                    shows(Bid._1NT, points(6, 10), shape(Suit.Hearts, 0, 2), shape(Suit.Spades, 0, 3)),
                    shows(Bid._2NT, points(11, 12), shape(Suit.Hearts, 0, 2), shape(Suit.Spades, 0, 3))
            );
        } else {
            choices.addRules(SolidSuitNatC.BIDS(ps));
            choices.addRules(Jacoby2NT.initiateConvention(ps));
            choices.addRules(
                    partnerBids(OpenBid2NatC::responderChangedSuits),
                    shows(Bid._2C, points(GAME_OR_BETTER), longerThan(Suit.Diamonds), shape(Suit.Spades, 0, 4)),
                    shows(Bid._2C, points(GAME_OR_BETTER), shape(4), longerOrEqual(Suit.Clubs, Suit.Diamonds), shape(Suit.Spades, 0, 4)),
                    shows(Bid._2C, dummyPoints(Suit.Hearts, GAME_OR_BETTER), longerThan(Suit.Diamonds), shape(Suit.Spades, 0, 4)),
                    shows(Bid._2C, dummyPoints(Suit.Hearts, GAME_OR_BETTER), shape(4), longerOrEqual(Suit.Clubs, Suit.Diamonds), shape(Suit.Spades, 0, 4)),
                    shows(Bid._2D, points(GAME_OR_BETTER), longerOrEqual(Suit.Diamonds, Suit.Clubs), shape(Suit.Spades, 0, 4)),
                    shows(Bid._2D, dummyPoints(Suit.Hearts, GAME_OR_BETTER), longerOrEqual(Suit.Diamonds, Suit.Clubs), shape(Suit.Spades, 0, 4)),
                    propertiesAgreeTrump(raises, OpenBid2NatC::responderRaisedMajor, true),
                    shows(Bid._2H, dummyPoints(RAISE_1), shape(3, 5)),
                    shows(Bid._3H, dummyPoints(MEDIUM_HAND), shape(4, 5)),
                    shows(Bid._4H, points(WEAK_4_LEVEL), shape(5, 10)),
                    properties(Bid._1S, true),
                    shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), shape(Suit.Hearts, 0, 3)),
                    properties(Bid._1NT, OpenBid2NatC::semiForcingNT, false, false, false, null, null, null, UserText.SemiForcing, null),
                    shows(Bid._1NT, points(RESPOND_1NT_OVER_MAJOR), shape(Suit.Hearts, 0, 3), shape(Suit.Spades, 0, 3)),
                    shows(Bid._3NT, FLAT, points(RAISE_TO_3NT))
            );
            choices.addRules(weakJumpShift(Suit.Hearts));
        }
        choices.addPassRule(points(RESPOND_PASS));
        return choices;
    }

    public static PositionCalls oneSpade(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(AcesAsk.initiateConvention(ps));
        Call[] raises = new Call[]{Bid._2S, Bid._3S, Bid._4S};
        if (ps.isPassedHand()) {
            choices.addRules(
                    partnerBids(OpenBid2NatC::responderChangedSuits),
                    propertiesAgreeTrump(raises, OpenBid2NatC::responderRaisedMajor, true),
                    propertiesAgreeTrump(new Call[]{Bid._1NT}, OpenBid2NatC::responder1NT, true),
                    propertiesAgreeTrump(new Call[]{Bid._2NT}, OpenBid2NatC::responder2NT, true),
                    shows(Bid._2S, highCardPoints(MINIMUM_HAND), shape(3, 5)),
                    shows(Bid._3S, highCardPoints(11, 11), shape(3, 5)),

                    shows(Bid._3H, highCardPoints(11, 11), shape(5, 10)),
                    shows(Bid._3D, highCardPoints(11, 11), shape(5, 10)),
                    shows(Bid._3C, highCardPoints(11, 11), shape(5, 10)),

                    shows(Bid._2C, highCardPoints(MINIMUM_HAND), shape(5, 10)),
                    shows(Bid._2D, highCardPoints(MINIMUM_HAND), shape(5, 10)),
                    shows(Bid._2H, highCardPoints(MINIMUM_HAND), shape(5, 10)),

                    shows(Bid._2NT, highCardPoints(11, 11), shape(Suit.Spades, 0, 2)),
                    shows(Bid._1NT, highCardPoints(MINIMUM_HAND), shape(Suit.Spades, 0, 2))
            );
        } else {
            choices.addRules(SolidSuitNatC.BIDS(ps));
            choices.addRules(
                    partnerBids(OpenBid2NatC::responderChangedSuits),
                    shows(Bid._2C, points(GAME_OR_BETTER), longerThan(Suit.Diamonds), shape(Suit.Hearts, 0, 4)),
                    shows(Bid._2C, points(GAME_OR_BETTER), shape(4), longerOrEqual(Suit.Clubs, Suit.Diamonds), shape(Suit.Hearts, 0, 4)),
                    shows(Bid._2C, dummyPoints(Suit.Spades, GAME_OR_BETTER), longerThan(Suit.Diamonds), shape(Suit.Hearts, 0, 4)),
                    shows(Bid._2C, dummyPoints(Suit.Spades, GAME_OR_BETTER), shape(4), longerOrEqual(Suit.Clubs, Suit.Diamonds), shape(Suit.Hearts, 0, 4)),
                    shows(Bid._2D, points(GAME_OR_BETTER), longerOrEqual(Suit.Diamonds, Suit.Clubs), shape(Suit.Hearts, 0, 4)),
                    shows(Bid._2D, dummyPoints(Suit.Spades, GAME_OR_BETTER), longerOrEqual(Suit.Diamonds, Suit.Clubs), shape(Suit.Hearts, 0, 4)),
                    shows(Bid._2H, shape(5, 10), points(GAME_OR_BETTER)),
                    propertiesAgreeTrump(raises, OpenBid2NatC::responderRaisedMajor, true),
                    shows(Bid._2S, dummyPoints(RAISE_1), shape(3, 5)),
                    shows(Bid._3S, dummyPoints(MEDIUM_HAND), shape(4, 5)),
                    shows(Bid._4S, points(WEAK_4_LEVEL), shape(5, 10)),
                    properties(Bid._1NT, OpenBid2NatC::semiForcingNT, false, false, false, null, null, null, UserText.SemiForcing, null),
                    shows(Bid._1NT, points(RESPOND_1NT_OVER_MAJOR), shape(Suit.Spades, 0, 3)),
                    shows(Bid._3NT, FLAT, points(RAISE_TO_3NT))
            );
            choices.addRules(weakJumpShift(Suit.Spades));
        }
        choices.addPassRule(points(RESPOND_PASS));
        return choices;
    }

    public static Iterable<CallFeature> weakOpen(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        for (CallFeature cf : AcesAsk.initiateConvention(ps)) bids.add(cf);
        bids.add(shows(Bid._4H, FIT_8_PLUS, ruleOf17()));
        bids.add(shows(Bid._4H, fit(10)));
        bids.add(shows(Bid._4S, FIT_8_PLUS, ruleOf17()));
        bids.add(shows(Bid._4S, fit(10)));
        bids.add(shows(Call.PASS));
        return bids;
    }

    public static Iterable<CallFeature> weakJumpShift(Suit openSuit) {
        List<CallFeature> bids = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            if (suit != openSuit) {
                bids.add(shows(new Bid(2, suit), IS_SINGLE_JUMP, points(WEAK_JUMP_SHIFT_POINTS), shape(6, 10), DECENT_PLUS_SUIT));
                bids.add(shows(new Bid(3, suit), IS_SINGLE_JUMP, points(WEAK_JUMP_SHIFT_POINTS), shape(6, 10), DECENT_PLUS_SUIT));
            }
        }
        return bids;
    }

    private static List<CallFeature> ntResponseToMinor(Suit minor, int level, Range pointRange, PositionCallsFactory partnerBids) {
        Bid bid = new Bid(level, Strain.NoTrump);
        List<CallFeature> rules = new ArrayList<>();
        rules.add(partnerBids(bid, partnerBids));
        BidRule rule = shows(bid, points(pointRange), shape(Suit.Hearts, 0, 3), shape(Suit.Spades, 0, 3));
        if (minor == Suit.Clubs) rule.addConstraint(shape(Suit.Diamonds, 0, 4));
        rules.add(rule);
        return rules;
    }
}
