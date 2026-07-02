package com.example.licytacja.moje.BridgeBidder;

import java.util.ArrayList;
import java.util.List;

public class CallDetails {
    private final Call call;
    private final List<CallAnnotation> annotations = new ArrayList<>();
    private final List<BidRule> rules = new ArrayList<>();
    private final CallGroup group;

    public CallDetails(CallGroup group, Call call) {
        this.group = group;
        this.call = call;
    }

    public Call getCall() {
        return call;
    }

    public List<CallAnnotation> getAnnotations() {
        return annotations;
    }

    public CallGroup getGroup() {
        return group;
    }

    public boolean hasRules() {
        return !rules.isEmpty();
    }

    public void add(CallFeature feature) {
        if (feature instanceof BidRule) {
            rules.add((BidRule) feature);
        } else if (feature instanceof CallAnnotation) {
            annotations.add((CallAnnotation) feature);
        }
        // TODO: Handle CallProperties if needed
    }

    public PositionState getPositionState() {
        return group.getPositionState();
    }

    public boolean pruneRules(PositionState ps) {
        List<BidRule> newRules = new ArrayList<>();
        for (BidRule rule : rules) {
            if (rule.satisfiesHandConstraints(ps, ps.getPublicHandSummary())) {
                newRules.add(rule);
            }
        }
        if (newRules.size() == rules.size()) return false;
        rules.clear();
        rules.addAll(newRules);
        return true;
    }

    public HandSummary showHand() {
        if (!hasRules()) return getPositionState().getPublicHandSummary();
        HandSummary.ShowState showHand = new HandSummary.ShowState();
        boolean firstRule = true;
        for (BidRule rule : rules) {
            HandSummary hs = rule.showHand(getPositionState());
            showHand.combine(hs, firstRule ? State.CombineRule.Show : State.CombineRule.CommonOnly);
            firstRule = false;
        }
        return showHand.getHandSummary();
    }

}
