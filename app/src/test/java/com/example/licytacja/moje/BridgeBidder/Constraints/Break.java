package com.example.licytacja.moje.BridgeBidder.Constraints;

import com.example.licytacja.moje.BridgeBidder.*;

public class Break {
    public static class HandBreak extends HandConstraint {
        private final String name;
        public int countPublic = 0;
        public int countPrivate = 0;

        public HandBreak(String name) {
            this.name = name;
        }

        @Override
        public boolean conforms(Call call, PositionState ps, HandSummary hs) {
            PairSummary pairSummary = new PairSummary(ps);
            PairSummary oppsSummary = PairSummary.opponents(ps);
            if (hs == ps.getPublicHandSummary()) {
                countPublic++;
            } else {
                countPrivate++;
            }
            return true;
        }

        public String getName() {
            return name;
        }
    }

    public static class StaticBreak extends StaticConstraint {
        private final String name;

        public StaticBreak(String name) {
            this.name = name;
        }

        @Override
        public boolean conforms(Call call, PositionState ps) {
            return true;
        }

        public String getName() {
            return name;
        }
    }
}
