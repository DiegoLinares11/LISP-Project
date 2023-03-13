package org.project.parsing;

import org.project.lexing.Lexer;

import java.util.List;

public class Parser {

    public static TreeNode buildNodeTree (List<String> tokens){
        return buildHelper(tokens);
    }

    public static TreeNode buildNodeTree (String expression){
        Lexer l = new Lexer();
        List<String> tokens = l.getTokens(expression);
        return buildHelper(tokens);
    }

    private static TreeNode buildHelper(List<String> tokens){
        TreeNode rootNode = new SExpression(tokens);
        return rootNode;
    }
}
