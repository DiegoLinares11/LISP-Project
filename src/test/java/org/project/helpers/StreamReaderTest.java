package org.project.helpers;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class StreamReaderTest {

    @Test
    public void streamToString() {
        String expectedExpression = "This is a test\nanother line";
        File file = new File("./testFiles/getTokensTest.txt");
        String expressionFetched;
        try {
            expressionFetched = StreamReader.fileToString(file);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(expectedExpression.equals(expressionFetched));
    }
}