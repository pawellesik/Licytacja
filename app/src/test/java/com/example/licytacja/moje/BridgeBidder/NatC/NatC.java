package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.Bidder;
import com.example.licytacja.moje.BridgeBidder.IBiddingSystem;
import com.example.licytacja.moje.BridgeBidder.LCStandard.Compete;
import com.example.licytacja.moje.BridgeBidder.LCStandard.Open;
import com.example.licytacja.moje.BridgeBidder.LCStandard.Overcall;
import com.example.licytacja.moje.BridgeBidder.PositionCalls;
import com.example.licytacja.moje.BridgeBidder.PositionRole;
import com.example.licytacja.moje.BridgeBidder.PositionState;

public class NatC extends Bidder implements IBiddingSystem {
    @Override
    public PositionCalls getPositionCalls(PositionState ps) {
        if (ps.getRole() == PositionRole.Opener && ps.getRoleRound() == 1) {
            return Open.getOpenPositionCalls(ps);
        } else if (ps.getRole() == PositionRole.Overcaller && ps.getRoleRound() == 1) {
            return Overcall.getOvercallPositionCalls(ps);
        } else {
            PositionCalls calls = new PositionCalls(ps);
            calls.addRules(Compete::compBids);
            return calls;
        }
    }
}
