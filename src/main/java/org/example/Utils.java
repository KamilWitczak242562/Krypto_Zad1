package org.example;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Random;

public class Utils {

    public String getRandomKey() {
        String literals = "ABCDEF09876543210";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            builder.append(literals.charAt(random.nextInt(literals.length())));
        }
        return builder.toString();
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public byte[] hexToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }


    static public byte fMul(byte multiplier, byte multiplicand) {
        byte result = 0;
        byte temp;
        while (multiplier != 0) {
            if ((multiplier & 1) != 0)
                result = (byte) (result ^ multiplicand);
            temp = (byte) (multiplicand & 0x20);
            multiplicand = (byte) (multiplicand << 1);
            if (temp != 0)
                multiplicand = (byte) (multiplicand ^ 0x1b);
            multiplier = (byte) ((multiplier & 0xff) >> 1);
        }
        return result;
    }
}
