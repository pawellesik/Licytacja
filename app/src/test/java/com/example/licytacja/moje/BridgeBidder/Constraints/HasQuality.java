package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class HasQuality extends HandConstraint {
    protected final Suit suit;
    protected final SuitQuality min;
    protected final SuitQuality max;

    public HasQuality(Suit suit, SuitQuality min, SuitQuality max) {
        this.suit = suit;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            Range quality = hs.getSuits().get(s).getQuality();
            return (min.ordinal() <= quality.getMax() && max.ordinal() >= quality.getMin());
        }
        return false;
    }

    public static class ShowsQuality extends HasQuality implements IShowsHand, IDescribeConstraint {
        public ShowsQuality(Suit suit, SuitQuality min, SuitQuality max) {
            super(suit, min, max);
        }

        @Override
        public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
            Suit s = getSuit(this.suit, call);
            if (s != null) {
                showHand.getSuits().get(s).showQuality(min, max);
            }
        }

        @Override
        public String describe(Call call, PositionState ps) {
            Suit s = getSuit(this.suit, call);
            if (s != null) {
                String suitSymbol = s.toSymbol();
                String minStr = min.name().toLowerCase();
                if (max == SuitQuality.Solid) return suitSymbol + " " + minStr + "+";
                if (min == max) return suitSymbol + " " + minStr;
                return suitSymbol + " " + minStr + "-" + max.name().toLowerCase();
            }
            return null;
        }
    }
}
