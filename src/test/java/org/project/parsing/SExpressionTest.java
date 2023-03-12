package org.project.parsing;

import org.junit.Test;
import org.project.parsing.primitiveFunctions.ArithmeticFunctions;
import org.project.parsing.primitiveFunctions.LispPrimitiveFunctions;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

public class SExpressionTest {

    @Test
    public void getPrimitiveFunction() throws NoSuchMethodException {
        Context context = new Context();
        SExpression rootNode = new SExpression("(list 100 5 10)");
        SExpression operands = (SExpression) rootNode.evaluate(context);
        TreeNode s = operands.getNode(0);
        Method m = SExpression.class.getDeclaredMethod("findPrimitiveFunction", String.class, List.class, Context.class, Class.class);
        m.setAccessible(true);
        Object result;
        try {
            result =  m.invoke(s,"list", s.getChildNodes(), context, LispPrimitiveFunctions.class);
        } catch (Exception e) {
            throw new RuntimeException("Function not found");
        }
        assertEquals("[100, 5, 10]", result.toString());
    }
}