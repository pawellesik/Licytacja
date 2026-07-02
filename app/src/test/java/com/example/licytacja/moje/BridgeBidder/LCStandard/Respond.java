package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import java.util.ArrayList;
import java.util.List;

public class Respond extends LCStandard {
    protected static final Range RESPOND_PASS = new Range(0, 5);
    protected static final Range RESPOND_1_LEVEL = new Range(6, 40);
    protected static final Range RAISE_1 = new Range(6, 10);
    protected static final Range LIMIT_RAISE = new Range(11, 12);
    protected static final Range NEW_SUIT_2_LEVEL = new Range(13, 40);

    public static PositionCalls oneClub(PositionState ps) {
        if (!ps.getRHO().isPassed()) {
            // return oppsInterferred(ps, Suit.Clubs);
        }

        PositionCalls choices = new PositionCalls(ps);
        if (ps.isPassedHand()) {
            // Passed hand logic
        } else {
            choices.addRules(SolidSuit.BIDS(ps));
            // Add rules for 1C
        }
        return choices;
    }

    public static Iterable<CallFeature> weakJumpShift(Suit openSuit) {
        List<CallFeature> bids = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            if (suit != openSuit) {
                bids.add(shows(new Bid(2, suit), IS_SINGLE_JUMP, points(0, 5), shape(6, 10)));
                bids.add(shows(new Bid(3, suit), IS_SINGLE_JUMP, points(0, 5), shape(6, 10)));
            }
        }
        return bids;
    }
}
