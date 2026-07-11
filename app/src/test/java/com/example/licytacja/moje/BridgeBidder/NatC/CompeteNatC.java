package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.Blackwood;
import com.example.licytacja.moje.BridgeBidder.Conventions.Gerber;
import java.util.ArrayList;
import java.util.List;

public class CompeteNatC extends NatC {

    public static Iterable<CallFeature> compBids(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(shows(Bid._4H, FIT_8_PLUS, pairPoints(26, 28), id("CompeteNatC.compBids _4H")));
        bids.add(shows(Bid._4S, FIT_8_PLUS, pairPoints(26, 28), id("CompeteNatC.compBids _4S")));

        bids.add(shows(Bid._4H, IS_FORCED_TO_GAME, FIT_8_PLUS, pairPoints(23, 25), id("CompeteNatC.compBids _4H")));
        bids.add(shows(Bid._4S, IS_FORCED_TO_GAME, FIT_8_PLUS, pairPoints(23, 25), id("CompeteNatC.compBids _4S")));

        bids.add(shows(Bid._4H, FIT_8_PLUS, pairPoints(29, 32), id("CompeteNatC.compBids _4H")));
        bids.add(shows(Bid._4S, FIT_8_PLUS, pairPoints(29, 32), id("CompeteNatC.compBids _4S")));

        bids.add(shows(Bid._2C, FIT_8_PLUS, pairPoints(20, 22), id("CompeteNatC.compBids _2C")));
        bids.add(shows(Bid._2D, FIT_8_PLUS, pairPoints(20, 22), id("CompeteNatC.compBids _2D")));
        bids.add(shows(Bid._2H, FIT_8_PLUS, pairPoints(20, 22), id("CompeteNatC.compBids _2H")));
        bids.add(shows(Bid._2S, FIT_8_PLUS, pairPoints(20, 22), id("CompeteNatC.compBids _2S")));

        bids.add(shows(Bid._3C, FIT_8_PLUS, pairPoints(23, 25), id("CompeteNatC.compBids _3C")));
        bids.add(shows(Bid._3D, FIT_8_PLUS, pairPoints(23, 25), id("CompeteNatC.compBids _3D")));
        bids.add(shows(Bid._3H, FIT_8_PLUS, pairPoints(23, 25), id("CompeteNatC.compBids _3H")));
        bids.add(shows(Bid._3S, FIT_8_PLUS, pairPoints(23, 25), id("CompeteNatC.compBids _3S")));

        bids.add(shows(Bid._3NT, OPPS_STOPPED, pairHighCardPoints(25, 31), id("CompeteNatC.compBids _3NT")));
        bids.add(shows(Bid._2NT, IS_OPPS_CONTRACT, OPPS_STOPPED, pairPoints(20, 24), id("CompeteNatC.compBids _2NT")));

        bids.add(shows(Bid._4C, not(Gerber.APPLIES), FIT_8_PLUS, pairPoints(26, 28), id("CompeteNatC.compBids _4C")));
        bids.add(shows(Bid._4D, FIT_8_PLUS, pairPoints(26, 28), id("CompeteNatC.compBids _4D")));

        bids.add(shows(Bid._5C, FIT_8_PLUS, pairPoints(29, 32), not(fit(Suit.Spades)), id("CompeteNatC.compBids _5C")));
        bids.add(shows(Bid._5D, FIT_8_PLUS, pairPoints(29, 32), id("CompeteNatC.compBids _5D")));

        for (CallFeature cf : ForcedBidNatC.bids(ps)) {
            bids.add(cf);
        }
        bids.add(shows(Call.PASS, id("CompeteNatC.compBids _PASS")));
        return bids;
    }
}
