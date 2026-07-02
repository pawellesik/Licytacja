package com.example.licytacja.moje.BridgeBidder;

public abstract class HandConstraint extends Constraint {
    public abstract boolean conforms(Call call, PositionState ps, HandSummary hs);
}
