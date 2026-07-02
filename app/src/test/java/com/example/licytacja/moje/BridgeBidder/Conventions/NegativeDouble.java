package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.Respond;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import java.util.ArrayList;
import java.util.List;

public class NegativeDouble extends Respond {
    public static Iterable<CallFeature> initiateConvention(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        Bid contractBid = ps.getBiddingState().getContract().getBid();
        if (contractBid != null && contractBid.getLevel() == 1 && contractBid.getStrain() != Strain.NoTrump) {
            Suit overcallSuit = contractBid.getSuit();
            Call partnerCall = ps.getPartner().getBidHistory(0);
            if (partnerCall instanceof Bid) {
                Suit openSuit = ((Bid) partnerCall).getSuit();
                bids.add(convention(Call.DOUBLE, UserText.NegativeDouble));
                if (overcallSuit == Suit.Diamonds) {
                    bids.add(shows(Call.DOUBLE, points(6, 40), shape(Suit.Hearts, 4), shape(Suit.Spades, 4)));
                } else if (overcallSuit == Suit.Hearts) {
                    bids.add(shows(Call.DOUBLE, points(6, 40), shape(Suit.Spades, 4)));
                    bids.add(shows(Bid._1S, points(6, 40), shape(5, 11)));
                }
                // Add more cases...
            }
        }
        return bids;
    }
}
