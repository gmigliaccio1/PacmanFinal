package org.pacmanfinal.characters;

import org.pacmanfinal.map.Maze;
import org.pacmanfinal.movement.Direction;

public class Pacman extends Entity {
    private int lives = 3;
    private boolean dead = false;

    public Pacman(int x, int y) {
        super(x, y);
    }

    public int getLives() {return lives;}
    public void loseLife(){
        lives--;
        dead = true;
    }

    public void revive(int startX,int startY){
        x = startX;
        y = startY;
        direction = Direction.NONE;
        dead = false;
    }

    @Override
    public void update(Maze maze) {
        int newX = x;
        int newY = y;

        switch (direction) {
            case UP: newY--; break;
            case DOWN: newY++; break;
            case LEFT: newX--; break;
            case RIGHT: newX++; break;
            case NONE: return;
        }
        if(maze.isWalkable(newX,newY)){
            x = newX;
            y = newY;
        }
    }
}
