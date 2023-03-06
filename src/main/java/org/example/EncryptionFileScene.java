package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class EncryptionFileScene {

    private AnchorPane pane;
    private Stage stage;
    private Scene scene;
    private GUI gui;
    private FileToBytes fileToBytes;
    private byte[] bytes;

    private TextArea areaToEncrypt;

    private TextArea areaAfterEncrypting;

    private BytesToFile bytesToFile;

    private Label keyLabel;

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
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                gui = new GUI();
            }
        });
        OurButton loadFile = new OurButton("Wczytaj plik", 10, 60);
        loadFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileToBytes = new FileToBytes();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open file to encrypt");
                String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                bytes = fileToBytes.load(path);
                areaToEncrypt = new TextArea();
                areaToEncrypt.setText(bytes.toString());
                areaToEncrypt.setLayoutX(10);
                areaToEncrypt.setLayoutY(120);
                pane.getChildren().add(areaToEncrypt);
            }
        });
        OurButton saveFile = new OurButton("Zapisz plik", 790, 60);
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select file to save");
                String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                bytesToFile = new BytesToFile();
                try {
                    bytesToFile.write(path, bytes);
                } catch (IOException e) {
                    throw new RuntimeException("Nie udało się zapisać do pliku");
                }
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
            generate.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    keyLabel = new Label();
                    keyLabel.setLayoutX(220);
                    keyLabel.setLayoutY(10);
                    keyLabel.setText("A8CA-81SA-sDA91-Sad1S");
                    keyLabel.setPrefWidth(500);
                    keyLabel.setMaxHeight(100);
                    keyLabel.setStyle("-fx-font: 34 arial; -fx-border-color: black;");
                    pane.getChildren().add(keyLabel);
                }
            });
            saveKey.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Select file to save");
                    String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                    try {
                        bytesToFile.write(path, keyLabel.getText().getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException("Nie udało się zapisać do pliku");
                    }
                }
            });
            encrypt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    areaAfterEncrypting = new TextArea();
                    areaAfterEncrypting.setText(areaToEncrypt.getText());
                    areaAfterEncrypting.setLayoutX(510);
                    areaAfterEncrypting.setLayoutY(120);
                    pane.getChildren().add(areaAfterEncrypting);
                }
            });
            pane.getChildren().add(encrypt);
            pane.getChildren().add(generate);
            pane.getChildren().add(saveKey);
        } else {
            OurButton loadKey = new OurButton("Wczytaj klucz", 10, 10);
            OurButton decrypt = new OurButton("Deszyfruj", 425, 400);
            loadKey.setPrefWidth(200);
            decrypt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    areaAfterEncrypting = new TextArea();
                    areaAfterEncrypting.setText(areaToEncrypt.getText());
                    areaAfterEncrypting.setLayoutX(510);
                    areaAfterEncrypting.setLayoutY(120);
                    pane.getChildren().add(areaAfterEncrypting);
                }
            });
            pane.getChildren().add(decrypt);
            pane.getChildren().add(loadKey);
        }
        pane.getChildren().add(back);
        pane.getChildren().add(loadFile);
        pane.getChildren().add(saveFile);
    }
}
