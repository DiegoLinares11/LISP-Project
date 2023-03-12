package org.project.parsing;

import org.project.lexing.Lexer;
import org.project.lexing.Patterns;
import org.project.parsing.primitiveFunctions.ArithmeticFunctions;
import org.project.parsing.primitiveFunctions.LispPrimitiveFunctions;
import org.project.parsing.primitiveFunctions.LogicFunctions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
    public SExpression(List<TreeNode> nodes, boolean clone ){
        this.childNodes.addAll(nodes);
    }

    /**
     * Report this is an Expression.
     * @return True, because SExpression IS an SExpression.
     */
    @Override
    public boolean isAtom() {
        return false;
    }

    /**
     * Evaluates this Sexpression recursively, by first evaluating its Child Nodes, and then
     * evaluating itself.
     * @param context Context of user defined variables and functions.
     * @return Tree node containing the result.
     */
    @Override
    public TreeNode evaluate(Context context) {
        // Interchanging variables and evaluating child nodes.
        evaluateChildNodes(context);

        // Evaluating Expression itself.
        String operator = this.getNode(0).toString(); // Operators are always in the first position.
        List<TreeNode> operands = this.getNodes(1, this.childNodes.size());
        TreeNode result = null;
        if (operator.matches(Patterns.ARITHMETIC_OPERATOR))
            switch (operator){
                case "+" -> result = ArithmeticFunctions.add(operands);
                case "-" -> result = ArithmeticFunctions.subtraction(operands);
                case "*" -> result = ArithmeticFunctions.multiplication(operands);
                case "/" -> result = ArithmeticFunctions.division(operands);
            }
        else if (operator.matches(Patterns.LOGIC_OPERATOR))
            switch (operator){
                case "=" -> result = LogicFunctions.eq(operands);      // Equal
                case "not" -> result = LogicFunctions.not(operands);   // not
                case "/=" -> result = LogicFunctions.ne(operands);     // Not equal
                case ">" -> result = LogicFunctions.gt(operands);      // Greater than
                case ">=" -> result = LogicFunctions.ge(operands);     // Greater or equal than
                case "<" -> result = LogicFunctions.lt(operands);      // Less than
                case "<=" -> result = LogicFunctions.le(operands);     // Less or equal than
                case "atom" -> result = LogicFunctions.atom(operands); // atom
            }
        else {
            try {
                // Try to find if it is a primitive lisp function.
                result = findPrimitiveFunction(operator, operands, context, LispPrimitiveFunctions.class);
            } catch (InvocationTargetException e){
                System.out.println(e.getCause().getMessage());
                throw new RuntimeException();
            } catch (NoSuchMethodException e){
                // If no method found, check if it is a User's defined function.
                if(context.functionExist(operator))
                    System.out.println("Call function");
                else
                    throw new RuntimeException("Operator \"" + operator + "\" Does NOT exist.");
            }
        }
        return result;
    }

    /**
     * Returns the "N" CHILD NODE of this SExpression.
     * Ex child nodes:
     *  [+ 40, 10] , index = 1
     * Ex output:
     * [40]
     * @param index Node index.
     * @return Return the child node in the selected index.
     */
    public TreeNode getNode(int index){
        return this.childNodes.get(index);
    }

    /**
     * Returns a sublist of nodes, within a given index range.
     * Ex child nodes:
     *  [+ 40, 10], start = 1, end = 3
     * Ex output:
     * [40, 10]
     * @param start Start range index, INCLUSIVE.
     * @param end End range index, EXCLUSIVE.
     * @return A sublist of childNodes.
     */
    public List<TreeNode> getNodes(int start, int end){
        return this.childNodes.subList(start, end);
    }

    private void evaluateChildNodes(Context context){
        List<TreeNode> newChildNodes = new ArrayList<>();
        // Interchanging variables and evaluating child nodes.
        for(TreeNode childNode : this.childNodes){
            if(childNode.toString().matches("quote|setq")){ // If operator is "quote" or "setq" STOP evaluation.
                newChildNodes = this.childNodes;
                break;
            }
            else if (context.variableExist(childNode.toString()))
                newChildNodes.add(context.getVariable(childNode.toString()));
            else{
                TreeNode evaluation = childNode.evaluate(context);
                if (evaluation != null) // Just add those child nodes which don't evaluate to null;
                    newChildNodes.add(evaluation);
            }
        }
        this.childNodes = newChildNodes;
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
     * Try to find a primitive lisp function within a class through Java Reflection,
     * invoke it, and return the evaluated TreeNode..
     * @param name Primitive function name you want to execute. (atom, setq, cond)
     * @param args Function's args.
     * @param primitiveFunctions Class to search in.
     * @return Primitive function result.
     */
    private TreeNode findPrimitiveFunction(String name, List<TreeNode> args, Context context, Class primitiveFunctions)
            throws InvocationTargetException, NoSuchMethodException{
        Method m;
        Object o = null;
        try {
            m = primitiveFunctions.getDeclaredMethod(name.toLowerCase(), List.class, Context.class);
            m.setAccessible(true);
            o = m.invoke(null, args, context);
        }catch (IllegalAccessException e){
            // Statements is never reached.
        }
        return (TreeNode) o;
    }
}
