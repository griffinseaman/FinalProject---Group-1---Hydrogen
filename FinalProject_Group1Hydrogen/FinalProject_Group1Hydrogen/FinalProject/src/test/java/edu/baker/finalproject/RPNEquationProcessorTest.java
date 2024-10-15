/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.baker.finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Yordanos Shiferaw
 */
public class RPNEquationProcessorTest {
    //MathContext for precision and delta for comparison
    private static final MathContext mc = new MathContext(10); //Precision of 10 digits
    private static final BigDecimal delta = BigDecimal.ONE.scaleByPowerOfTen(-mc.getPrecision() + 4);
    
    public RPNEquationProcessorTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }

    /**
     * Test of processInput method, of class RPNEquationProcessor.
     */
    @Test
    public void testProcessInput() {
        //To read all the ".rpn" files in the test_cases directory
        Path path = FileSystems.getDefault().getPath("test_cases");
        //Execute the test cases and confirm that the final result is a very small
        try {
            for (var p : Files.list(path).collect(Collectors.toList())) {
                System.out.println("Executing: " + p.toString());
                try (BufferedReader reader = new BufferedReader(new FileReader(p.toFile()))) {
                    RPNEquationProcessor eqProcessor = new RPNEquationProcessor(reader, mc);
                    BigDecimal result = eqProcessor.processInput(new OutputStreamWriter(System.out));

                    assertEquals(BigDecimal.ZERO, result);
                }
            }
        } catch (IOException ex) {
            System.out.println("An exception occured: " + 
                ex.getClass().getSimpleName() + " " + ex.getMessage());
        }
    }

    /**
     * Test error situations that print in the script.
     */
    @Test
    public void testProcessBadInput() {
        //To test error situations that print in the script
        //Will only open error_cases.rpn
        Path path = FileSystems.getDefault().getPath("test_cases", "error_cases.rpn");

        try {
            //Send the script output to a StringWriter
            StringWriter buffer = new StringWriter();

            RPNEquationProcessor processor = new RPNEquationProcessor(Files.newBufferedReader(path), mc);
            processor.processInput(buffer);
            //Use the output you captured in the StringWriter to search for all the different possible error messages
            String s = buffer.toString();
            assertTrue(s.contains("Stack empty!"));
            assertTrue(s.contains("Can't redefine"));
            assertTrue(s.contains("Undefined variable"));
            assertTrue(s.contains("ArrayIndexOutOfBoundsException"));
            assertTrue(s.contains("Illegal assignment"));
            assertTrue(s.contains("IllegalArgumentException"));
            System.out.println("Result: " + s);

        } catch (IOException ex) {
            System.out.println("An exception occured: " + 
                ex.getClass().getSimpleName() + " " + ex.getMessage());
        }
    }
    
}
