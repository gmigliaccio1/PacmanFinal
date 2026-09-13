package org.pacmanfinal.characters.enemy;

import org.pacmanfinal.characters.Pacman;
import org.pacmanfinal.movement.Direction;
import org.pacmanfinal.movement.Tile;

public class Inky extends Ghosts {

    //During Chase mode, his target is a bit complex. His target is relative to both Blinky
    // and Pac-Man, where the distance Blinky is from Pinky's target is doubled to get Inky's target

    private final Ghosts blinky;
    public Inky(int x, int y, Pacman pacman, Ghosts blinky) {
        super(x, y, GhostColorType.CYAN, 26, 29, pacman);//angolo in basso a destra
        this.blinky = blinky;
    }

    @Override
    protected Tile getChaseTarget(){
        int px = pacman.getX();
        int py = pacman.getY();
        Direction dir = pacman.getDirection();
        int targetX = px;
        int targetY = py;

        switch (dir) {
            case UP: targetY -= 2; break;
            case DOWN: targetY += 2; break;
            case LEFT: targetX -= 2; break;
            case RIGHT: targetX += 2; break;
        }

        int dx = targetX - blinky.getX();
        int dy = targetY - blinky.getY();
        return new Tile(blinky.getX() + 4 * dx, blinky.getY() + 4 * dy);
    }
}
