package org.project.parsing;

import org.project.lexing.Lexer;
import org.project.lexing.Patterns;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    public SExpression(ArrayList<TreeNode> nodes){
        this.childNodes = nodes;
    }

    @Override
    public boolean isSExpression() {
        return true;
    }

    @Override
    public TreeNode evaluate(Context context) {
        // Interchanging variables and evaluating child nodes..
        for(int i=0; i < childNodes.size(); i++){
            TreeNode childNode = this.childNodes.get(i);
            if(childNode.toString().equals("quote"))
                break;
            else
                this.childNodes.set(i, childNode.evaluate(context));
        }
        // Evaluating Expression.
        String operator = this.car().toString(); // Operators are always in the first position.
        SExpression operands = this.cdr();
        TreeNode result = null;
        if (operands.childNodes.isEmpty()) // Reached only when and expression is totally evaluate.
            return childNodes.get(0);
        else if (operator.equals("eval"))
            return PrimitiveFunctions.eval(operands, context);
        else if (operator.matches(Patterns.ARITHMETIC_OPERATOR))
            switch (operator){
                case "+" -> result = PrimitiveFunctions.add(operands);
                case "-" -> result = PrimitiveFunctions.subtraction(operands);
                case "*" -> result = PrimitiveFunctions.multiplication(operands);
                case "/" -> result = PrimitiveFunctions.division(operands);
            }
        else if (operator.matches(Patterns.LOGIC_OPERATOR))
            switch (operator){
                case "=" -> result = PrimitiveFunctions.eq(operands);      // Equal
                case "/=" -> result = PrimitiveFunctions.ne(operands);     // Not equal
                case ">" -> result = PrimitiveFunctions.gt(operands);      // Greater than
                case ">=" -> result = PrimitiveFunctions.ge(operands);     // Greater or equal than
                case "<" -> result = PrimitiveFunctions.lt(operands);      // Less than
                case "<=" -> result = PrimitiveFunctions.le(operands);     // Less or equal than
            }
        else {
            result = findPrimitiveFunction(operator, operands, PrimitiveFunctions.class);
        }
        return result;
    }

    public TreeNode car(){
        if (this.childNodes.size() == 0)
            throw new RuntimeException("Expression has no child-nodes");
        return this.childNodes.get(0);
    }

    public SExpression cdr(){
        if (this.childNodes.size() < 1)
            throw new RuntimeException("Expression has no enough child-nodes");
        return new SExpression(new ArrayList<>(
                this.childNodes.subList(1, this.childNodes.size())
        ));
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
        if (!isValid(tokens))
            throw new RuntimeException("Error: Uneven amount of parenthesis.");

        for (int index=1; index < tokens.size();) {
            String token = tokens.get(index);
            if (token.matches(Patterns.NUMBER) || token.matches(Patterns.BOOLEAN) || token.matches(Patterns.LITERAL)
                    || token.matches(Patterns.ARITHMETIC_OPERATOR) || token.matches(Patterns.LOGIC_OPERATOR)){
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

    private TreeNode findPrimitiveFunction(String name, SExpression args, Class primitiveFunctions){
        Method m;
        Object o = null;
        try {
            m = primitiveFunctions.getDeclaredMethod(name.toLowerCase(), SExpression.class);
            m.setAccessible(true);
            o = m.invoke(null, args);
            if (o.toString().matches("true"))
                return new Atom("T");
            else if (o.toString().matches("false"))
                return new Atom("NIL");
            else
                return (TreeNode) o;

        } catch (InvocationTargetException e) {
            System.out.println(e.getCause().getMessage());
        }catch (Exception e){
            throw new RuntimeException("Operator " + name + " Does NOT exist.");
        }
        return (TreeNode) o;
    }
}
