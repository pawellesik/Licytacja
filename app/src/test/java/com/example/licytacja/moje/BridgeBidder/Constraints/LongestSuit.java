package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class LongestSuit extends HandConstraint implements IDescribeConstraint {
    protected final Suit suit;

    public LongestSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            int longestOther = 0;
            for (Suit other : Suit.values()) {
                if (other != s) {
                    longestOther = Math.max(longestOther, hs.getSuits().get(other).getShape().getMax());
                }
            }
            return hs.getSuits().get(s).getShape().getMin() > longestOther;
        }
        return false;
    }

    @Override
    public String describe(Call call, PositionState ps) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            return "longest suit is " + s.toSymbol();
        }
        return null;
    }

    public static class ShowsLongestSuit extends LongestSuit implements IShowsHand {
        public ShowsLongestSuit(Suit suit) {
            super(suit);
        }

        @Override
        public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
            Suit s = getSuit(this.suit, call);
            if (s != null) {
                int minOther = 3;
                for (Suit other : Suit.values()) {
                    if (other != s) {
                        minOther = Math.max(minOther, ps.getPublicHandSummary().getSuits().get(other).getShape().getMin());
                    }
                }
                Range shape = ps.getPublicHandSummary().getSuits().get(s).getShape();
                showHand.getSuits().get(s).showShape(minOther + 1, shape.getMax());
            }
        }
    }
}
