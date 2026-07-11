package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.AcesAsk;

public class OpenBid2NatC extends OpenNatC {

    public static PositionCalls responderChangedSuits(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(AcesAsk.initiateConvention(ps));
        choices.addRules(
                //properties(new Call[]{Bid._3S, Bid._3H}, RespondBid2NatC::secondBidToGame),
                partnerBids(RespondBid2NatC::secondBid),

                shows(Bid._2S, IS_REBID, shape(6, 11), OpenBidding),
                shows(Bid._2H, IS_REBID, shape(6, 11), OpenBidding),

                shows(Bid._2S, IS_NEW_SUIT, shape(4, 11)),
                shows(Bid._2H, IS_NEW_SUIT, shape(4, 11)),

                shows(Bid._3S, IS_NEW_SUIT, shape(4, 11), pairHighCardPoints(PAIR_GAME)),
                shows(Bid._3H, IS_NEW_SUIT, shape(4, 11), pairHighCardPoints(PAIR_GAME))

        );
        //choices.addRules(CompeteNatC::compBids);//todo ??
        return choices;
    }

    public static PositionCalls semiForcingNT(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
                shows(Call.PASS, BALANCED, points(12, 13)),

                shows(Bid._2NT, BALANCED, highCardPoints(18, 19), points(19, 20)),

                shows(Bid._2C, IS_NEW_SUIT, shape(4, 6), points(12, 16)),
                shows(Bid._2D, IS_NEW_SUIT, shape(4, 6), points(12, 16)),
                shows(Bid._2H, IS_NEW_SUIT, shape(4, 6), points(12, 16)),

                shows(Bid._2C, IS_REBID, shape(6, 11), points(12, 16)),
                shows(Bid._2D, IS_REBID, shape(6, 11), points(12, 16)),
                shows(Bid._2H, IS_REBID, shape(6, 11), points(12, 16)),
                shows(Bid._2S, IS_REBID, shape(6, 11), points(12, 16))
        );
        return choices;
    }

    public static PositionCalls responderRaisedMinor(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(CompeteNatC::compBids);
        return choices;
    }

    public static PositionCalls responderRaisedMajor(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
                com.example.licytacja.moje.BridgeBidder.Conventions.Blackwood.initiateConvention(ps),
                partnerBids(Bid._3H, RespondBid2NatC::openerInvitedGame),
                partnerBids(Bid._3S, RespondBid2NatC::openerInvitedGame),

                shows(Bid._3H, FIT_8_PLUS, pairPoints(PAIR_GAME_INVITE)),
                shows(Bid._3S, FIT_8_PLUS, pairPoints(PAIR_GAME_INVITE)),

                shows(Bid._4H, FIT_8_PLUS, pairPoints(PAIR_GAME)),
                shows(Bid._4S, FIT_8_PLUS, pairPoints(PAIR_GAME)),
                shows(Call.PASS)
        );
        return choices;
    }

    public static PositionCalls responder1NT(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
                shows(Bid._2H, IS_REBID, shape(6, 11), points(12, 16)),
                shows(Bid._2S, IS_REBID, shape(6, 11), points(12, 16)),

                shows(Bid._2C, IS_NEW_SUIT, shape(5, 6), points(12, 17)),
                shows(Bid._2D, IS_NEW_SUIT, shape(5, 6), points(12, 17)),
                shows(Bid._2H, IS_NEW_SUIT, shape(5, 6), points(12, 17)),
                shows(Bid._2S, IS_NEW_SUIT, shape(5, 6), points(12, 17)),

                shows(Bid._3NT, pairPoints(PAIR_GAME)),
                shows(Call.PASS)
        );
        return choices;
    }
    public static PositionCalls responder2NT(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
                partnerBids(Bid._3H, RespondBid2NatC::openerInvitedGame),
                partnerBids(Bid._3S, RespondBid2NatC::openerInvitedGame),

                shows(Bid._3H, FIT_8_PLUS, pairPoints(PAIR_GAME_INVITE)),
                shows(Bid._3S, FIT_8_PLUS, pairPoints(PAIR_GAME_INVITE)),

                shows(Bid._3H, IS_NEW_SUIT, shape(5,10), pairPoints(PAIR_GAME_INVITE)),
                shows(Bid._3S, IS_NEW_SUIT, shape(5,10), pairPoints(PAIR_GAME_INVITE)),

                shows(Bid._3D, IS_NEW_SUIT, shape(5,10)),
                shows(Bid._3C, IS_NEW_SUIT, shape(5,10)),

                shows(Bid._3NT, FIT_8_PLUS, pairPoints(PAIR_GAME)),
                shows(Bid._3NT, FIT_8_PLUS, pairPoints(PAIR_GAME)),
                shows(Call.PASS)
        );
        return choices;
    }
}
