package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class TakeoutSuit extends HandConstraint implements IShowsHand {
    private final Suit suit;

    public TakeoutSuit(Suit suit) {
        this.suit = suit;
    }

    public static Suit higherRanking(Suit s1, Suit s2) {
        if (s1 == s2) return s1;
        switch (s1) {
            case Clubs: return s2;
            case Diamonds: return (s2 == Suit.Clubs) ? s1 : s2;
            case Hearts: return (s2 == Suit.Spades) ? s2 : s1;
            case Spades: return s1;
            default: return s1;
        }
    }

    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            if (ps.getOppsPairState().haveShownSuit(s)) return false;
            for (Suit other : Suit.values()) {
                if (other != s && !ps.getOppsPairState().haveShownSuit(other)) {
                    BetterSuit.IsBetterSuit better = new BetterSuit.IsBetterSuit(s, other, higherRanking(s, other), false);
                    if (!better.conforms(call, ps, hs)) return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
        Suit s = getSuit(this.suit, call);
        if (s != null) {
            showHand.getSuits().get(s).showShape(4, 11);
        }
    }
}
