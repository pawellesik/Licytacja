package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class NewSuit extends StaticConstraint implements IDescribeConstraint {
    private final Suit suit;

    public NewSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public boolean conforms(Call call, PositionState ps) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            return ps.getPairState().firstToShow(s) == null;
        }
        return false;
    }

    @Override
    public String describe(Call call, PositionState ps) {
        return "new suit";
    }
}
