package org.pacmanfinal.characters.enemy;

import org.pacmanfinal.characters.Pacman;
import org.pacmanfinal.movement.Direction;
import org.pacmanfinal.movement.Tile;

public class Pinky extends Ghosts {

    //targets 4 tiles ahead of pacman

    public Pinky(int x, int y, Pacman pacman) {
        super(x, y, GhostColorType.PINK, 1,1, pacman); //angolo alto a sinistra
    }

    @Override
    protected Tile getChaseTarget(){
        int px = pacman.getX();
        int py = pacman.getY();
        Direction dir = pacman.getDirection();

        switch (dir) {
            case UP: return new Tile(px , py - 4);
            case DOWN: return new Tile(px , py + 4);
            case LEFT: return new Tile(px - 4, py);
            case RIGHT: return new Tile(px + 4, py);
            default: return new Tile(px , py);
        }
    }
}
