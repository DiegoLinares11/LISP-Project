package org.project.parsing;

import org.project.lexing.Lexer;

import java.util.List;

/** Representation of a PARSER... which means: given a list of TOKENS, it builds a hierarchy tree
 * compound by nodes, which can be atoms (do not have children) or SExpressions (do have children);
 * Conforming the SECOND LAYER of code analysis after the LEXER.
 *
 * We can say is the responsable for translating LISP sintaxis to one Java can understand.
 *
 * More information on SExpression and Atom classes.
 *
 * Ex input:
 *  (, list, (, +, 2, 3, ), (, print (, *,  2, 10, ), ), )
 *
 * Ex output:
 * ROOT NODE/
 * ├─ list
 * ├─ EXP1/
 * │  ├─ +
 * │  ├─ 2
 * │  ├─ 3
 * ├─ EXP2/
 * │  ├─ print
 * │  ├─ EXPR3/
 * │  │  ├─ *
 * │  │  ├─ 2
 * │  │  ├─ 10
 */
public class Parser {

    /**
     * Builds a tree with the given tokens.
     * @param tokens Tokens to parse.
     * @return Root node of the generated tree.
     */
    public static TreeNode buildNodeTree (List<String> tokens){
        return buildHelper(tokens);
    }

    /**
     * Builds a tree with a given Lisp Expression.
     * @param expression Expression to parse.
     * @return Root node of the generated tree.
     */
    public static TreeNode buildNodeTree (String expression){
        Lexer l = new Lexer();
        List<String> tokens = l.getTokens(expression);
        return buildHelper(tokens);
    }

    /**
     * Takes a list of tokens and parse them.
     * @param tokens Tokens to parse.
     * @return Root node of the generated tree.
     */
    private static TreeNode buildHelper(List<String> tokens){
        TreeNode rootNode = new SExpression(tokens);
        return rootNode;
    }
}
