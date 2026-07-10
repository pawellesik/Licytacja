package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.*;

import java.util.ArrayList;
import java.util.List;

public class OpenNatC extends NatC {

    public static final HandConstraint FirstOpen = highCardPoints(12, 17);
    public static final HandConstraint Strong = highCardPoints(18, 40);
    public static final HandConstraint Weak = highCardPoints(7, 11);
    public static final HandConstraint DontOpen = highCardPoints(0, 11);

    public static final HandConstraint CantJumpShift = highCardPoints(12, 18);
    public static final HandConstraint DummyMinimum = dummyPoints(12, 16);
    public static final HandConstraint Medium = highCardPoints(17, 18);
    public static final HandConstraint DummyMedium = dummyPoints(17, 18);
    public static final HandConstraint Maximum = highCardPoints(19, 21);
    public static final HandConstraint DummyMaximum = dummyPoints(19, 26);
    public static final HandConstraint MediumOrBetter = highCardPoints(17, 21);

    public static final Range Rebid1NT = new Range(12, 15);
    public static final Range Rebid2NT = new Range(18, 20);

    public static final Range LessThanJumpShift = new Range(12, 18);
    public static final Range JumpShift = new Range(19, 21);

    public static PositionCalls getOpenPositionCalls(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);

        choices.addRules(SolidSuitNatC.BIDS(ps));
        //choices.addRules(NoTrumpNatC.open(ps));
        choices.addRules(openSuitWeak(ps));
        choices.addRules(openSuit(ps));

        choices.addPassRule(DontOpen);

        return choices;
    }

    public static Iterable<CallFeature> openSuit(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(partnerBids(Bid._1C, RespondNatC::oneClub));
        bids.add(partnerBids(Bid._1D, RespondNatC::oneDiamond));
        bids.add(partnerBids(Bid._1H, RespondNatC::oneHeart));
        bids.add(partnerBids(Bid._1S, RespondNatC::oneSpade));

        bids.add(shows(Bid._1C, Strong));

        bids.add(shows(Bid._1H, FirstOpen, shape(5, 8), betterThan(Suit.Spades)));
        bids.add(shows(Bid._1S, FirstOpen, shape(5, 8), longerOrEqual(Suit.Spades, Suit.Hearts)));
        bids.add(shows(Bid._1D, FirstOpen, shape(5, 10)));
        bids.add(shows(Bid._1C, FirstOpen, note("Otwarcie naturalne")));

        bids.add(shows(Call.PASS, isSeat(4), DontOpen));
        return bids;
    }

    private static List<CallFeature> openSuitWeak(PositionState ps) {
        List<CallFeature> rules = new ArrayList<>();
        rules.add(partnerBids(RespondNatC::weakOpen));
        rules.add(shows(Bid._3C, Weak, shape(7, 11)));
        rules.add(shows(Bid._3D, Weak, shape(7, 11)));
        rules.add(shows(Bid._3H, Weak, shape(7, 11)));
        rules.add(shows(Bid._3S, Weak, shape(7, 11)));
        return rules;
    }

}
