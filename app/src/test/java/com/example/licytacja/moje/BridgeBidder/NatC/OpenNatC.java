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

    public static final HandConstraint OneLevel = points(12, 17);
    public static final HandConstraint Strong = points(18, 40);
    public static final HandConstraint CantJumpShift = points(12, 18);
    public static final HandConstraint DummyMinimum = dummyPoints(12, 16);
    public static final HandConstraint Medium = points(17, 18);
    public static final HandConstraint DummyMedium = dummyPoints(17, 18);
    public static final HandConstraint Maximum = points(19, 21);
    public static final HandConstraint DummyMaximum = dummyPoints(19, 26);
    public static final HandConstraint MediumOrBetter = points(17, 21);

    public static final HandConstraint Weak = points(5, 11);
    public static final HandConstraint VeryWeak = points(3, 11);
    public static final HandConstraint DontOpen = points(0, 11);

    public static final Range Rebid1NT = new Range(12, 15);
    public static final Range Rebid2NT = new Range(18, 20);

    public static final Range LessThanJumpShift = new Range(12, 18);
    public static final Range JumpShift = new Range(19, 21);

    public static PositionCalls getOpenPositionCalls(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);

        //choices.addRules(SolidSuit.BIDS(ps));
        //choices.addRules(Strong2Clubs.open(ps));
        //choices.addRules(NoTrump.open(ps));
        //choices.addRules(openSuitWeak(ps));
        choices.addRules(openSuit(ps));

        return choices;
    }

    public static Iterable<CallFeature> openSuit(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(partnerBids(Bid._1C, RespondNatC::oneClub));
        bids.add(partnerBids(Bid._1D, RespondNatC::oneDiamond));
        bids.add(partnerBids(Bid._1H, RespondNatC::oneHeart));
        bids.add(partnerBids(Bid._1S, RespondNatC::oneSpade));

        bids.add(shows(Bid._1C, Strong));
        bids.add(shows(Bid._1H, OneLevel, shape(5, 10), betterThan(Suit.Spades)));
        bids.add(shows(Bid._1S, OneLevel, shape(5, 10), longerOrEqual(Suit.Spades, Suit.Hearts)));


        bids.add(shows(Bid._1C, OneLevel, note("Otwarcie naturalne z piątką")));
        //note("Otwarcie naturalne z piątką")
        if (ps.getSeat() == 3) {
            bids.addAll(thirdSeatWeak(and(IS_VUL, NOT_BALANCED, points(11, 11))));
            bids.addAll(thirdSeatWeak(and(IS_NOT_VUL, BALANCED, DECENT_PLUS_SUIT, points(11, 11))));
            bids.addAll(thirdSeatWeak(and(IS_NOT_VUL, NOT_BALANCED, points(10, 11))));
        }

        bids.add(shows(Call.PASS, isSeat(4), DontOpen));
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
        List<CallFeature> rules = new ArrayList<>();
        rules.add(partnerBids(RespondNatC::weakOpen));
        switch (ps.getSeat()) {
            case 1:
                addWeakRules(rules, and(IS_FAV_VUL, points(4, 11)));
                addWeakRules(rules, and(IS_FAV_VUL, points(8, 11), shape(5), EXCELLENT_PLUS_SUIT), 2);
                addWeakRules(rules, and(BOTH_NOT_VUL, points(5, 11), DECENT_PLUS_SUIT));
                addWeakRules(rules, and(IS_VUL, points(7, 11), GOOD_PLUS_SUIT));
                addWeakBid(rules, Bid._3C, and(IS_NOT_VUL, shape(6), points(5, 11), GOOD_PLUS_SUIT));
                addWeakBid(rules, Bid._3C, and(IS_VUL, shape(6), points(7, 11), GOOD_PLUS_SUIT));
                break;
            case 2:
                addWeakRules(rules, and(IS_NOT_VUL, points(6, 11), DECENT_PLUS_SUIT));
                addWeakRules(rules, and(IS_VUL, points(8, 11), GOOD_PLUS_SUIT));
                addWeakBid(rules, Bid._3C, and(IS_NOT_VUL, shape(6), points(6, 11), GOOD_PLUS_SUIT));
                addWeakBid(rules, Bid._3C, and(IS_VUL, shape(6), points(8, 11), EXCELLENT_PLUS_SUIT));
                break;
            case 3:
                addWeakRules(rules, and(IS_FAV_VUL, points(2, 13)));
                addWeakRules(rules, and(IS_FAV_VUL, points(2, 13), shape(5), GOOD_PLUS_SUIT), 2);
                addWeakRules(rules, and(BOTH_NOT_VUL, points(4, 13), DECENT_PLUS_SUIT));
                addWeakRules(rules, and(BOTH_NOT_VUL, points(4, 13), shape(5), EXCELLENT_PLUS_SUIT), 2);
                addWeakRules(rules, and(IS_VUL, points(6, 13), GOOD_PLUS_SUIT));
                addWeakBid(rules, Bid._3C, and(IS_FAV_VUL, shape(6), points(2, 13)));
                addWeakBid(rules, Bid._3C, and(BOTH_NOT_VUL, shape(6), points(4, 13), DECENT_PLUS_SUIT));
                addWeakBid(rules, Bid._3C, and(IS_VUL, shape(6), points(6, 13), GOOD_PLUS_SUIT));
                break;
            case 4:
                addWeakRules(rules, and(points(10, 15), DECENT_PLUS_SUIT));
                addWeakBid(rules, Bid._3C, and(shape(6), points(10, 15), EXCELLENT_PLUS_SUIT));
                break;
        }
        return rules;
    }

    public static void addWeakRules(List<CallFeature> rules, Constraint constraint) {
        addWeakRules(rules, constraint, 0);
    }

    public static void addWeakRules(List<CallFeature> rules, Constraint constraint, int onlyLevel) {
        int minLevel = onlyLevel == 0 ? 2 : onlyLevel;
        int maxLevel = onlyLevel == 0 ? 4 : onlyLevel;
        for (int lv = minLevel; lv <= maxLevel; lv++) {
            Constraint levelConstraint = constraint;
            if (onlyLevel == 0) {
                levelConstraint = and(constraint, shape(lv + 4));
            }
            for (Suit suit : Suit.values()) {
                Bid bid = new Bid(lv, suit);
                if (!bid.equals(Bid._2C)) {
                    addWeakBid(rules, bid, levelConstraint);
                }
            }
        }
    }

    private static void addWeakBid(List<CallFeature> rules, Bid bid, Constraint constraint) {
        if (bid.getSuit() != Suit.Hearts && bid.getSuit() != Suit.Spades) {
            rules.add(shows(bid, constraint, shape(Suit.Hearts, 0, 3), shape(Suit.Spades, 0, 3)));
        }
        if (bid.getSuit() == Suit.Hearts) {
            rules.add(shows(bid, constraint, shape(Suit.Spades, 0, 3)));
        } else {
            rules.add(shows(bid, constraint, shape(Suit.Hearts, 4, 5), showsBadSuit(Suit.Hearts)));
        }
        if (bid.getSuit() == Suit.Spades) {
            rules.add(shows(bid, constraint, shape(Suit.Hearts, 0, 3)));
        } else {
            rules.add(shows(bid, constraint, shape(Suit.Spades, 4, 5), showsBadSuit(Suit.Spades)));
        }
    }
}
