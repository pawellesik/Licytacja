package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class AgreedStrain extends StaticConstraint {
    private final Strain[] strains;

    public AgreedStrain(Strain... strains) {
        this.strains = strains;
    }

    @Override
    public boolean conforms(Call call, PositionState ps) {
        Strain[] sList = strains;
        if (sList.length == 0) {
            Strain s = getStrain(null, call);
            if (s != null) {
                sList = new Strain[]{s};
            } else {
                return false;
            }
        }
        for (Suit suit : Suit.values()) {
            if (ps.getPairState().getLastShownSuit() == suit) return true;
        }
        return false;
    }
}
