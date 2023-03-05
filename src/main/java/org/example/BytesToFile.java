package org.example;

import java.io.FileOutputStream;
import java.io.IOException;

public class BytesToFile {
    public static void write(String filePath, byte[] bytes) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(bytes);
        }
    }

    public void write() {
        String filePath = "E:\\Krypto_Zad1\\src\\main\\java\\org\\example\\fileTest.txt";
        try {
            byte[] bytes = FileToBytes.read(filePath);
            // wykonaj jakieś operacje na strumieniu bitów
            BytesToFile.write("E:\\Krypto_Zad1\\src\\main\\java\\org\\example\\outputFile.txt", bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
