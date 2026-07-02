package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RespondBid2 extends Respond {
    public static Iterable<CallFeature> secondBid(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        // bids.add(partnerBids(OpenBid3::thirdBid));
        bids.add(shows(Bid._2S, raisePartner(), points(6, 10)));
        for (CallFeature cf : Compete.compBids(ps)) {
            bids.add(cf);
        }
        return bids;
    }

    public static PositionCalls secondBid2Over1(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Suit trump = ps.getPairState().getTrumpSuit();
        if (trump != null) {
            choices.addRules(shows(new Bid(4, trump), pairPoints(25, 27)));
        }
        // choices.addRules(Blackwood.initiateConvention(ps));
        return choices;
    }
}
