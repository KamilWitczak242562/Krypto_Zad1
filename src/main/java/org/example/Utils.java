package org.example;

import java.util.BitSet;
import java.util.Random;

public class Utils {

    private static int KEY_SIZE = 128;

    public byte[] getRandomKey() {
        BitSet key = new BitSet(KEY_SIZE);
        byte[] bytes;
        Random random = new Random();
        for (int i = 0; i < KEY_SIZE; i++) {
            key.set(i, random.nextBoolean());
        }
        bytes = key.toByteArray();
        return bytes;
    }

    public String toString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
