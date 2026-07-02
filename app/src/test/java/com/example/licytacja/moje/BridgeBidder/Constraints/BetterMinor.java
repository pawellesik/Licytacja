package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class BetterMinor extends HandConstraint {
    private final Suit suit;

    public BetterMinor(Suit suit) {
        this.suit = suit;
    }

    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        Suit better = getSuit(this.suit, call);
        if (better != null && (better == Suit.Clubs || better == Suit.Diamonds)) {
            Range shapeClubs = hs.getSuits().get(Suit.Clubs).getShape();
            Range shapeDiamonds = hs.getSuits().get(Suit.Diamonds).getShape();
            if (shapeClubs.getMin() != shapeClubs.getMax() || shapeDiamonds.getMin() != shapeDiamonds.getMax()) return true;
            if (shapeClubs.getMin() < shapeDiamonds.getMin()) return (better == Suit.Diamonds);
            if (shapeClubs.getMin() > shapeDiamonds.getMin()) return (better == Suit.Clubs);
            if (shapeClubs.getMin() < 4) return (better == Suit.Clubs);
            return (better == Suit.Diamonds);
        }
        return false;
    }
}
