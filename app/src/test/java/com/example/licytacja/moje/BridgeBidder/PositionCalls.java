package com.example.licytacja.moje.BridgeBidder;

import java.util.*;

public class PositionCalls extends HashMap<Call, CallDetails> {
    private final PositionState positionState;
    private CallDetails bestCall = null;

    public PositionCalls(PositionState ps) {
        this.positionState = ps;
    }

    public PositionState getPositionState() {
        return positionState;
    }

    public CallDetails getBestCall() {
        return bestCall;
    }

    public void addRules(Iterable<CallFeature> rules) {
        CallGroup group = CallGroup.create(this, rules);
        for (Map.Entry<Call, CallDetails> entry : group.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
        if (bestCall == null) {
            bestCall = group.getBestCall();
        }
    }

    public void addRules(CallFeature... rules) {
        addRules(Arrays.asList(rules));
    }

    public void createPlaceholderCall(Call call) {
        // TODO: Port logic from Bidder.Shows
        // addRules(Bidder.shows(call));
    }

    public void logBidRule(BidRule rule) {
        // TODO: Implement logging
    }
}
