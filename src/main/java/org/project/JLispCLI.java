package org.project;

import org.project.lexing.Lexer;
import org.project.parsing.Parser;
import org.project.parsing.TreeNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/** Class for starting LISP INTERPRETER EXECUTION. */
public class JLispCLI
{

    /** Starts execution by getting the file where operations are*/
    public static void main(String[] args) {
        // IF HELP COMMAND IS CALLED
        if (args.length > 0 && args[0].equals("help")) {
            System.out.println("Here will go all the commands available in the program.");
        }
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
                TreeNode result = Interpreter.evaluate(Parser.buildNodeTree(Lexer.getTokens(lispFile)));
                
                System.out.println(result.toString());
                System.out.println("Do you want to save your answer in a .txt file?\nyes/no");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if (input.equals("yes")){
                    saveFile(result.toString());
                }else{
                    System.out.println("File not Saved.");
                }

            }
        }
    }
    private static void saveFile(String result){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the path where you want to save you .txt file:");
            String path = scanner.nextLine();
            String filePath = path + "/lispResult.txt"; // set the path of the new file
            try{ 
                File file = new File(filePath);
                FileWriter escribir = new FileWriter(file, true);
                escribir.write(result);
                escribir.close();
                System.out.println("Se creo y guardo el archivo exitosamente");
            }
            catch (Exception e){
                System.out.println("Error al escribir");
            }
        }
        catch (Exception e) {
            System.out.println("Error al escribir");
        }
    }
}
