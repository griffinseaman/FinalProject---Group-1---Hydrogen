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
 * @author Noah Wood, Griffin Seaman
 */
class BigDecimalUtils {
    static final BigDecimal recip(BigDecimal x, MathContext mc) 
    {
        // Return the reciprocal of x (1 / x) using BigDecimal arithmetic
        return BigDecimal.ONE.divide(x, mc);
    }

    static final BigDecimal floor(BigDecimal x, MathContext mc) 
    {
        // Use BigDecimal setScale to perform floor operation with RoundingMode.FLOOR
        return x.setScale(0, RoundingMode.FLOOR);
    }

    static final BigDecimal ceil(BigDecimal x, MathContext mc) 
    {
        // Use BigDecimal setScale to perform ceiling operation with RoundingMode.CEILING
        return x.setScale(0, RoundingMode.CEILING);
    }

    static final BigDecimal round(BigDecimal x, MathContext mc) 
    {
        // Round the BigDecimal with the provided MathContext
        return x.round(mc).setScale(mc.getPrecision(), mc.getRoundingMode());
    }

    static final BigDecimal trunc(BigDecimal x, MathContext mc) 
    {
        // Truncate the BigDecimal by setting the scale to 0 and using RoundingMode.DOWN
        return x.setScale(0, RoundingMode.DOWN);
    }

    /**
     * Computes nth power using BigDecimal arithmetic.
     * 
     * @param base The base
     * @param power The exponent
     * @param mc The MathContext for precision control
     * @return base^power as a BigDecimal
     * @author Noah Wood
     */
     static final BigDecimal ipow(BigDecimal base, BigDecimal power, MathContext mc)
    {
        if(power.equals(BigDecimal.ZERO) || power.intValue() == 0){
            return BigDecimal.ONE.setScale(mc.getPrecision());
        }
        if(base.equals(BigDecimal.ZERO)){
            return BigDecimal.ZERO.setScale(mc.getPrecision());
        }
        
        // Use a higher precision than is called for
        MathContext ic = new MathContext(mc.getPrecision() * 2, mc.getRoundingMode());
        long exp = power.longValue();
        boolean negPow = exp < 0;
        exp = Math.abs(exp);
        BigDecimal result = BigDecimal.ONE;
        BigDecimal currentBase = base;
        
        while(exp > 0){
            if((exp & 1) == 1){  // if the exponent is odd
                result = result.multiply(currentBase, ic);
            }
            currentBase = currentBase.multiply(currentBase,  ic); // Square the current base
            exp >>=1; // Divide exponent by 2
        }
        if(negPow){
            result = BigDecimal.ONE.divide(result, mc);
        }
        return result.round(mc).setScale(mc.getPrecision());
    }


    /**
     * Computes nth power using BigDecimal arithmetic.
     * 
     * @param base The base
     * @param ipower The exponent
     * @param mc The MathContext for precision control
     * @return base^power as a BigDecimal
     * @author Noah Wood
     */
    static final BigDecimal ipow(BigDecimal base, long ipower, MathContext mc)
    {
        if(ipower == 0){
            return BigDecimal.ONE.setScale(mc.getPrecision());
        }
        if(base.equals(BigDecimal.ZERO)){
            return BigDecimal.ZERO.setScale(mc.getPrecision());
        }
        
        // Use a higher precision than is called for
        MathContext ic = new MathContext(mc.getPrecision() * 2, mc.getRoundingMode());
        long exp = ipower;
        boolean negPow = exp < 0;
        exp = Math.abs(exp);
        BigDecimal result = BigDecimal.ONE;
        BigDecimal currentBase = base;
        
        while(exp > 0){
            if((exp & 1) == 1){  // if the exponent is odd
                result = result.multiply(currentBase, ic);
            }
            currentBase = currentBase.multiply(currentBase,  ic); // Square the current base
            exp >>=1; // Divide exponent by 2
        }
        if(negPow){
            result = BigDecimal.ONE.divide(result, mc);
        }
        return result.round(mc).setScale(mc.getPrecision());
//        return new BigDecimal(Math.pow(base.doubleValue(), (int)ipower), mc);
    }


    /**
     * Computes nth (integer) root using BigDecimal arithmetic.
     * 
     * @param base The base
     * @param power The root
     * @param mc The MathContext for precision control
     * @return base^(1/root) as a BigDecimal
     * @author Noah Wood
     */
    static final BigDecimal iroot(BigDecimal base, BigDecimal root, MathContext mc)
    {
        if(base.equals(BigDecimal.ZERO))
            return BigDecimal.ZERO.setScale(mc.getPrecision());
        if(root.equals(BigDecimal.ONE))
            return base.setScale(mc.getPrecision());
        if(root.equals(BigDecimal.ZERO))
            return BigDecimal.ONE.setScale(mc.getPrecision());
        return pow(base, BigDecimal.ONE.divide(root, mc), mc).setScale(mc.getPrecision());
    }

    /**
     * Computes nth (integer) root using BigDecimal arithmetic.
     * 
     * @param base The base
     * @param power The root
     * @param mc The MathContext for precision control
     * @return base^(1/root) as a BigDecimal
     * @author Noah Wood
     */
    static final BigDecimal iroot(BigDecimal base, long iroot, MathContext mc)
    {
        if(base.equals(BigDecimal.ZERO))
            return BigDecimal.ZERO.setScale(mc.getPrecision());
        if(iroot == 1)
            return base.setScale(mc.getPrecision());
        if(iroot == 0)
            return BigDecimal.ONE.setScale(mc.getPrecision());
        return pow(base, BigDecimal.ONE.divide(new BigDecimal(iroot, mc), mc), mc).setScale(mc.getPrecision());
    }

    /**
     * Computes nth power using BigDecimal arithmetic.
     * 
     * @param base The base
     * @param power The exponent
     * @param mc The MathContext for precision control
     * @return base^power as a BigDecimal
     * @author Noah Wood
     */
    static final BigDecimal pow(BigDecimal base, BigDecimal power, MathContext mc)
    {
        if(power.equals(BigDecimal.ZERO)){
            return BigDecimal.ONE;
        }
        if(base.equals(BigDecimal.ZERO)){
            return BigDecimal.ZERO;
        }
        
        // Log exponentiation
        BigDecimal logBase = log(base.abs(), mc);
        BigDecimal expRes = logBase.multiply(power, mc);
        BigDecimal result = exp(expRes, mc);
        if(base.signum() == -1 && !power.remainder(new BigDecimal(2)).equals(BigDecimal.ZERO))
            return result.round(mc).negate().setScale(mc.getPrecision());
        return result.round(mc).setScale(mc.getPrecision());
    }

    /**
     * Computes e^x using BigDecimal arithmetic.
     * 
     * @param x The exponent
     * @param mc The MathContext for precision control
     * @return e^x as a BigDecimal
     * @author Yordanos Shiferaw
     */
    static final BigDecimal exp(BigDecimal x, MathContext mc)
    {
        BigDecimal result = BigDecimal.ONE;
        BigDecimal term = BigDecimal.ONE;
        BigDecimal n = BigDecimal.ONE;

        //Use a higher precision for intermediate calculations
        MathContext mc2 = new MathContext(mc.getPrecision() + 10);

        for (int i = 1; i < mc.getPrecision() * 10; i++) {
            term = term.multiply(x, mc2).divide(n, mc2);
            result = result.add(term, mc2);
            n = n.add(BigDecimal.ONE);
        }

        //Round the final result to the desired precision
        return result.round(mc);
    }

    static final BigDecimal log(BigDecimal x, MathContext mc)
    {
        return new BigDecimal(Math.log(x.doubleValue()), mc);
    }

    /**
     * Computes 10^x.
     * 
     * @param x The exponent
     * @param mc The MathContext for precision control
     * @return The value of 10^x with the specified precision.
     * @author Yordanos Shiferaw
     */
    static final BigDecimal exp10(BigDecimal x, MathContext mc)
    {
        MathContext mc2 = new MathContext(mc.getPrecision() + 10);
        BigDecimal result = pow(BigDecimal.TEN, x, mc2);
        return result.round(mc);
    }

    /**
     * Computes the base-10 logarithm (log10) of a BigDecimal value.
     * 
     * @param x The exponent
     * @param mc The MathContext for precision control
     * @return The base-10 logarithm of the input BigDecimal with the specified precision.
     * @author Yordanos Shiferaw
     */
    static final BigDecimal log10(BigDecimal x, MathContext mc)
    {
        MathContext mc2 = new MathContext(mc.getPrecision() + 10);
        BigDecimal result = log(x,mc2).divide(log(BigDecimal.TEN, mc2));
        return result.round(mc);
    }

    static final BigDecimal cos(BigDecimal x, MathContext mc)
    {
        return new BigDecimal(Math.cos(x.doubleValue()), mc);
    }

    /**
     * Computes the sine of a given angle in radians.
     * 
     * @param x The angle in radians as a BigDecimal.
     * @param mc The MathContext for precision control
     * @return The sine of the given angle as a BigDecimal
     * @author Yordanos Shiferaw
     */
    static final BigDecimal sin(BigDecimal x, MathContext mc)
    {
        //Convert BigDecimal to double
        double xDouble = x.doubleValue();
        
        //Compute cosine using Math.exp
        double result = Math.sin(xDouble);
        
        //Convert result back to BigDecimal with specified precision
        return new BigDecimal(result, mc);
        
    }
    
    /**
     * Computes the tangent of a given angle in radians.
     * 
     * @param x The angle in radians as a BigDecimal.
     * @param mc The MathContext for precision control
     * @return The tangent of the given angle as a BigDecimal
     * @author Yordanos Shiferaw
     */
    static final BigDecimal tan(BigDecimal x, MathContext mc)
    {
        //Convert BigDecimal to double
        double xDouble = x.doubleValue();
        //Compute cosine using Math.exp
        double result = Math.tan(xDouble); 
        //Convert result back to BigDecimal with specified precision
        return new BigDecimal(result, mc);
    }
    
   /**
 * Computes the arccosine of a given value using BigDecimal arithmetic.
 * 
 * @param x The value as a BigDecimal, must be in the range [-1, 1].
 * @param mc The MathContext for precision control
 * @return The arccosine of the given value in radians as a BigDecimal
 * @author Abul Shordar
 */
    
static final BigDecimal acos(BigDecimal x, MathContext mc) {
    if (x.abs().compareTo(BigDecimal.ONE) > 0) {
        throw new ArithmeticException("Domain error: acos(x) is only defined for -1 <= x <= 1");
    }
    
    BigDecimal halfPi = pi(mc).divide(new BigDecimal("2"), mc);
    return halfPi.subtract(asin(x, mc), mc);
}

    /**
     
    Computes the arccosine of a given angle in radians.
    @param x The angle in radians as a BigDecimal.
    @param mc The MathContext for precision control
    @return The arccosine of the given angle as a BigDecimal
    @author Yordanos Shiferaw*/
    static final BigDecimal acos(BigDecimal x, MathContext mc){//Convert BigDecimal to double
        double xDouble = x.doubleValue();
        //Compute cosine using Math.exp
        double result = Math.acos(xDouble);
        //Convert result back to BigDecimal with specified precision
        return new BigDecimal(result, mc);
    }

    /**
     * Computes the arctangent of a given angle in radians.
     * 
     * @param x The angle in radians as a BigDecimal.
     * @param mc The MathContext for precision control
     * @return The arctangent of the given angle as a BigDecimal
     * @author Yordanos Shiferaw
     */
    static final BigDecimal atan(BigDecimal x, MathContext mc)
    {
        //Convert BigDecimal to double
        double xDouble = x.doubleValue();
        //Compute cosine using Math.exp
        double result = Math.atan(xDouble);
        //Convert result back to BigDecimal with specified precision
        return new BigDecimal(result, mc);
    }

    static final BigDecimal atan2(BigDecimal y, BigDecimal x, MathContext mc) {
    BigDecimal PI = new BigDecimal("3.14");
    BigDecimal HALF_PI = PI.divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
        
    // Check if x is zero
        if (x.compareTo(BigDecimal.ZERO) == 0) {
            // Return π/2 if y > 0, otherwise -π/2
            return (y.compareTo(BigDecimal.ZERO) > 0) ? HALF_PI : (y.compareTo(BigDecimal.ZERO) < 0) ? HALF_PI.negate() : BigDecimal.ZERO;
        }

        // Calculate y / x
        BigDecimal quotient = y.divide(x, mc);

        // Calculate atan(y / x)
        BigDecimal atanValue = atan(quotient, mc);

        // Adjust the result based on the sign of x
        if (x.compareTo(BigDecimal.ZERO) < 0) {
            atanValue = atanValue.add(PI);
        }

        return atanValue.round(mc);
    }

    static final BigDecimal e(MathContext mc) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal factorial = BigDecimal.ONE;

        // Set a limit for the series to achieve a desired precision
        int limit = mc.getPrecision() + 5; // Additional terms for accuracy

        for (int n = 0; n < limit; n++) {
            if (n > 0) {
                factorial = factorial.multiply(BigDecimal.valueOf(n)); // n!
            }
            sum = sum.add(BigDecimal.ONE.divide(factorial, mc)); // 1/n!
        }

        return sum.round(mc);
    }

    static final BigDecimal pi(MathContext mc) {
        // Precompute constant C
        BigDecimal C = new BigDecimal("426880").multiply(BigDecimal.valueOf(Math.sqrt(10005))); 
        BigDecimal M = BigDecimal.ONE; // M_k
        BigDecimal L = new BigDecimal(13591409); // L_k
        BigDecimal X = BigDecimal.ONE; // X_k
        BigDecimal K = BigDecimal.valueOf(6); // K = 6
        BigDecimal S = L; // Sum S initialized to L_0

        // Loop for terms in the series
        for (int k = 1; k <= mc.getPrecision() + 5; k++) {
            M = M.multiply(K.multiply(K.add(BigDecimal.ONE)).multiply(K.add(BigDecimal.valueOf(2))))
                   .divide(BigDecimal.valueOf(k * k * k), mc); // M_k

            L = L.add(new BigDecimal(545140134)); // L_k
            X = X.multiply(BigDecimal.valueOf(-262537412640768000L)); // X_k

            // Update sum S
            S = S.add(M.multiply(L).divide(X, mc)); // S += M_k * L_k / X_k
            
            K = K.add(BigDecimal.valueOf(12)); // Increment K
        }

        // Calculate π
        BigDecimal piValue = C.divide(S, mc);
        return piValue.setScale(mc.getPrecision(), RoundingMode.HALF_UP);
    }

    static final BigDecimal factorial(BigDecimal x, MathContext mc) {
         // Check if x is a non-negative integer
        if (x.compareTo(BigDecimal.ZERO) < 0 || x.scale() > 0) {
            throw new IllegalArgumentException("Factorial is defined for non-negative integers only.");
        }
        
        if (x.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ONE; // 0! = 1
        }

        BigDecimal result = BigDecimal.ONE;

        // Calculate factorial iteratively
        for (BigDecimal i = BigDecimal.ONE; i.compareTo(x) <= 0; i = i.add(BigDecimal.ONE)) {
            result = result.multiply(i, mc);
        }

        return result;
    }
