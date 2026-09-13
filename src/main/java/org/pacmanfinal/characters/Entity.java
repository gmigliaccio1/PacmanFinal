package org.pacmanfinal.characters;

import org.pacmanfinal.map.Maze;
import org.pacmanfinal.movement.Direction;

public abstract class Entity {
    protected int x, y;
    protected Direction direction = Direction.NONE;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public Direction getDirection() {return direction;}
    public void setDirection(Direction direction) {this.direction = direction;}
    public abstract void update(Maze maze);
}
