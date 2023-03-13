package org.project.parsing.primitiveFunctions;

import org.project.lexing.Patterns;
import org.project.parsing.*;

import java.util.ArrayList;
import java.util.List;

import static org.project.parsing.primitiveFunctions.functionHelper.*;

public class LispPrimitiveFunctions {

    //#######################################
    //--------- EXPRESSION SEPARATORS -------
    //#######################################

    public static TreeNode car (List<TreeNode> args, Context context){
        areArgumentsValid(args, "car", 0, (inArgs, reqArgs) -> inArgs > reqArgs);
        return getFirstMember(args);
    }

    public static TreeNode cdr (List<TreeNode> args, Context context){
        areArgumentsValid(args, "cdr", 0, (inArgs, reqArgs) -> inArgs > reqArgs);
        return new SExpression(getRemainingMembers(args), true);
    }

    public static TreeNode list (List<TreeNode> args, Context context){
        areArgumentsValid(args, "list", 0, (inArgs, reqArgs) -> inArgs >= reqArgs);
        if (args.size() == 0)
            return new SExpression(new ArrayList<>(), true);
        return new SExpression(args, true);
    }

    //#######################################
    //--------- EXPRESSION EVALUATORS -------
    //#######################################

    public static TreeNode eval (List<TreeNode> args, Context context){
        areArgumentsValid(args, "eval", 1, Integer::equals);
        return args.get(0).evaluate(context);
    }

    public static TreeNode quote (List<TreeNode> args, Context context){
        areArgumentsValid(args, "quote", 1, Integer::equals);
        return args.get(0);
    }

    //#######################################
    //--------- EXPRESSION EVALUATORS -------
    //#######################################

    public static TreeNode setq(List<TreeNode> args, Context context){
        areArgumentsValid(args, "setq", 2, Integer::equals);
        TreeNode variableName = getFirstMember(args);
        setq(variableName, args.get(1),context);
        return null;
    }

    public static TreeNode setq(TreeNode variableName, TreeNode variableValue, Context context){
        if (!variableName.toString().matches(Patterns.VALID_VARIABLE_NAME))
            throw new RuntimeException("\n\tERROR on : "
                    + "setq" + variableName.toString() + " " + variableValue.toString()
                    + "\n\tIlegal variable name: " + variableName.toString()
                    + "\n\tArgs must follow regex pattern: " + Patterns.VALID_VARIABLE_NAME);
        context.setVariable(variableName.toString(), variableValue);
        return null;
    }

    /**
     * Prints the following Node, and return it.
     * @param args Node to print. Can just be one.
     * @return The given node.
     */
    public static TreeNode print(List<TreeNode> args, Context context){
        areArgumentsValid(args, "print", 1, Integer::equals);
        System.out.println(getFirstMember(args).toString());
        return null;
    }

    public static TreeNode concatenate(List<TreeNode> args, Context context){
        areArgumentsValid(args, "list", 2, (inArgs, reqArgs) -> inArgs > reqArgs);
        String value = args.stream()
                .map(TreeNode::toString)
                .reduce("", String::concat);
        return new Atom(value);
    }

    //#######################################
    //--------- CUSTOM FUNCTIONS -------
    //#######################################

    public static TreeNode defun(List<TreeNode> args, Context context){
        areArgumentsValid(args, "defun", 3, Integer::equals);
        TreeNode functionName = args.get(0);
        TreeNode functionArgs = args.get(1);
        TreeNode functionBody = args.get(2);

        if (!functionName.toString().matches(Patterns.VALID_FUNCTION_NAME))
            throw new RuntimeException("\n\tERROR on : "
                + "defun" + args.toString()
                + "\n\tIlegal function name: " + functionName.toString()
                + "\n\tFunction's names must follow regex pattern: " + Patterns.VALID_FUNCTION_NAME);
        if (functionArgs.isAtom())
            throw new RuntimeException("\n\tERROR on : "
                    + "defun" + args.toString()
                    + "\n\tIlegal args: " + functionArgs.toString()
                    + "\n\tArgs MUST be encapsulated in a SExpression.");
        if (functionBody.isAtom())
            throw new RuntimeException("\n\tERROR on : "
                    + "defun" + args.toString()
                    + "\n\tIlegal body: " + functionBody.toString()
                    + "\n\tFunction body MUST be a SExpression.");
        context.setFunction(functionName.toString(),
                new userFunction(functionName.toString(), functionArgs, functionBody));
        return null;
    }

    //#######################################
    //--------- CONDITIONALS -------
    //#######################################
    public static TreeNode cond(List<TreeNode> statements, Context context){
        areArgumentsValid(statements, "cond", 0, (inArgs, reqArgs) -> inArgs > reqArgs);
        // Loop through each statement ((condition) (body))
        for(int i= 0; i < statements.size(); i ++){
            SExpression statement = (SExpression) statements.get(i);
            if(statement.getChildNodes().size() != 2)
                throw new RuntimeException("\n\tERROR on : cond" + statement.toString()
                        + "\n\tStatement: " + statement.toString()
                        + "\n\tEvery statement can just contain 2 elements : condition, body");

            // Evaluating statement's condition.
            TreeNode condition = statement.getNode(0).evaluate(context);
            if(!condition.toString().matches(Patterns.BOOLEAN))
                throw new RuntimeException("Condition of statement: "
                        + statement.toString()
                        + " MUST evaluate to a boolean (T, NIl)");

            // If condition evaluates to "T", evaluate its body.
            if(nodeAsBoolean(condition))
                return statement.getNode(1).evaluate(context);
        }
        return null;
    }
}
