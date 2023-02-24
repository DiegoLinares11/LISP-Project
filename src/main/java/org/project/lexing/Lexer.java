package org.project.lexing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.project.helpers.StreamReader.streamToString;

public class Lexer {

    public List<String> getTokens(InputStream stream){
        try {
            String expression = streamToString(stream);
            String depurateTokens = prepare(expression);
            return divide(depurateTokens);
        } catch (IOException e) {
            throw new RuntimeException("File not found Exception");
        }
    }

    public List<String> getTokens(String expression){
        String depurateTokens = prepare(expression);
        return divide(depurateTokens);
    }

    private String prepare (String expression){
        return expression.trim()
                .replace(")", ") ")
                .replace("(", "( ")
                .replace("\n", "")
                .replaceAll(" +", " ");
    }

    private List<String> divide (String expression){
        return Arrays.asList(expression.split(" "));
    }
}
