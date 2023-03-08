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
import java.util.BitSet;

public class EncryptionTextScene {
    private AnchorPane pane;
    private Stage stage;
    private Scene scene;
    private GUI gui;

    private String textToEncrypt;

    private TextToBytes textToBytes;

    private TextArea areaToWrite;

    private TextArea areaAfterEncrypting;

    private Label keyLabel;

    private BytesToFile bytesToFile;

    private byte[] bytes;

    private OurKeyGenerator ourKeyGenerator;

    private byte[] key;

    public EncryptionTextScene() {
        pane = new AnchorPane();
        stage = new Stage();
        scene = new Scene(pane, 1000, 500);
        stage.setScene(scene);
        addButtons();
        addText("Wpisz tekst", 10, 100);
        createTextArea();
        stage.show();
    }

    private void addButtons() {
        OurButton back = new OurButton("Powrót", 840, 440);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                gui = new GUI();
            }
        });
        OurButton saveFile = new OurButton("Zapisz do pliku", 790, 60);
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select file to save");
                String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                textToBytes = new TextToBytes();
                bytesToFile = new BytesToFile();
                bytes = textToBytes.textToBytes(areaAfterEncrypting.getText());
                try {
                    bytesToFile.write(path, bytes);
                } catch (IOException e) {
                    throw new RuntimeException("Nie udało się zapisać do pliku");
                }
            }
        });
        OurButton generate = new OurButton("Generuj klucz", 10, 10);
        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ourKeyGenerator = new OurKeyGenerator();
                key = ourKeyGenerator.getRandomKey();
                keyLabel = new Label();
                keyLabel.setLayoutX(220);
                keyLabel.setLayoutY(10);
                keyLabel.setText(ourKeyGenerator.toString(key));
                keyLabel.setPrefWidth(500);
                keyLabel.setMaxHeight(100);
                keyLabel.setStyle("-fx-font: 34 arial; -fx-border-color: black;");
                pane.getChildren().add(keyLabel);
            }
        });
        OurButton saveKey = new OurButton("Zapisz klucz", 790, 10);
        saveKey.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select file to save");
                String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                try {
                    bytesToFile.write(path, key);
                } catch (IOException e) {
                    throw new RuntimeException("Nie udało się zapisać do pliku");
                }
            }
        });
        OurButton encrypt = new OurButton("Szyfruj", 425, 400);
        encrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                areaAfterEncrypting = new TextArea();
                textToBytes = new TextToBytes();
                areaAfterEncrypting.setText(textToBytes.bytesToText(textToBytes.textToBytes(areaToWrite.getText())));
                areaAfterEncrypting.setLayoutX(510);
                areaAfterEncrypting.setLayoutY(120);
                pane.getChildren().add(areaAfterEncrypting);
            }
        });
        OurButton decrypt = new OurButton("Deszyfruj", 425, 450);
        generate.setPrefWidth(200);
        saveKey.setPrefWidth(200);
        saveFile.setPrefWidth(200);
        pane.getChildren().add(encrypt);
        pane.getChildren().add(generate);
        pane.getChildren().add(saveKey);
        pane.getChildren().add(decrypt);
        pane.getChildren().add(back);
        pane.getChildren().add(saveFile);
    }

    private void createTextArea() {
        areaToWrite = new TextArea();
        areaToWrite.setLayoutX(10);
        areaToWrite.setLayoutY(120);
        pane.getChildren().add(areaToWrite);
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
