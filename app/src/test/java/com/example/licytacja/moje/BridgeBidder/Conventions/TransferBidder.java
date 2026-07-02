package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.NoTrump;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import java.util.ArrayList;
import java.util.List;

public class TransferBidder extends NoTrump.OneNoTrumpBidder {
    public TransferBidder(NoTrump.NoTrumpDescription ntd) {
        super(ntd);
    }

    public static CallFeaturesFactory initiateConvention(NoTrump.NoTrumpDescription ntd) {
        return new TransferBidder(ntd)::initiate;
    }

    private Iterable<CallFeature> initiate(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(convention(UserText.JacobyTransfer));
        // Simple transfers
        bids.add(properties(Bid._2D, this::acceptTransfer, true, UserText.TransferToHearts));
        bids.add(properties(Bid._2H, this::acceptTransfer, true, UserText.TransferToSpades));
        
        bids.add(shows(Bid._2D, ntd.RR.lessThanInvite, shape(Suit.Hearts, 5, 11)));
        bids.add(shows(Bid._2H, ntd.RR.lessThanInvite, shape(Suit.Spades, 5, 11)));
        return bids;
    }

    private PositionCalls acceptTransfer(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        if (ps.getLHO().getBidHistory(0) == null) { // Simple check for no interference
             choices.addRules(shows(Bid._2H, partner(isLastBid(Bid._2D))));
             choices.addRules(shows(Bid._2S, partner(isLastBid(Bid._2H))));
        }
        return choices;
    }
}
