package org.project;

import org.project.lexing.Lexer;
import org.project.parsing.Parser;

import java.io.File;

/** Class for starting LISP INTERPRETER EXECUTION. */
public class JLispCLI
{
    /** Starts execution by getting the file where operations are*/
    public static void main(String[] args) {
        // IF NO FILE PATH GIVEN.
        if (args.length == 0){
            System.out.println("No file path for calculation given");
            System.out.println("\tTry : java -jar <jarName> <filePath>");
            System.out.println("\tExample : java -jar Calculator.jar ./operations.txt");
        }
        else{
            File lispFile = new File(args[0]);
            // IF FILE NOT EXIST
            if (!lispFile.exists()){
                System.out.println("Not such file exists.");
                System.out.println("\tTry again, with a valid path.");
            }
            // IF FILE EXIST, START PROGRAM!
            else{
                Interpreter.evaluate(
                        Parser.buildNodeTree(Lexer.getTokens(lispFile))
                );
            }
        }
    }
}
