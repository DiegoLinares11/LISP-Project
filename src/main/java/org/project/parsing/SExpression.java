package org.project.parsing;

import org.project.lexing.Lexer;
import org.project.lexing.Patterns;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Type of TreeNode that CAN HAVE CHILD NODES, which can be atoms or other SExpressions.
 *  Expressions like:
 *      ( + 3 (- 2 5)) -> SExpression[ +, 3, SExpression[ -, 2, 5]]
 *  Wil be translated to SExpressions during parsing.
 */
public class SExpression extends TreeNode{

    /**
     * Creates an SExpression from a list of tokens.
     * @param tokens Tokens to parse.
     */
    public SExpression(List<String> tokens){
        instanceHelper(tokens);
    }

    /**
     * Creates an Expression from a String of tokens.
     * @param expression Tokens to parse.
     */
    public SExpression(String expression){
        Lexer l = new Lexer();
        List<String> tokens = l.getTokens(expression);
        instanceHelper(tokens);
    }

    /**
     * Creates an Expression from an already created list of nodes.
     * @param nodes Nodes.
     */
    public SExpression(ArrayList<TreeNode> nodes){
        this.childNodes.addAll(nodes);
    }

    /**
     * Report this is an Expression.
     * @return True, because SExpression IS an SExpression.
     */
    @Override
    public boolean isSExpression() {
        return true;
    }

    @Override
    public TreeNode evaluate(Context context) {
        // Interchanging variables and evaluating child nodes.
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

    /**
     * Returns the FIRST CHILD NODE of this SExpression.
     * @return First child node.
     * Ex child nodes:
     *  [+ 40, 10]
     * Ex output:
     * [+]
     */
    public TreeNode car(){
        if (this.childNodes.size() == 0)
            throw new RuntimeException("Expression has no child-nodes");
        return this.childNodes.get(0);
    }

    /**
     * Returns the REST OF CHILD NODES which follow the first one.
     * Ex child nodes:
     *  [+ 40, 10]
     * Ex output:
     * [40, 10]
     * @return An Expression containing the rest of child nodes.
     */
    public SExpression cdr(){
        if (this.childNodes.size() < 1)
            throw new RuntimeException("Expression has no enough child-nodes");
        return new SExpression(new ArrayList<>(
                this.childNodes.subList(1, this.childNodes.size())
        ));
    }

    /**
     * When Parsing checks, if a list of tokens is a valid lisp expression.
     * By scan the position and number of parenthesis, in an list o tokens.
     * Ex input:
     *  () + 3 (- 2 5) -> Incomplete
     *  Ex output:
     *  false
     * @param tokens Tokens to analyze.
     * @return True : is valid SExpression, otherwise false.
     */
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

    /**
     * Given an Expression, and the index of an Expression Opener "(", it finds the index
     * of it's corresponden Expression Closer ")".
     * Ex input:
     * "( 3 ( 4 ) )", startIndex = 3
     * Ex output:
     * 5
     * @param tokens Tokens to read.
     * @param start Index of the Expression Opener you would like to know its closer.
     * @return Index of the Expression Closer.
     */
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
        return i;   // Else, we are sure there must be a close parenthesis at the end of the expression.
    }

    /**
     * Constructor Helper. Takes a list of tokens, checks if are a valid Lisp Expression
     * and parse them to a tree of nodes. Finishing by returning the root node. It is done recursively.
     * @param tokens Tokens to parse.
     */
    private void instanceHelper(List<String> tokens){
        if (!isValid(tokens))
            throw new RuntimeException("Error: Uneven amount of parenthesis.");

        for (int index=1; index < tokens.size();) {
            String token = tokens.get(index);
            // If it's an Expression Opener, create a new Expression with rest of tokens.
            if (token.matches(Patterns.EXPRESSION_OPENER)){
                int endOfSubExpression = getSExpressionCloser(tokens, index);
                List<String> subExpression = tokens.subList(index, endOfSubExpression + 1);
                this.childNodes.add(new SExpression(subExpression));
                index = endOfSubExpression + 1;    // Continue Parsing after subExpression.
            }
            else if (token.matches(Patterns.EXPRESSION_CLOSER)) {
                index ++;
            }
            // Else, the token must be a number, text or boolean, therefore creating and atom.
            else {
                this.childNodes.add(new Atom(token));
                index ++;
            }
        }
    }

    /**
     * Try to find a primitive lisp function within a class, invoke it, and return the evaluated TreeNode..
     * @param name Primitive function name you want to execute. (atom, setq, cond)
     * @param args Function's args.
     * @param primitiveFunctions Class to search in.
     * @return Primitive function result.
     */
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
