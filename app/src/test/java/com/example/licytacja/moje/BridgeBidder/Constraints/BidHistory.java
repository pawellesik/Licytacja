package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class BidHistory extends StaticConstraint {
    private final int bidIndex;
    private final Call call;

    public BidHistory(int bidIndex, Call call) {
        this.bidIndex = bidIndex;
        this.call = call;
    }

    @Override
    public boolean conforms(Call call, PositionState ps) {
        Call previousCall = ps.getBidHistory(bidIndex);

        if (previousCall != null) {
            if (this.call != null) {
                return previousCall.equals(this.call);
            }
            if (call instanceof Bid && previousCall instanceof Bid) {
                return ((Bid) call).getSuit() == ((Bid) previousCall).getSuit();
            }
        }
        return false;
    }

    @Override
    public String getLogDescription(Call call, PositionState ps) {
        if (this.call != null && bidIndex == 0) return "last call was " + this.call;
        if (this.call == null && call instanceof Bid && ((Bid) call).getSuit() != null) {
            return "last bid suit was " + ((Bid) call).getSuit().toSymbol();
        }
        return super.getLogDescription(call, ps);
    }
}
