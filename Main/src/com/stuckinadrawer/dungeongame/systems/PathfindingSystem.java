package com.stuckinadrawer.dungeongame.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.stuckinadrawer.dungeongame.Constants;
import com.stuckinadrawer.dungeongame.components.Position;
import com.stuckinadrawer.dungeongame.components.Solid;
import com.stuckinadrawer.dungeongame.screen.GameScreen;

import java.io.Serializable;
import java.util.*;

public class PathfindingSystem extends VoidEntitySystem {
    private Entity[][] level;
    @Mapper
    private ComponentMapper<Solid> solidComponentMapper;

    public PathfindingSystem(World world, Entity[][] level) {
        this.level = level;
        setWorld(world);
        solidComponentMapper = world.getMapper(Solid.class);
    }

    @Override
    protected void processSystem() {
        Position playerGoal = world.getManager(TagManager.class).getEntity(Constants.Tags.PLAYER_PATHFINDING_GOAL).getComponent(Position.class);
        Position player = world.getManager(TagManager.class).getEntity(Constants.Tags.PLAYER).getComponent(Position.class);
        //Gdx.app.log("hallo", "find path from: "+ player.getX()+" "+player.getY()+" to: " +playerGoal.getX() + " " + playerGoal.getY());
        if(isWalkable(playerGoal.getX(), playerGoal.getY())){

            createPath(new Node(player), new Node(playerGoal));
        }



    }

    private boolean isWalkable(int x, int y){

        Entity tile = level[x][y];
        Solid solid = solidComponentMapper.get(tile);
        return solid == null;

    }

    private void createPath(Node start, Node goal){
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

        //
       // Gdx.app.log("hallo", "PATH FOUND: ");
        String pathString = " ";
        LinkedList<Position> path = new LinkedList<Position>();
        Node current = goal;
        while(current.parent!=null){
            pathString += "("+current.pos.getX()+","+current.pos.getY()+") ";

            path.push(current.pos);
            current = current.parent;
        }
       // Gdx.app.log("hello", pathString);
        GameScreen.movementPath = path;
    }

    private List<Node> getNeighbours(Node n){
        List<Node> neighbours = new ArrayList<Node>();


        // x-1,y | x+1,y | x,y-1 | x,y+1
        //is neighbour out of bounds? nicht checken weil walkables immer mit solid umgeben sind!
        //is walkable -> also is solid im moment, gibt noch keine nicht walkable und nicht solid tiles, später? -> lava, tiefes wasser;

        //LINKS
        Position neighbour = new Position(n.pos.getX()-1, n.pos.getY());
        if(isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }

        //RECHTS
        neighbour = new Position(n.pos.getX()+1, n.pos.getY());
        if(isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }

        //OBEN
        neighbour = new Position(n.pos.getX(), n.pos.getY()-1);
        if(isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }

        //UNTEN
        neighbour = new Position(n.pos.getX(), n.pos.getY()+1);
        if(isWalkable(neighbour.getX(), neighbour.getY())){
            neighbours.add(new Node(neighbour));
        }
       // System.out.println("Neighbours added!");
        //System.out.println(n.pos.toString() + " has " + neighbours.size() +" acceptable neighbours");
        return neighbours;
    }

    //Manhattan distance
    private int heuristic(Node n, Node goal){
        int dx = Math.abs(n.pos.getX() - goal.pos.getX());
        int dy =  Math.abs(n.pos.getY() - goal.pos.getY());
        return dx+dy;
    }

    private class Node implements Serializable{
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
