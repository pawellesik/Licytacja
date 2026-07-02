package com.example.licytacja.moje.BridgeBidder;

import java.util.*;

public class PositionState {
    private int roleAssignedOffset = 0;
    private boolean roleAssigned = false;
    private HandSummary privateHandSummary;
    private final List<CallDetails> bids = new ArrayList<>();
    private final BiddingState biddingState;
    private final PairState pairState;
    private final Direction direction;
    private final int seat;
    private PositionRole role;
    private HandSummary publicHandSummary;

    public PositionState(BiddingState biddingState, PairState pairState, Direction direction, int seat, Hand hand) {
        this.biddingState = biddingState;
        this.pairState = pairState;
        this.direction = direction;
        this.seat = seat;
        this.role = PositionRole.Opener;
        this.publicHandSummary = new HandSummary();
        
        if (hand != null) {
            HandSummary.ShowState showHand = new HandSummary.ShowState();
            HandEvaluator.StandardHandEvaluator.evaluate(hand, showHand);
            this.privateHandSummary = showHand.getHandSummary();
        } else {
            this.privateHandSummary = null;
        }
    }

    public BiddingState getBiddingState() {
        return biddingState;
    }

    public boolean isForcedToBid() {
        return pairState.isForcedToBid(this) && !getRHO().isPassed(); // C# says !RightHandOpponent._bids.Last().Equals(Call.Pass)
    }

    public boolean isPassed() {
        return bids.isEmpty() || bids.get(bids.size() - 1).getCall().equals(Call.PASS);
    }

    public boolean isDoubled() {
        return !bids.isEmpty() && bids.get(bids.size() - 1).getCall().equals(Call.DOUBLE);
    }

    public PairState getOppsPairState() {
        return getRHO().getPairState();
    }

    public boolean isPassedHand() {
        return !bids.isEmpty() && bids.get(0).getCall().equals(Call.PASS);
    }

    public boolean hasHand() {
        return privateHandSummary != null;
    }

    public int getBidRound() {
        return bids.size() + 1;
    }

    public Direction getDirection() {
        return direction;
    }

    public PairState getPairState() {
        return pairState;
    }

    public PositionRole getRole() {
        return role;
    }

    public void setRole(PositionRole role) {
        this.role = role;
    }

    public int getRoleRound() {
        return getBidRound() - roleAssignedOffset;
    }

    public HandSummary getPublicHandSummary() {
        return publicHandSummary;
    }

    public PositionState getPartner() {
        return biddingState.getPositions().get(direction.partner());
    }

    public PositionState getRHO() {
        return biddingState.getPositions().get(direction.rightHandOpponent());
    }

    public PositionState getLHO() {
        return biddingState.getPositions().get(direction.leftHandOpponent());
    }

    public PositionCalls getPositionCalls() {
        PositionState partner = getPartner();
        PositionCallsFactory bidFactory = null;
        if (partner.bids.size() > 0) {
            bidFactory = partner.bids.get(partner.bids.size() - 1).getBidsFactory();
        }
        if (bidFactory != null) {
            return bidFactory.apply(this);
        }
        return pairState.getBiddingSystem().getPositionCalls(this);
    }

    public boolean isValidNextCall(Call call) {
        // TODO: Port ContractState logic
        return true;
    }

    public void makeCall(CallDetails callDetails) {
        // TODO: Port logic for updating role, pairState, etc.
        if (!callDetails.getCall().equals(Call.PASS) && !this.roleAssigned) {
            if (role == PositionRole.Opener) {
                assignRole(PositionRole.Opener);
                getPartner().assignRole(PositionRole.Responder);
                getLHO().setRole(PositionRole.Overcaller);
                getRHO().setRole(PositionRole.Overcaller);
            } else if (this.role == PositionRole.Overcaller) {
                assignRole(PositionRole.Overcaller);
                getPartner().assignRole(PositionRole.Advancer);
            }
        }
        bids.add(callDetails);
        pairState.updatePairProperties(callDetails);
        repeatUpdatesUntilStable(callDetails);
        pairState.updateLastShownSuit(callDetails.getCall(), this, callDetails.showHand());
    }

    private void assignRole(PositionRole role) {
        this.role = role;
        this.roleAssigned = true;
        this.roleAssignedOffset = bids.size();
    }

    public boolean repeatUpdatesUntilStable(CallDetails callDetails) {
        boolean stateChanged = false;
        for (int i = 0; i < 1000; i++) {
            stateChanged |= callDetails.pruneRules(this);
            HandSummary.ShowState showHand = new HandSummary.ShowState(publicHandSummary);
            showHand.combine(callDetails.showHand(), State.CombineRule.Merge);
            if (this.publicHandSummary.equals(showHand.getHandSummary())) {
                return stateChanged;
            }
            stateChanged = true;
            pairState.updateShownSuits(callDetails.getCall(), this, showHand.getHandSummary());
            publicHandSummary = showHand.getHandSummary();
        }
        return false;
    }

    public boolean privateHandConforms(BidRule rule) {
        return hasHand() && rule.satisfiesHandConstraints(this, this.privateHandSummary);
    }

    public List<Constraint> privateHandFailingConstraints(BidRule rule) {
        if (!hasHand()) return new ArrayList<>();
        return rule.failingHandConstraints(this, this.privateHandSummary);
    }

    public int getSeat() {
        return seat;
    }

    public boolean isVulnerable() {
        return pairState.areVulnerable();
    }

    public boolean isOpponentsContract() {
        return biddingState.getContract().isOpponents(this.direction);
    }

    public boolean isOurContract() {
        return biddingState.getContract().isOurs(this.direction);
    }

    public boolean isReverse(Call call) {
        // TODO
        return false;
    }

    public Call getLastCall() {
        return getBidHistory(0);
    }

    public Call getBidHistory(int historyLevel) {
        if (bids.size() <= historyLevel) {
            return null;
        }
        return bids.get(bids.size() - 1 - historyLevel).getCall();
    }

    public int getCallCount() {
        return bids.size();
    }

    public CallDetails getCallDetails(int index) {
        return bids.get(index);
    }

    public boolean updateBidIndex(int bidIndex, boolean[] updateHappened) {
        if (bidIndex >= bids.size()) {
            updateHappened[0] = false;
            return false;
        }
        updateHappened[0] = repeatUpdatesUntilStable(this.bids.get(bidIndex));
        return true;
    }
}
