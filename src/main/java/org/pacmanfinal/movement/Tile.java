package org.pacmanfinal.movement;

public class Tile {
    public final int x, y;
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof Tile)) return false;
        Tile tile = (Tile)obj;
        return x == tile.x && y == tile.y;
    }

    @Override
    public int hashCode(){
        return 31 * x + y;
    }
}
