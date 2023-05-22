package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class EncryptionFileScene {

    private AnchorPane pane;
    private Stage stage;
    private Scene scene;
    private GUI gui;
    private RSA rsa;
    private TextArea areaToEncrypt;
    private TextArea areaAfterEncrypting;
    private byte[] bytes;

    private BigInteger bytesD;
    private BigInteger bytesN;

    private Utils utils;
    private FIleHandler handler;

    private String toEncryptHex;
    private BigInteger[] decrypted;

    private BigInteger[] encrypted;
    private BigInteger[] toDecrypt;
    private Label keyP;
    private Label keyQ;

    public EncryptionFileScene(Boolean whatToDo) {
        rsa = new RSA();
        utils = new Utils();
        handler = new FIleHandler();
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

        if (whatToDo) {
            OurButton loadFile = new OurButton("Wczytaj plik", 10, 60);
            loadFile.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open file to encrypt");
                    String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                    try {
                        bytes = handler.load(path);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    toEncryptHex = utils.bytesToHex(bytes);
                    areaToEncrypt = new TextArea();
                    areaToEncrypt.setLayoutX(10);
                    areaToEncrypt.setLayoutY(120);
                    areaToEncrypt.setText(toEncryptHex);
                    pane.getChildren().add(areaToEncrypt);
                }
            });
            loadFile.setPrefWidth(200);
            OurButton generate = new OurButton("Generuj", 10, 10);
            generate.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    rsa.generateKeys(256);
                    keyP = new Label();
                    keyP.setLayoutX(220);
                    keyP.setLayoutY(10);
                    keyP.setText(rsa.getD().toString());
                    keyP.setPrefWidth(500);
                    keyP.setMaxHeight(100);
                    keyP.setStyle("-fx-font: 34 arial; -fx-border-color: black;");
                    pane.getChildren().add(keyP);
                    keyQ = new Label();
                    keyQ.setLayoutX(220);
                    keyQ.setLayoutY(60);
                    keyQ.setText(rsa.getN().toString());
                    keyQ.setPrefWidth(500);
                    keyQ.setMaxHeight(100);
                    keyQ.setStyle("-fx-font: 34 arial; -fx-border-color: black;");
                    pane.getChildren().add(keyQ);
                }
            });
            OurButton saveKey = new OurButton("Zapisz", 790, 10);
            saveKey.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    FileChooser fileChooser1 = new FileChooser();
                    fileChooser1.setTitle("Select file to save for D");
                    File path = fileChooser1.showSaveDialog(stage);
                    try {
                        handler.writeBig(String.valueOf(path), rsa.getD());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    fileChooser1.setTitle("Select file to save for N");
                    File path2 = fileChooser1.showSaveDialog(stage);
                    try {
                        handler.writeBig(String.valueOf(path2), rsa.getN());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            OurButton encrypt = new OurButton("Szyfruj", 425, 400);
            OurButton saveFile = new OurButton("Zapisz plik", 790, 60);
            saveFile.setPrefWidth(200);
            saveFile.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Select file to save");
                    File path = fileChooser.showSaveDialog(stage);
                    handler.saveCipher(encrypted, String.valueOf(path));
                }
            });
            encrypt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    encrypted = rsa.encrypt(bytes);
                    areaAfterEncrypting = new TextArea();
                    areaAfterEncrypting.setLayoutX(510);
                    areaAfterEncrypting.setLayoutY(120);
                    areaAfterEncrypting.setText(utils.arrToString(encrypted));
                    pane.getChildren().add(areaAfterEncrypting);
                }
            });
            pane.getChildren().add(encrypt);
            pane.getChildren().add(saveFile);
            pane.getChildren().add(generate);
            pane.getChildren().add(saveKey);
            pane.getChildren().add(loadFile);
        } else {
            OurButton loadFile = new OurButton("Wczytaj plik", 10, 60);
            loadFile.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open file to encrypt");
                    String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                    toDecrypt = handler.loadCipher(path);
                    areaToEncrypt = new TextArea();
                    areaToEncrypt.setLayoutX(10);
                    areaToEncrypt.setLayoutY(120);
                    areaToEncrypt.setText(utils.arrToString(toDecrypt));
                    pane.getChildren().add(areaToEncrypt);
                }
            });
            loadFile.setPrefWidth(200);
            OurButton decrypt = new OurButton("Deszyfruj", 425, 400);
            OurButton saveFile = new OurButton("Zapisz plik", 790, 60);
            saveFile.setPrefWidth(200);
            saveFile.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Select file to save");
                    File path = fileChooser.showSaveDialog(stage);
                    handler.saveDecrypted(decrypted, String.valueOf(path));
                }
            });
            decrypt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    decrypted = rsa.decrypt(toDecrypt);
                    areaAfterEncrypting = new TextArea();
                    areaAfterEncrypting.setLayoutX(510);
                    areaAfterEncrypting.setLayoutY(120);
                    areaAfterEncrypting.setText(utils.arrToString(decrypted));
                    pane.getChildren().add(areaAfterEncrypting);
                }
            });
            OurButton loadKey = new OurButton("Wczytaj", 10, 10);
            loadKey.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    handler = new FIleHandler();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open file with D");
                    String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                    try {
                        bytesD = handler.readBig(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    fileChooser.setTitle("Open file with N");
                    String path2 = fileChooser.showOpenDialog(stage).getAbsolutePath();
                    try {
                        bytesN = handler.readBig(path2);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    rsa.setD(bytesD);
                    rsa.setN(bytesN);
                    keyP = new Label();
                    keyP.setLayoutX(220);
                    keyP.setLayoutY(10);
                    keyP.setText(rsa.getD().toString());
                    keyP.setPrefWidth(500);
                    keyP.setMaxHeight(100);
                    keyP.setStyle("-fx-font: 34 arial; -fx-border-color: black;");
                    pane.getChildren().add(keyP);
                    keyQ = new Label();
                    keyQ.setLayoutX(220);
                    keyQ.setLayoutY(60);
                    keyQ.setText(rsa.getN().toString());
                    keyQ.setPrefWidth(500);
                    keyQ.setMaxHeight(100);
                    keyQ.setStyle("-fx-font: 34 arial; -fx-border-color: black;");
                    pane.getChildren().add(keyQ);
                }
            });
            pane.getChildren().add(decrypt);
            pane.getChildren().add(saveFile);
            pane.getChildren().add(loadKey);
            pane.getChildren().add(loadFile);
        }
        pane.getChildren().add(back);
    }
}
