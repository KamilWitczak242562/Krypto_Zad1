package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
        Utils utils = new Utils();
        String key = utils.getRandomKey();
        byte[] block = new byte[17];
        for (int i = 0; i < 17; i++) {
            block[i] = 0x02;
        }
        AES aes = new AES(utils.hexToByteArray(key));
        byte[] en = aes.encode(block);
        AES de_aes = new AES(utils.hexToByteArray(key));
        byte[] de = de_aes.decode(en);
        System.out.println("Tekst do szyfrowania: " + utils.bytesToHex(block));
        System.out.println("Tekst zaszyfrowany: " + utils.bytesToHex(en));
        System.out.println("Tekst odszyfrowany?: " + utils.bytesToHex(de));
        System.out.printf("Klucz: " + key);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
    }
}