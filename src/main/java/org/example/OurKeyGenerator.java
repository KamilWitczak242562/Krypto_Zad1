package org.example;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.BitSet;
import java.util.Random;

public class OurKeyGenerator {

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
        String s = new String(bytes, StandardCharsets.UTF_8);
        return s;
    }
}
