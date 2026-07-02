package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.Strong2Clubs;
import java.util.ArrayList;
import java.util.List;

public class Open extends LCStandard {
    public static final HandConstraint ONE_LEVEL = points(12, 21);
    public static final HandConstraint MINIMUM = points(12, 16);
    public static final HandConstraint MEDIUM_OR_BETTER = points(17, 21);
    public static final HandConstraint DONT_OPEN = points(0, 11);

    public static PositionCalls getOpenPositionCalls(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(SolidSuit.BIDS(ps));
        choices.addRules(Strong2Clubs.open(ps));
        // choices.addRules(NoTrump.open(ps));
        choices.addRules(openSuit(ps));
        if (ps.getSeat() != 4) {
            choices.addPassRule(DONT_OPEN);
        }
        return choices;
    }

    public static Iterable<CallFeature> openSuit(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(partnerBids(Bid._1C, Respond::oneClub));
        // More bids...
        return bids;
    }
}
