package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;
import java.util.*;

public class KeyCards extends HandConstraint implements IShowsHand, IDescribeConstraint {
    private final Set<Integer> count;
    private final Suit trumpSuit;
    private final Boolean haveQueen;

    public KeyCards(Suit trumpSuit, Boolean haveQueen, int... count) {
        this.trumpSuit = trumpSuit;
        this.haveQueen = haveQueen;
        this.count = new HashSet<>();
        for (int c : count) {
            this.count.add(c);
        }
    }

    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        Set<Integer> keyCards = hs.getCountAces();
        if (trumpSuit != null) {
            if (haveQueen != null) {
                Boolean q = hs.getSuits().get(trumpSuit).getHaveQueen();
                if (q != null && !q.equals(haveQueen)) return false;
            }
            keyCards = hs.getSuits().get(trumpSuit).getKeyCards();
        }
        if (keyCards == null) return true;
        for (int c : count) {
            if (keyCards.contains(c)) return true;
        }
        return false;
    }

    @Override
    public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
        if (trumpSuit != null) {
            showHand.getSuits().get(trumpSuit).showKeyCards(count);
            if (haveQueen != null) {
                showHand.getSuits().get(trumpSuit).showHaveQueen(haveQueen);
            }
        } else {
            showHand.showCountAces(count);
        }
    }

    @Override
    public String describe(Call call, PositionState ps) {
        String s = (count.size() == 1 && count.contains(1)) ? "" : "s";
        if (trumpSuit != null) {
            String str = count.toString() + " key card" + s;
            if (haveQueen != null) {
                str += haveQueen ? " and queen" : " no queen";
            }
            return str;
        } else {
            return count.toString() + " Ace" + s;
        }
    }
}
