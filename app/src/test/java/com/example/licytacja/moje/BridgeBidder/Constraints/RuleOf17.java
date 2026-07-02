package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class RuleOf17 extends HandConstraint implements IDescribeConstraint {
    private final Suit suit;

    public RuleOf17(Suit suit) {
        this.suit = suit;
    }

    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            Range hcp = hs.getHighCardPoints();
            if (hcp == null) return true;
            int pts = hcp.getMax();
            pts += hs.getSuits().get(s).getShape().getMax();
            return pts >= 17;
        }
        return false;
    }

    @Override
    public String describe(Call call, PositionState ps) {
        return "rule of 17";
    }
}
