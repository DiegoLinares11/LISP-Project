package org.project.parsing.primitiveFunctions;
import org.project.lexing.Patterns;
import org.project.parsing.Atom;
import org.project.parsing.TreeNode;

import java.util.List;
import static org.project.parsing.primitiveFunctions.functionHelper.*;

public class ArithmeticFunctions {

    //#######################################
    //--------- ARITHMETIC OPERATORS -------
    //#######################################

    public static TreeNode add (List<TreeNode> args){
        areArgumentsValid(args, "add", 0,
                (inArgs, reqArgs) -> inArgs > reqArgs, Patterns.NUMBER);
        double result = args.stream()
                .map(TreeNode::toString)
                .map(Double::parseDouble)
                .reduce(0.0, Double::sum);
        return new Atom(result);
    }

    public static TreeNode subtraction(List<TreeNode> args){
        areArgumentsValid(args, "subtraction", 0,
                (inArgs, reqArgs) -> inArgs > reqArgs, Patterns.NUMBER);
        double minuend = nodeAsNumeric(getFirstMember(args));
        double subtrahend = nodeAsNumeric(add(getRemainingMembers(args)));
        return new Atom(minuend -subtrahend);
    }

    public static TreeNode multiplication (List<TreeNode> args){
        areArgumentsValid(args, "multiplication", 0,
                (inArgs, reqArgs) -> inArgs > reqArgs, Patterns.NUMBER);
        double result = args.stream()
                .map(TreeNode::toString)
                .map(Double::parseDouble)
                .reduce(1.0, (a, b) -> (a * b));
        return new Atom(result);
    }

    public static TreeNode division(List<TreeNode> args){
        areArgumentsValid(args, "division", 0,
                (inArgs, reqArgs) -> inArgs > reqArgs, Patterns.NUMBER);
        double dividend = nodeAsNumeric(getFirstMember(args));
        double divisor = nodeAsNumeric(multiplication(getRemainingMembers(args)));
        return new Atom(dividend / divisor);
    }
}
