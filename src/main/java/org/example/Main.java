package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        //launch();
        Utils utils = new Utils();
        byte[] key = utils.getRandomKey();
        TextToBytes text = new TextToBytes();
        AES aes = new AES(key, text.textToBytes("test"));
        byte[] en = aes.encrypt();
        AES de_aes = new AES(key, en);
        byte[] de = de_aes.decrypt();
        System.out.println("Tekst do szyfrowania: " + text.bytesToText(text.textToBytes("test")));
        System.out.println("Tekst zaszyfrowany: " + text.bytesToText(en));
        System.out.println("Tekst odszyfrowany?: " + text.bytesToText(de));
        System.out.println("Tekst zaszyfrowany: " + utils.toString(en));
        System.out.println("Tekst do szyfrowania: " + utils.toString(text.textToBytes("test")));
        System.out.println("Tekst odszyfrowany?: " + utils.toString(de));
        System.out.printf("Klucz: " + utils.toString(key));
        System.out.printf("\n");
        byte[][] rs = aes.generateRoundKeys();
        byte[] r = new byte[rs.length * rs[0].length];
        byte[][] us = aes.generateRoundKeys();
        byte[] u = new byte[us.length * us[0].length];
        int x = 0;
        for (int i = 0; i < rs.length; i++) {
            for (int j = 0; j < rs[i].length; j++) {
                r[x] = rs[i][j];
                x++;
            }
        }
        int y = 0;
        for (int i = 0; i < us.length; i++) {
            for (int j = 0; j < us[i].length; j++) {
                u[y] = us[i][j];
                y++;
            }
        }
        System.out.printf("Rozszerzony klucz 1: " + utils.toString(r) + "\n");
        System.out.printf("Rozszerzony klucz 2: " + utils.toString(u));
    }

    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
    }
}