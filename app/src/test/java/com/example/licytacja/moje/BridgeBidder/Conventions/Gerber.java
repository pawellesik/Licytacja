package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import java.util.ArrayList;
import java.util.List;

public class Gerber extends Bidder {
    public static final StaticConstraint APPLIES = new SimpleStaticConstraint((call, ps) -> {
        Call partnerLast = ps.getPartner().getBidHistory(0);
        if (partnerLast instanceof Bid) {
            Bid bid = (Bid) partnerLast;
            return bid.getStrain() == Strain.NoTrump && bid.getLevel() < 3;
        }
        return false;
    }, "partner's last bid was 1NT or 2NT");

    public static Iterable<CallFeature> initiateConvention(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(properties(Bid._4C, Gerber::respondAces, true, UserText.Gerber));
        bids.add(shows(Bid._4C, APPLIES, pairPoints(31, 100)));
        return bids;
    }

    private static PositionCalls respondAces(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(propertiesForcingToGame(new Call[]{Bid._4D, Bid._4H, Bid._4S, Bid._4NT}, Gerber::placeContract, true));
        choices.addRules(shows(Bid._4D, aces(0, 4)));
        choices.addRules(shows(Bid._4H, aces(1)));
        choices.addRules(shows(Bid._4S, aces(2)));
        choices.addRules(shows(Bid._4NT, aces(3)));
        return choices;
    }

    private static PositionCalls placeContract(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(shows(Bid._7NT, pairPoints(36, 100))); // Simplified
        choices.addRules(shows(Bid._6NT));
        return choices;
    }
}
