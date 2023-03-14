package org.project.parsing.primitiveFunctions;

import org.project.lexing.Patterns;
import org.project.parsing.Atom;
import org.project.parsing.TreeNode;

import java.util.List;

import static org.project.parsing.primitiveFunctions.functionHelper.*;

/**
 * Defines the basic logic operators available in Lisp.
 */
public class LogicFunctions {

    //#######################################
    //--------- LOGIC OPERATORS -------
    //#######################################

    public static TreeNode not(List<TreeNode> args){
        areArgumentsValid(args, "not", 1, Integer::equals, Patterns.BOOLEAN);
        boolean operand = nodeAsBoolean(getFirstMember(args));
        return new Atom(!operand);
    }

    // Equal
    public static TreeNode eq(List<TreeNode> args){
        areArgumentsValid(args, "eq", 2, Integer::equals);
        String operand1 = args.get(0).toString();
        String operand2 = args.get(1).toString();
        return new Atom(operand1.equals(operand2));
    }

    // Not equal.
    public static TreeNode ne(List<TreeNode> args){
        areArgumentsValid(args, "ne", 2, Integer::equals);
        String operand1 = args.get(0).toString();
        String operand2 = args.get(1).toString();
        return new Atom(!operand1.equals(operand2));
    }

    // Smaller than
    public static TreeNode lt (List<TreeNode> args) {
        areArgumentsValid(args, "lt", 2, Integer::equals, Patterns.NUMBER);
        double operand1 = nodeAsNumeric(args.get(0));
        double operand2 = nodeAsNumeric(args.get(1));
        return new Atom(operand1 < operand2);
    }

    // Smaller than or equal
    public static TreeNode le (List<TreeNode> args) {
        areArgumentsValid(args, "le", 2, Integer::equals, Patterns.NUMBER);
        double operand1 = nodeAsNumeric(args.get(0));
        double operand2 = nodeAsNumeric(args.get(1));
        return new Atom(operand1 <= operand2);
    }

    // greater than
    public static TreeNode gt (List<TreeNode> args) {
        areArgumentsValid(args, "gt", 2, Integer::equals, Patterns.NUMBER);
        double operand1 = nodeAsNumeric(args.get(0));
        double operand2 = nodeAsNumeric(args.get(1));
        return new Atom(operand1 > operand2);
    }

    // Greater than or equal
    public static TreeNode ge (List<TreeNode> args) {
        areArgumentsValid(args, "ge", 2, Integer::equals, Patterns.NUMBER);
        double operand1 = nodeAsNumeric(args.get(0));
        double operand2 = nodeAsNumeric(args.get(1));
        return new Atom(operand1 >= operand2);
    }

    public static TreeNode atom(List<TreeNode> args){
        areArgumentsValid(args, "atom", 1, Integer::equals);
        TreeNode operand = getFirstMember(args);
        return  new Atom(operand.isAtom());
    }
}
