/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.baker.finalproject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class that models an RPN scientific calculator.
 * @author Richard Lesh
 * @editor Abul Shordar
 * Implements various mathematical functions using BigDecimal.
 * Add MathContext private member
 * Add MathContext argument to constructor used to set private member
 * Convert doubles in methods to BigDecimal
 * Convert all Math.func() calls to BigDecimalUtils.func() calls
 * Convert any functions implemented inline to a BigDecimalUtils.func() call, 
 * e.g. round(), trunc(), ipow(), factorial()Attache class source file to this issue.
 */
public class RPNCalculator {
    private final Deque<BigDecimal> stack = new ArrayDeque<>();
    private final MathContext mc;  // MathContext for precision control

    // Constructor that accepts MathContext for precision settings
    public RPNCalculator(MathContext mc) {
        this.mc = mc;
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Peek the top value of the stack
    public BigDecimal peek() {
        return stack.peek();
    }

    // Peek the second value (Y) on the stack
    public BigDecimal peekY() {
        swap();
        BigDecimal y = stack.peek();
        swap();
        return y;
    }

    // Swap the top two values on the stack
    public void swap() {
        BigDecimal x = stack.pop();
        BigDecimal y = stack.pop();
        value(x);
        value(y);
    }

    // Pop and discard the top value
    public void rollDown() {
        stack.pop();
    }

    // Clear the stack
    public void clear() {
        stack.clear();
    }

    // Push a value onto the stack
    public void value(BigDecimal v) {
        stack.push(v);
    }

    // Basic arithmetic operations
    public void add() {
        value(stack.pop().add(stack.pop(), mc));
    }

    public void sub() {
        BigDecimal x = stack.pop();
        BigDecimal y = stack.pop();
        value(y.subtract(x, mc));
    }

    public void mult() {
        value(stack.pop().multiply(stack.pop(), mc));
    }

    public void div() {
        BigDecimal x = stack.pop();
        BigDecimal y = stack.pop();
        if(x.equals(BigDecimal.ZERO)){
            value(new BigDecimal(Double.POSITIVE_INFINITY));
            return;
        }
        value(y.divide(x, mc));
    }

    // Min and Max functions
    public void min() {
        value(stack.pop().min(stack.pop()));
    }

    public void max() {
        value(stack.pop().max(stack.pop()));
    }

    // Absolute value and negation
    public void abs() {
        value(stack.pop().abs());
    }
    
    public void sign() {
        value(BigDecimal.valueOf(stack.pop().signum()));
    }
    
    public void recip() {
            value(BigDecimal.ONE.divide(stack.pop(), mc));
    }
    
    public void neg() {
        value(stack.pop().negate());
    }  

    public void floor() {
        value(BigDecimalUtils.floor(stack.pop(), mc));
    }
    
    public void ceil() {
        value(BigDecimalUtils.ceil(stack.pop(), mc));
    }
    
    public void round() {
        value(BigDecimalUtils.round(stack.pop(), mc));
    }
    
    public void trunc() {
        BigDecimal x = stack.pop();
        if (x.compareTo(BigDecimal.ZERO) < 0) 
            value(BigDecimalUtils.ceil(x, mc));
        else value(BigDecimalUtils.floor(x, mc));
    }
    
    public void ipow() {
        BigDecimal x = stack.pop(); 
        BigDecimal y = stack.pop(); 
        value(ipow(y,x.longValue()));
    }
    
    private static BigDecimal ipow(BigDecimal base, long ipower) {
        if (base.compareTo(BigDecimal.ZERO) == 0) {
            if (ipower == 0)
                throw new IllegalArgumentException("ipow(0,0) is indeterminate!");
            else return BigDecimal.ZERO;
        }
        if (ipower == 0) return BigDecimal.ONE;
        BigDecimal result = BigDecimal.ONE;
        BigDecimal factor = base;
        while (ipower != 0) {
            if ((ipower & 1L) == 1L)
                result = result.multiply(factor);
            ipower >>= 1;
            factor = factor.multiply(factor);
        }
        return result;
    }
    
    public void pow() {
        BigDecimal x = stack.pop();
        BigDecimal y = stack.pop();
        value(BigDecimalUtils.pow(y, x, mc));
    }
    
    public void root() {
        BigDecimal x = stack.pop(); 
        BigDecimal y = stack.pop(); 
        value(BigDecimalUtils.pow(y,BigDecimal.ONE.divide(x, mc), mc));
    }
    
    // Scientific functions using BigDecimalUtils
    public void exp() {
        value(BigDecimalUtils.exp(stack.pop(), mc));
    }

    public void log() {
        value(BigDecimalUtils.log(stack.pop(), mc));
    }

    public void exp10() {
        BigDecimal x = stack.pop();
        BigDecimal y = new BigDecimal(10);
        value(BigDecimalUtils.pow(y, x, mc));
    }
    
    public void log10() {
        value(BigDecimalUtils.log10(stack.pop(), mc));
    }
    
    public void cos() {
            value(BigDecimalUtils.cos(stack.pop(), mc));
    }
    
    public void sin() {
        value(BigDecimalUtils.sin(stack.pop(), mc));
    }
    
    public void tan() {
        value(BigDecimalUtils.tan(stack.pop(), mc));
    }
    
    public void acos() {
        value(BigDecimalUtils.acos(stack.pop(), mc));
    }
    
    public void asin() {
        value(BigDecimalUtils.asin(stack.pop(), mc));
    }
    
    public void atan() {
        value(BigDecimalUtils.atan(stack.pop(), mc));
    }
    
    public void atan2() {
        BigDecimal x = stack.pop(); 
        BigDecimal y = stack.pop(); 
        value(BigDecimalUtils.atan2(y, x, mc));
    }
    
    // Constants for e and pi from BigDecimalUtils
    public void e() {
        value(BigDecimalUtils.e(mc));
    }

    public void π() {
        value(BigDecimalUtils.pi(mc));
    }
    
    public void x() {value(peek());}
    public void y() {value(peekY());}
    public void factorial() {
        value(BigDecimalUtils.factorial(stack.pop(), mc));
    }
    
    private BigDecimal factorial(int n) {
        if (n < 2) {
            return BigDecimal.ONE;
        }
        return BigDecimal.valueOf(n).multiply(factorial(n - 1));
    }
}
