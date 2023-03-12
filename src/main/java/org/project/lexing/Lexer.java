package org.project.lexing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.project.helpers.StreamReader.streamToString;

/** Representation of a LEXICAL ANALYZER... which means: it reads an input and gets its TOKENS
 * (The Smallest lexical particles which have a meaning on LISP). Conforming the FIRST LAYER of code analysis.
 *
 * IMPORTANT: To make sure everything is wrapped within an expression, the Lexer always surrounds a code within a
 * Root Expression, explaining why it always return the expression with those extra parenthesis at the edges
 * and the "list" operator (because every expression must always start with an operator).
 *
 *  * Ex input:
 *  * "- (+ 3 2) a"
 *  * Ex output:
 *  * [(, list, -, (, +, 3, 2, ) ,a, )]
 */
public class Lexer {

    /**
     * Read the content of a stream, chop it, and return a list of the tokens it contains.
     * Ex input : (+ 3 2)
     * Ex output : ["+", "3", "2"]
     * @param stream Stream input to read from.
     * @return A list of the tokens contained in the input.
     */
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

    /**
     * Chops a string, and return a list of the tokens it contains.
     * Ex input : "(+ 3 2)"
     * Ex output : ["+", "3", "2"]
     * @param  expression String input to read from.
     * @return A list of the tokens contained in the input.
     */
    public List<String> getTokens(String expression){
        String depurateTokens = prepare(wrap(expression));  // Remove extra whitespaces and newlines, and wrap it.
        return divide(depurateTokens);                      // Divide the tokens by whitespaces.
    }

    /**
     * Format a string for get its tokens, by removing extra whitespaces and newline characters..
     * Ex input: "(+ 32 \n 2)"
     * Ex output: " ( + 32 2 ) "
     * @param expression Expression to prepare for future division.
     * @return A String ready to be chopped in tokens..
     */
    private String prepare (String expression){
        return expression
                .replace(")", " ) ")
                .replace("(", " ( ")
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }


    /**
     * Divides a string, and return a list of its tokens. Expressions withing ("") wont be split,
     * (used for accepting strings of text).
     * Ex input : " ( + 3 2 ) "
     * Ex output : ["+", "3", "2"]
     * @param expression Expression to chop.
     * @return A list of the tokens.
     */
    private List<String> divide (String expression){
        List<String> tokens = new ArrayList<>();
        boolean inStringToken = false;                      // When true, stop splitting by whitespaces.
        StringBuilder tempToken = new StringBuilder();
        for (char selectedChar : expression.toCharArray()) {

            if(selectedChar == '\"')
                inStringToken = !inStringToken;

            if(inStringToken)
                tempToken.append(selectedChar);

            else {
                // If whites space found, end token, and start with the next.
                if(selectedChar == ' '){
                    tokens.add(tempToken.toString());
                    tempToken.setLength(0);
                }
                // Else keep generating token.
                else
                    tempToken.append(selectedChar);
            }

        }
        tokens.add(tempToken.toString());   // Flush the last token.
        return tokens;
    }

    /**
     * Adds "(" ")" at the start and end of and expression.
     * Used to make sure everything is wrapped within a root SExpression.
     * Ex input : " + 3 2 "
     * Ex output : " ( + 3 2 ) "
     * @param expression
     * @return
     */
    private String wrap (String expression){
        return "( list " + expression + " )"; // "list" is added, at the root Node, son can be evaluated.
    }
}
