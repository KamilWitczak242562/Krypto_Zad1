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
    }
    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
    }
}