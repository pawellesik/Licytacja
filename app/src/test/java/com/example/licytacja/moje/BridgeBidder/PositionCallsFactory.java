package com.example.licytacja.moje.BridgeBidder;

@FunctionalInterface
public interface PositionCallsFactory {
    PositionCalls apply(PositionState ps);
}
