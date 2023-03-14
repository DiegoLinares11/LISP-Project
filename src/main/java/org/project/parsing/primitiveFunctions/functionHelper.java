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

    /**
     * Transform a node value to a double.
     * @param node Node to get its value.
     * @return Node numeric value.
     */
    public static double nodeAsNumeric (TreeNode node){
        return Double.parseDouble(node.toString());
    }

    /**
     * Transform a node value to a boolean.
     * @param node Node to get its value.
     * @return Node boolean value.
     */
    public static boolean nodeAsBoolean (TreeNode node){return node.toString().equals("T");}

    /**
     * Check if a set of arguments, fill a function's requirements.
     * Ex:
     * numArgs = 2 , requiredArgs = (in, req) -> in > req
     * Will mean, that this functions accepts MORE THAN 2 args.
     * @param args Args to check.
     * @param functionName Function's name.
     * @param numArgs Required number of args.
     * @param requiredArgs Function for args number evaluation.
     * @throws RuntimeException if args do not fill requirements.
     */
    public static void areArgumentsValid (List<TreeNode> args, String functionName,
                                           int numArgs,
                                           BiFunction<Integer, Integer, Boolean> requiredArgs){

        if (!requiredArgs.apply(args.size(), numArgs))
            throw new RuntimeException("Invalid number of arguments for \"" + functionName+ "\" function."
                    + "\n Required: " + numArgs
                    + "\n Given: " + args.size());
    }

    /**
     * Check if a set of arguments, fill a function's requirements.
     * Ex:
     * numArgs = 3 , requiredArgs = (in, req) -> in = req, type = [1-9]+
     * Will mean, that this functions accepts JUST 3 args, and they need to be NUMBERS.
     *
     * @param args Args to check.
     * @param functionName Function's name.
     * @param numArgs Required number of args.
     * @param requiredArgs Function for args number evaluation.
     * @param type Regex pattern, of the required data type.
     * @throws RuntimeException if args do not fill requirements.
     */
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

    /**
     * Checks if a node's is of a specific data type.
     * @param node Node to check.
     * @param type Regex pattern, of the data type.
     * @return True = match type; false = not match.
     */
    public static boolean isNodeType (TreeNode node, String type){
        return node.toString().matches(type);
    }

    /**
     * Return the first element of a list of children.
     * @param members List of nodes.
     * @return First member of the given list.
     */
    public static TreeNode getFirstMember(List<TreeNode> members){
        return members.get(0);
    }

    /**
     * Return a sublist of children, containing all elements except for the first.
     * @param members List of nodes.
     * @return All members, except for the first.
     */
    public static List<TreeNode> getRemainingMembers(List<TreeNode> members){
        return members.subList(1, members.size());
    }
}
