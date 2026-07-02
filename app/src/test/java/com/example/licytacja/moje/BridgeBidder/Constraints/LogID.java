package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class LogID extends StaticConstraint {
    private final String id;

    public LogID(String id) {
        this.id = id;
    }

    @Override
    public boolean conforms(Call call, PositionState ps) {
        return true;
    }

    public static String getID(BidRule rule) {
        for (Constraint constraint : rule.getConstraints()) {
            if (constraint instanceof LogID) {
                return ((LogID) constraint).id;
            }
        }
        return null;
    }

    @Override
    public String getLogDescription(Call call, PositionState ps) {
        return null;
    }
}
