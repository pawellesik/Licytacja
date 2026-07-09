package com.example.licytacja.moje;

import com.example.licytacja.moje.BridgeBidder.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class Plesik {

    @Test
    public void testAILicytacja() {
        // 1. Tworzymy obiekt gry
        Game game = new Game();


        game.getDeal().put(Direction.N, Hand.parse("K9854.QJ2.AKT9.2"));

        game.getDeal().put(Direction.S, Hand.parse("AJT9.AQJ.K5432.2"));// 18hpc

        // 3. Konfiguracja licytacji
        game.dealer = Direction.N;
        game.bidSystemNS = "NatC";
        game.bidSystemEW = "NatC";
        //game.bidSystemNS = "LC-Basic";
        //game.bidSystemEW = "LC-Basic";

        BiddingState state = new BiddingState(game);

        System.out.println("AI North trzyma rękę: " + game.getDeal().get(Direction.N));
        System.out.println("AI South trzyma rękę: " + game.getDeal().get(Direction.S));
        System.out.println("--- Rozpoczynamy licytację ---\n");

        // 4. Pętla licytacji aż do końca (3 pasy)
        while (!state.getContract().isAuctionComplete()) {
            Direction turn = state.getNextToAct().getDirection();

            if (turn == Direction.N || turn == Direction.S) {
                PositionCalls choices = state.getCallChoices();
                CallDetails best = choices.getBestCall();
                
                if (best == null) {
                    System.err.println("BŁĄD: AI " + turn + " nie wie co zalicytować!");
                    break;
                }
                
                System.out.println(turn + " licytuje: " + best.getCall());
                System.out.println("   [Uzasadnienie: " + best.getDescription(state.getNextToAct()) + "]");
                state.makeCall(best);
                printPublicKnowledge(state);
            }
            else {
                state.makeCall(Call.PASS);
            }
        }

        // 5. Wyświetlamy ostateczny kontrakt
        System.out.println("\n--- Koniec licytacji ---");
        System.out.println("Finalny kontrakt: " + state.getContract().toString());
        System.out.println("\n------------------------------");
    }

    private void printPublicKnowledge(BiddingState state) {
        System.out.println("   --- WIEDZA PUBLICZNA ---");
        for (Direction d : Direction.values()) {
            PositionState pos = state.getPositions().get(d);
            if (pos == null) continue;
            HandSummary summary = pos.getPublicHandSummary();
            if (summary == null) continue;
            
            StringBuilder sb = new StringBuilder();
            
            Range p = summary.getPoints();
            if (p != null && p.getMin() > 0) {
                sb.append("Pkt: ").append(p.getMin()).append("-").append(p.getMax()).append(" ");
            }
            
            for (Suit s : Suit.values()) {
                HandSummary.SuitSummary suitSum = summary.getSuits().get(s);
                if (suitSum != null) {
                    Range shape = suitSum.getShape();
                    if (shape != null && shape.getMin() > 0) {
                        sb.append(s.toSymbol()).append(":").append(shape.getMin()).append("+ ");
                    }
                }
            }
            
            if (summary.getCountAces() != null && !summary.getCountAces().isEmpty()) {
                sb.append("Asy: ").append(summary.getCountAces()).append(" ");
            }
            if (summary.getCountKings() != null && !summary.getCountKings().isEmpty()) {
                sb.append("króle: ").append(summary.getCountKings()).append(" ");
            }

            if (sb.length() > 0) {
                System.out.println("   " + d + ": " + sb.toString());
            }
        }
        
        // Wyświetlamy uzgodnione atuty dla obu par (NS i EW)
        Suit nsTrump = state.getPositions().get(Direction.N).getPairState().getTrumpSuit();
        if (nsTrump != null) System.out.println("   UZGODNIONY ATUT NS: " + nsTrump.toSymbol());

        System.out.println("   ------------------------");
    }
}
