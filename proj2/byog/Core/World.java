package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class World {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    //an arraylist that contain rooms
    private List<Room> existingRoom = new ArrayList<>();

    //an arraylist that contain hallways
    private List<Room> existingHallway = new ArrayList<>();

    //this record the player's position
    private Position playerPos;

    //this record the door's position
    private Position doorPos;

    public Position getPlayerPos() {
        return playerPos;
    }

    public void setPlayPos(Position p) {
        playerPos = p;
    }

    public Position getDoorPos() {
        return doorPos;
    }

    public void setDoorPos(Position p) {
        doorPos = p;
    }

    //add one new room to the world, if overlapped & out of bound, add new room until meet condition
    public void addRoom(TETile[][] world, Random random) {
        Room r = new Room(random);
        while (!r.checkBoundOverlap(existingRoom)) {
            r = new Room(random);
        }
        existingRoom.add(r);
        r.plotFloorWall(world);
    }

    /*
    using the hallwayHelper method from the Room class, I get an ArrayList of Hallway (Room)
    then I loop through the ArrayList to plot each Hallway (Room)
     */
    public void addHallway(TETile[][] world, Room r1, Room r2, Random random) {
        existingHallway = Room.hallwayHelper(r1, r2, random);
        for (Room item : existingHallway) {
            item.plotFloorWall(world);
        }
    }

    //sort the list of room according to x coordinates
    public void sortInitialX() {
        Collections.sort(existingRoom);
    }

    //randomly add a door to the world
    public void addDoor(TETile[][] world, Random random) {
        Position temp = new Position(RandomUtils.uniform(random, 1, WIDTH - 1),
                RandomUtils.uniform(random, 1, HEIGHT - 1));
        while (!temp.checkDoor(world)) {
            temp = new Position(RandomUtils.uniform(random, 1, WIDTH - 1),
                    RandomUtils.uniform(random, 1, HEIGHT - 1));
        }
        doorPos = new Position(temp.getXpos(), temp.getYpos());
        world[temp.getXpos()][temp.getYpos()] = Tileset.LOCKED_DOOR;
    }

    //randomly add a player to the world
    public void addPlayer(TETile[][] world, Random random) {
        Position temp = new Position(RandomUtils.uniform(random, 1, WIDTH - 1),
                RandomUtils.uniform(random, 1, HEIGHT - 1));
        while (world[temp.getXpos()][temp.getYpos()].character() != '·') {
            temp = new Position(RandomUtils.uniform(random, 1, WIDTH - 1),
                    RandomUtils.uniform(random, 1, HEIGHT - 1));
        }
        playerPos = new Position(temp.getXpos(), temp.getYpos());
        world[temp.getXpos()][temp.getYpos()] = Tileset.PLAYER;
    }

    //establish a new game
    public TETile[][] newGame(long seed) {
        Random random = new Random(seed);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int x = RandomUtils.uniform(random, 20, 25);
        for (int i = 0; i < x; i++) {
            addRoom(world, random);
        }
        sortInitialX();
        for (int i = 0; i < existingRoom.size() - 1; i++) {
            addHallway(world, existingRoom.get(i), existingRoom.get(i + 1), random);
        }
        addDoor(world, random);
        addPlayer(world, random);
        return world;
    }

    //move one step according to the given character, and update the player position
    public TETile[][] oneStep(TETile[][] world, char ch) {
        Position newPos = playerPos.move(ch);
        if (world[newPos.getXpos()][newPos.getYpos()].character() == '#') {
            return world;
        } else {
            world[newPos.getXpos()][newPos.getYpos()] = Tileset.PLAYER;
            world[getPlayerPos().getXpos()][getPlayerPos().getYpos()] = Tileset.FLOOR;
            setPlayPos(newPos);
            return world;
        }
    }
}
