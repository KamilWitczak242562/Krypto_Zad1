package org.example;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Random;

public class Utils {

    private static int KEY_SIZE = 128;

    public byte[] getRandomKey() {
        BitSet key = new BitSet(KEY_SIZE);
        byte[] bytes;
        Random random = new Random();
        for (int i = 0; i < 128; i++) {
            key.set(i, true);
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

    public byte[] textToBytes(String text) {
        byte[] bytes = text.getBytes();
        return bytes;
    }

    public String convertStringToHex(String str) {
        StringBuffer hex = new StringBuffer();
        for (char temp : str.toCharArray()) {
            int decimal = (int) temp;
            hex.append(Integer.toHexString(decimal));
        }
        return hex.toString();

    }

    public String convertHexToString(String hex) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String tempInHex = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(tempInHex, 16);
            result.append((char) decimal);
        }
        return result.toString();

    }

    public byte[] hexToByte(String hex) {
        int it = Integer.parseInt(hex, 16);
        BigInteger bigInt = BigInteger.valueOf(it);
        byte[] bytearray = (bigInt.toByteArray());
        return bytearray;
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public String byteToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}
