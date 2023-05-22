package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.codec.DecoderException;

import java.io.*;
import java.math.BigInteger;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        GUI gui = new GUI();
    }

}