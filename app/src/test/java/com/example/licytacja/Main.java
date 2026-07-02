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
        Result result = JUnitCore.runClasses(TestHand.class);
        result = JUnitCore.runClasses(TestStrain.class);
        result = JUnitCore.runClasses(TestStrain.class);
        result = JUnitCore.runClasses(TestDirection.class);
        result = JUnitCore.runClasses(TestGame.class);
        result = JUnitCore.runClasses(RandomStress.class);
        result = JUnitCore.runClasses(PBNUtils.class);
        result = JUnitCore.runClasses(PBNTest.class);
        result = JUnitCore.runClasses(InvalidArguments.class);
        result = JUnitCore.runClasses(BiddingTests.class);
    }
}
