package org.pacmanfinal.movement;

import org.pacmanfinal.map.Maze;

import java.util.*;

public class PathFinding {

    static class Node implements Comparable<Node> {
        Tile tile;
        Node parent;
        int g, f; //g costo da inizio, f = g + h
        Node(Tile tile, Node parent, int g, int f) {
            this.tile = tile;
            this.parent = parent;
            this.g = g;
            this.f = f;
        }

        public int compareTo(Node o) {
            return Integer.compare(this.f, o.f);
        }
    }

    public static List<Tile> findPath(Maze maze, Tile start, Tile goal){
        PriorityQueue<Node> open = new PriorityQueue<Node>();
        Map<Tile, Integer> gScore = new HashMap<>();
        Set<Tile> closed = new HashSet<>();
        open.add(new Node(start, null, 0, heuristic(start, goal)));
        gScore.put(start, 0);
        while (!open.isEmpty()) {
            Node curr = open.poll();
            if(curr.tile.equals(goal)) {
                return reconstructPath(curr);
            }
                closed.add(curr.tile);
                for(Tile neighbor : maze.getNeighbors(curr.tile)){
                    if(!maze.isWalkable(neighbor.x, neighbor.y) || closed.contains(neighbor)){
                        continue;
                    }
                    int tentG = curr.g + 1;
                    if(tentG < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)){
                        gScore.put(neighbor, tentG);
                        int f = tentG + heuristic(neighbor, goal);
                        open.add(new Node(neighbor, curr, tentG, f));
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Tile> reconstructPath(Node node) {
        List<Tile> path = new ArrayList<>();
        while(node != null){
            path.add(0, node.tile);
            node = node.parent;
        }
        return path;
    }

    private static int heuristic(Tile a, Tile b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); //Manhattan distance
    }
}
