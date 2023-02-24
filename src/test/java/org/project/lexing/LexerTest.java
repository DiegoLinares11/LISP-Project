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
        List<String> expectedArray = Arrays.asList("1", "(", ")", "some", "Tokens");
        Lexer l = new Lexer();
        assertTrue(expectedArray.equals(l.getTokens(expression)));
    }
}