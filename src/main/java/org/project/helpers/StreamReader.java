package org.project.helpers;

import java.io.*;

/**
 * Class for reading files.
 */
public class StreamReader {
    /**
     * Reads a file, and return it as a String.
     * @param file File to read.
     * @return String equivalent.
     * @throws IOException If file do not exist.
     */
    public static String fileToString(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = stream.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString("UTF-8");
    }
}
