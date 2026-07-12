package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.Gerber;
import com.example.licytacja.moje.BridgeBidder.Conventions.StaymanBidder;
import com.example.licytacja.moje.BridgeBidder.Conventions.TransferBidder;
import com.example.licytacja.moje.BridgeBidder.LCStandard.NoTrump;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;

import java.util.ArrayList;
import java.util.List;

public class NoTrumpNatC extends Bidder {

    public static class Open1NTDescription extends NoTrump.Open1NTDescription {
    }

    public static class Balancing1NTDescription extends NoTrump.Balancing1NTDescription {
    }

    public static class OneNoTrumpBidderNatC extends Bidder {
        public static final OneNoTrumpBidderNatC OPEN = new OneNoTrumpBidderNatC(new Open1NTDescription());

        protected final NoTrump.NoTrumpDescription ntd;

        protected OneNoTrumpBidderNatC(NoTrump.NoTrumpDescription ntd) {
            this.ntd = ntd;
        }

        public static Iterable<CallFeature> open(PositionState ps) {
            return OPEN.bids(ps);
        }

        public Iterable<CallFeature> bids(PositionState ps) {
            List<CallFeature> bids = new ArrayList<>();
            bids.add(properties(Bid._1NT, (PositionCallsFactory) this::conventionalResponses, false, UserText.OneNoTrumpRange, true));
            bids.add(shows(Bid._1NT, ntd.OR.open, BALANCED));
            return bids;
        }

        protected PositionCalls conventionalResponses(PositionState ps) {
            if (ps.getRHO().getLastCall() instanceof Bid && !ps.getRHO().getLastCall().equals(Bid._2C)) {
                return ps.getPairState().getBiddingSystem().getPositionCalls(ps);
            }
            PositionCalls choices = new PositionCalls(ps);
            choices.addRules(StaymanBidder.initiateConvention(ntd));
            choices.addRules(TransferBidder.initiateConvention(ntd));
            choices.addRules(Gerber::initiateConvention);
            choices.addRules(Natural1NTNatC.respond(ntd));
            return choices;
        }
    }

    public static class Natural1NTNatC extends OneNoTrumpBidderNatC {
        public Natural1NTNatC(NoTrump.NoTrumpDescription ntd) {
            super(ntd);
        }

        public static CallFeaturesFactory respond(NoTrump.NoTrumpDescription ntd) {
            return new Natural1NTNatC(ntd)::naturalResponse;
        }

        private Iterable<CallFeature> naturalResponse(PositionState ps) {
            List<CallFeature> bids = new ArrayList<>();
            bids.add(partnerBids((PositionCallsFactory) this::openerRebid));
            bids.add(partnerBids(Bid._4NT, CompeteNatC::compBids));

            bids.add(shows(Bid._2C, shape(5, 11), ntd.RR.lessThanInvite));
            bids.add(shows(Bid._2D, shape(5, 11), ntd.RR.lessThanInvite));
            bids.add(shows(Bid._2H, shape(5, 11), ntd.RR.lessThanInvite));
            bids.add(shows(Bid._2S, shape(5, 11), ntd.RR.lessThanInvite));

            bids.add(shows(Bid._2NT, ntd.RR.inviteGame, longestMajor(4)));

            bids.add(properties(Bid._3H, true));
            bids.add(properties(Bid._3S, true));
            bids.add(shows(Bid._3H, ntd.RR.gameOrBetter, shape(5, 11)));
            bids.add(shows(Bid._3S, ntd.RR.gameOrBetter, shape(5, 11)));

            bids.add(shows(Bid._3NT, ntd.RR.game, longestMajor(4)));

            bids.add(shows(Bid._4NT, ntd.RR.inviteSlam));

            bids.add(shows(Bid._6NT, FLAT, ntd.RR.smallSlam));
            bids.add(shows(Bid._6NT, BALANCED, shape(Suit.Hearts, 2, 3), shape(Suit.Spades, 2, 3), ntd.RR.smallSlam));

            bids.add(shows(Call.PASS, ntd.RR.lessThanInvite));
            return bids;
        }

        private PositionCalls openerRebid(PositionState ps) {
            PositionCalls choices = new PositionCalls(ps);
            choices.addRules(partnerBids((CallFeaturesFactory) this::responderRebid));

            choices.addRules(shows(Call.PASS, partner(isLastBid(Bid._3NT))));
            choices.addRules(shows(Call.PASS, ntd.OR.dontAcceptInvite, partner(isLastBid(Bid._2NT))));
            choices.addRules(shows(Call.PASS, partner(isLastBid(Bid._2C))));
            choices.addRules(shows(Call.PASS, partner(isLastBid(Bid._2D))));
            choices.addRules(shows(Call.PASS, partner(isLastBid(Bid._2H))));
            choices.addRules(shows(Call.PASS, partner(isLastBid(Bid._2S))));

            choices.addRules(properties(Bid._3H, true));
            choices.addRules(properties(Bid._3S, true));
            choices.addRules(shows(Bid._3H, partner(isLastBid(Bid._2NT)), ntd.OR.acceptInvite, shape(5)));
            choices.addRules(shows(Bid._3S, partner(isLastBid(Bid._2NT)), ntd.OR.acceptInvite, shape(5)));

            choices.addRules(shows(Bid._3NT, ntd.OR.acceptInvite, partner(isLastBid(Bid._2NT))));
            choices.addRules(shows(Bid._3NT, partner(isLastBid(Bid._3H)), shape(Suit.Hearts, 0, 2)));
            choices.addRules(shows(Bid._3NT, partner(isLastBid(Bid._3S)), shape(Suit.Spades, 0, 2)));

            choices.addRules(shows(Bid._4H, partner(isLastBid(Bid._3H)), shape(3, 5)));
            choices.addRules(shows(Bid._4S, partner(isLastBid(Bid._3S)), shape(3, 5)));

            return choices;
        }

        private Iterable<CallFeature> responderRebid(PositionState ps) {
            List<CallFeature> bids = new ArrayList<>();
            bids.add(shows(Bid._3NT, partner(isLastBid(Bid._3H)), shape(Suit.Hearts, 0, 2)));
            bids.add(shows(Bid._3NT, partner(isLastBid(Bid._3S)), shape(Suit.Spades, 0, 2)));

            bids.add(shows(Bid._4H, partner(isLastBid(Bid._3H)), shape(3, 4)));
            bids.add(shows(Bid._4S, partner(isLastBid(Bid._3S)), shape(3, 4)));

            bids.add(shows(Call.PASS));
            return bids;
        }
    }

    public static Iterable<CallFeature> open(PositionState ps) {
        return OneNoTrumpBidderNatC.open(ps);
    }
}
