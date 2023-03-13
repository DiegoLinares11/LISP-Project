package org.project.helpers;

import java.io.*;

public class StreamReader {
    public static String streamToString (File file) throws IOException {
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
