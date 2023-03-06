package org.example;

import java.io.FileOutputStream;
import java.io.IOException;

public class BytesToFile {
    public static void write(String filePath, byte[] bytes) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(bytes);
        }
    }
}
