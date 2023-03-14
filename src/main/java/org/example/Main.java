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
        launch();
        Utils utils = new Utils();
        byte[] key = utils.getRandomKey();
        TextToBytes text = new TextToBytes();
        AES aes = new AES(key, text.textToBytes("testttttttttttttt"));
        byte[] en = aes.encrypt();
        System.out.println(text.bytesToText(en));
        System.out.printf(utils.toString(key));
        System.out.printf("\n");
        byte[][] rs = aes.generateRoundKeys();
        byte[] r = new byte[rs.length * rs[0].length];
        int x = 0;
        for (int i = 0; i < rs.length; i++) {
            for (int j = 0; j < rs[i].length; j++) {
                r[x] = rs[i][j];
                x++;
            }
        }
        System.out.printf(utils.toString(r));
    }
    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
    }
}