package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.TakeoutDouble;
import java.util.ArrayList;
import java.util.List;

public class Overcall extends LCStandard {
    public static PositionCalls getOvercallPositionCalls(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(suitOvercall(ps));
        choices.addRules(NoTrump::strongOvercall);
        choices.addRules(TakeoutDouble::initiateConvention);
        choices.addRules(NoTrump::balancingOvercall);
        choices.addPassRule(points(0, 17));
        return choices;
    }

    public static Iterable<CallFeature> suitOvercall(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        // bids.add(partnerBids(Advance.FIRST_BID));
        bids.add(shows(Bid._1S, points(7, 17), shape(6, 10)));
        bids.add(shows(Bid._1H, points(7, 17), shape(6, 10)));
        bids.add(shows(Bid._1D, points(7, 17), shape(6, 10)));
        // More rules...
        return bids;
    }
}
