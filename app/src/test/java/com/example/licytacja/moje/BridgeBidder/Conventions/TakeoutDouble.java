package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.Compete;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import java.util.ArrayList;
import java.util.List;

public class TakeoutDouble extends Bidder {
    public static Iterable<CallFeature> initiateConvention(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        if (ps.isOpponentsContract() && ps.getBiddingState().getOpeningBid() != null && ps.getBiddingState().getOpeningBid().getStrain() != Strain.NoTrump) {
            Bid contractBid = ps.getBiddingState().getContract().getBid();
            if (contractBid != null && contractBid.getLevel() <= 3) {
                bids.addAll(takeout(ps, contractBid.getLevel()));
            }
        }
        return bids;
    }

    private static List<CallFeature> takeout(PositionState ps, int level) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(properties(Call.DOUBLE, TakeoutDouble::respond, true, UserText.TakeoutDouble));
        bids.add(shows(Call.DOUBLE, points(17, 100)));

        BidRule rule = shows(Call.DOUBLE, points(12, 16));
        for (Suit s : Suit.values()) {
            if (ps.getOppsPairState().haveShownSuit(s)) {
                rule.addConstraint(shape(s, 0, 4));
            } else {
                rule.addConstraint(shape(s, 3, 4));
            }
        }
        bids.add(rule);
        return bids;
    }

    private static PositionCalls respond(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(partnerBids(PositionCalls.fromCallFeaturesFactory(TakeoutDouble::doublerRebid)));
        choices.addRules(shows(Call.PASS, ruleOf9()));
        choices.addRules(shows(Bid._1D, takeoutSuit(), points(0, 8)));
        choices.addRules(shows(Bid._1H, takeoutSuit(), points(0, 8)));
        choices.addRules(shows(Bid._1S, takeoutSuit(), points(0, 8)));
        choices.addRules(shows(Bid._1NT, BALANCED, OPPS_STOPPED, points(6, 10)));
        return choices;
    }

    private static Iterable<CallFeature> doublerRebid(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(partnerBids(TakeoutDouble::advancerRebid));
        bids.add(shows(Bid._4H, FIT_8_PLUS, pairPoints(25, 30)));
        bids.add(shows(Bid._4S, FIT_8_PLUS, pairPoints(25, 30)));
        return bids;
    }

    private static PositionCalls advancerRebid(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(Compete.compBids(ps));
        return choices;
    }
}
