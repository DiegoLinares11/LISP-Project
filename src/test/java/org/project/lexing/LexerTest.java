package org.project.lexing;

import org.junit.Test;
import org.project.helpers.StreamReader;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LexerTest {

    @Test
    public void getTokensTest(){
        String expression = "1 ( ) \n some Tokens ; hola";
        List<String> expectedArray = Arrays.asList("(", "list" ,"1", "(", ")", "some", "Tokens", ")");
        Lexer l = new Lexer();
        List<String> a = l.getTokens(expression);
        System.out.println(a.toString());
        assertTrue(expectedArray.equals(l.getTokens(expression)));
    }
    @Test
    public void getTokensFromStream(){
        List<String> expectedArray = Arrays.asList("(", "list", "This", "is", "a", "test", "another", "line", ")");
        File testFile = new File("./testFiles/getTokensTest.txt");
        assertEquals(expectedArray, Lexer.getTokens(testFile));
    }

}
