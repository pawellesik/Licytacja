package com.example.licytacja;

import com.example.licytacja.moje.TestBridgeBidder.BiddingTests;
import com.example.licytacja.moje.TestBridgeBidder.InvalidArguments;
import com.example.licytacja.moje.TestBridgeBidder.PBNTest;
import com.example.licytacja.moje.TestBridgeBidder.PBNUtils;
import com.example.licytacja.moje.TestBridgeBidder.RandomStress;
import com.example.licytacja.moje.TestBridgeBidder.TestDirection;
import com.example.licytacja.moje.TestBridgeBidder.TestGame;
import com.example.licytacja.moje.TestBridgeBidder.TestHand;
import com.example.licytacja.moje.TestBridgeBidder.TestStrain;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Uruchamiam testy z TestHand przez JUnitCore ===");
        simpleTest();

    }

    @Test
    public void runMain() {
        main(new String[]{});
    }

    private static void simpleTest(){
        runAndPrint(TestHand.class);
        runAndPrint(TestStrain.class);
        runAndPrint(TestDirection.class);
        runAndPrint(TestGame.class);
        runAndPrint(BiddingTests.class);
    }

    private static void runAndPrint(Class<?> clazz) {
        System.out.println("\nUruchamiam: " + clazz.getSimpleName());
        Result result = JUnitCore.runClasses(clazz);
        if (result.wasSuccessful()) {
            System.out.println("  Wszystkie " + result.getRunCount() + " testów OK.");
        } else {
            System.out.println("  BŁĘDY: " + result.getFailureCount() + " na " + result.getRunCount());
            for (Failure f : result.getFailures()) {
                System.out.println("    - " + f.getTestHeader() + ": " + f.getMessage());
            }
        }
    }
}
