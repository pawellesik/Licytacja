package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import java.util.ArrayList;
import java.util.List;

public class Strong2Clubs extends Bidder {
    protected static final Range STRONG_OPEN_RANGE = new Range(22, 40);
    protected static final Range GAME_IN_HAND = new Range(25, 40);
    protected static final Range POSITIVE_RESPONSE = new Range(8, 18);
    protected static final Range WAITING = new Range(0, 18);
    protected static final Range RESPOND_BUST = new Range(0, 4);
    protected static final Range RESPOND_SUIT_NOT_BUST = new Range(5, 7);
    protected static final Range RESPOND_NT_NOT_BUST = new Range(5, 9);

    public static Iterable<CallFeature> open(PositionState ps) {
        List<CallFeature> rules = new ArrayList<>();
        rules.add(properties(Bid._2C, Strong2Clubs::respond, true, UserText.Strong));
        rules.add(shows(Bid._2C, points(22, 40)));
        return rules;
    }

    private static PositionCalls respond(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        if (ps.getRHO().isPassed()) {
            Call[] positiveCalls = {Bid._2H, Bid._2S, Bid._2NT, Bid._3C, Bid._3D};
            choices.addRules(properties(positiveCalls, Strong2Clubs::openerRebidPositiveResponse, true));
            choices.addRules(shows(Bid._2H, points(8, 18), shape(5, 11))); // Simplified GoodPlusSuit
            choices.addRules(shows(Bid._2S, points(8, 18), shape(5, 11)));
            choices.addRules(shows(Bid._2NT, points(8, 18), BALANCED));
            choices.addRules(shows(Bid._3C, points(8, 18), shape(5, 11)));
            choices.addRules(shows(Bid._3D, points(8, 18), shape(5, 11)));

            choices.addRules(properties(Bid._2D, Strong2Clubs::openerRebidWaiting, true));
            choices.addRules(shows(Bid._2D, points(0, 18)));
        } else if (ps.getRHO().isDoubled()) {
            // SOS Redouble?
        }
        return choices;
    }

    private static PositionCalls openerRebidWaiting(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        // choices.addRules(TwoNoTrump.After2COpen.BIDS(ps));
        choices.addRules(properties(new Call[]{Bid._2H, Bid._2S, Bid._3C, Bid._3D}, Strong2Clubs::responder2ndBid, true));
        choices.addRules(shows(Bid._2H, shape(5, 11)));
        choices.addRules(shows(Bid._2S, shape(5, 11)));
        choices.addRules(shows(Bid._3C, shape(5, 11)));
        choices.addRules(shows(Bid._3D, shape(5, 11)));
        return choices;
    }

    private static PositionCalls openerRebidPositiveResponse(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(partnerBids(Strong2Clubs::responder2ndBid));
        choices.addRules(shows(Bid._3H, FIT_8_PLUS));
        choices.addRules(shows(Bid._3S, FIT_8_PLUS));
        choices.addRules(shows(Bid._4C, FIT_8_PLUS));
        choices.addRules(shows(Bid._4D, FIT_8_PLUS));
        // More rebids...
        return choices;
    }

    private static PositionCalls responder2ndBid(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        // Implement responder's 2nd bid
        return choices;
    }
}
