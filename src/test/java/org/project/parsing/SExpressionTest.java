package org.project.parsing;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SExpressionTest {

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