package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import java.util.ArrayList;
import java.util.List;

public class Strong2Clubs extends Bidder {
    protected static final Range STRONG_OPEN_RANGE = new Range(22, 40);
    protected static final Range POSITIVE_RESPONSE = new Range(8, 18);
    protected static final Range WAITING = new Range(0, 18);

    public static Iterable<CallFeature> open(PositionState ps) {
        List<CallFeature> rules = new ArrayList<>();
        rules.add(properties(Bid._2C, Strong2Clubs::respond, true, UserText.Strong));
        rules.add(shows(Bid._2C, points(STRONG_OPEN_RANGE)));
        return rules;
    }

    private static PositionCalls respond(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        if (ps.getRHO().isPassed()) {
            Call[] positiveCalls = {Bid._2H, Bid._2S, Bid._2NT, Bid._3C, Bid._3D};
            choices.addRules(
                propertiesForcingToGame(positiveCalls, Strong2Clubs::openerRebidPositiveResponse, true),
                shows(Bid._2H, points(POSITIVE_RESPONSE), shape(5, 11), GOOD_PLUS_SUIT),
                shows(Bid._2S, points(POSITIVE_RESPONSE), shape(5, 11), GOOD_PLUS_SUIT),
                shows(Bid._2NT, points(POSITIVE_RESPONSE), BALANCED),
                shows(Bid._3C, points(POSITIVE_RESPONSE), shape(5, 11), GOOD_PLUS_SUIT),
                shows(Bid._3D, points(POSITIVE_RESPONSE), shape(5, 11), GOOD_PLUS_SUIT)
            );

            choices.addRules(
                properties(Bid._2D, Strong2Clubs::openerRebidWaiting, true),
                shows(Bid._2D, points(WAITING))
            );
        }
        return choices;
    }

    private static PositionCalls openerRebidWaiting(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
            partnerBids(Strong2Clubs::responder2ndBid),
            propertiesForcingToGame(new Call[]{Bid._2H, Bid._2S, Bid._3C, Bid._3D}, Strong2Clubs::responder2ndBid, true),
            shows(Bid._2H, shape(5, 11)),
            shows(Bid._2S, shape(5, 11)),
            shows(Bid._3C, shape(5, 11)),
            shows(Bid._3D, shape(5, 11)),
            shows(Bid._3NT, pairPoints(25, 40)),
            shows(Bid._2NT, BALANCED)
        );
        return choices;
    }

    private static PositionCalls openerRebidPositiveResponse(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
            partnerBids(Strong2Clubs::responder2ndBid),
            shows(Bid._3H, FIT_8_PLUS),
            shows(Bid._3S, FIT_8_PLUS),
            shows(Bid._4C, FIT_8_PLUS),
            shows(Bid._4D, FIT_8_PLUS)
        );
        return choices;
    }

    private static PositionCalls responder2ndBid(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
            partnerBids(Strong2Clubs::openerRebidWaiting), 
            shows(Bid._3H, shape(5, 11)),
            shows(Bid._3S, shape(5, 11)),
            shows(Bid._2S, shape(5, 11)),
            shows(Bid._3C, shape(5, 11)),
            shows(Bid._3D, shape(5, 11)),
            shows(Bid._4H, FIT_8_PLUS),
            shows(Bid._4S, FIT_8_PLUS),
            shows(Bid._3NT, pairPoints(25, 40)),
            shows(Call.PASS)
        );
        return choices;
    }
}
