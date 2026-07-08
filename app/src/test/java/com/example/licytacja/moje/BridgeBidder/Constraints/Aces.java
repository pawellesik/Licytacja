package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;
import java.util.*;

public class Aces extends HandConstraint implements IShowsHand, IDescribeConstraint {
    private final Set<Integer> count = new HashSet<>();

    public Aces(int... count) {
        for (int c : count) this.count.add(c);
    }

    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        Set<Integer> aces = hs.getCountAces();
        if (aces == null) return true;
        for (int c : count) {
            if (aces.contains(c)) return true;
        }
        return false;
    }

    @Override
    public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
        showHand.showCountAces(count);
    }

    @Override
    public String describe(Call call, PositionState ps) {
        return count.toString() + " Ace" + (count.size() == 1 && count.contains(1) ? "" : "s");
    }
}
