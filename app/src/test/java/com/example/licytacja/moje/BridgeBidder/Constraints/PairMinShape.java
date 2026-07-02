package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class PairMinShape {
    public static class PairHasMinShape extends HandConstraint {
        protected final Suit suit;
        protected final int min;
        protected final boolean desiredValue;
        protected final boolean useContractSuit;

        public PairHasMinShape(Suit suit, int min, boolean desiredValue) {
            this.suit = suit;
            this.min = min;
            this.desiredValue = desiredValue;
            this.useContractSuit = false;
        }

        public PairHasMinShape(int min, boolean desiredValue) {
            this.suit = null;
            this.min = min;
            this.desiredValue = desiredValue;
            this.useContractSuit = true;
        }

        @Override
        public boolean conforms(Call call, PositionState ps, HandSummary hs) {
            Suit s = null;
            if (useContractSuit) {
                // if (ps.getBiddingState().getContract().isOurs(ps.getDirection())) {
                //    s = ps.getBiddingState().getContract().getBid().getSuit();
                // }
                // Need to fix this once ContractState is ready
            } else {
                s = getSuit(this.suit, call);
            }
            if (s != null) {
                Range shape = hs.getSuits().get(s).getShape();
                Range partnerShape = ps.getPartner().getPublicHandSummary().getSuits().get(s).getShape();
                return (shape.getMax() + partnerShape.getMin() >= min) ? desiredValue : !desiredValue;
            }
            return false;
        }
    }

    public static class PairShowsMinShape extends PairHasMinShape implements IShowsHand, IDescribeConstraint {
        public PairShowsMinShape(Suit suit, int min, boolean desiredValue) {
            super(suit, min, desiredValue);
        }

        @Override
        public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
            Suit s = getSuit(this.suit, call);
            if (s != null) {
                Range shape = ps.getPublicHandSummary().getSuits().get(s).getShape();
                Range partnerShape = ps.getPartner().getPublicHandSummary().getSuits().get(s).getShape();
                int newMin = min - partnerShape.getMin();
                if (newMin > shape.getMin()) {
                    showHand.getSuits().get(s).showShape(newMin, Math.max(newMin, shape.getMax()));
                }
            }
        }

        @Override
        public String describe(Call call, PositionState ps) {
            Suit s = getSuit(this.suit, call);
            if (s != null) {
                return min + "+ pair " + s.toSymbol();
            }
            return null;
        }
    }
}
