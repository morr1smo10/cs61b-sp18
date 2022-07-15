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

    private List<Room> existingRoom = new ArrayList<>();

    private List<Room> existingHallway = new ArrayList<>();

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
        world[temp.getXpos()][temp.getYpos()] = Tileset.PLAYER;
    }

    public static TETile[][] newGame(TERenderer ter, long seed) {
        Random random = new Random(seed);
        //ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        World w = new World();
        int x = RandomUtils.uniform(random, 20, 25);
        for (int i = 0; i < x; i++) {
            w.addRoom(world, random);
        }
        w.sortInitialX();
        for (int i = 0; i < w.existingRoom.size() - 1; i++) {
            w.addHallway(world, w.existingRoom.get(i), w.existingRoom.get(i + 1), random);
        }
        w.addDoor(world, random);
        w.addPlayer(world, random);
        //ter.renderFrame(world);
        return world;
    }

/*
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Random random = new Random();

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        World w = new World();

        int x = RandomUtils.uniform(random, 20, 25);

        for (int i = 0; i < x; i++){
            w.addRoom(world);
        }

        w.sortInitialX();

        for (int i = 0; i < w.existingRoom.size() - 1; i++){
            w.addHallway(world,w.existingRoom.get(i), w.existingRoom.get(i+1));
        }

        w.addDoor(world);

        w.addPlayer(world);

        ter.renderFrame(world);
    }

 */

}
