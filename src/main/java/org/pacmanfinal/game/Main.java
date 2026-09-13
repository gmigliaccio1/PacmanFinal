package org.pacmanfinal.game;

import javafx.application.Application;
import javafx.stage.Stage;
import org.pacmanfinal.gui.GameGUI;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        new GameGUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

