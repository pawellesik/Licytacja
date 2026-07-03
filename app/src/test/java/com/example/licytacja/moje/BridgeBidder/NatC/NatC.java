package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.Compete;

public class NatC extends Bidder implements IBiddingSystem {
    @Override
    public PositionCalls getPositionCalls(PositionState ps) {
        if (ps.getRole() == PositionRole.Opener && ps.getRoleRound() == 1) {
            return OpenNatC.getOpenPositionCalls(ps);
        }
        PositionCalls calls = new PositionCalls(ps);
        calls.addRules(Compete::compBids);
        return calls;
    }
}
