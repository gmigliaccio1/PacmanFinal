package org.pacmanfinal.characters.enemy;

import org.pacmanfinal.characters.Pacman;
import org.pacmanfinal.movement.Tile;

public class Blinky extends Ghosts {

    // direct chase pacman

    public Blinky(int x, int y, Pacman pacman) {
        super(x, y, GhostColorType.RED, 26, 1, pacman); //Angolo alto a destra
    }

    @Override
    protected Tile getChaseTarget(){
        return new Tile(pacman.getX(), pacman.getY());
    }
}
