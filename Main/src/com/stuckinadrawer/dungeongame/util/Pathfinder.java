package com.stuckinadrawer.dungeongame.util;

import com.stuckinadrawer.dungeongame.Level;

import java.io.Serializable;
import java.util.*;

public class Pathfinder {

    private Level level;

    public Pathfinder(Level level){
        this.level = level;
    }

    public LinkedList<Position> findPath(Position startPos, Position goalPos){
        Node start = new Node(startPos);
        Node goal = new Node(goalPos);

        Set<Node> open  = new HashSet<Node>();
        Set<Node> closed = new HashSet<Node>();
        start.g = 0;
        start.h = heuristic(start, goal);
        start.f = start.h;

        open.add(start);

        while(true){

            if(open.size() == 0){
                System.out.println("###NO PATH###");
                break;
            }

            //take open node with smallest f as current
            Node current  = null;
            for(Node node: open){
                if(current == null || node.f < current.f) current = node;
            }

            assert current != null;
            if(current.equals(goal)){
                goal = current;
                break;
            }

            open.remove(current);
            closed.add(current);
            for(Node neighbour: getNeighbours(current)){

                int nextG = current.g + neighbour.cost;

                //if nextG (cost of current path to neighbour) is shorter than g of neighbour, look at it again
                if(nextG < neighbour.g){
                    open.remove(neighbour);
                    closed.remove(neighbour);
                }

                if(!open.contains(neighbour) && !closed.contains(neighbour)){
                    neighbour.g = nextG;
                    neighbour.h = heuristic(neighbour, goal);
                    neighbour.f = neighbour.g + neighbour.h;
                    neighbour.parent = current;
                    open.add(neighbour);
                }
            }


        }

        // Gdx.app.log("hallo", "PATH FOUND: ");
        String pathString = " ";
        LinkedList<Position> path = new LinkedList<Position>();
        Node current = goal;
        while(current.parent!=null){
            pathString += "("+current.pos.getX()+","+current.pos.getY()+") ";

            path.push(current.pos);
            current = current.parent;
        }

        return path;

    }

    //Manhattan distance
    private int heuristic(Node n, Node goal){
        int dx = Math.abs(n.pos.getX() - goal.pos.getX());
        int dy =  Math.abs(n.pos.getY() - goal.pos.getY());
        return dx+dy;
    }

    private List<Node> getNeighbours(Node n){
        List<Node> neighbours = new ArrayList<Node>();


        // x-1,y | x+1,y | x,y-1 | x,y+1
        //is neighbour out of bounds? nicht checken weil walkables immer mit solid umgeben sind!
        //is walkable -> also is solid im moment, gibt noch keine nicht walkable und nicht solid tiles, später? -> lava, tiefes wasser;

        //LINKS
        Position neighbour = new Position(n.pos.getX()-1, n.pos.getY());
        if(level.isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }

        //RECHTS
        neighbour = new Position(n.pos.getX()+1, n.pos.getY());
        if(level.isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }

        //OBEN
        neighbour = new Position(n.pos.getX(), n.pos.getY()-1);
        if(level.isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }

        //UNTEN
        neighbour = new Position(n.pos.getX(), n.pos.getY()+1);
        if(level.isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }

        /* MORE NEIGHBOURS FOR DIAGONAL WALKING */
        /*
        // LINKS OBEN
        neighbour = new Position(n.pos.getX()-1, n.pos.getY()-1);
        if(level.isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }
        // LINKS UNTEN
        neighbour = new Position(n.pos.getX()-1, n.pos.getY()+1);
        if(level.isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }
        // RECHTS OBEN
        neighbour = new Position(n.pos.getX()+1, n.pos.getY()-1);
        if(level.isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }
        // RECHTS UNTEN
        neighbour = new Position(n.pos.getX()+1, n.pos.getY()+1);
        if(level.isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }
        */



        // System.out.println("Neighbours added!");
        //System.out.println(n.pos.toString() + " has " + neighbours.size() +" acceptable neighbours");
        return neighbours;
    }

    private class Node implements Serializable {
        Node parent;
        //rank
        int f;
        //cost from starting point
        int g;
        //estimated cost to goal
        int h;
        Position pos;
        int cost = 1;

        public Node(Position pos){
            this.pos = pos;
        }

        @Override
        public boolean equals(Object obj){
            if (obj instanceof Node) {
                Node n = (Node) obj;
                return (this.pos.getX() == n.pos.getX()) && (this.pos.getY() == n.pos.getY());
            }
            return super.equals(obj);
        }


        @Override
        public int hashCode() {
            long bits = java.lang.Double.doubleToLongBits(pos.getX());
            bits ^= java.lang.Double.doubleToLongBits(pos.getY()) * 31;
            return (((int) bits) ^ ((int) (bits >> 32)));
        }

    }

}
