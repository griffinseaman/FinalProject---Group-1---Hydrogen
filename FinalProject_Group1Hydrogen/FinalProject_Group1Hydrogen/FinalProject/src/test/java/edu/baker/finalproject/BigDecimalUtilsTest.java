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
import java.math.RoundingMode;



/**
 *
 * @author abulm
 */

class BigDecimalUtilsTest {

    private static final MathContext MC = new MathContext(34, RoundingMode.HALF_UP);
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
        "-2.5, -3.0",   
        "2.5, 3.0",     
        "-1.5, -2.0",   
        "1.5, 2.0"      
    })
    void testRound(String input, String expected) {
        BigDecimal x = new BigDecimal(input);
        BigDecimal expectedValue = new BigDecimal(expected);
        // Don't use MC here, we're testing round() precision
        BigDecimal actualValue = BigDecimalUtils.round(x, new MathContext(0, RoundingMode.HALF_UP));
        assertTrue(expectedValue.compareTo(actualValue) == 0, 
                   "round(" + input + ") = " + actualValue + " should be " + expected);
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
        assertEquals(1, BigDecimalUtils.exp(BigDecimal.ZERO, MC).doubleValue(), "exp(0) should be 1");
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
                   "exp(" + input + ") should be close to " + expected +", but was: " + actualValue);
    }

    @Test
    void testExpLargePositive() {
        BigDecimal result = BigDecimalUtils.exp(new BigDecimal("100"), MC);
        System.out.println(result.toEngineeringString());
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

    /**
     * Parameterized test for ipow(BigDecimal base, BigDecimal power, MathContext mc).
     * 
     * @param base The base
     * @param power The exponent
     * @param expected The expected result to test against
     * @author Noah Wood
     */
    @ParameterizedTest
    @CsvSource({
        "2, 3, 8.000000000", // Normal case: 2^3 = 8
        "5, 0, 1.000000000", // Edge case: any number to the power of 0 is 1
        "0, 3, 0.000000000", // Edge case: 0^n = 0 for n > 0
        "10, -1, 0.100000000", // Negative power: 10^-1 = 0.1
        "-2, 3, -8.000000000", // Odd power with negative base: (-2)^3 = -8
        "-2, 2, 4.000000000",// Even power with negative base: (-2)^2 = 4
        "2, -2, 0.250000000", // Negative power: 2^-2 = 0.25
        "0, 0, 1.000000000", // Edge case: 0^0 is commonly defined as 1
        "27, 0.333333333, 1.000000000",// Fractional power: cube root of 27 is 3 BUT 
        // there are no fractions in integer power, should round to 27^0 = 1
        "1.000001, 1000000, 2.718280470"  // Very large powers
    })
    public void testIpowDecimal(BigDecimal base, BigDecimal power, double expected){
        assertEquals(expected,
                BigDecimalUtils.ipow(base, power, MC).doubleValue(),
                1e-9);
    }
    
    /**
     * Parameterized test for ipow(BigDecimal base, long ipower, MathContext mc).
     *  
     * @param base The base
     * @param power The exponent
     * @param expected The expected result to test against
     * @author Noah Wood
     */
    @ParameterizedTest
    @CsvSource({
        "2, 3, 8.000000000", // Normal case
        "5, 0, 1.000000000", // Any number to the power of 0
        "0, 3, 0.000000000", // 0^n = 0 for n > 0
        "-2, 2, 4.000000000",// Even power with negative base
        "-2, 3, -8.000000000", // Odd power with negative base
        "10, -1, 0.100000000",// Negative power
        "0, 0, 1.000000000", // 0^0 is defined as 1
        "1.000001, 1000000, 2.718280470"  // Very large powers
    })
    public void testIpowLong(BigDecimal base, long ipower, double expected) {
        assertEquals(expected, 
                BigDecimalUtils.ipow(base, ipower, MC).doubleValue(),
                1e-9);
    }
        
    /**
     * Parameterized test for iroot(BigDecimal base, BigDecimal root, MathContext mc).
     *  
     * @param base The base
     * @param root The root
     * @param expected The expected result to test against
     * @author Noah Wood
     */
    @ParameterizedTest
    @CsvSource({
        "8, 3, 2.00", // Cube root of 8
        "9, 2, 3.00", // Square root of 9
        "27, 3, 3.00",// Cube root of 27
        "16, 4, 2.00",// Fourth root of 16
        "7, 1, 7.00", // 1th root of n is always n
        "1, 5, 1.00", // nth root of 1 is always 1
        "0, 3, 0.00", // nth root of 0 is 0
        "-8, 3, -2.00", // Cube root of negative number (-8) is -2
        "8, 0, 1.00", // Base with root = 0, assumed 1 as a rule
        "0.01, 2, 0.10" // Small fractional roots: sqrt(0.01) = 0.1
    })
    public void testIrootDecimal(BigDecimal base, BigDecimal root, double expected) {
        assertEquals(expected, 
                BigDecimalUtils.iroot(base, root, MC).doubleValue(), 
                1e-9);
    }
    
    /**
     * Parameterized test for iroot(BigDecimal base, long iroot, MathContext mc).
     *  
     * @param base The base
     * @param root The root
     * @param expected The expected result to test against
     * @author Noah Wood
     */
    @ParameterizedTest
    @CsvSource({
        "8, 3, 2.00", // Cube root of 8
        "9, 2, 3.00", // Square root of 9
        "27, 3, 3.00",// Cube root of 27
        "16, 4, 2.00",// Fourth root of 16
        "7, 1, 7.00", // 1th root of n is always n
        "1, 5, 1.00", // nth root of 1 is always 1
        "0, 3, 0.00", // nth root of 0 is 0
        "-8, 3, -2.00", // Cube root of negative number (-8) is -2
        "8, 0, 1.00", // Base with root = 0, assumed 1 as a rule
        "0.01, 2, 0.10" // Small fractional roots: sqrt(0.01) = 0.1
    })
    public void testIrootLong(BigDecimal base, long iroot, double expected) {
        assertEquals(expected, 
                BigDecimalUtils.iroot(base, iroot, MC).doubleValue(),
                1e-9);
    }
    
    /**
     * Parameterized test for pow(BigDecimal base, BigDecimal power, MathContext mc).
     *  
     * @param base The base
     * @param power The exponent
     * @param expected The expected result to test against
     * @author Noah Wood
     */
    @ParameterizedTest
    @CsvSource({
        "2, 3, 8", // Normal case: 2^3 = 8
        "5, 0, 1", // Any number to the power of 0
        "0, 5, 0", // 0^5 = 0
        "7, -1, 0.14285714285714288", // Negative powers
        "3, 0.5, 1.732050807568874", // Fractional power: sqrt(3)
        "-2, 2, 4", // Even power with negative base
        "-2, 3, -8", // Odd power with negative base
        "-2, -2, 0.25", // Negative even power with negative base
    })
    public void testPow(BigDecimal base, BigDecimal power, double expected) {
        assertEquals(expected, 
                BigDecimalUtils.pow(base, power, MC).doubleValue(),
                1e-9);
    }
    
    /**
     * Parameterized test for log10(BigDecimal x, MathContext mc).
     *  
     * @param x The test input
     * @param expected The expected result to test against
     * @author Noah Wood
     */
    @ParameterizedTest
    @CsvSource({
        "100, 2",
        "10, 1",
        "0.1, -1",
        "0.01, -2",
    })
    public void testLog10(BigDecimal x, BigDecimal expected){
        assertEquals(expected, BigDecimalUtils.log10(x, MC));
    }    
    
    /**
     * Parameterized test for exp10(BigDecimal x, MathContext mc).
     * 
     * @param x The exponent
     * @param expected The expected result to test against
     * @author Noah Wood
     */
    @ParameterizedTest
    @CsvSource({
        "1, 10",
        "2, 100",
        "0, 1",
        "0.1, 1.25892541"
    })
    public void testExp10(BigDecimal x, double expected){
        assertTrue(Math.abs(expected - BigDecimalUtils.exp10(x, MC).doubleValue()) <= 1e9);
    }
    
    /**
     * Parameterized test for acos(BigDecimal x, MathContext mc).
     *  
     * @param x The test input
     * @param expected The expected result to test against
     * @author Noah Wood
     */
    @ParameterizedTest
    @CsvSource({
        "1, 0",
        "-1, 3.141592654",
        "0.5, 1.047197551",
    })
    public void testAcos(BigDecimal x, double expected){
        assertEquals(expected,
               BigDecimalUtils.acos(x, MC).doubleValue(),
               1e-9);
    }

    /**
    * Test for sin method in BigDecimalUtils.
    * @author Yordanos Shiferaw
    */
    @Test
    public void testSin() {
        //Call the BigDecimalUtils.sin method to compute sine
        BigDecimal actual = BigDecimalUtils.sin(new BigDecimal("0.5235987755982989"), MC);
        BigDecimal expected = new BigDecimal("0.5");
        
        //Compare the actual result with the expected result
        assertEquals(expected.doubleValue(), actual.doubleValue(), 1e-9);
    }
    
    /**
    * Test for cos method in BigDecimalUtils.
    * @author Yordanos Shiferaw
    */
    @Test
    public void testCos() {
        //Call the BigDecimalUtils.cos method to compute sine
        BigDecimal actual = BigDecimalUtils.cos(new BigDecimal(Math.PI / 3), MC);
        BigDecimal expected = new BigDecimal("0.5");
        
        //Compare the actual result with the expected result
        assertEquals(expected.doubleValue(), actual.doubleValue(), 1e-9);
    }
    
    /**
    * Test for tan method in BigDecimalUtils.
    * @author Yordanos Shiferaw
    */
    @Test
    public void testTan() {
        //Call the BigDecimalUtils.cos method to compute cosine
        BigDecimal actual = BigDecimalUtils.tan(new BigDecimal(Math.PI), MC);
        BigDecimal expected = new BigDecimal("0");

        //Compare the actual result with the expected result using a tolerance level
        assertEquals(expected.doubleValue(), actual.doubleValue(), 1e-9);
    }
    
    /**
    * Test for log method in BigDecimalUtils.
    */
    @ParameterizedTest
    @CsvSource({
        "1, 0",
        "2.71828182845, 1",
        "0.3678794412, -1",
        "7.3890560989, 2",
    })
    public void testLog(BigDecimal x, BigDecimal expected){
        BigDecimal actual = BigDecimalUtils.log(x, MC);
        assertEquals(expected.doubleValue(), actual.doubleValue(), 1e-9);
    }
    
    /**
    * Test for asin method in BigDecimalUtils.
    * @author Yordanos Shiferaw
    */
    @Test
    public void testAsin() {
        //Call the BigDecimalUtils.cos method to compute cosine
        BigDecimal actual = BigDecimalUtils.asin(new BigDecimal("0.5"), MC);
        BigDecimal expected = new BigDecimal(Math.PI / 6);

        //Compare the actual result with the expected result using a tolerance level
        assertEquals(expected.doubleValue(), actual.doubleValue(), 1e-9);
    }
    
    /**
    * Test for atan method in BigDecimalUtils.
    * @author Yordanos Shiferaw
    */
    @Test
    public void testAtan() {
        //Call the BigDecimalUtils.cos method to compute cosine
        BigDecimal actual = BigDecimalUtils.atan(new BigDecimal("1"), MC);
        BigDecimal expected = new BigDecimal(Math.PI / 4);

        //Compare the actual result with the expected result using a tolerance level
        assertEquals(expected.doubleValue(), actual.doubleValue(), 1e-9);
    }

    //atan2 Tests
    @Test
    public void testAtan2_QuadrantI() {
        BigDecimal y = new BigDecimal("1.0");
        BigDecimal x = new BigDecimal("1.0");
        BigDecimal result = BigDecimalUtils.atan2(y, x, MC);
        BigDecimal expected = new BigDecimal("0.7853981634"); // π/4
        assertEquals(expected.doubleValue(), result.doubleValue(), 1e-9);
    }

    @Test
    public void testAtan2_QuadrantII() {
        BigDecimal y = new BigDecimal("1.0");
        BigDecimal x = new BigDecimal("-1.0");
        BigDecimal result = BigDecimalUtils.atan2(y, x, MC);
        BigDecimal expected = new BigDecimal("2.356194490"); // 3π/4
        assertEquals(expected.doubleValue(), result.doubleValue(), 1e-9);
    }

    @Test
    public void testAtan2_QuadrantIII() {
        BigDecimal y = new BigDecimal("-1.0");
        BigDecimal x = new BigDecimal("-1.0");
        BigDecimal result = BigDecimalUtils.atan2(y, x, MC);
        BigDecimal expected = new BigDecimal("-2.356194490"); // -3π/4
        assertEquals(expected.doubleValue(), result.doubleValue(), 1e-9);
    }

    @Test
    public void testAtan2_QuadrantIV() {
        BigDecimal y = new BigDecimal("-1.0");
        BigDecimal x = new BigDecimal("1.0");
        BigDecimal result = BigDecimalUtils.atan2(y, x, MC);
        BigDecimal expected = new BigDecimal("-0.7853981634"); // -π/4
        assertEquals(expected.doubleValue(), result.doubleValue(), 1e-9);
    }

    @Test
    public void testAtan2_OnAxis() {
        BigDecimal y = new BigDecimal("0");
        BigDecimal x = new BigDecimal("0");
        // atan2(0, 0) is usually undefined, but for this example, we can check a specific value
        assertThrows(ArithmeticException.class, () -> {
            BigDecimalUtils.atan2(y, x, MC);
        });
    }

    @Test
    public void testAtan2_PositiveAndNegative() {
        BigDecimal y = new BigDecimal("3.0");
        BigDecimal x = new BigDecimal("-2.0");
        BigDecimal result = BigDecimalUtils.atan2(y, x, MC);
        BigDecimal expected = new BigDecimal("2.158798930");
        assertEquals(expected.doubleValue(),
                result.doubleValue(),
                1e-9);
    }
    
    //e Tests
    @Test
    public void testE_HighPrecision() {
        BigDecimal x = new BigDecimal("1.0");
        BigDecimal result = BigDecimalUtils.e(MC);
        BigDecimal expected = new BigDecimal("2.718281828459045090795598298427649", MC); // e^1 with higher precision
        assertEquals(expected, result);
    }
    
    //Pi Tests
    @Test
    public void testPi() {
        BigDecimal result = BigDecimalUtils.pi(MC);
        assertEquals(Math.PI,
                result.doubleValue(),
                1e-9);
    }

    @Test
    public void testPi_HigherPrecision() {
        BigDecimal result = BigDecimalUtils.pi(MC);
        BigDecimal expected = new BigDecimal(Math.PI, MC);
        assertEquals(expected, result);
    }
    
    //Factorial Tests
    @Test
    public void testFactorial_PositiveInteger() {
        BigDecimal x = new BigDecimal("5.0");
        BigDecimal result = BigDecimalUtils.factorial(x, MC);
        BigDecimal expected = new BigDecimal("120");
        assertEquals(expected, result);
    }

    @Test
    public void testFactorial_Zero() {
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal result = BigDecimalUtils.factorial(x, MC);
        BigDecimal expected = BigDecimal.ONE;
        assertEquals(expected, result);
    }

    @Test
    public void testFactorial_Negative() {
        BigDecimal x = new BigDecimal("-1.0");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BigDecimalUtils.factorial(x, MC);
        });
        assertEquals("Factorial is not defined for negative or non-integer values.", exception.getMessage());
    }

    @Test
    public void testFactorial_NonInteger() {
        BigDecimal x = new BigDecimal("2.5");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BigDecimalUtils.factorial(x, MC);
        });
        assertEquals("Factorial is not defined for negative or non-integer values.", exception.getMessage());
    }

    @Test
    public void testFactorial_LargeInteger() {
        BigDecimal x = new BigDecimal("20.0");
        BigDecimal result = BigDecimalUtils.factorial(x, MC);
        
        BigDecimal expected = new BigDecimal("2.43290200817664E+18"); // 20!
        assertEquals(expected.doubleValue(), result.doubleValue(), 1e-9);
    }
}
