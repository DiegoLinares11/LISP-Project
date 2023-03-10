package org.project.parsing;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrimitiveFunctionsTest {

    @Test
    public void quote() {
        Context context = new Context();
        TreeNode s = new SExpression("((quote (+ 2 3)))");
        TreeNode result = s.evaluate(context);
        assertEquals("[[+, 2, 3]]", result.toString());
    }

    @Test
    public void eval() {
        Context context = new Context();
        TreeNode s = new SExpression("(eval (quote (+ 2 3)))");
        TreeNode result = s.evaluate(context);
        assertEquals(5.0d, Double.parseDouble(result.toString()), 0.5);
    }

    @Test
    public void add() {
        Context context = new Context();
        TreeNode s = new SExpression("(+ 20 50 (+ 2 3))");
        TreeNode result = s.evaluate(context);
        assertEquals(75.0d, Double.parseDouble(result.toString()), 0.5);

    }

    @Test
    public void subtraction() {
        Context context = new Context();
        TreeNode s = new SExpression("(- 18 7 1)");
        TreeNode result = s.evaluate(context);
        assertEquals(10.0d, Double.parseDouble(result.toString()), 0.5);

    }

    @Test
    public void multiplication() {
        Context context = new Context();
        TreeNode s = new SExpression("(* 3 2 6)");
        TreeNode result = s.evaluate(context);
        assertEquals(36.0d, Double.parseDouble(result.toString()), 0.5);

    }

    @Test
    public void division() {
        Context context = new Context();
        TreeNode s = new SExpression("(/ 100 5 10)");
        TreeNode result = s.evaluate(context);
        assertEquals(2.0d, Double.parseDouble(result.toString()), 0.5);
    }

    @Test
    public void eq() {
        Context context = new Context();
        TreeNode s = new SExpression("(= hello hello)");
        TreeNode result = s.evaluate(context);
        assertEquals("T", result.toString());
    }

    @Test
    public void ne() {
        Context context = new Context();
        TreeNode s = new SExpression("(/= 3 3)");
        TreeNode result = s.evaluate(context);
        assertEquals("NIL", result.toString());
    }

    @Test
    public void gt() {
        Context context = new Context();
        TreeNode s = new SExpression("(> 5 2)");
        TreeNode result = s.evaluate(context);
        assertEquals("T", result.toString());
    }

    @Test
    public void ge() {
        Context context = new Context();
        TreeNode s = new SExpression("(>= 5 5)");
        TreeNode result = s.evaluate(context);
        assertEquals("T", result.toString());
    }

    @Test
    public void lt() {
        Context context = new Context();
        TreeNode s = new SExpression("(< 4 10)");
        TreeNode result = s.evaluate(context);
        assertEquals("T", result.toString());
    }

    @Test
    public void le() {
        Context context = new Context();
        TreeNode s = new SExpression("(<= 100 100)");
        TreeNode result = s.evaluate(context);
        assertEquals("T", result.toString());
    }

    @Test
    public void compoundLogicAssessment() {
        Context context = new Context();
        TreeNode s = new SExpression("(= (/= hello bye) T)");
        TreeNode result = s.evaluate(context);
        assertEquals("T", result.toString());
    }

}