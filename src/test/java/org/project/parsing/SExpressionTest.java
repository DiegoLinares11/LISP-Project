package org.project.parsing;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SExpressionTest {

    @Test
    public void getPrimitiveFunction() throws NoSuchMethodException {
        Context context = new Context();
        SExpression rootNode = new SExpression("(list 100 5 10)");
        SExpression operands = (SExpression) rootNode.evaluate(context);
        SExpression s = (SExpression) operands.car();
        Method m = SExpression.class.getDeclaredMethod("findPrimitiveFunction", String.class, SExpression.class, Class.class);
        m.setAccessible(true);
        TreeNode result;
        try {
            result = (TreeNode) m.invoke(s,"add", s, PrimitiveFunctions.class);
        } catch (Exception e) {
            throw new RuntimeException("Function not found");
        }
        assertEquals(115.0, Double.parseDouble(result.toString()), 0.5);
    }
}