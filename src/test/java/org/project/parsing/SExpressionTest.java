package org.project.parsing;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SExpressionTest {

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

    @Test
    public void getPrimitiveFunction() throws NoSuchMethodException {
        Context context = new Context();
        SExpression rootNode = new SExpression("(100 5 10)");

        Method m = SExpression.class.getDeclaredMethod("findPrimitiveFunction", String.class, SExpression.class, Class.class);
        m.setAccessible(true);
        SExpression s = (SExpression) rootNode.getChildNodes().get(0);
        TreeNode result;
        try {
            result = (TreeNode) m.invoke(s,"add", s, PrimitiveFunctions.class);
        } catch (Exception e) {
            throw new RuntimeException("Function not found");
        }
        assertEquals(115.0, Double.parseDouble(result.toString()), 0.5);
    }
}