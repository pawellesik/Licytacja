package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;
import java.util.*;

public class SumPairAcesAndKings extends HandConstraint implements IDescribeConstraint {
    private final int[] counts;

    public SumPairAcesAndKings(int... counts) {
        this.counts = counts;
    }

    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        Set<Integer> ourAces = hs.getCountAces();
        Set<Integer> ourKings = hs.getCountKings();
        Set<Integer> partnerAces = ps.getPartner().getPublicHandSummary().getCountAces();
        Set<Integer> partnerKings = ps.getPartner().getPublicHandSummary().getCountKings();

        // If any piece of information is missing, we conservatively return true to avoid blocking.
        if (ourAces == null || ourKings == null || partnerAces == null || partnerKings == null) {
            return true;
        }

        for (int myA : ourAces) {
            for (int myK : ourKings) {
                for (int pA : partnerAces) {
                    for (int pK : partnerKings) {
                        int total = myA + myK + pA + pK;
                        for (int c : counts) {
                            if (total == c) return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String describe(Call call, PositionState ps) {
        return Arrays.toString(counts) + " total Aces and Kings in pair";
    }
}
