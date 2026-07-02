package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.NoTrump;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import java.util.ArrayList;
import java.util.List;

public class StaymanBidder extends NoTrump.OneNoTrumpBidder {
    public StaymanBidder(NoTrump.NoTrumpDescription ntd) {
        super(ntd);
    }

    public static CallFeaturesFactory initiateConvention(NoTrump.NoTrumpDescription ntd) {
        return new StaymanBidder(ntd)::initiate;
    }

    private Iterable<CallFeature> initiate(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        Call call = Bid._2C;
        // Simplified interference logic
        bids.add(convention(call, UserText.Stayman));
        bids.add(properties(call, this::answer, true));
        bids.add(shows(call, ntd.RR.inviteOrBetter, shape(Suit.Hearts, 4), shape(Suit.Spades, 0, 4)));
        bids.add(shows(call, ntd.RR.inviteOrBetter, shape(Suit.Spades, 4), shape(Suit.Hearts, 0, 4)));
        return bids;
    }

    private PositionCalls answer(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(shows(Bid._2D, shape(Suit.Hearts, 0, 3), shape(Suit.Spades, 0, 3)));
        choices.addRules(shows(Bid._2H, shape(4, 5)));
        choices.addRules(shows(Bid._2S, shape(4, 5)));
        return choices;
    }
}
