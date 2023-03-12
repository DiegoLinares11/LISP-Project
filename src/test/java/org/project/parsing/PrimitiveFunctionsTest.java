package org.project.parsing;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrimitiveFunctionsTest {

    @Test
    public void quote() {
        Context context = new Context();
        SExpression s = new SExpression("(quote (+ 2 3))");
        TreeNode result = s.evaluate(context);
        assertEquals("[[+, 2, 3]]", result.toString());
    }

    @Test
    public void eval() {
        Context context = new Context();
        SExpression s = new SExpression("(eval (quote (+ 2 3)))");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(5.0d, Double.parseDouble(result.car().toString()), 0.5);
    }

    @Test
    public void setq() {
        Context context = new Context();
        TreeNode s = new SExpression("(setq x 2) (setq y 45) (+ x y)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(47.0d, Double.parseDouble(result.car().toString()), 0.5);
    }

    @Test
    public void setqTwice() {
        Context context = new Context();
        TreeNode s = new SExpression("(setq x 2) (setq x 45) (+ x 5)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(50.0d, Double.parseDouble(result.car().toString()), 0.5);
    }

    @Test
    public void add() {
        Context context = new Context();
        TreeNode s = new SExpression("(+ 20 50 (+ 2 3))");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(75.0d, Double.parseDouble(result.car().toString()), 0.5);

    }

    @Test
    public void subtraction() {
        Context context = new Context();
        TreeNode s = new SExpression("(- 18 7 1)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(10.0d, Double.parseDouble(result.car().toString()), 0.5);

    }

    @Test
    public void multiplication() {
        Context context = new Context();
        TreeNode s = new SExpression("(* 3 2 6)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(36.0d, Double.parseDouble(result.car().toString()), 0.5);

    }

    @Test
    public void division() {
        Context context = new Context();
        TreeNode s = new SExpression("(/ 100 5 10)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(2.0d, Double.parseDouble(result.car().toString()), 0.5);
    }

    @Test
    public void not() {
        Context context = new Context();
        TreeNode s = new SExpression("(not (= hello hello))");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("NIL", result.car().toString());
    }

    @Test
    public void eq() {
        Context context = new Context();
        TreeNode s = new SExpression("(= hello hello)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.car().toString());
    }

    @Test
    public void atom() {
        Context context = new Context();
        TreeNode s = new SExpression("(atom 3)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.car().toString());
    }

    @Test
    public void ne() {
        Context context = new Context();
        TreeNode s = new SExpression("(/= 3 3)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("NIL", result.car().toString());
    }

    @Test
    public void gt() {
        Context context = new Context();
        TreeNode s = new SExpression("(> 5 2)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.car().toString());
    }

    @Test
    public void ge() {
        Context context = new Context();
        TreeNode s = new SExpression("(>= 5 5)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.car().toString());
    }

    @Test
    public void lt() {
        Context context = new Context();
        TreeNode s = new SExpression("(< 4 10)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.car().toString());
    }

    @Test
    public void le() {
        Context context = new Context();
        TreeNode s = new SExpression("(<= 100 100)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.car().toString());
    }

    @Test
    public void compoundLogicAssessment() {
        Context context = new Context();
        TreeNode s = new SExpression("(= (/= hello bye) T)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.car().toString());
    }

}