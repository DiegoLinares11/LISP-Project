package org.project.helpers.printer;

import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Provides basic printing and reading user's input
 * functionalities for terminal.
 */
public class Printer {

    /**
     * Prints a message to terminal.
     * @param message message
     */
    public static void print(String message){
        System.out.print(message);
    }

    /**
     * Prints a message to terminal, with a given set of colors.
     * @param message text to print.
     * @param color text's color message, from the constants in {@link AnsiColors} enum.
     */
    public static void print(String message, AnsiColors color){
        String colorCode = color != null? color.getCode() : "";
        System.out.print(colorCode + message + AnsiColors.RESET.getCode());
    }

    /**
     * Prints a message to terminal with a given set of colors,
     * Use the two colors to set foreground and background of the message.
     * @param message text to print.
     * @param foreground text's color message, from the constants in {@link AnsiColors} enum.
     * @param background text's color message, from the constants in {@link AnsiColors} enum.
     */
    public static void print(String message, AnsiColors foreground, AnsiColors background){
        String colorCode1 = foreground != null? foreground.getCode() : "";
        String colorCode2 = background != null? background.getCode() : "";
        System.out.print(
                colorCode1
                        + colorCode2
                        + message
                        + AnsiColors.RESET.getCode());
    }

    /**
     * Prints a message to terminal.
     * @param message message
     */
    public static void println(String message){
        System.out.println(message);
    }

    /**
     * Prints a message to terminal, with a given set of colors.
     * @param message text to print.
     * @param color text's color message, from the constants in {@link AnsiColors} enum.
     */
    public static void println(String message, AnsiColors color){
        String colorCode = color != null? color.getCode() : "";
        System.out.println(colorCode + message + AnsiColors.RESET.getCode());
    }

    /**
     * Prints a message to terminal with a given set of colors,
     * Use the two colors to set foreground and background of the message.
     * @param message text to print.
     * @param foreground text's color message, from the constants in {@link AnsiColors} enum.
     * @param background text's color message, from the constants in {@link AnsiColors} enum.
     */
    public static void println(String message, AnsiColors foreground, AnsiColors background){
        String colorCode1 = foreground != null? foreground.getCode() : "";
        String colorCode2 = background != null? background.getCode() : "";
        System.out.println(
                colorCode1
                + colorCode2
                + message
                + AnsiColors.RESET.getCode());
    }

    /**
     * A combination of a print and input actions. First print a given message
     * then waits for user's input.
     * @param message text to print before reading input.
     * @return Input wrote by the user.
     */
    public static String input(String message) {
        final Scanner reader = new Scanner(System.in);
        System.out.println(message);
        return reader.nextLine();
    }

    /**
     * A combination of a print and input actions. First print a give message
     * then waits for user's input.
     * @param message text to print before reading input.
     * @return Input wrote by the user.
     */
    public static String input(String message, AnsiColors color) {
        final Scanner reader = new Scanner(System.in);
        println(message, color);
        return reader.nextLine();
    }

    /**
     * Works the same as input but ask for input until it meet a given condition.
     * @param message text to print before reading input.
     * @param condition Function to evaluate if the input valus is correct.
     * @return Input wrote by the user.
     */
    public static String inputIf(String message,Predicate<String> condition){
        String value;
        while (true){
            value = input(message);
            if(condition.test(value))
                break;
            printConfirmation("Wrong input", "Try again...");
        }
        return value;
    }

    /**
     * Works the same as input but ask for input until it meet a given condition.
     * @param message text to print before reading input.
     * @param wrongInputMessage text to print if the input don't fulfill the condition.
     * @param condition Function to evaluate if the input valus is correct.
     * @return Input wrote by the user.
     */
    public static String inputIf(String message, String wrongInputMessage ,Predicate<String> condition){
        String value;
        while (true){
            value = input(message);
            if(condition.test(value))
                break;
            printConfirmation(wrongInputMessage, "Try again...");
        }
        return value;
    }

    /**
     * A combination of a print and input actions. First print a give message
     * then waits for user's input.
     * @param message text to print before reading input.
     * @param foreground text's color message, from the constants in {@link AnsiColors} enum.
     * @param background text's color message, from the constants in {@link AnsiColors} enum.
     * @return Input wrote by the user.
     */
    public static String input(String message, AnsiColors foreground, AnsiColors background) {
        final Scanner reader = new Scanner(System.in);
        println(message, foreground, background);
        return reader.nextLine();
    }

    /**
     * Works the same as "inputMethod" but returning an int.
     * @param message text to print before reading input.
     * @return int given by the user.
     */
    public static int inputInt(String message) {
        final Scanner reader = new Scanner(System.in);
        System.out.println(message);
        while(!reader.hasNextInt()) {
            println("Please enter an integer \n", AnsiColors.RED);
            reader.next();
        }
        return reader.nextInt();
    }

    /**
     * Works the same as "inputMethod" but returning an int.
     * @param message text to print before reading input.
     * @param color text's color message, from the constants in {@link AnsiColors} enum.
     * @return int given by the user.
     */
    public static int inputInt(String message, AnsiColors color) {
        final Scanner reader = new Scanner(System.in);
        println(message, color);
        while(!reader.hasNextInt()) {
            println("Please enter an integer \n", AnsiColors.RED);
            reader.next();
        }
        return reader.nextInt();
    }

    /**
     * Works the same as "inputMethod" but returning an int.
     * @param message text to print before reading input.
     * @param foreground text's color message, from the constants in {@link AnsiColors} enum.
     * @param background text's color message, from the constants in {@link AnsiColors} enum.
     * @return int given by the user.
     */
    public static int inputInt(String message, AnsiColors foreground, AnsiColors background) {
        final Scanner reader = new Scanner(System.in);
        println(message, foreground, background);
        while(!reader.hasNextInt()) {
            println("Please enter an integer \n", AnsiColors.RED);
            reader.next();
        }
        return reader.nextInt();
    }

    /**
     * Works the same as input but ask for input until it meet a given condition.
     * @param message text to print before reading input.
     * @param condition Function to evaluate if the input valus is correct.
     * @return Input wrote by the user.
     */
    public static int inputIntIf(String message,Predicate<Integer> condition){
        int value;
        while (true){
            value = inputInt(message);
            if(condition.test(value))
                break;
            printConfirmation("Wrong input", "Try again...");
        }
        return value;
    }

    /**
     * Works the same as input but ask for input until it meet a given condition.
     * @param message text to print before reading input.
     * @param wrongInputMessage text to print if the input don't fulfill the condition.
     * @param condition Function to evaluate if the input valus is correct.
     * @return Input wrote by the user.
     */
    public static int inputIntIf(String message, String wrongInputMessage ,Predicate<Integer> condition){
        int value;
        while (true){
            value = inputInt(message);
            if(condition.test(value))
                break;
            printConfirmation(wrongInputMessage, "Try again...");
        }
        return value;
    }

    /**
     * Prints a message, and stop the program flow until, user press "ENTER".
     * @param message text to print before reading input.
     * @param footer  text to print after reading input.
     */
    public static void printConfirmation(String message, String footer) {
        final Scanner reader = new Scanner(System.in);
        System.out.println(message);
        System.out.println(footer);
        reader.nextLine();
    }

    /**
     * Prints a message, and stop the program flow until, user press "ENTER".
     * @param message text to print before reading input.
     * @param footer Subtitle after the main message.
     * @param color text's color message, from the constants in {@link AnsiColors} enum.
     * @param footer  text to print after reading input.
     */
    public static void printConfirmation(String message, String footer, AnsiColors color) {
        final Scanner reader = new Scanner(System.in);
        println(message, color);
        println(footer);
        reader.nextLine();
    }

    /**
     * Clears the console, based on OS.
     */
    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
