package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import java.util.ArrayList;
import java.util.List;

public class Compete extends Bidder {
    public static final Iterable<CallFeature> COMP_BIDS = compBids(null); // Simple placeholder

    public static Iterable<CallFeature> compBids(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(shows(Bid._2H, FIT_8_PLUS, pairPoints(20, 22)));
        bids.add(shows(Bid._2S, FIT_8_PLUS, pairPoints(20, 22)));
        bids.add(shows(Call.PASS));
        return bids;
    }
}
