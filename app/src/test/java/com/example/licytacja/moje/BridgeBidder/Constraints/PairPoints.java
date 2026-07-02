package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class PairPoints {
    protected final boolean useStartingPoints;
    protected final boolean useAgreedStrain;
    protected final Suit suit;
    protected final int min;
    protected final int max;

    public PairPoints(Suit suit, int min, int max) {
        this.useStartingPoints = false;
        this.useAgreedStrain = false;
        this.suit = suit;
        this.min = min;
        this.max = max;
    }

    public PairPoints(int min, int max) {
        this.useStartingPoints = false;
        this.useAgreedStrain = true;
        this.suit = null;
        this.min = min;
        this.max = max;
    }

    public Suit getSuit(PositionState ps, Call call) {
        if (useAgreedStrain) {
            return ps.getPairState().getLastShownSuit();
        }
        return Constraint.getSuit(this.suit, call);
    }

    public Range getPoints(Call call, PositionState ps, HandSummary hs) {
        Range points = hs.getStartingPoints();
        Suit s = getSuit(ps, call);
        if (!useStartingPoints && s != null) {
            PositionState firstToShow = ps.getPairState().firstToShow(s);
            if (firstToShow == ps) {
                points = hs.getSuits().get(s).getLongHandPoints();
            } else if (firstToShow != null) {
                points = hs.getSuits().get(s).getDummyPoints();
            }
        }
        if (points == null) {
            points = hs.getPoints();
            if (!useStartingPoints && points != null) {
                points = new Range(points.getMin(), points.getMax() + 8);
            }
        }
        return points != null ? points : new Range(0, 100);
    }

    public boolean dynamicallyConforms(Call call, PositionState ps, HandSummary hs) {
        Range posPoints = getPoints(call, ps, hs);
        Range partnerPoints = getPoints(call, ps.getPartner(), ps.getPartner().getPublicHandSummary());
        return (posPoints.getMax() + partnerPoints.getMin() >= min && posPoints.getMin() + partnerPoints.getMin() <= max);
    }

    public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
        Range pointsPartner = getPoints(call, ps.getPartner(), ps.getPartner().getPublicHandSummary());
        int showMin = Math.max(min - pointsPartner.getMin(), 0);
        int showMax = Math.max(max - pointsPartner.getMin(), 0);
        Suit s = getSuit(ps, call);
        PositionState firstToShow = s == null ? null : ps.getPairState().firstToShow(s);
        if (useStartingPoints || firstToShow == null) {
            showHand.showStartingPoints(showMin, showMax);
        } else if (firstToShow == ps) {
            showHand.getSuits().get(s).showLongHandPoints(showMin, showMax);
        } else {
            showHand.getSuits().get(s).showDummyPoints(showMin, showMax);
        }
    }

    public static class PairHasShownPoints extends StaticConstraint {
        private final PairPoints pairPoints;
        public PairHasShownPoints(Suit suit, int min, int max) {
            this.pairPoints = new PairPoints(suit, min, max);
        }
        @Override
        public boolean conforms(Call call, PositionState ps) {
            Range posPoints = pairPoints.getPoints(call, ps, ps.getPublicHandSummary());
            Range partnerPoints = pairPoints.getPoints(call, ps.getPartner(), ps.getPartner().getPublicHandSummary());
            int minP = posPoints.getMin() + partnerPoints.getMin();
            return (minP >= pairPoints.min && minP <= pairPoints.max);
        }
    }

    public static class PairShowsPoints extends HandConstraint implements IShowsHand, IDescribeConstraint {
        private final PairPoints pairPoints;
        public PairShowsPoints(Suit suit, int min, int max) {
            this.pairPoints = new PairPoints(suit, min, max);
        }
        public PairShowsPoints(int min, int max) {
            this.pairPoints = new PairPoints(min, max);
        }
        @Override
        public boolean conforms(Call call, PositionState ps, HandSummary hs) {
            return pairPoints.dynamicallyConforms(call, ps, hs);
        }
        @Override
        public void showHand(Call call, PositionState ps, HandSummary.ShowState showHand) {
            pairPoints.showHand(call, ps, showHand);
        }
        @Override
        public String describe(Call call, PositionState ps) {
            return pairPoints.min + (pairPoints.min == pairPoints.max ? "" : "-" + pairPoints.max) + " pair points";
        }
    }
}
