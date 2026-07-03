package com.example.licytacja.moje;

import com.example.licytacja.moje.BridgeBidder.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class Plesik {

    @Test
    public void testAILicytacja() {
        // 1. Tworzymy obiekt gry
        Game game = new Game();

        // 2. Podajemy karty tylko dla N (North).
        game.getDeal().put(Direction.N, Hand.parse("AKJT9.AKJ.J872.8"));

        // 3. Ustawiamy rozdającego i inicjujemy stan licytacji
        game.dealer = Direction.N;
        game.bidSystemNS = "NatC";
        game.bidSystemEW = "NatC";

        BiddingState state = new BiddingState(game);

        //state.makeCall(Call.PASS);
        //state.makeCall(Call.PASS);
        //state.makeCall(Call.parse("2NT")); // North otwiera
        //state.makeCall(Call.parse("4NT")); // South (partner) licytuje 1 Pik
        //state.makeCall(Call.PASS);        // West pasuje
        //state.makeCall(Call.parse("1NT"));// North licytuje 1NT
        //state.makeCall(Call.PASS);        // East pasuje
        //state.makeCall(Call.parse("2NT"));// South licytuje 2NT (Inwit)
        //state.makeCall(Call.PASS);        // West pasuje

        // 5. Pobieramy decyzję AI (North)
        PositionCalls choices = state.getCallChoices();
        CallDetails best = choices.getBestCall();

        // Wyświetlamy wynik w konsoli podczas testu
        System.out.println("AI North trzyma rękę: " + game.getDeal().get(Direction.N));
        if (best != null) {
            System.out.println("AI wybiera: " + best.getCall());
            System.out.println("Uzasadnienie: " + best.getDescription(state.getNextToAct()));
        }

        // Weryfikacja
        assertNotNull(best);
    }
}
