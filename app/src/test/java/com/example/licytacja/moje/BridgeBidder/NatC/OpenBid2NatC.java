package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.AcesAsk;

public class OpenBid2NatC extends OpenNatC {

    public static PositionCalls responderChangedSuits(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(AcesAsk.initiateConvention(ps));
        System.out.print("level sie rowna "+ ps.getPartner().getBid().toString());
        if ( ps.getPartner().getBid() != null && ps.getPartner().getBid().getLevel() == 2) {
            choices.addRules(partnerBids(RespondBid2NatC::secondBid));
        }
        choices.addRules(
                //properties(new Call[]{Bid._3S, Bid._3H}, RespondBid2NatC::secondBidToGame),
                //partnerBids(RespondBid2NatC::secondBid),

                shows(Bid._2S, IS_REBID, shape(6, 11), OpenBidding, id("OpenBid2NatC.responderChangedSuits _2S")),
                shows(Bid._2H, IS_REBID, shape(6, 11), OpenBidding, id("OpenBid2NatC.responderChangedSuits _2H")),

                shows(Bid._2S, IS_NEW_SUIT, shape(4, 11), id("OpenBid2NatC.responderChangedSuits _2S")),
                shows(Bid._2H, IS_NEW_SUIT, shape(4, 11), id("OpenBid2NatC.responderChangedSuits _2H")),

                shows(Bid._3S, IS_NEW_SUIT, shape(4, 11), pairHighCardPoints(PAIR_GAME), id("OpenBid2NatC.responderChangedSuits _3S")),
                shows(Bid._3H, IS_NEW_SUIT, shape(4, 11), pairHighCardPoints(PAIR_GAME), id("OpenBid2NatC.responderChangedSuits _3H"))

        );
        //choices.addRules(CompeteNatC::compBids);//todo ??
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
                propertiesAgreeTrump(new Call[]{Bid._3H, Bid._3S}, RespondBid2NatC::openerInvitedGame, false),
               // partnerBids(Bid._3S, RespondBid2NatC::openerInvitedGame),

                shows(Bid._3H, FIT_8_PLUS, pairPoints(PAIR_GAME_INVITE), id("OpenBid2NatC.responderRaisedMajor _3H")),
                shows(Bid._3S, FIT_8_PLUS, pairPoints(PAIR_GAME_INVITE), id("OpenBid2NatC.responderRaisedMajor _2S")),

                shows(Bid._4H, FIT_8_PLUS, pairPoints(PAIR_GAME), id("OpenBid2NatC.responderRaisedMajor _4H")),
                shows(Bid._4S, FIT_8_PLUS, pairPoints(PAIR_GAME), id("OpenBid2NatC.responderRaisedMajor _4S")),
                shows(Call.PASS, id("OpenBid2NatC.responderRaisedMajor _PASS"))
        );
        return choices;
    }

    public static PositionCalls responder1NT(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
                shows(Bid._2H, IS_REBID, shape(6, 11), points(12, 17), id("OpenBid2NatC.responder1NT _2H")),
                shows(Bid._2S, IS_REBID, shape(6, 11), points(12, 17), id("OpenBid2NatC.responder1NT _2S")),

                shows(Bid._2C, IS_NEW_SUIT, shape(5, 6), points(12, 17), id("OpenBid2NatC.responder1NT _2C")),
                shows(Bid._2D, IS_NEW_SUIT, shape(5, 6), points(12, 17), id("OpenBid2NatC.responder1NT _2D")),
                shows(Bid._2H, IS_NEW_SUIT, shape(5, 6), points(12, 17), id("OpenBid2NatC.responder1NT _2H")),
                shows(Bid._2S, IS_NEW_SUIT, shape(5, 6), points(12, 17), id("OpenBid2NatC.responder1NT _2S")),

                shows(Bid._3NT, pairHighCardPoints(PAIR_GAME), id("OpenBid2NatC.responder1NT _3NT")),
                shows(Call.PASS)
        );
        return choices;
    }
    public static PositionCalls responder2NT(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
                propertiesAgreeTrump(new Call[]{Bid._3H, Bid._3S}, RespondBid2NatC::openerInvitedGame, false),
              //  partnerBids(Bid._3S, RespondBid2NatC::openerInvitedGame),

                shows(Bid._3H, FIT_8_PLUS, pairPoints(PAIR_GAME_INVITE), id("OpenBid2NatC.responder2NT _3H")),
                shows(Bid._3S, FIT_8_PLUS, pairPoints(PAIR_GAME_INVITE), id("OpenBid2NatC.responder2NT _3S")),

                shows(Bid._3H, IS_NEW_SUIT, shape(5,10), pairPoints(PAIR_GAME_INVITE), id("OpenBid2NatC.responder2NT _3H")),
                shows(Bid._3S, IS_NEW_SUIT, shape(5,10), pairPoints(PAIR_GAME_INVITE), id("OpenBid2NatC.responder2NT _3S")),

                shows(Bid._3D, IS_NEW_SUIT, shape(5,10), id("OpenBid2NatC.responder2NT _3D")),
                shows(Bid._3C, IS_NEW_SUIT, shape(5,10), id("OpenBid2NatC.responder2NT _3C")),

                shows(Bid._3NT, FIT_8_PLUS, pairPoints(PAIR_GAME), id("OpenBid2NatC.responder2NT _3NT")),
                shows(Bid._3NT, FIT_8_PLUS, pairPoints(PAIR_GAME), id("OpenBid2NatC.responder2NT _3NT")),
                shows(Call.PASS, id("OpenBid2NatC.responder2NT _PASS"))
        );
        return choices;
    }
}
