package com.example.licytacja.moje.BridgeBidder;

import java.util.*;

public class HandSummary extends State {
    public static class ShowState {
        private final HandSummary handSummary;
        private final Map<Suit, SuitSummary.ShowState> suits = new EnumMap<>(Suit.class);

        public ShowState() {
            this(null);
        }

        public ShowState(HandSummary startState) {
            this.handSummary = (startState == null) ? new HandSummary() : new HandSummary(startState);
            for (Suit suit : Suit.values()) {
                this.suits.put(suit, new SuitSummary.ShowState(handSummary, handSummary.suits.get(suit)));
            }
        }

        public HandSummary getHandSummary() {
            return handSummary;
        }

        public void showPoints(int min, int max) {
            handSummary.showPoints(min, max);
        }

        public void showIsBalanced(boolean isBalanced) {
            handSummary.isBalanced = combineBool(handSummary.isBalanced, isBalanced, CombineRule.Show);
        }

        public void combine(HandSummary other, CombineRule combineRule) {
            handSummary.combine(other, combineRule);
        }
    }

    public static class SuitSummary {
        public static class ShowState {
            private final HandSummary handSummary;
            private final SuitSummary suitSummary;

            public ShowState(HandSummary handSummary, SuitSummary suitSummary) {
                this.handSummary = handSummary;
                this.suitSummary = suitSummary;
            }

            public void showShape(int min, int max) {
                suitSummary.shape = combineRange(suitSummary.shape, new Range(min, max), CombineRule.Show);
            }

            public void showQuality(SuitQuality min, SuitQuality max) {
                suitSummary.quality = combineRange(suitSummary.quality, new Range(min.ordinal(), max.ordinal()), CombineRule.Show);
            }
        }

        private Range shape;
        private Range quality; // Store as range of ordinals

        public SuitSummary() {}

        public SuitSummary(SuitSummary other) {
            this.shape = other.shape;
            this.quality = other.quality;
        }

        public void combine(SuitSummary other, CombineRule cr) {
            this.shape = combineRange(this.shape, other.shape, cr);
            this.quality = combineRange(this.quality, other.quality, cr);
        }

        public Range getShape() {
            return shape != null ? shape : new Range(0, 13);
        }

        public Range getQuality() {
            return quality != null ? quality : new Range(SuitQuality.Poor.ordinal(), SuitQuality.Solid.ordinal());
        }

        public void trimShape(int claimed) {
            Range s = getShape();
            if (s.getMax() + claimed - s.getMin() > 13) {
                int newMax = 13 - claimed + s.getMin();
                this.shape = new Range(s.getMin(), newMax);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SuitSummary that = (SuitSummary) o;
            return Objects.equals(shape, that.shape) && Objects.equals(quality, that.quality);
        }

        @Override
        public int hashCode() {
            return Objects.hash(shape, quality);
        }
    }

    private Range points;
    private Range highCardPoints;
    private Boolean isBalanced;
    private final Map<Suit, SuitSummary> suits = new EnumMap<>(Suit.class);

    public HandSummary() {
        for (Suit suit : Suit.values()) {
            suits.put(suit, new SuitSummary());
        }
    }

    public HandSummary(HandSummary other) {
        this.points = other.points;
        this.highCardPoints = other.highCardPoints;
        this.isBalanced = other.isBalanced;
        for (Suit suit : Suit.values()) {
            suits.put(suit, new SuitSummary(other.suits.get(suit)));
        }
    }

    protected void showPoints(int min, int max) {
        this.points = combineRange(this.points, new Range(min, max), CombineRule.Show);
    }

    protected void combine(HandSummary other, CombineRule cr) {
        this.points = combineRange(this.points, other.points, cr);
        this.highCardPoints = combineRange(this.highCardPoints, other.highCardPoints, cr);
        this.isBalanced = combineBool(this.isBalanced, other.isBalanced, cr);
        for (Suit suit : Suit.values()) {
            this.suits.get(suit).combine(other.suits.get(suit), cr);
        }
        trimShape();
    }

    public void trimShape() {
        int claimed = 0;
        for (Suit suit : Suit.values()) {
            claimed += suits.get(suit).getShape().getMin();
        }
        for (Suit suit : Suit.values()) {
            suits.get(suit).trimShape(claimed);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandSummary that = (HandSummary) o;
        return Objects.equals(points, that.points) &&
                Objects.equals(highCardPoints, that.highCardPoints) &&
                Objects.equals(isBalanced, that.isBalanced) &&
                Objects.equals(suits, that.suits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points, highCardPoints, isBalanced, suits);
    }

    public Map<Suit, SuitSummary> getSuits() {
        return suits;
    }
}
