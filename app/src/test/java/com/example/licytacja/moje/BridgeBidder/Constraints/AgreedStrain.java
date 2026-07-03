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
        
        Suit lastShown = ps.getPairState().getLastShownSuit();
        if (lastShown == null) return false;
        
        Strain agreedStrain = lastShown.toStrain();
        for (Strain s : sList) {
            if (s == agreedStrain) return true;
        }
        return false;
    }
}
