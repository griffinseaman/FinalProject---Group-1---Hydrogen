/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.baker.finalproject;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * RPN Calculator Project
 * @author Richard Lesh
 * @editor Griffin Seaman
 */
public class FinalProject {

    /**
     * Pass in a relative file path argument to read from a file.
     * Otherwise reads from System.in.
     */
    public static void main(String[] args) {
        System.out.println("RPN Calculator!");

        // Define a MathContext with desired precision and rounding mode
        MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP); // Example: 10-digit precision

        Reader reader;
        try {
            if (args.length >= 1) {
                Path path = FileSystems.getDefault().getPath(args[0]);
                reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            } else {
                System.out.println("Enter one value or function name per line then <ENTER>.");
                System.out.println("Type 'Exit' then <ENTER> to quit.");
                reader = new InputStreamReader(System.in);
            }

            // Pass MathContext to the RPNEquationProcessor constructor
            RPNEquationProcessor eqProcessor = new RPNEquationProcessor(reader, mathContext);
            BigDecimal result = eqProcessor.processInput(new OutputStreamWriter(System.out));
        } catch (Exception ex) {
            System.out.println("An exception occurred: " +
                    ex.getClass().getSimpleName() + " " + ex.getMessage());
        }
    }
}
