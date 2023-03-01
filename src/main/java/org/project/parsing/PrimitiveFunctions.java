package org.project.parsing;
import org.project.lexing.Patterns;

import java.util.List;
import java.util.function.Predicate;

public class PrimitiveFunctions {

    public static TreeNode car (SExpression expression){
        areArgumentsValid(expression, "car", (requiredArgs) -> requiredArgs == 1);
        return expression.car();
    }

    public static TreeNode cdr (SExpression expression){
        areArgumentsValid(expression, "car", (requiredArgs) -> requiredArgs == 1);
        return expression.cdr();
    }

    public static TreeNode add (SExpression expression){
        areArgumentsValid(expression, "add", (requiredArgs) -> requiredArgs > 0, Patterns.NUMBER);
        List<TreeNode> operands = expression.getChildNodes();
        double result = operands.stream()
                .map(TreeNode::toString)
                .map(Double::parseDouble)
                .reduce(0.0, Double::sum);
        return new Atom(result);
    }

    public static TreeNode subtraction(SExpression expression){
        areArgumentsValid(expression, "subtraction", (requiredArgs) -> requiredArgs > 0, Patterns.NUMBER);
        double minuend = nodeAsNumeric(expression.car());
        double subtrahend = nodeAsNumeric(add(expression.cdr()));
        return new Atom(minuend -subtrahend);
    }

    public static TreeNode multiplication (SExpression expression){
        areArgumentsValid(expression, "multiplication", (requiredArgs) -> requiredArgs > 0, Patterns.NUMBER);
        List<TreeNode> operands = expression.getChildNodes();
        double result = operands.stream()
                .map(TreeNode::toString)
                .map(Double::parseDouble)
                .reduce(1.0, (a, b) -> (a * b));
        return new Atom(result);
    }

    public static TreeNode division(SExpression expression){
        areArgumentsValid(expression, "division", (requiredArgs) -> requiredArgs > 0, Patterns.NUMBER);
        double dividend = nodeAsNumeric(expression.car());
        double divisor = nodeAsNumeric(multiplication(expression.cdr()));
        return new Atom(dividend / divisor);
    }

    /**
     * Prints the following Node, and return it.
     * @param expression Node to print. Can just be one.
     * @return The given node.
     */
    public static TreeNode print(SExpression expression){
        areArgumentsValid(expression, "division", (requiredArgs) -> requiredArgs == 0);
        System.out.println(expression.car().toString());
        return expression;
    }

    private static double nodeAsNumeric (TreeNode node){
        return Double.parseDouble(node.toString());
    }

    private static void areArgumentsValid (SExpression expression, String functionName, Predicate<Integer> requiredArgs){
        if (expression.getChildNodes().size() == 0)
            throw new RuntimeException("No operands given for: " + functionName);
        if (!requiredArgs.test(expression.getChildNodes().size()))
            throw new RuntimeException("Invalid number of arguments for \"eq\" function."
                + "\n Required: " + requiredArgs
                + "\n Given: " + expression.getChildNodes().size());
    }

    private static void areArgumentsValid (SExpression expression, String functionName, Predicate<Integer> requiredArgs, String type){
        areArgumentsValid(expression, functionName, requiredArgs);
        for (TreeNode node: expression.getChildNodes()) {
            if (!isNodeType(node, type))
                throw new RuntimeException("\n\tERROR on :" + functionName + expression.toString()
                        + "\n\tIlegal args types for: " + functionName
                        + "\n\tArgs must follow regex pattern: " + type);
        }

    }

    private static boolean isNodeType (TreeNode node, String type){
        return node.toString().matches(type);
    }

}
