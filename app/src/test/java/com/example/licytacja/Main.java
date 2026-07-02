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
    private static java.io.PrintWriter out;

    public static void main(String[] args) {
        try {
            out = new java.io.PrintWriter(new java.io.FileWriter("C:/Users/plesik/AndroidStudioProjects/Licytacja/test_results.txt"));
            out.println("=== Uruchamiam testy przez JUnitCore ===");
            simpleTest();
            out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
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
        out.println("\nUruchamiam: " + clazz.getSimpleName());
        Result result = JUnitCore.runClasses(clazz);
        if (result.wasSuccessful()) {
            out.println("  Wszystkie " + result.getRunCount() + " testów OK.");
        } else {
            out.println("  BŁĘDY: " + result.getFailureCount() + " na " + result.getRunCount());
            for (Failure f : result.getFailures()) {
                out.println("    - " + f.getTestHeader() + ":\n" + f.getMessage());
            }
        }
    }
}
