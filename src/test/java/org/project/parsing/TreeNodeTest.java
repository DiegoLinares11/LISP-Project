package org.project.parsing;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeNodeTest {

    @Test
    public void getInstance() {
        TreeNode s = new SExpression("(+ 2 (+ 1 2))");
        System.out.println(s.toString());
        assertEquals(s.toString(), "[[+, 2, [+, 1, 2]]]");
    }

    @Test
    public void getInstance2() {
        TreeNode s = new SExpression("(defun owo (a b) (+ a b))");
        System.out.println(s.toString());
        assertEquals(s.toString(), "[[defun, owo, [a, b], [+, a, b]]]");
    }

    @Test
    public void unevenParenthesisMustThrowException() {
        assertThrows(RuntimeException.class,
                () -> new SExpression("(defun owo (a b) (+ a b)"));
    }
}