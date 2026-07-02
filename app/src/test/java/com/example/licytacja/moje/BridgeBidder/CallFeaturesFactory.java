package com.example.licytacja.moje.BridgeBidder;

@FunctionalInterface
public interface CallFeaturesFactory {
    Iterable<CallFeature> apply(PositionState ps);
}
