package org.project;

import org.junit.Test;
import org.project.lexing.Lexer;
import org.project.parsing.Parser;
import org.project.parsing.SExpression;
import org.project.parsing.TreeNode;

import java.io.File;

import static org.junit.Assert.*;
import static org.project.parsing.primitiveFunctions.functionHelper.nodeAsNumeric;

public class InterpreterTest {

    @Test
    public void evaluateFactorial() {
        File lispFile = new File("./testFiles/factorialTest.lisp");
        SExpression result = (SExpression) (Interpreter.evaluate(
                Parser.buildNodeTree(Lexer.getTokens(lispFile))
        ));
        assertEquals(6.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void evaluateFibonacci() {
        File lispFile = new File("./testFiles/fibonacciTest.lisp");
        SExpression result = (SExpression) (Interpreter.evaluate(
                Parser.buildNodeTree(Lexer.getTokens(lispFile))
        ));
        assertEquals(5.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }
}