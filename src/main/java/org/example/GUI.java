package org.example;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GUI {
    private AnchorPane pane;
    private Scene scene;
    private Stage stage;

    public GUI(){
        pane = new AnchorPane();
        scene = new Scene(pane, 1024, 1024);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
