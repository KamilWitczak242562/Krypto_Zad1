package org.example;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class OurButton extends Button {

    public OurButton(String text, int x, int y) {
        setText(text);
        setStyle("-fx-font: 24 arial;");
        setPrefHeight(39);
        setPrefWidth(150);
        setLayoutX(x);
        setLayoutY(y);
    }

}
