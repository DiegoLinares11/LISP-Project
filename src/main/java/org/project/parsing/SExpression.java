package org.project.parsing;

import org.project.lexing.Lexer;
import org.project.lexing.Patterns;
import java.util.List;

public class SExpression extends TreeNode{

    public SExpression(List<String> tokens){
        instanceHelper(tokens);
    }

    public SExpression(String expression){
        Lexer l = new Lexer();
        List<String> tokens = l.getTokens(expression);
        instanceHelper(tokens);
    }

    @Override
    public boolean isSExpression() {
        return true;
    }

    @Override
    public TreeNode evaluate() {
        return null;
    }

    private boolean isValid(List<String> tokens){
        if (!tokens.get(0).matches(Patterns.EXPRESSION_OPENER)) // Every expression must starts with "(";
            return false;

        int openParenthesis = 1;                           // Looping through tokens finding parenthesis.
        for (int i=1; openParenthesis > 0 && i < tokens.size(); i++){
            if(tokens.get(i).matches(Patterns.EXPRESSION_OPENER))
                openParenthesis++;
            else if (tokens.get(i).matches(Patterns.EXPRESSION_CLOSER))
                openParenthesis--;
        }
        return openParenthesis == 0;                        // Returns if there are uneven pairs of parenthesis.
    }

    private int getSExpressionCloser(List<String> tokens, int start){
        int openParenthesis = 1;
        int closedParenthesis = 0;
        int i = start + 1;
        while(i < tokens.size()){
            if(tokens.get(i).matches(Patterns.EXPRESSION_OPENER))
                openParenthesis++;
            if(tokens.get(i).matches(Patterns.EXPRESSION_CLOSER))
                closedParenthesis ++;
            if(openParenthesis == closedParenthesis)
                break;
            i++;
        }
        return i;   // We are sure there must be a close parenthesis at the end of the expression.
    }

    private void instanceHelper(List<String> tokens){
        this.tokens = tokens;
        if (!isValid(tokens))
            throw new RuntimeException("Error: Uneven amount of parenthesis.");

        for (int index=1; index < tokens.size();) {
            String token = tokens.get(index);
            if (token.matches(Patterns.NUMBER) || token.matches(Patterns.BOOLEAN)
                    || token.matches(Patterns.LITERAL) || token.matches(Patterns.ARITHMETIC_OPERATOR)){
                this.childNodes.add(new Atom(token));
                index ++;
            } else if (token.matches(Patterns.EXPRESSION_OPENER)) {
                int endOfSubExpression = getSExpressionCloser(tokens, index);
                List<String> subExpression = tokens.subList(index, endOfSubExpression + 1);
                this.childNodes.add(new SExpression(subExpression));
                index = endOfSubExpression + 1;    // Continue Parsing after subExpression.
            } else if (token.matches(Patterns.EXPRESSION_CLOSER)) {
                index++;
            }
        }
    }
}
