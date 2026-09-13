package org.pacmanfinal.characters.enemy;

import org.pacmanfinal.characters.Pacman;
import org.pacmanfinal.movement.Tile;

public class Clyde extends Ghosts{

    //Chases directly after Pac-Man, but tries to head to his Scatter corner when within an 8-Dot radius of Pac-Man

    public Clyde(int x, int y, Pacman pacman) {
        super(x, y, GhostColorType.ORANGE, 1, 29, pacman); //angolo basso a sinistra
    }

    @Override
    protected Tile getChaseTarget(){
        int dx = x - pacman.getX();
        int dy = y - pacman.getY();
        int distanceSquared = dx * dx + dy * dy;
        if (distanceSquared > 64){
            return new Tile(pacman.getX(), pacman.getY());
        }else{
            return new Tile(scatterTargetX, scatterTargetY);
        }
    }
}
