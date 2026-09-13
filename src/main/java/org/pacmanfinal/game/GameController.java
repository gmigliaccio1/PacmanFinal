package org.pacmanfinal.game;

import javafx.animation.AnimationTimer;
import org.pacmanfinal.characters.Pacman;
import org.pacmanfinal.characters.enemy.*;
import org.pacmanfinal.map.Maze;
import org.pacmanfinal.movement.Direction;
import org.pacmanfinal.gui.GameGUI;

import java.util.List;

public class GameController {
    private final GameGUI gui;
    private Maze maze;
    private Pacman pacman;
    private List<Ghosts> ghosts;
    private int score = 0;
    private boolean gameOver = false;
    private boolean win = false;
    private long scaredEnd = 0;
    private final long PACMAN_MOVE_DELAY = 100_000_000L;
    private long lastMoveTime = 0;
    private final long GHOST_MOVE_DELAY = 120_000_000L;
    private long lastGhostMoveTime = 0;
    private Direction nextDir = Direction.NONE;
    private AnimationTimer gameLoop;

    public GameController(GameGUI gui) {
        this.gui = gui;
        initializeGame();
    }

    private void initializeGame() {
        maze = new Maze();
        pacman = new Pacman(13, 25);
        Ghosts blinky = new Blinky(13, 14, pacman);
        Ghosts pinky = new Pinky(14, 14, pacman);
        Ghosts inky = new Inky(12, 14, pacman, blinky);
        Ghosts clyde = new Clyde(15, 14, pacman);
        ghosts = List.of(blinky, pinky, inky, clyde);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameOver || win) return;
                updateGame(now);
            }
        };
        gameLoop.start();
    }

    public void onDirectionInput(String key) {
        switch (key) {
            case "UP" -> nextDir = Direction.UP;
            case "DOWN" -> nextDir = Direction.DOWN;
            case "LEFT" -> nextDir = Direction.LEFT;
            case "RIGHT" -> nextDir = Direction.RIGHT;
        }
    }

    private void updateGame(long now) {
        long seconds = now / 1_000_000_000L;
        if ((seconds / 7) % 2 == 0) {
            ghosts.forEach(ghost -> {
                if (!ghost.isScared()) ghost.setMode(GhostMode.SCATTER);
            });
        } else {
            ghosts.forEach(ghost -> {
                if (!ghost.isScared()) ghost.setMode(GhostMode.CHASE);
            });
        }
        if (now - lastMoveTime >= PACMAN_MOVE_DELAY) {
            lastMoveTime = now;

            if (maze.canMove(pacman.getX(), pacman.getY(), nextDir)) {
                pacman.setDirection(nextDir);
            }

            pacman.update(maze);
            handlePellets(now);
            checkCollisions();
        }

        if(now - lastGhostMoveTime >= GHOST_MOVE_DELAY) {
            lastGhostMoveTime = now;
            ghosts.forEach(ghost -> ghost.update(maze));
        }

        if (now > scaredEnd) {
            ghosts.forEach(g -> g.setScared(false));
        }

        gui.render(maze, pacman, ghosts);
        gui.updateScore(score);
        gui.updateLives(pacman.getLives());

        if (allPelletsEaten()) {
            win = true;
            gameLoop.stop();
            gui.showWin();
        }
    }

    private void handlePellets(long now) {
        int x = pacman.getX();
        int y = pacman.getY();
        if (maze.hasPellet(x, y)) {
            maze.eatPallet(x, y);
            score += 10;
        } else if (maze.hasPowerPellet(x, y)) {
            maze.eatPallet(x, y);
            ghosts.forEach(g -> g.setScared(true));
            scaredEnd = now + 5_000_000_000L;
        }
    }

    private void checkCollisions() {
        for (Ghosts g : ghosts) {
            if (g.getX() == pacman.getX() && g.getY() == pacman.getY()) {
                if (g.isScared()) {
                    score += 200;
                    g.setX(14); g.setY(14); g.setScared(false);
                } else {
                    pacman.loseLife();
                    if (pacman.getLives() == 0) {
                        gameOver = true;
                        gui.showGameOver();
                        gameLoop.stop();
                    } else {
                        pacman.revive(13, 25);
                        ghosts.forEach(gh -> {
                            gh.setX(13 + ghosts.indexOf(gh));
                            gh.setY(14);
                        });
                    }
                }
            }
        }
    }

    private boolean allPelletsEaten() {
        for (int[] row : maze.getMap()) {
            for (int tile : row) {
                if (tile == Maze.PELLET || tile == Maze.POWER_PELLET)
                    return false;
            }
        }
        return true;
    }

    public void forceRender() {
        gui.render(maze, pacman, ghosts);
    }
}