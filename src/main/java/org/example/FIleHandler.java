package org.example;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class FIleHandler {
    public void writeBig(String filePath, BigInteger object) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(object);
        }
    }

    public BigInteger readBig(String fileName) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (BigInteger) ois.readObject();
        }
    }

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
            bytes = FIleHandler.read(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public void saveCipher(BigInteger[] data, String path) {
        try {
            File file = new File(path);
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < data.length; i++) {
                writer.write(data[i].toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDecrypted(BigInteger[] data, String path) {
        byte[] tab;
        try {
            File file = new File(path);
            FileOutputStream fos = new FileOutputStream(file);
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals(BigInteger.ZERO)) {
                    tab = new byte[1];
                    tab[0] = '\000';
                    fos.write(tab);
                } else {
                    tab = data[i].toByteArray();
                    if (tab[0] == '\000' && tab.length == 32) {
                        tab = subArrays(tab, 1, tab.length);
                    }
                    if (tab.length == 30) {
                        tab = addZero(tab);
                    }
                    if (i == data.length - 1) {
                        tab = subArrayWithOutZero(tab);
                    }
                    fos.write(tab);
                }
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BigInteger[] loadCipher(String path) {
        BigInteger[] array = new BigInteger[1];
        try {
            File file = new File(path);
            Scanner sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextBigInteger()) {
                if (i > 0) {
                    array = Arrays.copyOf(array, array.length + 1);
                }
                array[i] = sc.nextBigInteger();
                i++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static byte[] subArrays(byte[] data, int beginning, int end) {
        byte[] subArray = new byte[end - beginning];
        for (int i = 0; beginning < end; beginning++, i++) {
            subArray[i] = data[beginning];
        }
        return subArray;
    }

    public byte[] addZero(byte[] data) {
        byte[] output = new byte[data.length + 1];
        output[0] = '\000';
        for (int i = 0; i < data.length; i++) {
            output[i + 1] = data[i];
        }
        return output;
    }

    public byte[] subArrayWithOutZero(byte[] data) {
        ArrayList<Byte> tab = new ArrayList<Byte>();
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i] == '\000') {
                continue;
            } else {
                tab.add(data[i]);
            }
        }
        byte[] wynik = new byte[tab.size()];
        for (int j = 0, i = tab.size() - 1; i >= 0; i--, j++) {
            wynik[j] = tab.get(i).byteValue();
        }
        return wynik;
    }
}
