package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class Flat {
    public static class IsFlat extends HandConstraint {
        protected final boolean desiredValue;

        public IsFlat(boolean desiredValue) {
            this.desiredValue = desiredValue;
        }

        @Override
        public boolean conforms(Call call, PositionState ps, HandSummary hs) {
            return hs.getIsFlat() == null || hs.getIsFlat() == desiredValue;
        }
    }

    public static class ShowsFlat extends IsFlat implements IShowsHand, IDescribeConstraint {
        public ShowsFlat(boolean desiredValue) {
            super(desiredValue);
        }

        @Override
        public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
            showHand.showIsFlat(desiredValue);
            if (desiredValue) {
                for (Suit suit : Suit.values()) {
                    showHand.getSuits().get(suit).showShape(3, 4);
                }
            }
        }

        @Override
        public String describe(Call call, PositionState ps) {
            return desiredValue ? "flat" : "not flat";
        }
    }
}
