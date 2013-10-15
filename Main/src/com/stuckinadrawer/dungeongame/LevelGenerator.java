package com.stuckinadrawer.dungeongame;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityManager;
import com.artemis.World;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.stuckinadrawer.dungeongame.components.AI;
import com.stuckinadrawer.dungeongame.components.Position;
import com.stuckinadrawer.dungeongame.components.Tile;
import com.stuckinadrawer.dungeongame.systems.AISystem;

import java.util.ArrayList;

public class LevelGenerator {

    private int roomCount;
    private int minSize = 5;
    private int maxSize = 15;
    private Tiles[][] level;
    private ArrayList<Room> rooms;
    private int levelWidth = 64;
    private int levelHeight = 64;
    private World world;

    public LevelGenerator(World world) {
        this.world = world;
        roomCount = MathUtils.random(15, 25);
        initEmptyLevel();
    }

    public Entity[][] generateLevel() {

        Gdx.app.log("hallo", "Level is now generating");

        generateRooms();
        moveRoomsCloser();
        buildCorridors();
        createEnemies();
        putRoomsInMap();
        buildWalls();


        return createEntityLevel();
    }

    private void createEnemies() {
        for(Room r: rooms){
            int x = MathUtils.random(r.x+1, r.x+r.width-1);
            int y = MathUtils.random(r.y+1, r.y+r.height-1);
            Entity entity = EntityFactory.createEnemy(world, x, y);
            entity.addToWorld();

        }
    }

    private void buildWalls() {

        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                //level[x][y] = Tiles.EMPTY;
                if (level[x][y] == Tiles.ROOM || level[x][y] == Tiles.CORRIDOR) {
                    for (int xx = x - 1; xx <= x + 1; xx++) {
                        for (int yy = y - 1; yy <= y + 1; yy++) {
                            if (level[xx][yy] == Tiles.EMPTY) {
                                level[xx][yy] = Tiles.WALL;
                            }
                        }
                    }
                }
            }
        }

    }

    private void putRoomsInMap() {
        System.out.println("RoomCount: " + roomCount);
        for (int i = 0; i < roomCount; i++) {
            Room room = rooms.get(i);
            //System.out.println("Putting room "+i+" in map!");
            //System.out.println("Room Added: x:"+room.x + "  y:"+room.y + " width:"+room.width+" height:"+room.height);
            for (int x = room.x; x < room.width + room.x; x++) {
                for (int y = room.y; y < room.height + room.y; y++) {

                    level[x][y] = Tiles.ROOM;

                }
            }
        }

    }

    private void buildCorridors() {
        Boolean first = true;
        for (int i = 0; i < rooms.size(); i++) {
            Room roomA = rooms.get(i);
            Room roomB;
            if (first) {
                roomB = findClosestRoom(roomA);
                first = false;
            } else {
                int index = MathUtils.random(0, rooms.size() - 1);
                roomB = rooms.get(index);
                first = true;
            }


            if (roomB == null) {
                continue;
            }
            int pointAX = MathUtils.random(roomA.x + 1, roomA.x + roomA.width - 1);
            int pointAY = MathUtils.random(roomA.y + 1, roomA.y + roomA.height - 1);

            int pointBX = MathUtils.random(roomB.x + 1, roomB.x + roomB.width - 1);
            int pointBY = MathUtils.random(roomB.y + 1, roomB.y + roomB.height - 1);

            while ((pointBX != pointAX) || (pointBY != pointAY)) {
                if (pointBX != pointAX) {
                    if (pointBX > pointAX) pointBX--;
                    else pointBX++;
                } else {
                    if (pointBY > pointAY) pointBY--;
                    else pointBY++;
                }

                level[pointBX][pointBY] = Tiles.CORRIDOR;
            }
        }

    }

    private void moveRoomsCloser() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < rooms.size(); j++) {
                Room room = rooms.get(j);
                rooms.remove(room);
                while (true) {
                    int oldX = room.x;
                    int oldY = room.y;

                    if (room.x > 1) room.x--;
                    if (room.y > 1) room.y--;
                    if ((room.x == 1) && (room.y == 1)) {
                        break;
                    }

                    if (doesRoomCollide(room)) {
                        room.x = oldX;
                        room.y = oldY;
                        break;
                    }
                }
                rooms.add(room);

            }
        }

    }

    private void generateRooms() {
        rooms = new ArrayList<Room>();
        for (int i = 0; i < roomCount; i++) {

            int width = MathUtils.random(minSize, maxSize);
            int height = MathUtils.random(minSize, maxSize);
            int x = MathUtils.random(1, levelWidth - width - 1);
            int y = MathUtils.random(1, levelHeight - height - 1);

            Room r = new Room(x, y, width, height);
            if (doesRoomCollide(r)) {
                i--;
            } else {
                //to make sure not 2 rooms are directly next to each other
                r.width--;
                r.height--;

                rooms.add(r);

            }

        }

    }

    private Room findClosestRoom(Room room) {
        int midX = room.x + (room.width / 2);
        int midY = room.y * (room.height / 2);

        Room closest = null;
        int closestDistance = Integer.MAX_VALUE;
        for (Room roomToCheck : rooms) {
            if (room.equals(roomToCheck)) continue;
            int roomToCheckMidX = roomToCheck.x + (roomToCheck.width / 2);
            int roomToCheckMidY = roomToCheck.y + (roomToCheck.height / 2);
            int distance = Math.abs(midX - roomToCheckMidX) + Math.abs(midY - roomToCheckMidY);
            if (distance < closestDistance) {
                closest = roomToCheck;
                closestDistance = distance;
            }
        }
        if (closest == null) {
            System.out.println("ROOM WAS NULL");
        }
        return closest;
    }

    private boolean doesRoomCollide(Room newRoom) {
        for (Room roomToCheck : rooms) {
            if (!(
                    (newRoom.x + newRoom.width < roomToCheck.x) ||
                            (newRoom.x > roomToCheck.x + roomToCheck.width) ||
                            (newRoom.y + newRoom.height < roomToCheck.y) ||
                            (newRoom.y > roomToCheck.y + roomToCheck.height)
            )) {
                return true;
            }

        }
        return false;
    }

    void initEmptyLevel() {

        level = new Tiles[levelWidth][levelHeight];

        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                level[x][y] = Tiles.EMPTY;
            }
        }

    }

    Entity[][] createEntityLevel() {
        Entity[][] entityLevel = new Entity[levelWidth][levelHeight];

        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                Entity e;
                switch (level[x][y]) {
                    case WALL:
                        e = EntityFactory.createWallTile(world, x, y);
                        break;
                    case ROOM:
                        e = EntityFactory.createRoomFloorTile(world, x, y);
                        break;
                    case CORRIDOR:
                        e = EntityFactory.createCorridorFloorTile(world, x, y);
                        break;
                    case EMPTY:
                        e = EntityFactory.createEmptyTile(world, x, y);
                        break;
                    default:
                        e = EntityFactory.createEmptyTile(world, x, y);
                }
                entityLevel[x][y] = e;
                e.addToWorld();
            }
        }

        registerEnemiesWithTiles(entityLevel);

        return entityLevel;

    }

    private void registerEnemiesWithTiles(Entity[][] entityLevel) {
        ComponentMapper<Position> positionComponentMapper = world.getMapper(Position.class);
        AISystem aIS = world.getSystem(AISystem.class);
        ImmutableBag bag = aIS.getActives();
        for(int i = 0; i < bag.size(); i++){
            Entity enemy = (Entity)bag.get(i);
            Position pos = positionComponentMapper.get(enemy);
            Entity entity = entityLevel[pos.getX()][pos.getY()];
            Tile tile = entity.getComponent(Tile.class);
            tile.setActor(enemy);



        }


    }


    enum Tiles {
        EMPTY, WALL, ROOM, CORRIDOR
    }

    private class Room {
        int x;
        int y;
        int width;
        int height;

        public Room(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

    }
}
