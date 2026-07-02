package com.example.licytacja.moje.BridgeBidder.Conventions;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.LCStandard.NoTrump;
import com.example.licytacja.moje.BridgeBidder.LCStandard.UserText;
import java.util.ArrayList;
import java.util.List;

public class TransferBidder extends NoTrump.OneNoTrumpBidder {
    public TransferBidder(NoTrump.NoTrumpDescription ntd) {
        super(ntd);
    }

    public static CallFeaturesFactory initiateConvention(NoTrump.NoTrumpDescription ntd) {
        return new TransferBidder(ntd)::initiate;
    }

    private Iterable<CallFeature> initiate(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(convention(UserText.JacobyTransfer));

        bids.add(properties(Bid._2D, true, UserText.TransferToHearts, true));
        bids.add(properties(Bid._2H, true, UserText.TransferToSpades, true));
        bids.add(properties(Bid._2S, true, UserText.TransferToClubs, true));

        bids.add(partnerBids((PositionCallsFactory) this::acceptTransfer));

        // For weak hands, transfer to longest major.
        bids.add(shows(Bid._2D, ntd.RR.lessThanInvite, shape(Suit.Hearts, 5, 11), longer(Suit.Hearts, Suit.Spades)));
        bids.add(shows(Bid._2D, ntd.RR.inviteGame, shape(Suit.Hearts, 5, 11), shape(Suit.Spades, 0, 5)));
        bids.add(shows(Bid._2D, ntd.RR.gameOrBetter, shape(Suit.Hearts, 5, 11), shape(Suit.Spades, 0, 4)));

        bids.add(shows(Bid._2H, ntd.RR.lessThanInvite, shape(Suit.Spades, 5, 11), longerOrEqual(Suit.Spades, Suit.Hearts)));
        bids.add(shows(Bid._2H, ntd.RR.inviteGame, shape(Suit.Spades, 5, 11), shape(Suit.Hearts, 0, 4)));
        bids.add(shows(Bid._2H, ntd.RR.gameOrBetter, shape(Suit.Spades, 5, 11)));

        bids.add(shows(Bid._2S, ntd.RR.lessThanInvite, shape(Suit.Clubs, 6, 11)));
        bids.add(shows(Bid._2S, ntd.RR.lessThanInvite, shape(Suit.Diamonds, 6, 11)));
        return bids;
    }

    private PositionCalls acceptTransfer(PositionState ps) {
        if (ps.getRHO().getBid() != null) {
            return ps.getPairState().getBiddingSystem().getPositionCalls(ps);
        }
        PositionCalls choices = new PositionCalls(ps);
        if (ps.getRHO().isDoubled()) {
            choices.addRules(
                partnerBids(Call.PASS, (CallFeaturesFactory) this::openerShowsTwo),
                shows(Call.PASS, partner(isLastBid(2, Suit.Diamonds)), shape(Suit.Hearts, 0, 2)),
                shows(Call.PASS, partner(isLastBid(2, Suit.Hearts)), shape(Suit.Spades, 0, 2)),
                shows(Bid._2H, partner(isLastBid(2, Suit.Diamonds)), shape(3, 5)),
                shows(Bid._2S, partner(isLastBid(2, Suit.Hearts)), shape(3, 5))
            );
        }
        choices.addRules(
            partnerBids((CallFeaturesFactory) this::explainTransfer),
            shows(Bid._3H, partner(isLastBid(2, Suit.Diamonds)), ntd.OR.superAccept, shape(4, 5)),
            shows(Bid._3S, partner(isLastBid(2, Suit.Hearts)), ntd.OR.superAccept, shape(4, 5)),
            shows(Bid._2H, partner(isLastBid(2, Suit.Diamonds))),
            shows(Bid._2S, partner(isLastBid(2, Suit.Hearts))),
            shows(Bid._3C, partner(isLastBid(2, Suit.Spades)))
        );
        return choices;
    }

    private Iterable<CallFeature> openerShowsTwo(PositionState ps) {
        Suit suit = ps.getLastCall().equals(Bid._2D) ? Suit.Hearts : Suit.Spades;
        List<CallFeature> bids = new ArrayList<>();
        bids.add(partnerBids((CallFeaturesFactory) this::openerRebid));
        bids.add(shows(new Bid(4, suit), ntd.RR.gameOrBetter, OPPS_NOT_STOPPED));
        bids.add(shows(new Bid(4, suit), shape(6, 10), ntd.RR.gameOrBetter));
        bids.add(shows(Bid._3NT, ntd.RR.gameOrBetter, OPPS_STOPPED));
        bids.add(shows(new Bid(3, suit), ntd.RR.inviteGame, OPPS_NOT_STOPPED));
        bids.add(shows(new Bid(3, suit), shape(6, 10), ntd.RR.inviteGame));
        bids.add(shows(Bid._2NT, ntd.RR.inviteGame, OPPS_STOPPED));
        bids.add(shows(new Bid(2, suit)));
        return bids;
    }

    private Iterable<CallFeature> explainTransfer(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(partnerBids((CallFeaturesFactory) this::openerRebid));
        bids.add(shows(Bid._2S, ntd.RR.inviteGame, shape(5, 11)));
        bids.add(properties(Bid._3H, true));
        bids.add(shows(Bid._3H, ntd.RR.gameOrBetter, partner(isLastBid(Bid._2S)), shape(5)));
        bids.add(shows(Bid._4H, ntd.RR.gameIfSuperAccept, partner(isLastBid(Bid._3H))));
        bids.add(shows(Bid._4S, ntd.RR.gameIfSuperAccept, partner(isLastBid(Bid._3S))));
        bids.add(shows(Bid._2NT, ntd.RR.inviteGame, partner(isLastBid(Bid._2H)), shape(Suit.Hearts, 5)));
        bids.add(shows(Bid._2NT, ntd.RR.inviteGame, partner(isLastBid(Bid._2S)), shape(Suit.Spades, 5)));
        bids.add(shows(Bid._3D, partner(isLastBid(Bid._3C)), shape(Suit.Diamonds, 6, 11)));
        bids.add(shows(Bid._3H, ntd.RR.inviteGame, partner(isLastBid(Bid._2H)), shape(6, 11)));
        bids.add(shows(Bid._3S, ntd.RR.inviteGame, partner(isLastBid(Bid._2S)), shape(6, 11)));
        bids.add(shows(Bid._3NT, ntd.RR.game, partner(isLastBid(Bid._2H)), shape(Suit.Hearts, 5)));
        bids.add(shows(Bid._3NT, ntd.RR.game, partner(isLastBid(Bid._2S)), shape(Suit.Spades, 5)));
        bids.add(shows(Bid._4H, ntd.RR.game, partner(isLastBid(Bid._2H)), shape(6, 11)));
        bids.add(shows(Bid._4S, ntd.RR.game, partner(isLastBid(Bid._2S)), shape(6, 11)));
        bids.add(shows(Bid._4NT, ntd.RR.inviteSlam, partner(isLastBid(Bid._2H)), shape(Suit.Hearts, 5)));
        bids.add(shows(Bid._4NT, ntd.RR.inviteSlam, partner(isLastBid(Bid._2S)), shape(Suit.Spades, 5)));
        bids.add(shows(Call.PASS, ntd.RR.lessThanInvite));
        return bids;
    }

    private Iterable<CallFeature> openerRebid(PositionState ps) {
        List<CallFeature> bids = new ArrayList<>();
        bids.add(shows(Call.PASS, isLastBid(Bid._3C), partner(isLastBid(Bid._3D))));
        bids.add(properties(Bid._3H, (PositionCallsFactory) this::placeGameContract, true, isLastBid(Bid._2S)));
        bids.add(properties(Bid._3S, (PositionCallsFactory) this::placeGameContract, true, isLastBid(Bid._2H)));
        bids.add(shows(Bid._3H, ntd.OR.acceptInvite, isLastBid(Bid._2S), shape(5), shape(Suit.Spades, 2)));
        bids.add(shows(Bid._3S, ntd.OR.acceptInvite, isLastBid(Bid._2H), shape(5), shape(Suit.Hearts, 2)));
        bids.add(shows(Bid._3H, ntd.OR.dontAcceptInvite, isLastBid(Bid._2H), shape(3, 5)));
        bids.add(shows(Bid._3S, ntd.OR.dontAcceptInvite, isLastBid(Bid._2S), shape(3, 5)));
        bids.add(shows(Bid._4H, ntd.OR.acceptInvite, fit()));
        bids.add(shows(Bid._4H, ntd.OR.acceptInvite, isLastBid(Bid._2H), partner(isLastBid(Bid._2NT)), shape(3, 5)));
        bids.add(shows(Bid._4H, isLastBid(Bid._2H), partner(isLastBid(Bid._3NT)), shape(3, 5)));
        bids.add(shows(Bid._4H, isLastBid(Bid._2S), partner(isLastBid(Bid._3H)), shape(3, 5), longerOrEqual(Suit.Hearts, Suit.Spades)));
        bids.add(shows(Bid._4S, ntd.OR.acceptInvite, partner(isLastBid(Bid._3S))));
        bids.add(shows(Bid._4S, ntd.OR.acceptInvite, isLastBid(Bid._2S), partner(isLastBid(Bid._2NT)), shape(3, 5)));
        bids.add(shows(Bid._4S, isLastBid(Bid._2S), partner(isLastBid(Bid._3NT)), shape(3, 5)));
        bids.add(shows(Bid._4S, isLastBid(Bid._2H), partner(isLastBid(Bid._3S)), shape(3, 5), longer(Suit.Spades, Suit.Hearts)));
        bids.add(shows(Bid._3NT, ntd.OR.acceptInvite));
        bids.add(shows(Bid._5H, partner(isLastBid(Bid._4NT)), fit(), ntd.OR.dontAcceptInvite));
        bids.add(shows(Bid._5S, partner(isLastBid(Bid._4NT)), fit(), ntd.OR.dontAcceptInvite));
        bids.add(shows(Bid._6H, partner(isLastBid(Bid._4NT)), fit(), ntd.OR.acceptInvite));
        bids.add(shows(Bid._6S, partner(isLastBid(Bid._4NT)), fit(), ntd.OR.acceptInvite));
        bids.add(shows(Bid._6NT, partner(isLastBid(Bid._4NT)), ntd.OR.acceptInvite));
        bids.add(shows(Call.PASS));
        return bids;
    }

    private PositionCalls placeGameContract(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(shows(Bid._4H, FIT_8_PLUS));
        choices.addRules(shows(Bid._4S, FIT_8_PLUS));
        choices.addRules(shows(Bid._3NT));
        return choices;
    }

    public static class Transfer2NT extends Bidder {
        private final NoTrump.TwoNoTrump ntb;

        public Transfer2NT(NoTrump.TwoNoTrump ntb) {
            this.ntb = ntb;
        }

        public Iterable<CallFeature> initiateConvention(PositionState ps) {
            List<CallFeature> bids = new ArrayList<>();
            bids.add(properties(Bid._3D, (PositionCallsFactory) this::acceptTransfer, true, UserText.TransferToHearts, true));
            bids.add(properties(Bid._3H, (PositionCallsFactory) this::acceptTransfer, true, UserText.TransferToSpades, true));
            bids.add(shows(Bid._3D, shape(Suit.Hearts, 5, 11), longer(Suit.Hearts, Suit.Spades)));
            bids.add(shows(Bid._3H, shape(Suit.Spades, 5, 11), longerOrEqual(Suit.Spades, Suit.Hearts)));
            return bids;
        }

        private PositionCalls acceptTransfer(PositionState ps) {
            PositionCalls choices = new PositionCalls(ps);
            choices.addRules(partnerBids((CallFeaturesFactory) this::explainTransfer));
            choices.addRules(shows(Bid._3H, partner(isLastBid(Bid._3D))));
            choices.addRules(shows(Bid._3S, partner(isLastBid(Bid._3H))));
            return choices;
        }

        private Iterable<CallFeature> explainTransfer(PositionState ps) {
            List<CallFeature> bids = new ArrayList<>();
            bids.add(partnerBids((CallFeaturesFactory) this::placeContract));
            bids.add(shows(Call.PASS, ntb.respondNoGame));
            bids.add(shows(Bid._3NT, ntb.respondGame, partner(isLastBid(Bid._3H)), shape(Suit.Hearts, 5)));
            bids.add(shows(Bid._3NT, ntb.respondGame, partner(isLastBid(Bid._3S)), shape(Suit.Spades, 5)));
            bids.add(shows(Bid._4H, ntb.respondGame, partner(isLastBid(Bid._3H)), shape(6, 11)));
            bids.add(shows(Bid._4S, ntb.respondGame, partner(isLastBid(Bid._3S)), shape(6, 11)));
            return bids;
        }

        private Iterable<CallFeature> placeContract(PositionState ps) {
            List<CallFeature> bids = new ArrayList<>();
            bids.add(shows(Bid._4H, FIT_8_PLUS));
            bids.add(shows(Bid._4S, FIT_8_PLUS));
            bids.add(shows(Call.PASS));
            return bids;
        }
    }

    public static class Transfer3NT extends Bidder {
        private final NoTrump.ThreeNoTrump ntb;

        public Transfer3NT(NoTrump.ThreeNoTrump ntb) {
            this.ntb = ntb;
        }

        public Iterable<CallFeature> initiateConvention(PositionState ps) {
            List<CallFeature> bids = new ArrayList<>();
            bids.add(properties(Bid._4D, (PositionCallsFactory) (p -> acceptTransfer(p, Strain.Hearts))));
            bids.add(partnerBids(Bid._4H, (PositionCallsFactory) (p -> acceptTransfer(p, Strain.Spades))));
            bids.add(shows(Bid._4D, shape(Suit.Hearts, 5, 11)));
            bids.add(shows(Bid._4H, shape(Suit.Spades, 5, 11)));
            return bids;
        }

        public PositionCalls acceptTransfer(PositionState ps, Strain strain) {
            PositionCalls choices = new PositionCalls(ps);
            choices.addRules(shows(new Bid(4, strain)));
            return choices;
        }
    }
}
