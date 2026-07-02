package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.TakeoutDouble;
import java.util.ArrayList;
import java.util.List;

public class Overcall extends LCStandard {

    public static PositionCalls getOvercallPositionCalls(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(Overcall::suitOvercall);
        choices.addRules(NoTrump::strongOvercall);
        choices.addRules(TakeoutDouble::initiateConvention);
        choices.addRules(NoTrump::balancingOvercall);
        choices.addPassRule(points(LESS_THAN_OVERCALL));

        return choices;
    }

    public static Iterable<CallFeature> suitOvercall(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(partnerBids(Advance::firstBid));

        bids.add(shows(Bid._1D, points(OVERCALL_1_LEVEL), shape(5, 11), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._1D, points(OVERCALL_1_LEVEL), shape(6, 11)));
        bids.add(shows(Bid._1H, points(OVERCALL_1_LEVEL), shape(5, 11), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._1H, points(OVERCALL_1_LEVEL), shape(6, 11)));
        bids.add(shows(Bid._1S, points(OVERCALL_1_LEVEL), shape(5, 11), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._1S, points(OVERCALL_1_LEVEL), shape(6, 11)));

        bids.add(shows(Bid._2C, IS_NON_JUMP, points(OVERCALL_1_LEVEL), shape(5, 11), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._2C, IS_NON_JUMP, points(OVERCALL_1_LEVEL), shape(6, 11)));
        bids.add(shows(Bid._2D, IS_NON_JUMP, points(OVERCALL_1_LEVEL), shape(5, 11), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._2D, IS_NON_JUMP, points(OVERCALL_1_LEVEL), shape(6, 11)));
        bids.add(shows(Bid._2H, IS_NON_JUMP, points(OVERCALL_1_LEVEL), shape(5, 11), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._2H, IS_NON_JUMP, points(OVERCALL_1_LEVEL), shape(6, 11)));
        bids.add(shows(Bid._2S, IS_NON_JUMP, points(OVERCALL_1_LEVEL), shape(5, 11), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._2S, IS_NON_JUMP, points(OVERCALL_1_LEVEL), shape(6, 11)));

        // Weak jump overcalls
        bids.add(shows(Bid._2D, IS_SINGLE_JUMP, points(OVERCALL_WEAK_2_LEVEL), shape(6), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._2H, IS_SINGLE_JUMP, points(OVERCALL_WEAK_2_LEVEL), shape(6), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._2S, IS_SINGLE_JUMP, points(OVERCALL_WEAK_2_LEVEL), shape(6), GOOD_PLUS_SUIT));

        bids.add(shows(Bid._3C, IS_SINGLE_JUMP, points(OVERCALL_WEAK_3_LEVEL), shape(7), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._3D, IS_SINGLE_JUMP, points(OVERCALL_WEAK_3_LEVEL), shape(7), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._3H, IS_SINGLE_JUMP, points(OVERCALL_WEAK_3_LEVEL), shape(7), GOOD_PLUS_SUIT));
        bids.add(shows(Bid._3S, IS_SINGLE_JUMP, points(OVERCALL_WEAK_3_LEVEL), shape(7), GOOD_PLUS_SUIT));

        return bids;
    }

    public static Iterable<CallFeature> secondBid(PositionState ps) {
        return Advance.secondBid(ps);
    }
}
