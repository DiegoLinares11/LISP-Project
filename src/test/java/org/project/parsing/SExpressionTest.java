package org.project.parsing;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

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