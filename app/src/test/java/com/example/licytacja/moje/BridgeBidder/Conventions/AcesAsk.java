package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.Bid;
import com.example.licytacja.moje.BridgeBidder.Bidder;
import com.example.licytacja.moje.BridgeBidder.Call;
import com.example.licytacja.moje.BridgeBidder.CallFeature;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import com.example.licytacja.moje.BridgeBidder.PositionCalls;
import com.example.licytacja.moje.BridgeBidder.PositionState;
import com.example.licytacja.moje.BridgeBidder.Range;
import com.example.licytacja.moje.BridgeBidder.Suit;

import java.util.ArrayList;
import java.util.List;

public class AcesAsk extends Bidder {
    private static final Range SLAM_OR_BETTER = new Range(28, 100);
    private static final Range GRAND_SLAM = new Range(36, 100);

    public static Iterable<CallFeature> initiateConvention(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        //Suit suit = getAgreedSuit(ps);
        //if (suit != null) {
        bids.add(properties(Bid._4C, AcesAsk::respondKeyCards, true, UserText.AcesAsc));
        bids.add(shows(Bid._4C, fit(ps.getPairState().getLastShownSuit())));
        //}
        return bids;
    }

    private static Suit getAgreedSuit(PositionState ps) {
        Suit trump = ps.getPairState().getTrumpSuit();
        if (trump != null) return trump;
        return ps.getPairState().getLastShownSuit();
    }

    public static PositionCalls respondKeyCards(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Suit suit = getAgreedSuit(ps);
        if (suit != null) {
            choices.addRules(
                properties(new Call[]{Bid._5C, Bid._5D, Bid._5H, Bid._5S}, AcesAsk::placeContract, true),
                shows(Bid._5C, keyCards(suit, 1, 4)),
                shows(Bid._5D, keyCards(suit, 0, 3)),
                shows(Bid._5H, keyCards(suit, false, 2, 5)),
                shows(Bid._5S, keyCards(suit, true, 2, 5))
            );
            return choices;
        }
        throw new RuntimeException("This should never happen. No agreed suit.");
    }

    public static PositionCalls placeContract(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Suit suit = getAgreedSuit(ps);
        if (suit != null) {
            choices.addRules(
                properties(Bid._5NT, AcesAsk::respondKings, true),
                shows(Bid._5NT, pairKeyCards(suit, true, 5), pairPoints(GRAND_SLAM)),
                shows(Bid._5NT, pairKeyCards(suit, true, 5), pairPoints(SLAM_OR_BETTER), fit(9, suit)),

                shows(new Bid(6, suit), pairPoints(SLAM_OR_BETTER), pairKeyCards(suit, null, 4, 5)),

                shows(Call.PASS, CONTRACT_IS_AGREED_STRAIN, pairKeyCards(suit, null, 0, 1, 2, 3)),

                shows(new Bid(5, suit), pairKeyCards(suit, null, 0, 1, 2, 3)),
                shows(new Bid(6, suit), IS_NON_JUMP, pairKeyCards(suit, null, 0, 1, 2, 3))
            );
            return choices;
        }
        throw new RuntimeException("This should not happen");
    }

    public static PositionCalls respondKings(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
            properties(new Call[]{Bid._6C, Bid._6D, Bid._6H, Bid._6S}, AcesAsk::tryGrandSlam, true),
            shows(Bid._6C, kings(0, 4)),
            shows(Bid._6D, kings(1)),
            shows(Bid._6H, kings(2)),
            shows(Bid._6S, kings(3))
        );
        return choices;
    }

    public static PositionCalls tryGrandSlam(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Suit suit = getAgreedSuit(ps);
        if (suit != null) {
            choices.addRules(
                shows(new Bid(7, suit), pairKeyCards(suit, true, 5), pairKings(4)),
                shows(Call.PASS, CONTRACT_IS_AGREED_STRAIN),
                shows(new Bid(6, suit)),
                shows(new Bid(7, suit))
            );
            return choices;
        }
        throw new RuntimeException("This should not happen");
    }
}
