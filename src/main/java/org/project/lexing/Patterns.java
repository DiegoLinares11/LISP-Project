package org.project.lexing;

/**
 * This class contains useful regex patterns to identify the different types
 * of tokens a Lisp expression can have.
 * */
public class Patterns {
    public static final String WHITESPACE = "\\s";
    public static final String NUMBER = "[0-9]+";
    public static final String BOOLEAN = "T|NIL";
    public static final String LITERAL = "[a-zA-Z0-9]+";
    public static final String VALID_FUNCTION_NAME = "[a-zA-Z][a-zA-Z0-9]*";
    public static final String ARITHMETIC_OPERATOR = "[+\\-*/]";
    public static final String EXPRESSION_LIMITER = "[()]";
}
