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
        // Rozkład: Pik AKJ, Kier T92, Karo QJ8, Trefl 7654
        game.getDeal().put(Direction.N, Hand.parse("AKJ.T92.QJ8.AKQJ"));

        // 3. Ustawiamy rozdającego i inicjujemy stan licytacji
        game.dealer = Direction.N;
        BiddingState state = new BiddingState(game);

        // 4. Symulujemy licytację:
        state.makeCall(Call.parse("2NT")); // North otwiera
        state.makeCall(Call.PASS);        // East pasuje
        state.makeCall(Call.parse("4NT")); // South (partner) licytuje 1 Pik
        state.makeCall(Call.PASS);        // West pasuje
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
            System.out.println("Po licytacji partnera 2NT, AI wybiera: " + best.getCall());
            System.out.println("Uzasadnienie: " + best.getDescription(state.getNextToAct()));
        }

        // Weryfikacja
        assertNotNull(best);
    }
}
