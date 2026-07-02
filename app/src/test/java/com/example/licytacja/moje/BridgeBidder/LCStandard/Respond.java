package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.NegativeDouble;
import com.example.licytacja.moje.BridgeBidder.Conventions.TakeoutDouble;
import java.util.ArrayList;
import java.util.List;

public class Respond extends LCStandard {
    protected static final Range RESPOND_PASS = new Range(0, 5);
    protected static final Range RESPOND_1_LEVEL = new Range(6, 40);
    protected static final Range RAISE_1 = new Range(6, 10);
    protected static final Range LIMIT_RAISE = new Range(11, 12);
    protected static final Range NEW_SUIT_2_LEVEL = new Range(13, 40);
    protected static final Range RESPOND_1NT_OVER_MAJOR = new Range(6, 12);

    public static PositionCalls oneClub(PositionState ps) {
        if (!ps.getRHO().isPassed()) {
            return oppsInterferred(ps, Suit.Clubs);
        }

        PositionCalls choices = new PositionCalls(ps);
        if (ps.isPassedHand()) {
            choices.addRules(partnerBids(OpenBid2::responderChangedSuits));
            choices.addRules(shows(Bid._1D, points(6, 40), shape(5, 10), longestMajor(3)));
            choices.addRules(shows(Bid._1H, points(6, 40), shape(4)));
            choices.addRules(shows(Bid._1S, points(6, 40), shape(4, 10)));
        } else {
            choices.addRules(SolidSuit.BIDS(ps));
            choices.addRules(properties(new Call[]{Bid._1D, Bid._1H, Bid._1S}, OpenBid2::responderChangedSuits, true));
            choices.addRules(shows(Bid._1D, points(6, 40), shape(5, 10), longestMajor(3)));
            choices.addRules(shows(Bid._1H, points(6, 40), shape(4)));
            choices.addRules(shows(Bid._1S, points(6, 40), shape(4, 10)));
        }
        choices.addPassRule(points(0, 5));
        return choices;
    }

    public static PositionCalls oneDiamond(PositionState ps) {
        if (!ps.getRHO().isPassed()) {
            return oppsInterferred(ps, Suit.Diamonds);
        }
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(SolidSuit.BIDS(ps));
        choices.addRules(properties(new Call[]{Bid._1H, Bid._1S}, OpenBid2::responderChangedSuits, true));
        choices.addRules(shows(Bid._1H, points(6, 40), shape(4)));
        choices.addRules(shows(Bid._1S, points(6, 40), shape(4)));
        choices.addPassRule(points(0, 5));
        return choices;
    }

    public static PositionCalls oneHeart(PositionState ps) {
        if (!ps.getRHO().isPassed()) {
            return oppsInterferred(ps, Suit.Hearts);
        }
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(SolidSuit.BIDS(ps));
        // choices.addRules(Jacoby2NT.initiateConvention(ps));
        choices.addRules(Bidder.properties(Bid._1S, OpenBid2::responderChangedSuits, true));
        choices.addRules(shows(Bid._1S, points(6, 40), shape(4)));
        choices.addPassRule(points(0, 5));
        return choices;
    }

    public static PositionCalls oneSpade(PositionState ps) {
        if (!ps.getRHO().isPassed()) {
            return oppsInterferred(ps, Suit.Spades);
        }
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(SolidSuit.BIDS(ps));
        // choices.addRules(Jacoby2NT.initiateConvention(ps));
        choices.addRules(Bidder.properties(Bid._1NT, OpenBid2::semiForcingNT, true));
        choices.addPassRule(points(0, 5));
        return choices;
    }

    private static PositionCalls oppsInterferred(PositionState ps, Suit openSuit) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(NegativeDouble.initiateConvention(ps));
        // Add more interference logic
        return choices;
    }

    public static Iterable<CallFeature> weakJumpShift(Suit openSuit) {
        List<CallFeature> bids = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            if (suit != openSuit) {
                bids.add(shows(new Bid(2, suit), IS_SINGLE_JUMP, points(0, 5), shape(6, 10)));
                bids.add(shows(new Bid(3, suit), IS_SINGLE_JUMP, points(0, 5), shape(6, 10)));
            }
        }
        return bids;
    }
}
