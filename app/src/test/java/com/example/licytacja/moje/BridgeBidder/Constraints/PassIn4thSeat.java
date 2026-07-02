package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class PassIn4thSeat extends HandConstraint {
    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        if (ps.getSeat() != 4) return false;
        Range hcp = hs.getHighCardPoints();
        if (hcp == null) return true;

        Range spadeShape = hs.getSuits().get(Suit.Spades).getShape();
        return (hcp.getMax() + spadeShape.getMax() < 15);
    }
}
