package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;
import java.util.*;

public class PairKeyCards extends HandConstraint implements IDescribeConstraint {
    private final int[] count;
    private final Suit trumpSuit;
    private final Boolean hasQueen;

    public PairKeyCards(Suit trumpSuit, Boolean hasQueen, int... count) {
        this.trumpSuit = trumpSuit;
        this.hasQueen = hasQueen;
        this.count = count;
    }

    @Override
    public boolean conforms(Call call, PositionState ps, HandSummary hs) {
        Set<Integer> ourKeyCards = hs.getCountAces();
        Set<Integer> partnerKeyCards = ps.getPartner().getPublicHandSummary().getCountAces();
        Boolean partnerHasQueen = null;
        Boolean weHaveQueen = null;

        if (trumpSuit != null) {
            ourKeyCards = hs.getSuits().get(trumpSuit).getKeyCards();
            weHaveQueen = hs.getSuits().get(trumpSuit).getHaveQueen();
            partnerKeyCards = ps.getPartner().getPublicHandSummary().getSuits().get(trumpSuit).getKeyCards();
            partnerHasQueen = ps.getPartner().getPublicHandSummary().getSuits().get(trumpSuit).getHaveQueen();
        }

        if (ourKeyCards == null) {
            if (partnerKeyCards == null) return true;
            return Arrays.stream(count).max().orElse(0) >= partnerKeyCards.stream().min(Integer::compare).orElse(0);
        }
        if (partnerKeyCards == null) {
            return Arrays.stream(count).max().orElse(0) >= ourKeyCards.stream().min(Integer::compare).orElse(0);
        }

        if (hasQueen != null) {
            if (!hasQueen && (Boolean.TRUE.equals(weHaveQueen) || Boolean.TRUE.equals(partnerHasQueen))) return false;
            if (hasQueen && (Boolean.FALSE.equals(weHaveQueen) && Boolean.FALSE.equals(partnerHasQueen))) return false;
        }

        for (int ourCount : ourKeyCards) {
            for (int pCount : partnerKeyCards) {
                for (int c : count) {
                    if (c == ourCount + pCount) return true;
                }
            }
        }
        return false;
    }

    @Override
    public String describe(Call call, PositionState ps) {
        String s = (count.length == 1 && count[0] == 1) ? "" : "s";
        String countStr = Arrays.toString(count).replace("[", "").replace("]", "").replace(",", " or");
        if (trumpSuit != null) {
            String str = countStr + " key card" + s;
            if (hasQueen != null) {
                str += hasQueen ? " and queen" : " no queen";
            }
            return str;
        } else {
            return countStr + " Ace" + s;
        }
    }
}
