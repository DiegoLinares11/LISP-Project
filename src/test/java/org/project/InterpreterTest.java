package org.project;

import org.junit.Test;
import org.project.lexing.Lexer;
import org.project.parsing.Parser;

import java.io.File;

import static org.junit.Assert.*;

public class InterpreterTest {

    @Test
    public void evaluate() {
        File lispFile = new File("./test.lisp");
        Interpreter.evaluate(
                Parser.buildNodeTree(Lexer.getTokens(lispFile))
        );
    }
}