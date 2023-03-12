package org.project.parsing.primitiveFunctions;

import org.project.lexing.Patterns;
import org.project.parsing.Context;
import org.project.parsing.SExpression;
import org.project.parsing.TreeNode;

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
        if (!variableName.toString().matches(Patterns.VALID_VARIABLE_NAME))
            throw new RuntimeException("\n\tERROR on : setq" + args.toString()
                    + "\n\tIlegal variable name: " + variableName.toString()
                    + "\n\tArgs must follow regex pattern: " + Patterns.VALID_FUNCTION_NAME);
        TreeNode variableValue = args.get(1);
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

    //#######################################
    //--------- CONDITIONALS -------
    //#######################################
}
