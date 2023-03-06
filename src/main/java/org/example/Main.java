package org.example;

import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {
    public static void main(String[] args) {

        //FileToBytes fileToBytes = new FileToBytes();
        //fileToBytes.load();

        //BytesToFile bytesToFile = new BytesToFile();
        //bytesToFile.write();

        //wczytywanie pliku do strumienia działa a to niżej przekształca strumień danych na plik. Może się przyda.


        launch();
    }




    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();


    }


}