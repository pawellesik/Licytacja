package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import java.util.ArrayList;
import java.util.List;

public class OpenBid3 extends Open {

    public static Iterable<CallFeature> thirdBid(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(shows(Bid._1NT, BALANCED, points(Rebid1NT)));
        bids.add(shows(Bid._2NT, BALANCED, points(Rebid2NT)));

        for (CallFeature cf : Compete.compBids(ps)) {
            bids.add(cf);
        }
        return bids;
    }
}
