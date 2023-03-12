package org.project.lexing;

import org.junit.Test;
import org.project.helpers.StreamReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LexerTest {

    @Test
    public void getTokensTest(){
        String expression = "1 ( ) \n some Tokens";
        List<String> expectedArray = Arrays.asList("(", "list" ,"1", "(", ")", "some", "Tokens", ")");
        Lexer l = new Lexer();
        List<String> a = l.getTokens(expression);
        System.out.println(a.toString());
        assertTrue(expectedArray.equals(l.getTokens(expression)));
    }
    @Test
    public void getTokensFromStream(){
        List<String> expectedArray = Arrays.asList("(", "list", "This", "is", "a", "test", "another", "line", ")");
        InputStream stream = null;
        try {
            stream = new FileInputStream(new File("./testFiles/getTokensTest.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Lexer l = new Lexer();
        assertEquals(expectedArray, l.getTokens(stream));
    }
}
