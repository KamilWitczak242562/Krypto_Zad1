package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EncryptionFileScene {

    private AnchorPane pane;
    private Stage stage;
    private Scene scene;
    private GUI gui;
    private FileToBytes fileToBytes;
    private byte[] bytes;

    public EncryptionFileScene(Boolean whatToDo) {
        pane = new AnchorPane();
        stage = new Stage();
        scene = new Scene(pane, 1000, 500);
        stage.setScene(scene);
        addButtons(whatToDo);
        stage.show();
    }

    private void addButtons(Boolean whatToDo) {
        OurButton back = new OurButton("Powrót", 840, 440);
        OurButton loadFile = new OurButton("Wczytaj plik", 10, 60);
        OurButton saveFile = new OurButton("Zapisz plik", 790, 60);
        loadFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileToBytes = new FileToBytes();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open file to encrypt");
                String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                bytes = fileToBytes.load(path);
                TextArea textArea = new TextArea();
                textArea.setText(bytes.toString());
                textArea.setLayoutX(10);
                textArea.setLayoutY(110);
                pane.getChildren().add(textArea);
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                gui = new GUI();
            }
        });
        loadFile.setPrefWidth(200);
        saveFile.setPrefWidth(200);
        if (whatToDo) {
            OurButton generate = new OurButton("Generuj klucz", 10, 10);
            OurButton saveKey = new OurButton("Zapisz klucz", 790, 10);
            OurButton encrypt = new OurButton("Szyfruj", 425, 400);
            generate.setPrefWidth(200);
            saveKey.setPrefWidth(200);
            encrypt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    TextArea textArea = new TextArea();
                    textArea.setText(bytes.toString());
                    textArea.setLayoutX(510);
                    textArea.setLayoutY(110);
                    pane.getChildren().add(textArea);
                }
            });
            pane.getChildren().add(encrypt);
            pane.getChildren().add(generate);
            pane.getChildren().add(saveKey);
        } else {
            OurButton loadKey = new OurButton("Wczytaj klucz", 10, 10);
            OurButton decrypt = new OurButton("Deszyfruj", 425, 400);
            loadKey.setPrefWidth(200);
            pane.getChildren().add(decrypt);
            pane.getChildren().add(loadKey);
        }
        pane.getChildren().add(back);
        pane.getChildren().add(loadFile);
        pane.getChildren().add(saveFile);
    }
}
