package org.project.parsing;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeNodeTest {

    @Test
    public void getInstance() {
        TreeNode s = new SExpression("(+ 2 (+ 1 2))");
        System.out.println(s.toString());
        assertEquals("[list, [+, 2, [+, 1, 2]]]", s.toString());
    }

    @Test
    public void getInstance2() {
        TreeNode s = new SExpression("(defun owo (a b) (+ a b))");
        System.out.println(s.toString());
        assertEquals("[list, [defun, owo, [a, b], [+, a, b]]]", s.toString());
    }

    @Test
    public void unevenParenthesisMustThrowException() {
        assertThrows(RuntimeException.class,
                () -> new SExpression("(defun owo (a b) (+ a b)"));
    }
}