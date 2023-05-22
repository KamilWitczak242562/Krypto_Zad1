package org.example;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Utils {

    public String bytesToHex(byte bytes[]) {
        byte rawData[] = bytes;
        StringBuilder hexText = new StringBuilder();
        String initialHex = null;
        int initHexLength = 0;

        for (int i = 0; i < rawData.length; i++) {
            int positiveValue = rawData[i] & 0x000000FF;
            initialHex = Integer.toHexString(positiveValue);
            initHexLength = initialHex.length();
            while (initHexLength++ < 2) {
                hexText.append("0");
            }
            hexText.append(initialHex);
        }
        return hexText.toString();
    }

    public String bigIntToString(BigInteger big) {
        byte[] tab = big.toByteArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tab.length; i++) {
            sb.append((char) tab[i]);
        }
        return sb.toString();
    }

    public BigInteger stringToBigInt(String str) {
        byte[] tab = new byte[str.length()];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = (byte) str.charAt(i);
        }
        return new BigInteger(1, tab);
    }

    public String arrToString(BigInteger[] big) {
        String s = new String();
        for (int i = 0; i < big.length; i++) {
            s += big[i].toString() + "\n";
        }
        return s;
    }
}