package org.project.parsing.primitiveFunctions;

import org.project.parsing.TreeNode;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A bundle of utility methods, used for working with Lisp operators with more ease.
 * Includes methods for:
 * - Casting
 * - Arguments validation
 * - Arguments selection.
 */
public class functionHelper {

    public static double nodeAsNumeric (TreeNode node){
        return Double.parseDouble(node.toString());
    }

    public static boolean nodeAsBoolean (TreeNode node){return node.toString().equals("T");}

    public static void areArgumentsValid (List<TreeNode> args, String functionName,
                                           int numArgs,
                                           BiFunction<Integer, Integer, Boolean> requiredArgs){

        if (!requiredArgs.apply(args.size(), numArgs))
            throw new RuntimeException("Invalid number of arguments for \"" + functionName+ "\" function."
                    + "\n Required: " + numArgs
                    + "\n Given: " + args.size());
    }

    public static void areArgumentsValid (List<TreeNode> args, String functionName,
                                           int numArgs,
                                           BiFunction<Integer, Integer, Boolean> requiredArgs,
                                           String type){
        areArgumentsValid(args, functionName, numArgs, requiredArgs);
        for (TreeNode node: args) {
            if (!isNodeType(node, type))
                throw new RuntimeException("\n\tERROR on :" + functionName + args.toString()
                        + "\n\tIlegal args types for: " + functionName
                        + "\n\tArgs must follow regex pattern: " + type);
        }

    }

    public static boolean isNodeType (TreeNode node, String type){
        return node.toString().matches(type);
    }

    public static TreeNode getFirstMember(List<TreeNode> children){
        return children.get(0);
    }

    public static List<TreeNode> getRemainingMembers(List<TreeNode> children){
        return children.subList(1, children.size());
    }
}
