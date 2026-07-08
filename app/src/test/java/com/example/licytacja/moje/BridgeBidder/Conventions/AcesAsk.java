package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.Bid;
import com.example.licytacja.moje.BridgeBidder.Bidder;
import com.example.licytacja.moje.BridgeBidder.Call;
import com.example.licytacja.moje.BridgeBidder.CallFeature;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import com.example.licytacja.moje.BridgeBidder.PositionCalls;
import com.example.licytacja.moje.BridgeBidder.PositionState;
import com.example.licytacja.moje.BridgeBidder.Range;
import com.example.licytacja.moje.BridgeBidder.Strain;
import com.example.licytacja.moje.BridgeBidder.Suit;

import java.util.ArrayList;
import java.util.List;

public class AcesAsk extends Bidder {
    private static final Range SLAM_OR_BETTER = new Range(28, 40);
    private static final Range GRAND_SLAM = new Range(28, 100);

    public static Iterable<CallFeature> initiateConvention(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(properties(Bid._4C, AcesAsk::respondCountAces, true, true, false, ps.getPairState().getLastShownSuit(), null, null, UserText.AcesAsc, null));
        bids.add(shows(Bid._4C, IS_ANY_JUMP, pairPoints(SLAM_OR_BETTER)));
        return bids;
    }

    private static Suit getAgreedSuit(PositionState ps) {
        Suit trump = ps.getPairState().getTrumpSuit();
        if (trump != null) return trump;
        return ps.getPairState().getLastShownSuit();
    }

    public static PositionCalls respondCountAces(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Suit suit = getAgreedSuit(ps);
        if (suit != null) {
            choices.addRules(
                    properties(new Call[]{Bid._4D, Bid._4H, Bid._4S, Bid._4NT, Bid._5C}, AcesAsk::askKing, true),
                    shows(Bid._4D, aces(0)),
                    shows(Bid._4H, aces(1)),
                    shows(Bid._4S, aces(2)),
                    shows(Bid._4NT, aces(3)),
                    shows(Bid._5C, aces(4))
            );
            return choices;
        }
        throw new RuntimeException("This should never happen. No agreed suit.");
    }

    public static PositionCalls askKing(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Suit suit = getAgreedSuit(ps);
        if (suit != null) {
            if (suit.isMinor()) {
                choices.addRules(shows(new Bid(5, suit), pairAces(1)),
                        shows(new Bid(5, suit), pairAces(2))
                );
            } else if (suit.isMajor()) {
                choices.addRules(shows(new Bid(4, suit), pairAces(1)),
                        shows(new Bid(4, suit), pairAces(2)));

            }
            Call partnerCall = ps.getPartner().getLastCall();
            Bid bid = getNextBidWithoutTrump(partnerCall, suit);
            choices.addRules(
                    properties(bid, AcesAsk::respondKings, true),
                    shows(bid, pairAces(3)),
                    shows(bid, pairAces(4)));

            choices.addRules(shows(Call.PASS));
            return choices;
        }
        throw new RuntimeException("No agreed suit in askKing");
    }

    private static Bid getNextBidWithoutTrump(Call partnerCall, Suit suit) {
        if (partnerCall != null) {
            Call nCall = Call.getNextCall(partnerCall);

            while (true) {
                if (nCall instanceof Bid) {
                    Suit suitOfNextCall = ((Bid) nCall).getSuit();
                    if (suit != suitOfNextCall) {
                        return (Bid) nCall;
                    } else {
                        nCall = Call.getNextCall(nCall);
                    }
                }
            }

        }
        return (Bid) Call.PASS;
    }

    public static PositionCalls respondKings(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Call partnerCall = ps.getPartner().getLastCall();

        Call call0Kings = Call.getNextCall(partnerCall);
        Call call1Kings = Call.getNextCall(call0Kings);
        Call call2Kings = Call.getNextCall(call1Kings);
        Call call3Kings = Call.getNextCall(call2Kings);
        Call call4Kings = Call.getNextCall(call3Kings);

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
