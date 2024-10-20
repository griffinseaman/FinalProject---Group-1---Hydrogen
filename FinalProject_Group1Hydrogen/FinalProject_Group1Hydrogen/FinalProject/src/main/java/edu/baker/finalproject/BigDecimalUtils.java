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
     * @author Noah Wood // Uh.... I don't actually remember doing this?
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
        return new BigDecimal(Math.log(x.doubleValue()), mc);
    }

    static final BigDecimal exp10(BigDecimal x, MathContext mc)
    {
        return pow(BigDecimal.TEN, x, mc);
    }

    static final BigDecimal log10(BigDecimal x, MathContext mc)
    {
        return new BigDecimal(Math.log10(x.doubleValue()), mc);
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
     * Computes the arcsine of a given angle in radians.
     * 
     * @param x The angle in radians as a BigDecimal.
     * @param mc The MathContext for precision control
     * @return The arcsine of the given angle as a BigDecimal
     * @author Yordanos Shiferaw
     */
    static final BigDecimal asin(BigDecimal x, MathContext mc)
    {
        //Convert BigDecimal to double
        double xDouble = x.doubleValue();  
        //Compute cosine using Math.exp
        double result = Math.asin(xDouble);
        //Convert result back to BigDecimal with specified precision
        return new BigDecimal(result, mc);
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

    static final BigDecimal atan2(BigDecimal y, BigDecimal x, MathContext mc)
    {
        if(y.equals(x) && x.equals(BigDecimal.ZERO))
            throw new ArithmeticException("atan2(0,0) undefined!");
        //Convert BigDecimal to double
        double yDouble = y.doubleValue();
        double xDouble = x.doubleValue();
        
        //compute atan2
        double result = Math.atan2(yDouble, xDouble);
        
        //Convert back to BigDecimal
        return new BigDecimal(result,mc);
    }

    static final BigDecimal e(MathContext mc)
    {
        return exp(BigDecimal.ONE, mc);
    }

    static final BigDecimal pi(MathContext mc)
    {
        //Convert Math.Pi to BigDecimal
        return new BigDecimal(Math.PI, mc);
    }

    static final BigDecimal factorial(BigDecimal x, MathContext mc)
    {
        if(x.signum()==-1 || x.doubleValue() != x.intValue())
            throw new IllegalArgumentException("Factorial is not defined for negative or non-integer values.");
        //Convert to double
        double xDouble = x.doubleValue();
        
        //Compute factorial
        BigDecimal result = BigDecimal.ONE;
        for (long i = 2; i <= xDouble; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        
        //Return result
        return result.round(mc);
    }
}
