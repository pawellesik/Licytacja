package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class Balanced {
    public static class ShowsBalanced extends HandConstraint implements IShowsHand, IDescribeConstraint {
        private final boolean desiredValue;

        public ShowsBalanced(boolean desiredValue) {
            this.desiredValue = desiredValue;
        }

        @Override
        public boolean conforms(Call call, PositionState ps, HandSummary hs) {
            return hs.getIsBalanced() == null || hs.getIsBalanced() == desiredValue;
        }

        @Override
        public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
            showHand.showIsBalanced(desiredValue);
            if (desiredValue) {
                for (Suit suit : Suit.values()) {
                    showHand.getSuits().get(suit).showShape(2, 5);
                }
            }
        }

        @Override
        public String describe(Call call, PositionState ps) {
            return desiredValue ? "balanced" : "not balanced";
        }
    }
}
