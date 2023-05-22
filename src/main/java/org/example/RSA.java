package org.example;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;

    private Utils utils;

    public RSA() {

    }

    public void generateKeys(int numBits) {
        utils = new Utils();
        Random random = new Random();
        p = BigInteger.probablePrime(numBits / 2, random);
        q = BigInteger.probablePrime(numBits / 2, random);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        do {
            e = new BigInteger(phi.bitLength(), random);
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE));

        d = e.modInverse(phi);
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getN() {
        return N;
    }

    public void setN(BigInteger n) {
        this.N = n;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public String encryptString(String plaintext) {
        String output = new String();
        int chars = (N.bitLength() - 1) / 8;
        while (plaintext.length() % chars != 0)
            plaintext += ' ';
        int chunks = plaintext.length() / chars;
        BigInteger[] cipher = new BigInteger[chunks];
        for (int i = 0; i < chunks; i++) {
            String s = plaintext.substring(chars * i, chars * (i + 1));
            cipher[i] = utils.stringToBigInt(s);
            cipher[i] = cipher[i].modPow(e, N);
        }
        for (int i = 0; i < cipher.length; i++) {
            output += cipher[i] + "\n";
        }
        return output;
    }

    public String decryptString(String ciphertext) {
        String[] rows = ciphertext.split("\n");
        BigInteger[] bigIntegers = new BigInteger[rows.length];
        for (int i = 0; i < rows.length; i++) {
            bigIntegers[i] = new BigInteger(rows[i]);
        }
        String output = new String();
        for (int i = 0; i < bigIntegers.length; i++) {
            output += utils.bigIntToString(bigIntegers[i].modPow(d, N));
        }
        return output;
    }

    public BigInteger[] encrypt(byte[] message) {
        int chars = (N.bitLength() - 1) / 8;
        while (message.length % chars != 0) {
            message = Arrays.copyOf(message, message.length + 1);
            message[message.length - 1] = '\000';
        }
        int chunks = message.length / chars;
        BigInteger[] cipher = new BigInteger[chunks];
        for (int i = 0; i < chunks; i++) {
            byte[] pom = FIleHandler.subArrays(message, chars * i, chars * (i + 1));
            cipher[i] = new BigInteger(1, pom);
            cipher[i] = cipher[i].modPow(e, N);
        }
        return cipher;
    }

    public BigInteger[] decrypt(BigInteger[] cipher) {
        BigInteger[] output = new BigInteger[cipher.length];
        for (int i = 0; i < cipher.length; i++) {
            output[i] = cipher[i].modPow(d, N);
        }
        return output;
    }
}
