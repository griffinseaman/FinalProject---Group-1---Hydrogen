/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.baker.finalproject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Noah Wood
 */
class BigDecimalUtils {
    static final BigDecimal recip(BigDecimal x, MathContext mc) 
    {
        // Convert the BigDecimal to double
        double xDouble = x.doubleValue();

        // Perform the reciprocal operation (1 / x)
        double resultDouble = 1.0 / xDouble;

        // Convert the result back to BigDecimal with the specified MathContext
        return new BigDecimal(resultDouble, mc);
    }

    static final BigDecimal floor(BigDecimal x, MathContext mc) 
    {
        // Convert BigDecimal to double
        double xDouble = x.doubleValue();
        
        // Use Math.floor to compute the floor value of the double
        double result = Math.floor(xDouble);
        
        // Convert result back to BigDecimal with specified precision
        return new BigDecimal(result, mc);
    }

    static final BigDecimal ceil(BigDecimal x, MathContext mc) 
    {
        // Convert BigDecimal to double
        double xDouble = x.doubleValue();
        
        // Use Math.ceil to compute the ceiling value of the double
        double result = Math.ceil(xDouble);
        
        // Convert result back to BigDecimal with specified precision
        return new BigDecimal(result, mc);
    }

    static final BigDecimal round(BigDecimal x, MathContext mc) {
        // Convert BigDecimal to double
        double xDouble = x.doubleValue();

        // Round the double value using Math.round and convert back to BigDecimal
        BigDecimal result = new BigDecimal(Math.round(xDouble), mc);

        // Return the result with the specified precision
        return result.setScale(mc.getPrecision(), mc.getRoundingMode());
    }

    static final BigDecimal trunc(BigDecimal x, MathContext mc) 
    {
        // Convert BigDecimal to double
        double xDouble = x.doubleValue();

        // Use Math.floor for positive numbers and Math.ceil for negative numbers
        double result = xDouble >= 0 ? Math.floor(xDouble) : Math.ceil(xDouble);

        // Convert result back to BigDecimal with specified precision
        return new BigDecimal(result, mc);
    }

    static final BigDecimal ipow(BigDecimal base, BigDecimal power, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal ipow(BigDecimal base, long ipower, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal iroot(BigDecimal base, BigDecimal root, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal iroot(BigDecimal base, long iroot, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal pow(BigDecimal base, BigDecimal power, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    /**
     * Computes e^x using BigDecimal arithmetic.
     * 
     * @param x The exponent
     * @param mc The MathContext for precision control
     * @return e^x as a BigDecimal
     * @author Noah Wood
     */
    static final BigDecimal exp(BigDecimal x, MathContext mc)
    {
        // Convert BigDecimal to double
        double xDouble = x.doubleValue();
        
        // Compute e^x using Math.exp
        double result = Math.exp(xDouble);
        
        // Convert result back to BigDecimal with specified precision
        return new BigDecimal(result, mc);
    }

    static final BigDecimal log(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal exp10(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal log10(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal cos(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal sin(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal tan(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal asin(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal acos(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal atan(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal atan2(BigDecimal y, BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal e(MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal pi(MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }

    static final BigDecimal factorial(BigDecimal x, MathContext mc)
    {
        return BigDecimal.ZERO; // STUB ONLY
    }
}
