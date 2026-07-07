package com.example.licytacja.moje;

import com.example.licytacja.moje.BridgeBidder.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class Plesik {

    @Test
    public void testAILicytacja() {
        // 1. Tworzymy obiekt gry
        Game game = new Game();

        // 2. Podajemy karty dla N i S
        game.getDeal().put(Direction.N, Hand.parse("A985432.2.KJT9.2"));
        game.getDeal().put(Direction.S, Hand.parse("KJT9.AKQ.AQ432.A"));

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
            
            // Jeśli kolej na N lub S (nasza para AI)
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
            } 
            // Jeśli kolej na przeciwników (E lub W) - automatycznie PASUJEMY
            else {
                System.out.println(turn + " licytuje: Pass");
                state.makeCall(Call.PASS);
            }
        }

        // 5. Wyświetlamy ostateczny kontrakt
        System.out.println("\n--- Koniec licytacji ---");
        System.out.println("Finalny kontrakt: " + state.getContract().toString());
    }
}
