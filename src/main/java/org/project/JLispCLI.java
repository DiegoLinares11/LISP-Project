package org.project;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.project.helpers.FileWriter;
import org.project.helpers.printer.AnsiColors;
import org.project.helpers.printer.Printer;
import org.project.lexing.Lexer;
import org.project.parsing.Parser;
import org.project.parsing.TreeNode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** Class for starting LISP INTERPRETER EXECUTION. */
public class JLispCLI
{
    @Parameter
    private List<String> parameters = new ArrayList<>();
    @Parameter (names = "--help", help = true, description = "Displays help message.")
    private boolean showHelp;
    @Parameter (names = {"--input", "-i"}, description = "Defines a source to read code from.")
    String inputFile;
    @Parameter (names = {"--output", "-o"}, description = "Defines where to write code results.")
    String outputFile;

    /** Starts execution by fetching command arguments.*/
    public static void main(String[] args) {
        JLispCLI jLispCLI = new JLispCLI();
        JCommander commandParser =JCommander.newBuilder()
                .addObject(jLispCLI)
                .args(args)
                .build();
        commandParser.parse();       // Fetching arguments.
        jLispCLI.run(commandParser); // Executing program.
    }

    /**
     * Runs JLisp, according the given parameters.
     * @param commandParser Jcommander object that contains program args.
     */
    private void run(JCommander commandParser) {
        TreeNode result = null;
        if (this.showHelp)
            showHelpMessage(commandParser);
        else if (this.inputFile != null)
            result = runFile(inputFile);
        else if (this.parameters.isEmpty()){
            Printer.println("WELCOME TO JLISP INTERPRETER!");
            Printer.println("\tType \"HELP\" for more information\n" +
                    "\tYou can start entering your code now:\n");
            String input = Printer.input("JLisp >> ", AnsiColors.BLACK, AnsiColors.WHITE_BACKGROUND);
            runInlineCode(input, commandParser);
        }

        // Write results if possible.
        if(this.outputFile != null && result != null)
            FileWriter.saveFile(result.toString(), outputFile);
    }

    private static TreeNode runInlineCode (String code, JCommander commander){
        if (code.strip().equals("HELP")){
            showHelpMessage(commander);
            return null;
        }
        else
            return Interpreter.evaluate(
                    Parser.buildNodeTree(Lexer.getTokens(code)));
    }

    private static TreeNode runFile(String filepath){
        File file = new File(filepath);
        if (file.exists()){
            return Interpreter.evaluate(
                    Parser.buildNodeTree(Lexer.getTokens(file)));
        }
        else{
            Printer.println("Not such file exists.", AnsiColors.RED);
            Printer.println("\tTry again, with a valid path.", AnsiColors.RED);
            return null; // Return an empty result.
        }
    }

    private static void showHelpMessage (JCommander commander) {
        commander.usage();
        Printer.println("The LISP FUNCTIONS available in the program:" +
                "\n+,-,*,/\t\t\tBasic arithmetic operators\n" +
                "=,>,<,>=,<=,not\tLogic arithmetic operators\n" +
                "atom\t\t\tCheck the the next value is an atom\n" +
                "print\t\t\tOutput values to the console\n" +
                "cond\t\t\tConditional statement\n" +
                "concatenate\t\tTwo or more sequences together to form a new sequence\n" +
                "setq\t\t\tAssign a value to a variable\n" +
                "defun\t\t\tDefine a new function in Lisp\n" +
                "list\t\t\tUsed to create a new list\n" +
                "cdr\t\t\t\tRetrieve the tail of a list\n" +
                "car\t\t\t\tRetrieve the head of a list");
    }
}
