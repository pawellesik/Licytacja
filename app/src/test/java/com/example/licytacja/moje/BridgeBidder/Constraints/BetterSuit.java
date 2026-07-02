package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class BetterSuit {
    public static class IsBetterSuit extends HandConstraint {
        protected final Suit better;
        protected final Suit worse;
        protected final Suit defaultIfEqual;
        protected final boolean lengthOnly;

        public IsBetterSuit(Suit better, Suit worse, Suit defaultIfEqual, boolean lengthOnly) {
            this.better = better;
            this.worse = worse;
            this.defaultIfEqual = defaultIfEqual;
            this.lengthOnly = lengthOnly;
        }

        @Override
        public boolean conforms(Call call, PositionState ps, HandSummary hs) {
            Suit b = getSuit(this.better, call);
            Suit w = getSuit(this.worse, call);
            Suit d = getSuit(this.defaultIfEqual, call);
            if (b != null && w != null) {
                Range bShape = hs.getSuits().get(b).getShape();
                Range wShape = hs.getSuits().get(w).getShape();
                if (bShape.getMax() < wShape.getMin()) return false;
                if (bShape.getMax() == wShape.getMin() && w == d) return false;
                if (!lengthOnly && bShape.getMax() == wShape.getMin()) {
                    int bq = hs.getSuits().get(b).getQuality().getMin();
                    int wq = hs.getSuits().get(w).getQuality().getMin();
                    if (bq > wq) return true;
                    if (wq > bq) return false;
                }
                return true;
            }
            return false;
        }
    }

    public static class ShowsBetterSuit extends IsBetterSuit implements IShowsHand, IDescribeConstraint {
        public ShowsBetterSuit(Suit better, Suit worse, Suit defaultIfEqual, boolean lengthOnly) {
            super(better, worse, defaultIfEqual, lengthOnly);
        }

        @Override
        public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
            Suit b = getSuit(this.better, call);
            Suit w = getSuit(this.worse, call);
            if (b != null && w != null) {
                Range bShape = ps.getPublicHandSummary().getSuits().get(b).getShape();
                Range wShape = ps.getPublicHandSummary().getSuits().get(w).getShape();
                showHand.getSuits().get(w).showShape(wShape.getMin(), Math.min(wShape.getMax(), bShape.getMax()));
            }
        }

        @Override
        public String describe(Call call, PositionState ps) {
            Suit b = getSuit(this.better, call);
            Suit w = getSuit(this.worse, call);
            Suit d = getSuit(this.defaultIfEqual, call);
            if (b != null && w != null && d != null) {
                boolean betterOrEqual = (d == b);
                if (lengthOnly) {
                    return b.toSymbol() + (betterOrEqual ? " longer or equal to " : " longer than ") + w.toSymbol();
                }
                return b.toSymbol() + (betterOrEqual ? " better or equal to " : " better than ") + w.toSymbol();
            }
            return null;
        }
    }
}
