package com.example.licytacja.moje.BridgeBidder;

public abstract class StaticConstraint extends Constraint {
    public abstract boolean conforms(Call call, PositionState ps);
}
