package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import java.util.ArrayList;
import java.util.List;

public class Blackwood extends Bidder {
    public static Iterable<CallFeature> initiateConvention(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        Suit suit = getAgreedSuit(ps);
        if (suit != null) {
            bids.add(properties(Bid._4NT, Blackwood::respondKeyCards, true, UserText.Blackwood));
            bids.add(shows(Bid._4NT, pairPoints(suit, 32, 100)));
        }
        return bids;
    }

    private static Suit getAgreedSuit(PositionState ps) {
        Suit trump = ps.getPairState().getTrumpSuit();
        if (trump != null) return trump;
        return ps.getPairState().getLastShownSuit();
    }

    private static PositionCalls respondKeyCards(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Suit suit = getAgreedSuit(ps);
        if (suit != null) {
            choices.addRules(propertiesForcingToGame(new Call[]{Bid._5C, Bid._5D, Bid._5H, Bid._5S}, Blackwood::placeContract, true));
            choices.addRules(shows(Bid._5C, pairKeyCards(suit, null, 1, 4)));
            choices.addRules(shows(Bid._5D, pairKeyCards(suit, null, 0, 3)));
            choices.addRules(shows(Bid._5H, pairKeyCards(suit, false, 2, 5)));
            choices.addRules(shows(Bid._5S, pairKeyCards(suit, true, 2, 5)));
        }
        return choices;
    }

    private static PositionCalls placeContract(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Suit suit = getAgreedSuit(ps);
        if (suit != null) {
             choices.addRules(shows(new Bid(6, suit)));
        }
        return choices;
    }
}
