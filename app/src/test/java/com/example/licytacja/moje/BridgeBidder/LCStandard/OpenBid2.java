package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.Blackwood;
import java.util.ArrayList;
import java.util.List;

public class OpenBid2 extends Open {
    public static PositionCalls responderChangedSuits(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(partnerBids(PositionCalls.fromCallFeaturesFactory(RespondBid2::secondBid)));
        
        choices.addRules(shows(Bid._2H, raisePartner(), points(12, 16))); // Simplified
        choices.addRules(shows(Bid._2S, raisePartner(), points(12, 16)));
        
        choices.addRules(shows(Bid._1NT, BALANCED, points(12, 15)));
        return choices;
    }

    public static PositionCalls twoOverOne(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(partnerBids(RespondBid2::secondBid2Over1));
        choices.addRules(shows(Bid._2NT, BALANCED));
        return choices;
    }

    public static PositionCalls semiForcingNT(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(shows(Call.PASS, BALANCED, points(12, 13)));
        return choices;
    }

    public static PositionCalls oneNTOverMinorOpen(PositionState ps) {
        return responderChangedSuits(ps);
    }

    public static PositionCalls twoNTOverMinorOpen(PositionState ps) {
        return responderChangedSuits(ps);
    }

    public static PositionCalls threeNTOverClubOpen(PositionState ps) {
        return responderChangedSuits(ps);
    }

    public static PositionCalls responderBidNT(PositionState ps) {
        return ps.getPairState().getBiddingSystem().getPositionCalls(ps);
    }

    public static PositionCalls responderRaisedMinor(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(Compete.compBids(ps));
        return choices;
    }

    public static PositionCalls responderRaisedMajor(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        // choices.addRules(Blackwood.initiateConvention(ps));
        choices.addRules(shows(Bid._4H, FIT_8_PLUS, pairPoints(25, 31)));
        choices.addRules(shows(Bid._4S, FIT_8_PLUS, pairPoints(25, 31)));
        return choices;
    }
    
    public static PositionCalls responderPassedInCompetition(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(shows(Call.PASS));
        return choices;
    }
}
