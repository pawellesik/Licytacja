package com.example.licytacja;

import com.example.licytacja.moje.TestBridgeBidder.TestHand;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Uruchamiam testy z TestHand przez JUnitCore ===");
        
        // Sposób 1: Przez JUnitCore (zalecane)
        Result result = JUnitCore.runClasses(TestHand.class);
        
        for (Failure failure : result.getFailures()) {
            System.out.println("BŁĄD w " + failure.getTestHeader() + ": " + failure.getMessage());
        }
        
        if (result.wasSuccessful()) {
            System.out.println("SUKCES: Wszystkie testy w TestHand przeszły (Liczba: " + result.getRunCount() + ")");
        } else {
            System.out.println("PORAŻKA: Niektóre testy nie przeszły.");
        }

        System.out.println("\n=== Ręczne wywołanie logiki testu ===");
        // Sposób 2: Ręczne utworzenie instancji i wywołanie metody
        try {
            TestHand test = new TestHand();
            test.basic();
            System.out.println("Metoda basic() wykonana pomyślnie (ręcznie).");
        } catch (Throwable e) {
            System.err.println("Błąd podczas ręcznego wywołania: " + e.getMessage());
        }
    }

    @Test
    public void runMain() {
        main(new String[]{});
    }
}
