package org.project.lexing;

/**
 * This class contains useful regex patterns to identify the different types
 * of tokens a Lisp expression can have.
 * */
public class Patterns {
    public static final String WHITESPACE = "\\s";
    public static final String NUMBER = "-\\d+\\.?\\d*|\\d+\\.?\\d*";   // Accepts integers (1, -2) or decimal (0.0, 0.1)
    public static final String BOOLEAN = "T|NIL";
    public static final String LITERAL = "[a-zA-Z0-9]+";
    public static final String VALID_FUNCTION_NAME = "[a-zA-Z][a-zA-Z0-9]*";
    public static final String ARITHMETIC_OPERATOR = "[+\\-*/]";
    public static final String LOGIC_OPERATOR = "=|<|>|<=|>=|/=";
    public static final String EXPRESSION_OPENER = "\\(";
    public static final String EXPRESSION_CLOSER = "\\)";
}
