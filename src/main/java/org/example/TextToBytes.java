package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TextToBytes {

    public static void write(String filePath, String text) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] bytes = text.getBytes();  //przekształcamy tekst na tablicę bajtów

            outputStream.write(bytes); //zapis do pliku
        }
    }
}
