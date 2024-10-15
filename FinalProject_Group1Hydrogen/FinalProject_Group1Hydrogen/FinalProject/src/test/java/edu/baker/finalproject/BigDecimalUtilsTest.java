/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.baker.finalproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.MathContext;



/**
 *
 * @author abulm
 */

class BigDecimalUtilsTest {

    private static final MathContext MC = new MathContext(15);
    private static final double DELTA = 1e-10;
    
    @Test
    void testRecip() {
        BigDecimal x = new BigDecimal("2");
        BigDecimal expected = new BigDecimal("0.5");
        BigDecimal actual = BigDecimalUtils.recip(x, MC);
        assertTrue(expected.subtract(actual).abs().doubleValue() < DELTA, "recip(2) should be 0.5");
    }

    @ParameterizedTest
    @CsvSource({
        "2.7, 2.0",
        "-2.7, -3.0",
        "0.5, 0.0",
        "10.0, 10.0"
    })
    void testFloor(String input, String expected) {
        BigDecimal x = new BigDecimal(input);
        BigDecimal expectedValue = new BigDecimal(expected);
        BigDecimal actualValue = BigDecimalUtils.floor(x, MC);
        assertTrue(expectedValue.compareTo(actualValue) == 0, 
                   "floor(" + input + ") should be " + expected);
    }

    @ParameterizedTest
    @CsvSource({
        "2.1, 3.0",
        "-2.9, -2.0",
        "0.5, 1.0",
        "10.0, 10.0"
    })
    void testCeil(String input, String expected) {
        BigDecimal x = new BigDecimal(input);
        BigDecimal expectedValue = new BigDecimal(expected);
        BigDecimal actualValue = BigDecimalUtils.ceil(x, MC);
        assertTrue(expectedValue.compareTo(actualValue) == 0, 
                   "ceil(" + input + ") should be " + expected);
    }

    @ParameterizedTest
    @CsvSource({
        "-2.5, -2.0",   
        "2.5, 3.0",     
        "-1.5, -1.0",   
        "1.5, 2.0"      
    })
    void testRound(String input, String expected) {
        BigDecimal x = new BigDecimal(input);
        BigDecimal expectedValue = new BigDecimal(expected);
        BigDecimal actualValue = BigDecimalUtils.round(x, MC);
        assertTrue(expectedValue.compareTo(actualValue) == 0, 
                   "round(" + input + ") should be " + expected);
    }

    @ParameterizedTest
    @CsvSource({
        "2.7, 2.0",
        "-2.7, -2.0",
        "0.5, 0.0",
        "10.0, 10.0"
    })
    void testTrunc(String input, String expected) {
        BigDecimal x = new BigDecimal(input);
        BigDecimal expectedValue = new BigDecimal(expected);
        BigDecimal actualValue = BigDecimalUtils.trunc(x, MC);
        assertTrue(expectedValue.compareTo(actualValue) == 0, 
                   "trunc(" + input + ") should be " + expected);
    }

    @Test
    void testExpZero() {
        assertEquals(BigDecimal.ONE, BigDecimalUtils.exp(BigDecimal.ZERO, MC), "exp(0) should be 1");
    }

    @Test
    void testExpOne() {
        BigDecimal expected = new BigDecimal("2.718281828459045");
        BigDecimal actual = BigDecimalUtils.exp(BigDecimal.ONE, MC);
        assertTrue(expected.subtract(actual).abs().doubleValue() < DELTA, "exp(1) should be close to e");
    }

    @ParameterizedTest
    @CsvSource({
        "2, 7.389056098930650",
        "-1, 0.367879441171442",
        "10, 22026.465794806718",
        "-5, 0.006737946999085",
        "0.5, 1.648721270700128"
    })
    void testExpVariousInputs(String input, String expected) {
        BigDecimal x = new BigDecimal(input);
        BigDecimal expectedValue = new BigDecimal(expected);
        BigDecimal actualValue = BigDecimalUtils.exp(x, MC);
        assertTrue(expectedValue.subtract(actualValue).abs().doubleValue() < DELTA, 
                   "exp(" + input + ") should be close to " + expected);
    }

    @Test
    void testExpLargePositive() {
        BigDecimal result = BigDecimalUtils.exp(new BigDecimal("100"), MC);
        assertTrue(result.compareTo(new BigDecimal("2.688117141816135e+43")) > 0, "exp(100) should be very large");
    }

    @Test
    void testExpLargeNegative() {
        BigDecimal result = BigDecimalUtils.exp(new BigDecimal("-100"), MC);
        assertTrue(result.compareTo(BigDecimal.ZERO) > 0 && result.compareTo(new BigDecimal("1e-43")) < 0, 
                   "exp(-100) should be very close to zero but positive");
    }

    @Test
    void testExpPrecision() {
        MathContext highPrecision = new MathContext(50);
        BigDecimal result = BigDecimalUtils.exp(BigDecimal.ONE, highPrecision);
        assertEquals(50, result.precision(), "Result should have the precision specified by MathContext");
    }
}