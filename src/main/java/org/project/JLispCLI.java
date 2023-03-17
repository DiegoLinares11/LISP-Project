package org.project;

import org.project.lexing.Lexer;
import org.project.parsing.Parser;
import org.project.parsing.TreeNode;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/** Class for starting LISP INTERPRETER EXECUTION. */
public class JLispCLI
{

    /** Starts execution by getting the file where operations are*/
    public static void main(String[] args) {
        // IF HELP COMMAND IS CALLED
        if (args.length > 0 && args[0].equals("help")) {
            System.out.println("The lisp functions available in the program:\n+,-,*,/		        Basic arithmetic operators\nprint			Output values to the console\ncond			Conditional statement\nconcatenate		Two or more sequences together to form a new sequence\nsetq			Assign a value to a variable\ndefun			Define a new function in Lisp\nlist			Used to create a new list\ncdr			Retrieve the tail of a list\ncar			Retrieve the head of a list");
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
                System.out.println("Do you want to save your answer in a .txt file?\nyes/no");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if (input.equals("yes")){
                    System.out.println("Enter the path where you want to save you .txt file:");
                    String path = scanner.nextLine();
                    String filePath = path + "/lispResult.txt"; // set the path of the new file
                    System.out.println("Do you want to clear the file?\nyes) Clear the file \nno) append current file");
                    String input2 = scanner.nextLine();
                    if (input2.equals("yes")){
                        clearFile(filePath);
                        saveFile(result.toString(), filePath);
                    }else{
                        saveFile(result.toString(), filePath);
                    }
                }else{
                    System.out.println("File not Saved.");
                }

            }
        }
    }
    private static void saveFile(String result,String filePath){
        try {
            Scanner scanner = new Scanner(System.in);
            try{ 
                File file = new File(filePath);
                FileWriter escribir = new FileWriter(file, true);
                escribir.write(result);
                escribir.close();
                System.out.println("It was succesfully saved.");
            }
            catch (Exception e){
                System.out.println("Error when written");
            }
        }
        catch (Exception e) {
            System.out.println("Error when written");
        }

    }
    private static void clearFile(String filePath)
    { 
        try{
            FileWriter fw = new FileWriter(filePath, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
        }catch(Exception exception){
            System.out.println("Exception have been caught");
        }
    }
}
