package org.pacmanfinal.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.pacmanfinal.characters.Pacman;
import org.pacmanfinal.characters.enemy.Ghosts;
import org.pacmanfinal.game.GameController;
import org.pacmanfinal.map.Maze;

import java.util.List;

public class GameGUI {

    private final Canvas canvas;
    private final Label scoreLabel;
    private final Label livesLabel;
    private final Label gameOverLabel;
    private final Label winLabel;

    private final GameController controller;

    public GameGUI(Stage stage) {
        this.canvas = new Canvas(Maze.WIDTH * 20, Maze.HEIGHT * 20); // Initial size
        this.scoreLabel = new Label("Score: 0");
        this.livesLabel = new Label("Lives: 3");
        this.gameOverLabel = new Label("GAME OVER!");
        this.winLabel = new Label("YOU WIN!");

        VBox root = setupLayout();
        Scene scene = new Scene(root);

        setupStyle();
        setupInput(scene);
        setupStage(stage, scene);

        this.controller = new GameController(this); 
        setupResizeListeners(); 
    }

    private VBox setupLayout() {
        HBox uiBar = new HBox(20, scoreLabel, livesLabel);
        uiBar.setPadding(new Insets(10));
        uiBar.setStyle("-fx-background-color: black;");

        gameOverLabel.setVisible(false);
        winLabel.setVisible(false);
        gameOverLabel.setTextFill(Color.RED);
        winLabel.setTextFill(Color.LIMEGREEN);
        gameOverLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        winLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        StackPane overlay = new StackPane(canvas, gameOverLabel, winLabel);

        VBox.setVgrow(overlay, Priority.ALWAYS);

        VBox root = new VBox(uiBar, overlay);

        canvas.widthProperty().bind(overlay.widthProperty());
        canvas.heightProperty().bind(overlay.heightProperty());

        return root;
    }

    private void setupResizeListeners() {
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> controller.forceRender());
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> controller.forceRender());
    }

    private void setupStyle() {
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        livesLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
    }

    private void setupInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            String key = event.getCode().toString();
            controller.onDirectionInput(key);
        });
    }

    private void setupStage(Stage stage, Scene scene) {
        stage.setTitle("Pacman");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        stage.getScene().getWindow().widthProperty().addListener((obs, oldVal, newVal) -> {
            controller.forceRender();
        });
        stage.getScene().getWindow().heightProperty().addListener((obs, oldVal, newVal) -> {
            controller.forceRender();
        });
    }

    public void render(Maze maze, Pacman pacman, List<Ghosts> ghosts) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double tileW = canvas.getWidth() / Maze.WIDTH;
        double tileH = canvas.getHeight() / Maze.HEIGHT;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawMaze(gc, maze, tileW, tileH);
        drawEntity(gc, pacman.getX(), pacman.getY(), tileW, tileH, Color.YELLOW);

        for (Ghosts ghost : ghosts) {
            Color c = switch (ghost.getColor()) {
                case RED -> Color.RED;
                case PINK -> Color.PINK;
                case CYAN -> Color.CYAN;
                case ORANGE -> Color.ORANGE;
                default -> Color.LIGHTGRAY;
            };
            if (ghost.isScared()) c = Color.LIGHTBLUE;
            drawEntity(gc, ghost.getX(), ghost.getY(), tileW, tileH, c);
        }

        gc.setImageSmoothing(true);
    }

    private void drawMaze(GraphicsContext gc, Maze maze, double tileW, double tileH) {
        int[][] map = maze.getMap();
        for (int y = 0; y < Maze.HEIGHT; y++) {
            for (int x = 0; x < Maze.WIDTH; x++) {
                switch (map[y][x]) {
                    case Maze.WALL -> drawWall(gc, x, y, tileW, tileH);
                    case Maze.PELLET -> drawPellet(gc, x, y, tileW, tileH);
                    case Maze.POWER_PELLET -> drawPowerPellet(gc, x, y, tileW, tileH);
                }
            }
        }
    }

    private void drawWall(GraphicsContext gc, int x, int y, double tileW, double tileH) {
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(x * tileW, y * tileH, tileW, tileH);
    }

    private void drawPellet(GraphicsContext gc, int x, int y, double tileW, double tileH) {
        gc.setFill(Color.WHITE);
        double size = Math.min(tileW, tileH) / 5.0;
        gc.fillOval(x * tileW + tileW / 2 - size / 2, y * tileH + tileH / 2 - size / 2, size, size);
    }

    private void drawPowerPellet(GraphicsContext gc, int x, int y, double tileW, double tileH) {
        gc.setFill(Color.ORANGE);
        double size = Math.min(tileW, tileH) / 2.0;
        gc.fillOval(x * tileW + tileW / 2 - size / 2, y * tileH + tileH / 2 - size / 2, size, size);
    }

    private void drawEntity(GraphicsContext gc, int x, int y, double tileW, double tileH, Color color) {
        gc.setFill(color);
        gc.fillOval(x * tileW, y * tileH, tileW, tileH);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }

    public void showGameOver() {
        gameOverLabel.setVisible(true);
    }

    public void showWin() {
        winLabel.setVisible(true);
    }
}