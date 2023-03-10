package org.project.parsing;
import org.project.lexing.Patterns;

import java.util.List;
import java.util.function.BiFunction;

public class PrimitiveFunctions {

    //#######################################
    //--------- ARITHMETIC OPERATORS -------
    //#######################################

    public static TreeNode car (SExpression expression){
        areArgumentsValid(expression, "car", 1, Integer::equals);
        return expression.car();
    }

    public static TreeNode cdr (SExpression expression){
        areArgumentsValid(expression, "cdr", 1, Integer::equals);
        return expression.cdr();
    }

    public static TreeNode add (SExpression expression){
        areArgumentsValid(expression, "add", 0,
                (args, reqArgs) -> args > reqArgs, Patterns.NUMBER);
        List<TreeNode> operands = expression.getChildNodes();
        double result = operands.stream()
                .map(TreeNode::toString)
                .map(Double::parseDouble)
                .reduce(0.0, Double::sum);
        return new Atom(result);
    }

    public static TreeNode subtraction(SExpression expression){
        areArgumentsValid(expression, "subtraction", 0,
                (args, reqArgs) -> args > reqArgs, Patterns.NUMBER);
        double minuend = nodeAsNumeric(expression.car());
        double subtrahend = nodeAsNumeric(add(expression.cdr()));
        return new Atom(minuend -subtrahend);
    }

    public static TreeNode multiplication (SExpression expression){
        areArgumentsValid(expression, "multiplication", 0,
                (args, reqArgs) -> args > reqArgs, Patterns.NUMBER);
        List<TreeNode> operands = expression.getChildNodes();
        double result = operands.stream()
                .map(TreeNode::toString)
                .map(Double::parseDouble)
                .reduce(1.0, (a, b) -> (a * b));
        return new Atom(result);
    }

    public static TreeNode division(SExpression expression){
        areArgumentsValid(expression, "division", 0,
                (args, reqArgs) -> args > reqArgs, Patterns.NUMBER);
        double dividend = nodeAsNumeric(expression.car());
        double divisor = nodeAsNumeric(multiplication(expression.cdr()));
        return new Atom(dividend / divisor);
    }

    //#######################################
    //--------- LOGIC OPERATORS -------
    //#######################################

    public static TreeNode not(SExpression expression){
        areArgumentsValid(expression, "not", 1, Integer::equals, Patterns.BOOLEAN);
        boolean operand = nodeAsBoolean(expression.car());
        return new Atom(!operand);
    }

    // Equal
    public static TreeNode eq(SExpression expression){
        areArgumentsValid(expression, "eq", 2, Integer::equals);
        String operand1 = expression.getChildNodes().get(0).toString();
        String operand2 = expression.getChildNodes().get(1).toString();
        return new Atom(operand1.equals(operand2));
    }

    // Not equal.
    public static TreeNode ne(SExpression expression){
        areArgumentsValid(expression, "ne", 2, Integer::equals);
        String operand1 = expression.getChildNodes().get(0).toString();
        String operand2 = expression.getChildNodes().get(1).toString();
        return new Atom(!operand1.equals(operand2));
    }

    // Smaller than
    public static TreeNode lt (SExpression expression) {
        areArgumentsValid(expression, "lt", 2, Integer::equals, Patterns.NUMBER);
        double operand1 = nodeAsNumeric(expression.getChildNodes().get(0));
        double operand2 = nodeAsNumeric(expression.getChildNodes().get(1));
        return new Atom(operand1 < operand2);
    }

    // Smaller than or equal
    public static TreeNode le (SExpression expression) {
        areArgumentsValid(expression, "le", 2, Integer::equals, Patterns.NUMBER);
        double operand1 = nodeAsNumeric(expression.getChildNodes().get(0));
        double operand2 = nodeAsNumeric(expression.getChildNodes().get(1));
        return new Atom(operand1 <= operand2);
    }

    // greater than
    public static TreeNode gt (SExpression expression) {
        areArgumentsValid(expression, "gt", 2, Integer::equals, Patterns.NUMBER);
        double operand1 = nodeAsNumeric(expression.getChildNodes().get(0));
        double operand2 = nodeAsNumeric(expression.getChildNodes().get(1));
        return new Atom(operand1 > operand2);
    }

    // Greater than or equal
    public static TreeNode ge (SExpression expression) {
        areArgumentsValid(expression, "ge", 2, Integer::equals, Patterns.NUMBER);
        double operand1 = nodeAsNumeric(expression.getChildNodes().get(0));
        double operand2 = nodeAsNumeric(expression.getChildNodes().get(1));
        return new Atom(operand1 >= operand2);
    }


    /**
     * Prints the following Node, and return it.
     * @param expression Node to print. Can just be one.
     * @return The given node.
     */
    public static TreeNode print(SExpression expression){
        areArgumentsValid(expression, "print", 1, Integer::equals);
        System.out.println(expression.car().toString());
        return expression;
    }

    private static double nodeAsNumeric (TreeNode node){
        return Double.parseDouble(node.toString());
    }

    private static boolean nodeAsBoolean (TreeNode node){return node.toString().equals("T");}

    private static void areArgumentsValid (SExpression expression, String functionName,
                                           int numArgs,
                                           BiFunction<Integer, Integer, Boolean> requiredArgs){

        if (expression.getChildNodes().size() == 0)
            throw new RuntimeException("No operands given for: " + functionName);
        if (!requiredArgs.apply(expression.getChildNodes().size(), numArgs))
            throw new RuntimeException("Invalid number of arguments for \"eq\" function."
                + "\n Required: " + numArgs
                + "\n Given: " + expression.getChildNodes().size());
    }

    private static void areArgumentsValid (SExpression expression, String functionName,
                                           int numArgs,
                                           BiFunction<Integer, Integer, Boolean> requiredArgs,
                                           String type){
        areArgumentsValid(expression, functionName, numArgs, requiredArgs);
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
