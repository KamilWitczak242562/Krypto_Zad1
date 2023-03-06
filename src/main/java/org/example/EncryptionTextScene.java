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
import javafx.stage.Stage;

public class EncryptionTextScene {
    private AnchorPane pane;
    private Stage stage;
    private Scene scene;
    private GUI gui;

    private String textToEncrypt;

    private TextArea areaToWrite;

    private TextArea areaAfterEncrypting;

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
        OurButton generate = new OurButton("Generuj klucz", 10, 10);
        OurButton saveKey = new OurButton("Zapisz klucz", 790, 10);
        OurButton encrypt = new OurButton("Szyfruj", 425, 400);
        encrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                areaAfterEncrypting = new TextArea();
                areaAfterEncrypting.setText(areaToWrite.getText());
                areaAfterEncrypting.setLayoutX(510);
                areaAfterEncrypting.setLayoutY(110);
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
        areaToWrite.setLayoutY(110);
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
