package com.example.licytacja.moje.BridgeBidder;

public class CallProperties extends CallFeature {
    private final PositionCallsFactory partnerBids;
    private final boolean forcing1Round;
    private final boolean forcingToGame;
    private Suit trumpSuit = null;

    public CallProperties(Call call, PositionCallsFactory partnerBids, boolean forcing1Round, boolean forcingToGame,
                          boolean agreeTrump, Suit trump, StaticConstraint... constraints) {
        super(call, (Constraint[]) constraints);
        this.partnerBids = partnerBids;
        this.forcing1Round = forcing1Round;
        this.forcingToGame = forcingToGame;
        if (agreeTrump) {
            if (call instanceof Bid) {
                this.trumpSuit = ((Bid) call).getSuit();
            }
        } else if (trump != null) {
            this.trumpSuit = trump;
        }
    }

    public PositionCallsFactory getPartnerBids() {
        return partnerBids;
    }

    public boolean isForcing1Round() {
        return forcing1Round;
    }

    public boolean isForcingToGame() {
        return forcingToGame;
    }

    public Suit getTrumpSuit() {
        return trumpSuit;
    }
}
