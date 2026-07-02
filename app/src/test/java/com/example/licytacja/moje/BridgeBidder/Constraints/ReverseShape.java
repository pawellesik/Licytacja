package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class ReverseShape {
    public static class HasReverseShape extends HandConstraint {
        protected Suit openSuit(PositionState ps) {
            Bid openingBid = ps.getBiddingState().getOpeningBid();
            return openingBid != null ? openingBid.getSuit() : null;
        }

        protected Suit bidSuit(Call call) {
            return call instanceof Bid ? ((Bid) call).getSuit() : null;
        }

        @Override
        public boolean conforms(Call call, PositionState ps, HandSummary hs) {
            if (ps.isReverse(call)) {
                Suit oSuit = openSuit(ps);
                Suit bSuit = bidSuit(call);
                if (oSuit != null && bSuit != null) {
                    Range openingShape = hs.getSuits().get(oSuit).getShape();
                    Range reverseSuitShape = hs.getSuits().get(bSuit).getShape();
                    return (reverseSuitShape.getMax() > 3 && reverseSuitShape.getMin() < openingShape.getMax());
                }
            }
            return false;
        }
    }

    public static class ShowsReverseShape extends HasReverseShape implements IShowsHand {
        @Override
        public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
            Suit oSuit = openSuit(ps);
            Suit bSuit = bidSuit(call);
            if (oSuit != null && bSuit != null) {
                Range openingShape = ps.getPublicHandSummary().getSuits().get(oSuit).getShape();
                showHand.getSuits().get(bSuit).showShape(4, openingShape.getMax() - 1);
                showHand.getSuits().get(oSuit).showShape(5, openingShape.getMax());
            }
        }
    }
}
