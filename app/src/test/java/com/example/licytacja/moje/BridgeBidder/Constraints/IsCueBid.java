package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class IsCueBid extends StaticConstraint {
    private final Suit suit;

    public IsCueBid(Suit suit) {
        this.suit = suit;
    }

    @Override
    public boolean conforms(Call call, PositionState ps) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            return ps.getOppsPairState().haveShownSuit(s);
        }
        return false;
    }
}
