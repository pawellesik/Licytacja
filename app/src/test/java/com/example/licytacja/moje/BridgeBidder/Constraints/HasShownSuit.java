package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class HasShownSuit extends StaticConstraint {
    private final Suit suit;
    private final boolean eitherPartner;

    public HasShownSuit(Suit suit, boolean eitherPartner) {
        this.suit = suit;
        this.eitherPartner = eitherPartner;
    }

    @Override
    public boolean conforms(Call call, PositionState ps) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            PositionState firstToShow = ps.getPairState().firstToShow(s);
            if (firstToShow == null) return false;
            return eitherPartner || firstToShow == ps;
        }
        return false;
    }

    @Override
    public String getLogDescription(Call call, PositionState ps) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            if (eitherPartner) {
                return ps.getDirection().pair().name() + " has shown " + s.toSymbol();
            }
            return ps.getDirection().name() + " has shown " + s.toSymbol();
        }
        return null;
    }
}
