package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI {
    private AnchorPane pane;
    private Scene scene;
    private Stage stage;

    private EncryptionFileScene encryptionFileScene;
    private EncryptionTextScene encryptionTextFile;

    public GUI() {
        pane = new AnchorPane();
        scene = new Scene(pane, 1000, 250);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("AES");
        drawLine();
        addButtons();
        addText("Szyfruj", 190, 50);
        addText("Deszyfruj", 670, 50);
        stage.show();
    }

    private void addButtons() {
        OurButton buttonEncryptingFile = new OurButton("Plik", 50, 150);
        OurButton buttonEncryptingText = new OurButton("Tekst", 300, 150);
        OurButton buttonDecryptingFile = new OurButton("Plik", 550, 150);
        OurButton buttonDecryptingText = new OurButton("Tekst", 800, 150);
        buttonEncryptingText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                encryptionTextFile = new EncryptionTextScene();
            }
        });
        buttonDecryptingText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                encryptionTextFile = new EncryptionTextScene();
            }
        });
        buttonEncryptingFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                encryptionFileScene = new EncryptionFileScene(true);
            }
        });
        buttonDecryptingFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                encryptionFileScene = new EncryptionFileScene(false);
            }
        });
        pane.getChildren().add(buttonEncryptingFile);
        pane.getChildren().add(buttonEncryptingText);
        pane.getChildren().add(buttonDecryptingFile);
        pane.getChildren().add(buttonDecryptingText);
    }

    private void drawLine() {
        Line blackLine = new Line();
        blackLine.setStartX(500);
        blackLine.setStartY(0);
        blackLine.setEndX(500);
        blackLine.setEndY(500);
        blackLine.setFill(Color.BLACK);
        blackLine.setStrokeWidth(5);
        pane.getChildren().add(blackLine);
    }

    private void addText(String text, int x, int y) {
        Text t = new Text(text);
        t.setLayoutY(y);
        t.setLayoutX(x);
        t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        t.setFill(Color.WHITE);
        t.setStrokeWidth(2);
        t.setStroke(Color.BLUE);
        pane.getChildren().add(t);
    }
}
