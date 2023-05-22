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
import javafx.stage.Stage;

public class EncryptionTextScene {
    private AnchorPane pane;
    private Stage stage;
    private Scene scene;
    private GUI gui;
    private RSA rsa;
    private TextArea areaToWrite;
    private TextArea areaAfterEncrypting;

    private Label keyE;
    private Label keyD;


    public EncryptionTextScene() {
        rsa = new RSA();
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
        OurButton generate = new OurButton("Generuj", 10, 10);
        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rsa.generateKeys(256);
                keyE = new Label();
                keyE.setLayoutX(220);
                keyE.setLayoutY(10);
                keyE.setText(rsa.getD().toString());
                keyE.setPrefWidth(500);
                keyE.setMaxHeight(100);
                keyE.setStyle("-fx-font: 34 arial; -fx-border-color: black;");
                pane.getChildren().add(keyE);
                keyD = new Label();
                keyD.setLayoutX(220);
                keyD.setLayoutY(60);
                keyD.setText(rsa.getN().toString());
                keyD.setPrefWidth(500);
                keyD.setMaxHeight(100);
                keyD.setStyle("-fx-font: 34 arial; -fx-border-color: black;");
                pane.getChildren().add(keyD);
            }
        });
        OurButton back = new OurButton("Powrót", 840, 440);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                gui = new GUI();
            }
        });
        OurButton encrypt = new OurButton("Szyfruj", 425, 400);
        encrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                areaAfterEncrypting = new TextArea();
                areaAfterEncrypting.setLayoutX(510);
                areaAfterEncrypting.setLayoutY(120);
                areaAfterEncrypting.setText(rsa.encryptString(areaToWrite.getText()));
                pane.getChildren().add(areaAfterEncrypting);
            }
        });
        OurButton decrypt = new OurButton("Deszyfruj", 425, 450);
        decrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                areaToWrite.setText(rsa.decryptString(areaAfterEncrypting.getText()));
            }
        });
        pane.getChildren().add(encrypt);
        pane.getChildren().add(decrypt);
        pane.getChildren().add(back);
        pane.getChildren().add(generate);
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
