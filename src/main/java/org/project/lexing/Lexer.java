package org.project.lexing;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.project.helpers.StreamReader.streamToString;

public class Lexer {

    public List<String> getTokens(InputStream stream){
        try {
            String expression = streamToString(stream);     // Read Stream.
            String depurateTokens = prepare(expression);    // Remove extra whitespaces and newlines.
            depurateTokens = wrap(depurateTokens);          // Wrap everything into parenthesis, to be the parent Expression.
            return divide(depurateTokens);                  // Divide the tokens by whitespaces.
        } catch (IOException e) {
            throw new RuntimeException("File not found Exception");
        }
    }

    public List<String> getTokens(String expression){
        String depurateTokens = wrap(prepare(expression));
        return divide(depurateTokens);
    }

    private String prepare (String expression){
        return expression.trim()
                .replace(")", " ) ")
                .replace("(", " ( ")
                .replace("\n", "")
                .replaceAll("\\s+", " ");
    }

    private List<String> divide (String expression){
        return Arrays.asList(expression.split(" "));
    }

    private String wrap (String expression){
        return "(" + expression + ")";
    }
}
