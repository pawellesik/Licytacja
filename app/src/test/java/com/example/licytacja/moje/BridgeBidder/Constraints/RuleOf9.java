package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class RuleOf9 extends HandConstraint implements IDescribeConstraint {
    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        if (ps.isOpponentsContract() && ps.getBiddingState().getContract().getBid() != null) {
            Bid oppsBid = ps.getBiddingState().getContract().getBid();
            Suit oppsSuit = oppsBid.getSuit();
            if (oppsSuit != null) {
                int level = oppsBid.getLevel();
                Integer ruleOf9Points = hs.getSuits().get(oppsSuit).getRuleOf9Points();
                if (ruleOf9Points != null) {
                    return (level + ruleOf9Points >= 9);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String describe(Call call, PositionState ps) {
        return "rule of 9";
    }
}
