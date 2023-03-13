package org.project.parsing;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void buildNodeTree() {
        TreeNode tree = Parser.buildNodeTree("(+ 2 (+ 1 2))");
        assertEquals("[list, [+, 2, [+, 1, 2]]]", tree.toString());
    }

    @Test
    public void buildNodeTree2() {
        TreeNode tree = Parser.buildNodeTree("(defun owo (a b) (+ a b))");
        assertEquals(tree.toString(), "[list, [defun, owo, [a, b], [+, a, b]]]");
    }

    @Test
    public void unevenParenthesisMustThrowException() {
        assertThrows(RuntimeException.class,
                () -> Parser.buildNodeTree("(defun owo (a b) (+ a b)"));
    }
}