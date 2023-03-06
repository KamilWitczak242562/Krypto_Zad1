package org.example;

import java.io.*;

public class FileToBytes {
    public static byte[] read(String filePath) throws IOException {
        File file = new File(filePath);
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try (InputStream inputStream = new FileInputStream(file)) {
            int offset = 0;
            int numRead;
            while (offset < bytes.length && (numRead = inputStream.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
        }
        return bytes;
    }

    public byte[] load(String path) {
        String filePath = path;
        byte[] bytes = null;
        try {
            bytes = FileToBytes.read(filePath); //Wczytanie pliku do strumienia.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}






