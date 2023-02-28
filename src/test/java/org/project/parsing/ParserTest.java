package org.project.parsing;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void buildNodeTree() {
        Parser p = new Parser();
        TreeNode tree = p.buildNodeTree("(+ 2 (+ 1 2))");
        assertEquals(tree.toString(), "[[+, 2, [+, 1, 2]]]");
    }

    @Test
    public void buildNodeTree2() {
        Parser p = new Parser();
        TreeNode tree = p.buildNodeTree("(defun owo (a b) (+ a b))");
        assertEquals(tree.toString(), "[[defun, owo, [a, b], [+, a, b]]]");
    }

    @Test
    public void unevenParenthesisMustThrowException() {
        assertThrows(RuntimeException.class,
                () -> {
            Parser p = new Parser();
            p.buildNodeTree("(defun owo (a b) (+ a b)");
        });
    }
}