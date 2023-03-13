package org.project.parsing;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.project.parsing.primitiveFunctions.functionHelper.nodeAsNumeric;

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
        assertEquals(5.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void list() {
        Context context = new Context();
        TreeNode s = new SExpression("(list 2)");
        SExpression result = (SExpression) s.evaluate(context);
        System.out.println(result);
        //assertEquals(47.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void defun() {
        Context context = new Context();
        TreeNode s = new SExpression("(defun owo (a b) (+ a b)) (owo 2 3)");
        SExpression result = (SExpression) s.evaluate(context);
        System.out.println(result);
        //assertEquals(47.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void defunForFarenheitToCelsiusConversion() {
        Context context = new Context();
        TreeNode s = new SExpression("(defun toFarenheit (Celcius)\n" +
                "(+ (* Celcius 1.8) 32))\n" +
                "(print (toFarenheit 3))");
        SExpression result = (SExpression) s.evaluate(context);
        System.out.println(result);
        //assertEquals(47.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void defunFibonacci() {
        Context context = new Context();
        TreeNode s = new SExpression("(defun fibonacci (n)\n" +
                "    (cond \n" +
                "        ((< n 2) 1)" +
                "        (T (+ (fibonacci (- n 1)) (fibonacci (- n 2))))\n" +
                "    )\n" +
                ")\n" +
                "(fibonacci 5)");
        SExpression result = (SExpression) s.evaluate(context);
        System.out.println(result);
        assertEquals(8.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void Factorial() {
        Context context = new Context();
        TreeNode s = new SExpression("(defun factorial (num) \n" +
                "   (cond \n" +
                "        ((> num 1) (* num (factorial (- num 1))))\n" +
                "        (T 1)\n" +
                "   )\n" +
                ")\n" +
                "(factorial 3)");
        SExpression result = (SExpression) s.evaluate(context);
        System.out.println(result);
        assertEquals(6.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void setq() {
        Context context = new Context();
        TreeNode s = new SExpression("(setq x 2) (+ x 45)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(47.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void setqTwice() {
        Context context = new Context();
        TreeNode s = new SExpression("(setq x 2) (setq x 45) (+ x 5)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(50.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void add() {
        Context context = new Context();
        TreeNode s = new SExpression("(+ 20 50 (+ 2 3))");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(75.0d, nodeAsNumeric(result.getNode(0)), 0.5);

    }

    @Test
    public void subtraction() {
        Context context = new Context();
        TreeNode s = new SExpression("(- 18 7 1)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(10.0d, nodeAsNumeric(result.getNode(0)), 0.5);

    }

    @Test
    public void multiplication() {
        Context context = new Context();
        TreeNode s = new SExpression("(* 3 2 6)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(36.0d, nodeAsNumeric(result.getNode(0)), 0.5);

    }

    @Test
    public void division() {
        Context context = new Context();
        TreeNode s = new SExpression("(/ 100 5 10)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(2.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }

    @Test
    public void not() {
        Context context = new Context();
        TreeNode s = new SExpression("(not (= hello hello))");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("NIL", result.getNode(0).toString());
    }

    @Test
    public void eq() {
        Context context = new Context();
        TreeNode s = new SExpression("(= hello hello)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.getNode(0).toString());
    }

    @Test
    public void atom() {
        Context context = new Context();
        TreeNode s = new SExpression("(atom 3)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.getNode(0).toString());
    }

    @Test
    public void ne() {
        Context context = new Context();
        TreeNode s = new SExpression("(/= 3 3)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("NIL", result.getNode(0).toString());
    }

    @Test
    public void gt() {
        Context context = new Context();
        TreeNode s = new SExpression("(> 5 2)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.getNode(0).toString());
    }

    @Test
    public void ge() {
        Context context = new Context();
        TreeNode s = new SExpression("(>= 5 5)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.getNode(0).toString());
    }

    @Test
    public void lt() {
        Context context = new Context();
        TreeNode s = new SExpression("(< 4 10)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.getNode(0).toString());
    }

    @Test
    public void le() {
        Context context = new Context();
        TreeNode s = new SExpression("(<= 100 100)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.getNode(0).toString());
    }

    @Test
    public void compoundLogicAssessment() {
        Context context = new Context();
        TreeNode s = new SExpression("(= (/= hello bye) T)");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals("T", result.getNode(0).toString());
    }

    @Test
    public void print() {
        Context context = new Context();
        TreeNode s = new SExpression("(print \"hello world\")");
        SExpression result = (SExpression) s.evaluate(context);
    }

    @Test
    public void concatenate() {
        Context context = new Context();
        TreeNode s = new SExpression("(concatenate Diego joel)");
        SExpression result = (SExpression) s.evaluate(context);
    }

       @Test
    public void cond() {
        Context context = new Context();
        TreeNode s = new SExpression("(cond ((= a b) (+ 2 2))  (T (- 2 2))  )");
        SExpression result = (SExpression) s.evaluate(context);
        assertEquals(0.0d, nodeAsNumeric(result.getNode(0)), 0.5);
    }
}