package org.pacmanfinal.characters.enemy;

import org.pacmanfinal.characters.Entity;
import org.pacmanfinal.characters.Pacman;
import org.pacmanfinal.map.Maze;
import org.pacmanfinal.movement.Direction;
import org.pacmanfinal.movement.PathFinding;
import org.pacmanfinal.movement.Tile;

import java.util.List;

public abstract class Ghosts extends Entity {
    protected GhostMode mode = GhostMode.SCATTER;
    protected final GhostColorType color;
    protected final int scatterTargetX, scatterTargetY;
    protected Pacman pacman;
    private boolean scared = false;

    public Ghosts(int x, int y, GhostColorType color, int scatterTargetX, int scatterTargetY, Pacman pacman) {
        super(x, y);
        this.color = color;
        this.scatterTargetX = scatterTargetX;
        this.scatterTargetY = scatterTargetY;
        this.pacman = pacman;
    }

    public GhostColorType getColor() {
        return color;
    }

    public void setMode(GhostMode mode) {
        this.mode = mode;
    }

    protected abstract Tile getChaseTarget();

    @Override
    public void update(Maze maze){
        Tile target;
        if(mode == GhostMode.FRIGHTENED) {
            List<Tile> neighbors = maze.getNeighbors(new Tile(x,y));
            if(!neighbors.isEmpty()) {
                Tile randomMove = neighbors.get((int) (Math.random() * neighbors.size()));
                moveTo(randomMove, maze);
            }
            return;
        }
        if(mode == GhostMode.SCATTER) {
            target = new Tile(scatterTargetX, scatterTargetY);
        }else{
            target = getChaseTarget();
        }
        List<Tile> path = PathFinding.findPath(maze, new Tile(x,y), target);
        if(path.size() > 1){
            Tile next = path.get(1);
            moveTo(next, maze);
        }
    }

    protected void moveTo(Tile tile, Maze maze) {
        if(maze.isWalkable(tile.x, tile.y)){
            if(tile.x > x) direction = Direction.RIGHT;
            else if(tile.x < x) direction = Direction.LEFT;
            else if(tile.y > y) direction = Direction.UP;
            else if(tile.y < y) direction = Direction.DOWN;

            x=tile.x;
            y=tile.y;
        }
    }

    public boolean isScared() {
        return scared;
    }

    public void setScared(boolean scared) {
        this.scared = scared;
        if(scared){
            mode = GhostMode.FRIGHTENED;
        }else{
            mode = GhostMode.SCATTER;
        }
    }
}
