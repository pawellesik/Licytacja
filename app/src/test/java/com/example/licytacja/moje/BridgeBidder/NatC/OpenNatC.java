package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.Bid;
import com.example.licytacja.moje.BridgeBidder.Call;
import com.example.licytacja.moje.BridgeBidder.CallFeature;
import com.example.licytacja.moje.BridgeBidder.Constraint;
import com.example.licytacja.moje.BridgeBidder.Conventions.Strong2Clubs;
import com.example.licytacja.moje.BridgeBidder.HandConstraint;
import com.example.licytacja.moje.BridgeBidder.LCStandard.NoTrump;
import com.example.licytacja.moje.BridgeBidder.NatC.RespondNatC;
import com.example.licytacja.moje.BridgeBidder.LCStandard.SolidSuit;
import com.example.licytacja.moje.BridgeBidder.PositionCalls;
import com.example.licytacja.moje.BridgeBidder.PositionState;
import com.example.licytacja.moje.BridgeBidder.Range;
import com.example.licytacja.moje.BridgeBidder.Suit;

import java.util.ArrayList;
import java.util.List;

public class OpenNatC extends NatC {

    public static final HandConstraint OneLevel = highCardPoints(12, 17);
    public static final HandConstraint Strong = highCardPoints(18, 40);
    public static final HandConstraint Weak = highCardPoints(7, 11);
    public static final HandConstraint DontOpen = highCardPoints(0, 11);

    public static final Range LessThanJumpShift = new Range(12, 18);
    public static final Range JumpShift = new Range(19, 21);

    public static PositionCalls getOpenPositionCalls(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);

        choices.addRules(SolidSuit.BIDS(ps));
        //choices.addRules(NoTrump.open(ps));
        choices.addRules(openSuitWeak(ps));
        choices.addRules(openSuit(ps));

        return choices;
    }

    public static Iterable<CallFeature> openSuit(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();

        bids.add(properties(Bid._1C, true));

        bids.add(partnerBids(Bid._1C, RespondNatC::oneClub));
        bids.add(partnerBids(Bid._1D, RespondNatC::oneDiamond));
        bids.add(partnerBids(Bid._1H, RespondNatC::oneHeart));
        bids.add(partnerBids(Bid._1S, RespondNatC::oneSpade));

        bids.add(shows(Bid._1C, Strong));

        bids.add(shows(Bid._1H, OneLevel, shape(5, 8), betterThan(Suit.Spades)));
        bids.add(shows(Bid._1S, OneLevel, shape(5, 8), longerOrEqual(Suit.Spades, Suit.Hearts)));

        bids.add(shows(Bid._1D, OneLevel, shape(5, 10)));

        bids.add(shows(Bid._1C, OneLevel, note("Otwarcie naturalne z piątką")));

        if (ps.getSeat() == 3) {
            bids.addAll(thirdSeatWeak(and(IS_VUL, NOT_BALANCED, points(11, 11))));
            bids.addAll(thirdSeatWeak(and(IS_NOT_VUL, BALANCED, DECENT_PLUS_SUIT, points(11, 11))));
            bids.addAll(thirdSeatWeak(and(IS_NOT_VUL, NOT_BALANCED, points(10, 11))));
        }

        bids.add(shows(Call.PASS));
        return bids;
    }

    private static List<CallFeature> thirdSeat4CardMajor(Constraint range) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(shows(Bid._1S, range, GOOD_PLUS_SUIT, shape(4), betterOrEqualTo(Suit.Hearts)));
        bids.add(shows(Bid._1H, range, GOOD_PLUS_SUIT, shape(4), betterThan(Suit.Spades)));
        return bids;
    }

    private static List<CallFeature> thirdSeatWeak(Constraint range) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(shows(Bid._1C, range, LONGEST_SUIT, shape(Suit.Hearts, 0, 4)));
        bids.add(shows(Bid._1C, range, shape(4, 11), longerThan(Suit.Diamonds), longestMajor(4)));
        bids.add(shows(Bid._1D, range, LONGEST_SUIT, shape(Suit.Hearts, 0, 4)));
        bids.add(shows(Bid._1D, range, shape(4, 10), longerOrEqual(Suit.Diamonds, Suit.Clubs), longestMajor(4)));
        bids.add(shows(Bid._1H, range, shape(5, 10), longerThan(Suit.Spades)));
        bids.add(shows(Bid._1S, range, shape(5, 10), longerOrEqual(Suit.Spades, Suit.Hearts)));
        return bids;
    }

    private static List<CallFeature> openSuitWeak(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(partnerBids(RespondNatC::weakOpen));

        bids.add(shows(Bid._3S, Weak, shape(7, 10)));
        bids.add(shows(Bid._3H, Weak, shape(7, 10)));
        bids.add(shows(Bid._3D, Weak, shape(7, 10)));
        bids.add(shows(Bid._3C, Weak, shape(7, 10)));

        return bids;
    }

}
