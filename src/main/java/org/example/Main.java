package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {

        //FileToBytes fileToBytes = new FileToBytes();
        //fileToBytes.load("E:\\Krypto_Zad1\\src\\main\\java\\org\\example\\fileTest.txt");

        //BytesToFile bytesToFile = new BytesToFile();
        //bytesToFile.write();

        //wczytywanie pliku do strumienia działa a to niżej przekształca strumień danych na plik. Może się przyda.

        String filePath = "E:\\Krypto_Zad1\\src\\main\\java\\org\\example\\outputFile.txt";
        String text = "To jest tekst.";
        try {
            TextToBytes.write(filePath, text);
            System.out.println("Plik został zapisany.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();


    }


}