package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TextToBytes {

    public static byte[] textToBytes(String text) {
        byte[] bytes = text.getBytes();
        return bytes;
    }

    public String bytesToText(byte[] bytes) {
        String output = new String(bytes, StandardCharsets.UTF_8);
        return output;
    }
}
