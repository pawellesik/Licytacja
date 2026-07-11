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
    public static final Range RESPOND_PASS = new Range(0, 6);
    public static final Range MINIMUM_HAND = new Range(7, 10);
    public static final Range JUMP_HAND = new Range(11, 28);
    public static final Range JUMP_AFTER_PASS = new Range(11, 11);
    public static final Range WEAK = new Range(7, 11);


    public static PositionCalls oneClub(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        if (ps.isPassedHand()) {
            choices.addRules(
                    //partnerBids(OpenBid2NatC::responderChangedSuits),
                    //shows(Bid._1D, points(RESPOND_1_LEVEL), shape(5, 10), longestMajor(3)),
                    //shows(Bid._1H, points(RESPOND_1_LEVEL), shape(4), shape(Suit.Spades, 0, 4)),
                    //shows(Bid._1H, points(RESPOND_1_LEVEL), shape(5, 10), longerThan(Suit.Spades)),
                    //shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), longerOrEqualTo(Suit.Hearts)),
                    //propertiesAgreeTrump(new Call[]{Bid._2C, Bid._3C, Bid._4C, Bid._5C}, OpenBid2NatC::responderRaisedMinor, true),
                    //shows(Bid._2C, points(RAISE_1), shape(5), longestMajor(3)),
                    //shows(Bid._3C, points(LIMIT_RAISE), shape(5), longestMajor(3)),
                    //shows(Bid._5C, points(WEAK_5_LEVEL), shape(7, 10)),
                    //shows(Bid._4C, points(WEAK_4_LEVEL), shape(6))
            );
        } else {
            choices.addRules(
                    //properties(new Call[]{Bid._1D, Bid._1H, Bid._1S}, OpenBid2NatC::responderChangedSuits, true),
                    //shows(Bid._1D, points(RESPOND_1_LEVEL), shape(5, 10), longestMajor(3)),
                    //shows(Bid._1D, points(LIMIT_RAISE_OR_BETTER), shape(5, 10), longerThan(Suit.Hearts), longerThan(Suit.Spades)),
                    //shows(Bid._1H, points(RESPOND_1_LEVEL), shape(4), shape(Suit.Spades, 0, 4)),
                    //shows(Bid._1H, points(RESPOND_1_LEVEL), shape(5, 10), longerThan(Suit.Spades)),
                    //shows(Bid._1S, points(RESPOND_1_LEVEL), shape(4, 10), longerOrEqualTo(Suit.Hearts)),
                    //propertiesAgreeTrump(new Call[]{Bid._2C, Bid._3C, Bid._4C, Bid._5C}, OpenBid2NatC::responderRaisedMinor, true),
                    //shows(Bid._2C, points(RAISE_1), shape(5), longestMajor(3)),
                    //shows(Bid._3C, points(LIMIT_RAISE), shape(5), longestMajor(3)),
                    //shows(Bid._5C, points(WEAK_5_LEVEL), shape(7, 10)),
                    //shows(Bid._4C, points(WEAK_4_LEVEL), shape(6))
            );
        }
        choices.addPassRule(points(RESPOND_PASS));
        return choices;
    }

    public static PositionCalls oneDiamond(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        if (ps.isPassedHand()) {
            choices.addRules(
                    properties(new Call[]{Bid._1H, Bid._1S, Bid._2H, Bid._2S}, OpenBid2NatC::responderChangedSuits, true),
                    shows(Bid._1S, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _1S")),
                    shows(Bid._1H, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _1H")),

                    shows(Bid._2S, highCardPoints(JUMP_AFTER_PASS), shape(5, 10), id("RespondNatC.oneSpade _2S")),
                    shows(Bid._2H, highCardPoints(JUMP_AFTER_PASS), shape(5, 10), id("RespondNatC.oneSpade _2H")),

                    shows(Bid._3S, highCardPoints(WEAK), shape(7, 10), id("RespondNatC.oneSpade _3S")),
                    shows(Bid._3H, highCardPoints(WEAK), shape(7, 10), id("RespondNatC.oneSpade _3H")),

                    shows(Bid._2C, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2C")),
                    shows(Bid._2D, highCardPoints(MINIMUM_HAND), fit(), id("RespondNatC.oneSpade _2D")),

                    shows(Bid._3C, highCardPoints(JUMP_AFTER_PASS), shape(5, 10), id("RespondNatC.oneSpade _3C")),
                    shows(Bid._3D, highCardPoints(JUMP_AFTER_PASS), fit(), id("RespondNatC.oneSpade _3D")),

                    shows(Bid._2NT, highCardPoints(JUMP_AFTER_PASS), shape(Suit.Diamonds, 0, 2), id("RespondNatC.oneSpade _2NT")),
                    shows(Bid._1NT, highCardPoints(MINIMUM_HAND), shape(Suit.Diamonds, 0, 2), id("RespondNatC.oneSpade _1NT"))

            );
        } else {
            choices.addRules(
                    properties(new Call[]{Bid._1H, Bid._1S, Bid._2H, Bid._2S}, OpenBid2NatC::responderChangedSuits, true),
                    properties(new Call[]{Bid._3D}, OpenBid2NatC::responderRaisedMinor, true),

                    shows(Bid._1S, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _1S")),
                    shows(Bid._1H, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _1H")),

                    shows(Bid._2S, highCardPoints(JUMP_HAND), shape(5, 10), id("RespondNatC.oneSpade _2S")),
                    shows(Bid._2H, highCardPoints(JUMP_HAND), shape(5, 10), id("RespondNatC.oneSpade _2H")),

                    shows(Bid._3S, highCardPoints(WEAK), shape(7, 10), id("RespondNatC.oneSpade _3S")),
                    shows(Bid._3H, highCardPoints(WEAK), shape(7, 10), id("RespondNatC.oneSpade _3H")),

                    shows(Bid._5D, highCardPoints(PAIR_WEAK_GAME), fit(), id("RespondNatC.oneSpade _5D")),

                    shows(Bid._2C, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2C")),
                    shows(Bid._2D, highCardPoints(MINIMUM_HAND), fit(), id("RespondNatC.oneSpade _2D")),

                    shows(Bid._3C, highCardPoints(JUMP_HAND), shape(5, 10), id("RespondNatC.oneSpade _3C")),
                    shows(Bid._3D, highCardPoints(JUMP_HAND), fit(), id("RespondNatC.oneSpade _3D")),

                    shows(Bid._3NT, BALANCED, pairHighCardPoints(PAIR_GAME), id("RespondNatC.oneSpade _3NT")),
                    shows(Bid._2NT, highCardPoints(JUMP_HAND), shape(Suit.Diamonds, 0, 2), id("RespondNatC.oneSpade _2NT")),
                    shows(Bid._1NT, highCardPoints(MINIMUM_HAND), shape(Suit.Diamonds, 0, 2), id("RespondNatC.oneSpade _1NT"))
            );
        }
        choices.addPassRule(points(RESPOND_PASS));
        choices.addRules(CompeteNatC::compBids);
        return choices;
    }

    public static PositionCalls oneHeart(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Call[] raises = new Call[]{Bid._2H, Bid._3H, Bid._4H};
        if (ps.isPassedHand()) {
            choices.addRules(
                    partnerBids(OpenBid2NatC::responderChangedSuits),
                    propertiesAgreeTrump(raises, OpenBid2NatC::responderRaisedMajor, true),
                    propertiesAgreeTrump(new Call[]{Bid._1NT}, OpenBid2NatC::responder1NT, true),
                    propertiesAgreeTrump(new Call[]{Bid._2NT}, OpenBid2NatC::responder2NT, true),

                    shows(Bid._2H, highCardPoints(MINIMUM_HAND), fit(), id("RespondNatC.oneSpade _2H")),
                    shows(Bid._3H, highCardPoints(JUMP_AFTER_PASS), fit(), id("RespondNatC.oneSpade _3H")),

                    shows(Bid._2S, highCardPoints(JUMP_AFTER_PASS), shape(5, 10), id("RespondNatC.oneSpade _2S")),
                    shows(Bid._3D, highCardPoints(JUMP_AFTER_PASS), shape(5, 10), id("RespondNatC.oneSpade _3D")),
                    shows(Bid._3C, highCardPoints(JUMP_AFTER_PASS), shape(5, 10), id("RespondNatC.oneSpade _3C")),

                    shows(Bid._1S, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _1S")),
                    shows(Bid._2C, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2C")),
                    shows(Bid._2D, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2D")),

                    shows(Bid._2NT, highCardPoints(JUMP_AFTER_PASS), shape(Suit.Spades, 0, 2), id("RespondNatC.oneSpade _2NT")),
                    shows(Bid._1NT, highCardPoints(MINIMUM_HAND), shape(Suit.Spades, 0, 2), id("RespondNatC.oneSpade _1NT"))
            );
        } else {
            choices.addRules(
                    partnerBids(OpenBid2NatC::responderChangedSuits),
                    propertiesAgreeTrump(raises, OpenBid2NatC::responderRaisedMajor, true),

                    shows(Bid._2H, highCardPoints(MINIMUM_HAND), fit(), id("RespondNatC.oneSpade _2H")),
                    shows(Bid._2S, highCardPoints(MINIMUM_HAND), shape(Suit.Hearts, 0, 2), shape(5, 10), id("RespondNatC.oneSpade _2S")),

                    shows(Bid._3H, highCardPoints(JUMP_HAND), fit(), id("RespondNatC.oneSpade _3H")),
                    shows(Bid._2S, highCardPoints(JUMP_HAND), shape(Suit.Hearts, 0, 2), shape(5, 10), id("RespondNatC.oneSpade _2S")),

                    shows(Bid._4S, highCardPoints(PAIR_GAME), fit(), id("RespondNatC.oneSpade _4S")),
                    shows(Bid._4H, highCardPoints(PAIR_GAME), fit(), id("RespondNatC.oneSpade _4H")),

                    shows(Bid._2C, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2C")),
                    shows(Bid._3C, highCardPoints(JUMP_HAND), shape(5, 10), id("RespondNatC.oneSpade _3C")),

                    shows(Bid._2D, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2D")),
                    shows(Bid._3D, highCardPoints(JUMP_HAND), shape(5, 10), id("RespondNatC.oneSpade _3D")),

                    shows(Bid._1NT, shape(Suit.Hearts, 0, 2), pairHighCardPoints(MINIMUM_HAND)),
                    shows(Bid._3NT, BALANCED, pairHighCardPoints(PAIR_GAME), id("RespondNatC.oneSpade _3NT"))
            );
        }
        choices.addPassRule(points(RESPOND_PASS), id("RespondNatC.oneHeartvvvv _PASS"));
        choices.addRules(CompeteNatC::compBids);
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

                    shows(Bid._2S, highCardPoints(MINIMUM_HAND), fit(), id("RespondNatC.oneSpade _2S")),
                    shows(Bid._3S, highCardPoints(JUMP_AFTER_PASS), fit(), id("RespondNatC.oneSpade _3S")),

                    shows(Bid._3H, highCardPoints(JUMP_AFTER_PASS), shape(5, 10), id("RespondNatC.oneSpade _3H")),
                    shows(Bid._3D, highCardPoints(JUMP_AFTER_PASS), shape(5, 10), id("RespondNatC.oneSpade _3D")),
                    shows(Bid._3C, highCardPoints(JUMP_AFTER_PASS), shape(5, 10), id("RespondNatC.oneSpade _3C")),

                    shows(Bid._2H, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2H")),
                    shows(Bid._2C, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2C")),
                    shows(Bid._2D, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2D")),

                    shows(Bid._2NT, highCardPoints(JUMP_AFTER_PASS), shape(Suit.Hearts, 0, 2), id("RespondNatC.oneSpade _2NT")),
                    shows(Bid._1NT, highCardPoints(MINIMUM_HAND), shape(Suit.Hearts, 0, 2), id("RespondNatC.oneSpade _1NT"))
            );
        } else {
            choices.addRules(SolidSuitNatC.BIDS(ps));
            choices.addRules(
                    partnerBids(OpenBid2NatC::responderChangedSuits),
                    propertiesAgreeTrump(raises, OpenBid2NatC::responderRaisedMajor, true),

                    shows(Bid._2S, highCardPoints(MINIMUM_HAND), fit(), id("RespondNatC.oneSpade _2S")),
                    shows(Bid._2H, highCardPoints(MINIMUM_HAND), shape(Suit.Spades, 0, 2), shape(5, 10), id("RespondNatC.oneSpade _2H")),

                    shows(Bid._3S, highCardPoints(JUMP_HAND), fit(), id("RespondNatC.oneSpade _3S")),
                    shows(Bid._3H, highCardPoints(JUMP_HAND), shape(Suit.Spades, 0, 2), shape(5, 10), id("RespondNatC.oneSpade _3H")),

                    shows(Bid._4S, highCardPoints(PAIR_GAME), fit(), id("RespondNatC.oneSpade _4S")),
                    shows(Bid._4H, highCardPoints(PAIR_GAME), fit(), id("RespondNatC.oneSpade _4H")),

                    shows(Bid._2C, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2C")),
                    shows(Bid._3C, highCardPoints(JUMP_HAND), shape(5, 10), id("RespondNatC.oneSpade _3C")),

                    shows(Bid._2D, highCardPoints(MINIMUM_HAND), shape(5, 10), id("RespondNatC.oneSpade _2D")),
                    shows(Bid._3D, highCardPoints(JUMP_HAND), shape(5, 10), id("RespondNatC.oneSpade _3D")),

                    shows(Bid._1NT, shape(Suit.Spades, 0, 2), pairHighCardPoints(MINIMUM_HAND)),
                    shows(Bid._3NT, BALANCED, pairHighCardPoints(PAIR_GAME), id("RespondNatC.oneSpade _3NT"))
            );
        }
        choices.addPassRule(points(RESPOND_PASS), id("RespondNatC.oneSpade _PASS"));
        choices.addRules(CompeteNatC::compBids);
        return choices;
    }

    public static Iterable<CallFeature> weakOpen(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        //for (CallFeature cf : AcesAsk.initiateConvention(ps)) bids.add(cf);
        //bids.add(shows(Bid._4H, FIT_8_PLUS, ruleOf17()));
        //bids.add(shows(Bid._4H, fit(10)));
        //bids.add(shows(Bid._4S, FIT_8_PLUS, ruleOf17()));
        //bids.add(shows(Bid._4S, fit(10)));
        //bids.add(shows(Call.PASS));
        return bids;
    }

}
