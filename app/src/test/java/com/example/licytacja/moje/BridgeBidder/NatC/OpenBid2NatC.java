package com.example.licytacja.moje.BridgeBidder.NatC;

import com.example.licytacja.moje.BridgeBidder.*;
import com.example.licytacja.moje.BridgeBidder.Conventions.Blackwood;

import java.util.ArrayList;
import java.util.List;

public class OpenBid2NatC extends OpenNatC {

    public static PositionCalls responderChangedSuits(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
                partnerBids(RespondBid2NatC::secondBid),

                // Responder bid a major suits and we have a fit.
                shows(Bid._2H, raisePartner(), DummyMinimum),
                shows(Bid._2S, raisePartner(), DummyMinimum),
                shows(Bid._3H, raisePartner(null, 1, 8), DummyMedium),
                shows(Bid._3S, raisePartner(null, 1, 8), DummyMedium),
                shows(Bid._4H, raisePartner(null, 2, 8), DummyMaximum),
                shows(Bid._4S, raisePartner(null, 2, 8), DummyMaximum),

                shows(Bid._1H, shape(4, 6)),
                shows(Bid._1S, shape(4, 6)),

                shows(Bid._2D, raisePartner(), Minimum),
                shows(Bid._3D, raisePartner(null, 1, 8), Medium),

                shows(Bid._2NT, BALANCED, points(Rebid2NT)),

                properties(new Bid[]{Bid._2D, Bid._2H, Bid._2S}, true, IS_REVERSE_BID),
                shows(Bid._2D, IS_REVERSE_BID, REVERSE_SHAPE, MediumOrBetter),
                shows(Bid._2H, IS_REVERSE_BID, REVERSE_SHAPE, MediumOrBetter),
                shows(Bid._2S, IS_REVERSE_BID, REVERSE_SHAPE, MediumOrBetter),

                shows(Bid._2H, IS_NEW_SUIT, IS_NOT_REVERSE, NOT_BALANCED, Minimum, shape(4, 6)),
                shows(Bid._2C, IS_NEW_SUIT, NOT_BALANCED, CantJumpShift, shape(4, 6)),
                shows(Bid._2D, IS_NEW_SUIT, IS_NOT_REVERSE, NOT_BALANCED, CantJumpShift, shape(4, 6)),

                shows(Bid._2C, IS_REBID, shape(6, 11), Minimum),
                shows(Bid._2D, IS_REBID, shape(6, 11), Minimum),
                shows(Bid._2H, IS_REBID, shape(6, 11), Minimum),
                shows(Bid._2S, IS_REBID, shape(6, 11), Minimum),

                shows(Bid._3C, IS_REBID, shape(6, 11), Medium),
                shows(Bid._3D, IS_REBID, shape(6, 11), Medium),
                shows(Bid._3H, IS_REBID, shape(6, 11), Medium),
                shows(Bid._3S, IS_REBID, shape(6, 11), Medium),

                shows(Bid._2H, isLastBid(Bid._1S), shape(4, 6), points(LessThanJumpShift)),
                shows(Bid._3H, isLastBid(Bid._1S), shape(4, 5), points(JumpShift)),

                propertiesForcingToGame(new Call[]{Bid._2H, Bid._2S, Bid._3C, Bid._3D, Bid._3H, Bid._3S}, true, IS_JUMP_SHIFT),
                shows(Bid._2H, IS_JUMP_SHIFT, shape(4, 6), points(JumpShift)),
                shows(Bid._2S, IS_JUMP_SHIFT, shape(4, 6), points(JumpShift)),
                shows(Bid._3C, IS_JUMP_SHIFT, shape(4, 6), points(JumpShift)),
                shows(Bid._3D, IS_JUMP_SHIFT, shape(4, 6), points(JumpShift)),
                shows(Bid._3H, IS_JUMP_SHIFT, shape(4, 6), points(JumpShift)),
                shows(Bid._3S, IS_JUMP_SHIFT, shape(4, 6), points(JumpShift)),

                shows(Bid._4H, IS_REBID, EXCELLENT_PLUS_SUIT, shape(7, 11), points(20, 21)),
                shows(Bid._3H, IS_REBID, shape(6, 11), points(17, 19)),
                shows(Bid._4S, IS_REBID, EXCELLENT_PLUS_SUIT, shape(7, 11), points(20, 21)),
                shows(Bid._3S, IS_REBID, shape(6, 11), points(17, 19)),

                shows(Bid._1NT, BALANCED, highCardPoints(12, 14), points(Rebid1NT))
        );
        return choices;
    }

    public static PositionCalls twoOverOne(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        Suit partnerSuit = ((Bid) ps.getPartner().getLastCall()).getSuit();
        choices.addRules(
                partnerBids(RespondBid2NatC::secondBid2Over1),

                properties(new Bid[]{Bid._3H, Bid._3S}, null, false, false, true, null, null, null, null, IS_REBID),
                shows(Bid._3H, IS_REBID, shape(7), EXCELLENT_PLUS_SUIT),
                shows(Bid._3H, IS_REBID, shape(8), GOOD_PLUS_SUIT),
                shows(Bid._3H, IS_REBID, shape(9, 11)),
                shows(Bid._3S, IS_REBID, shape(7), EXCELLENT_PLUS_SUIT),
                shows(Bid._3S, IS_REBID, shape(8), GOOD_PLUS_SUIT),
                shows(Bid._3S, IS_REBID, shape(9, 11)),

                properties(new Call[]{Bid._3H, Bid._4H}, null, false, false, true, null, null, null, null, isPartnersSuit()),
                shows(Bid._3H, isPartnersSuit(), shape(3, 7), points(14, 40)),
                shows(Bid._4H, isPartnersSuit(), shape(3, 7), points(12, 13)),

                shows(Bid._2H, IS_NEW_SUIT, shape(4, 6)),
                shows(Bid._2S, IS_NEW_SUIT, shape(4, 6)),

                shows(new Bid(3, partnerSuit), FIT_8_PLUS),

                shows(Bid._2NT, BALANCED),

                shows(Bid._2D, IS_REBID, shape(6, 11), LONGEST_SUIT),
                shows(Bid._2H, IS_REBID, shape(6, 8), LONGEST_SUIT),
                shows(Bid._2S, IS_REBID, shape(6, 8), LONGEST_SUIT),
                shows(Bid._3C, IS_REBID, shape(6, 11), LONGEST_SUIT),

                shows(Bid._2D, IS_NEW_SUIT, shape(4, 6)),
                shows(Bid._3C, IS_NEW_SUIT, shape(4, 6)),
                shows(Bid._3D, IS_NEW_SUIT, IS_NON_JUMP, shape(4, 6))
        );
        return choices;
    }

    public static PositionCalls responderPassedInCompetition(PositionState ps) {
        PositionCalls choices = new PositionCalls(ps);
        choices.addRules(
                shows(Bid._2C, IS_REBID, shape(6, 11), Minimum),
                shows(Bid._2D, IS_REBID, shape(6, 11), Minimum),
                shows(Bid._2H, IS_REBID, shape(6, 11), Minimum),
                shows(Bid._2S, IS_REBID, shape(6, 11), Minimum),

                shows(Bid._3C, IS_REBID, shape(6, 11), MediumOrBetter),
                shows(Bid._3D, IS_REBID, shape(6, 11), MediumOrBetter),
                shows(Bid._3H, IS_REBID, shape(6, 11), MediumOrBetter),
                shows(Bid._3S, IS_REBID, shape(6, 11), MediumOrBetter),

                shows(Call.PASS)
        );
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

    public static PositionCalls oneNTOverMajorOpen(PositionState ps) {
        return responderChangedSuits(ps);
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

    public static PositionCalls responderRaisedNT(PositionState ps) {
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
