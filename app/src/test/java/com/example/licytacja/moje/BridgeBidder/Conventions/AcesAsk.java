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
    private static final Range SLAM_OR_BETTER = new Range(28, 40);
    private static final Range GRAND_SLAM = new Range(36, 100);

    public static Iterable<CallFeature> initiateConvention(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        //bids.add(properties(Bid._4C, AcesAsk::respondKeyCards, true, UserText.AcesAsc));
        bids.add(properties(Bid._4C, AcesAsk::respondCountAces, true, true, true, ps.getPairState().getLastShownSuit(), null, null, UserText.AcesAsc, null));
        bids.add(shows(Bid._4C, fit(ps.getPairState().getLastShownSuit()), pairPoints(SLAM_OR_BETTER)));
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
            // 1. Definiujemy odzywki, które są PYTANIEM o Króle (wszystkie poza atutem i NT)
            List<Call> kingAskBids = new ArrayList<>();
            for (Suit s : Suit.values()) {
                if (s != suit) { // Jeśli to nie jest nasz atut
                    Call nextBid = ps.getBiddingState().getContract().nextAvailableBid(s);
                    if (nextBid instanceof Bid && ((Bid) nextBid).getLevel() < 7) {
                        kingAskBids.add(nextBid);
                    }
                }
            }

            // 2. Jeśli licytujemy nowy kolor -> Pytamy o Króle
            Call[] bidsArray = kingAskBids.toArray(new Call[0]);
            choices.addRules(properties(bidsArray, AcesAsk::respondKings, true));
            for (Call c : bidsArray) {
                choices.addRules(shows(c, pairPoints(GRAND_SLAM)));
                choices.addRules(shows(c, aces(4)));
            }


            // 4. Licytacja uzgodnionego koloru = ZAKOŃCZENIE (Gramy szlemika lub końcówkę)
            choices.addRules(
                    shows(new Bid(6, suit), pairPoints(SLAM_OR_BETTER), pairKeyCards(suit, null, 4, 5)),
                    shows(new Bid(5, suit), pairKeyCards(suit, null, 0, 1, 2, 3))
            );


            choices.addRules(shows(Call.PASS, CONTRACT_IS_AGREED_STRAIN));

            return choices;
        }
        throw new RuntimeException("No agreed suit in askKing");
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
