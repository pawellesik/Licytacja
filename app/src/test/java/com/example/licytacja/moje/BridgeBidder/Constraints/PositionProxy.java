package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class PositionProxy extends StaticConstraint implements IDescribeConstraint {
    public enum RelativePosition { Partner, LHO, RHO }

    private final RelativePosition relativePosition;
    private final StaticConstraint constraint;

    public PositionProxy(RelativePosition relativePosition, Constraint constraint) {
        this.relativePosition = relativePosition;
        this.constraint = (StaticConstraint) constraint;
    }

    private PositionState getPosition(PositionState positionState) {
        switch (relativePosition) {
            case Partner: return positionState.getPartner();
            case LHO: return positionState.getLHO();
            case RHO: return positionState.getRHO();
            default: return null;
        }
    }

    @Override
    public boolean conforms(Call call, PositionState ps) {
        return constraint.conforms(call, getPosition(ps));
    }

    private String getPositionName() {
        switch (relativePosition) {
            case Partner: return "partner";
            case LHO: return "LHO";
            case RHO: return "RHO";
            default: return "";
        }
    }

    @Override
    public String describe(Call call, PositionState ps) {
        if (constraint instanceof IDescribeConstraint) {
            return getPositionName() + " " + ((IDescribeConstraint) constraint).describe(call, getPosition(ps));
        }
        return null;
    }

    @Override
    public String getLogDescription(Call call, PositionState ps) {
        String desc = describe(call, ps);
        return desc == null ? getPositionName() + " " + constraint.getLogDescription(call, getPosition(ps)) : desc;
    }
}
