package com.example.licytacja.moje.BridgeBidder.LCStandard;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.Gerber;
import com.example.licytacja.moje.BridgeBidder.Conventions.StaymanBidder;
import com.example.licytacja.moje.BridgeBidder.Conventions.TransferBidder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NoTrump extends Bidder {

    public static class NoTrumpDescription {
        public static class OpenerRanges {
            public Constraint open;
            public Constraint dontAcceptInvite;
            public Constraint acceptInvite;
            public Constraint lessThanSuperAccept;
            public Constraint superAccept;
        }

        public static class ResponderRanges {
            public Constraint lessThanInvite;
            public Constraint inviteGame;
            public Constraint inviteOrBetter;
            public Constraint game;
            public Constraint gameOrBetter;
            public Constraint gameIfSuperAccept;
            public Constraint inviteSlam;
            public Constraint smallSlam;
            public Constraint grandSlam;

            public Constraint gameAsDummy;
            public Constraint inviteAsDummy;
            public Constraint smallSlamAsDummy;
            public Constraint grandSlamAsDummy;
        }

        public String openType;
        public OpenerRanges OR = new OpenerRanges();
        public ResponderRanges RR = new ResponderRanges();
    }

    public static class Open1NTDescription extends NoTrumpDescription {
        public Open1NTDescription() {
            openType = "Open1NT";
            OR.open = and(highCardPoints(15, 17), points(15, 18));
            OR.dontAcceptInvite = and(highCardPoints(15, 15), points(15, 16));
            OR.acceptInvite = and(highCardPoints(16, 17), points(16, 18));
            OR.lessThanSuperAccept = and(highCardPoints(15, 16), points(15, 17));
            OR.superAccept = and(highCardPoints(17, 17), points(17, 18));

            RR.lessThanInvite = points(0, 7);
            RR.inviteGame = points(8, 9);
            RR.inviteOrBetter = points(8, 40);
            RR.game = points(10, 15);
            RR.gameOrBetter = points(10, 40);
            RR.gameIfSuperAccept = points(6, 15);
            RR.inviteSlam = points(16, 17);
            RR.smallSlam = points(18, 19);
            RR.grandSlam = points(20, 40);
        }
    }

    public static class OneNoTrumpBidder extends Bidder {
        public static final OneNoTrumpBidder OPEN = new OneNoTrumpBidder(new Open1NTDescription());

        protected final NoTrumpDescription ntd;

        protected OneNoTrumpBidder(NoTrumpDescription ntd) {
            this.ntd = ntd;
        }

        public Iterable<CallFeature> bids(PositionState ps) {
            List<CallFeature> bids = new ArrayList<>();
            if (ps.getRole() == PositionRole.Opener && ps.getRoleRound() == 1) {
                bids.add(properties(Bid._1NT, this::conventionalResponses, true, UserText.OneNoTrumpRange));
                bids.add(shows(Bid._1NT, ntd.OR.open, BALANCED));
            }
            return bids;
        }

        private PositionCalls conventionalResponses(PositionState ps) {
            PositionCalls choices = new PositionCalls(ps);
            choices.addRules(StaymanBidder.initiateConvention(ntd));
            choices.addRules(TransferBidder.initiateConvention(ntd));
            choices.addRules(Gerber::initiateConvention);
            return choices;
        }
    }

    public static Iterable<CallFeature> open(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        for (CallFeature cf : OneNoTrumpBidder.OPEN.bids(ps)) {
            bids.add(cf);
        }
        return bids;
    }

    public static Iterable<CallFeature> strongOvercall(PositionState ps) {
        return new ArrayList<>();
    }

    public static Iterable<CallFeature> balancingOvercall(PositionState ps) {
        return new ArrayList<>();
    }
}
