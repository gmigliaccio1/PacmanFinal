package org.pacmanfinal.map;

import org.pacmanfinal.movement.Direction;
import org.pacmanfinal.movement.Tile;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int PELLET = 2;
    public static final int POWER_PELLET = 3;

    private final int[][] map;
    public static final int WIDTH = 28;
    public static final int HEIGHT = 31;

    public Maze() {
        this.map = createDefaultMaze();
    }

    private int[][] createDefaultMaze() {
        String[] mazeDesign = new String[] {
                "1111111111111111111111111111",
                "1............11............1",
                "1.1111.11111.11.11111.1111.1",
                "1..........................1",
                "1.1111.11.11111111.11.1111.1",
                "1......11..........11......1",
                "1.111111111111111111111111.1",
                "1............11............1",
                "1o1111.11111.11.11111.1111o1",
                "1.1111.11111.11.11111.1111.1",
                "1..........................1",
                "1.1111.11.11111111.11.1111.1",
                "1.1111.11.11111111.11.1111.1",
                "1......11....11....11......1",
                "111111.11111.11.11111.111111",
                "111111.11..........11.111111",
                "111111.11.111..111.11.111111",
                "111111.11.1......1.11.111111",
                "111111....1......1....111111",
                "111111.11.1......1.11.111111",
                "111111.11.11111111.11.111111",
                "111111.11..........11.111111",
                "111111.11.11111111.11.111111",
                "1............11............1",
                "1.1111.11111.11.11111.1111.1",
                "1o...1................1...o1",
                "1111.11111.111111.11111.1111",
                "1....11111...11...11111....1",
                "1.1111111111.11.1111111111.1",
                "1..........................1",
                "1111111111111111111111111111"
        };

        if(mazeDesign.length != HEIGHT) {
            throw new IllegalStateException("Maze height must be " + HEIGHT + "but is " + mazeDesign.length);
        }

        int[][] maze = new int[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            String row = mazeDesign[y];
            if (row.length() != WIDTH) {
                throw new IllegalStateException(
                        "Row " + y + " has length " + row.length() + " (expected " + WIDTH + ")"
                );
            }
            for(int x = 0; x < WIDTH; x++) {
                char c = row.charAt(x);
                switch (c){
                    case '1': maze[y][x] = WALL; break;
                    case '.' : maze[y][x] = PELLET; break;
                    case 'o': maze[y][x] = POWER_PELLET; break;
                    case ' ': maze[y][x] = EMPTY; break;
                }
            }
        }
        return maze;
    }

    public boolean isWalkable(int x, int y) {
        if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return false;
        return map[y][x] != 1;
    }

    public List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        int x = tile.x;
        int y = tile.y;
        if(isWalkable(x, y -1)) neighbors.add(new Tile(x, y - 1));
        if(isWalkable(x, y + 1)) neighbors.add(new Tile(x, y + 1));
        if(isWalkable(x - 1, y)) neighbors.add(new Tile(x - 1, y));
        if(isWalkable(x + 1, y)) neighbors.add(new Tile(x + 1, y));

        return neighbors;
    }

    public int getTileType(int x, int y) {
        if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return -1;
        return map[y][x];
    }

    public boolean hasPellet(int x, int y){return map[y][x] == PELLET;}
    public boolean hasPowerPellet(int x, int y){return map[y][x] == POWER_PELLET;}
    public boolean eatPallet(int x, int y) {
        if(getTileType(x, y) == PELLET || getTileType(x, y) == POWER_PELLET){
            map[y][x] = EMPTY;
            return true;
        }
        return false;
    }

    public int[][] getMap() {
        return map;
    }

    public boolean canMove(int x, int y, Direction dir) {
        switch (dir) {
            case UP: return isWalkable(x, y - 1);
            case DOWN: return isWalkable(x, y + 1);
            case LEFT: return isWalkable(x - 1, y);
            case RIGHT: return isWalkable(x + 1, y);
            default: return false;
        }
    }
}
