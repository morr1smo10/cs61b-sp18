package byog.Core;

import java.util.Random;
//import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.List;

public class Room implements Comparable<Room> {
    private int width;
    private int height;

    private Position pos;

    //get the width of the room
    public int getWidth() {
        return width;
    }

    //get the height of the room
    public int getHeight() {
        return height;
    }

    //get the starting Position of the room
    public Position getPos() {
        return pos;
    }

    //get the initial x coordinate
    public int initialX() {
        return getPos().getXpos();
    }

    //get the initial y coordinate
    public int initialY() {
        return getPos().getYpos();
    }

    //get the final x coordinate
    public int finalX() {
        return initialX() + getWidth() - 1;
    }

    //get the final y coordinate
    public int finalY() {
        return initialY() + getHeight() - 1;
    }

    //create random rooms
    public Room(Random random) {
        width = RandomUtils.uniform(random, 3, 11);
        height = RandomUtils.uniform(random, 3, 11);
        pos = new Position(random);
    }

    //the usual constructor of Room
    public Room(int x, int y, Position p) {
        width = x;
        height = y;
        pos = p;
    }

    /*
    this return an ArrayList of Room that contains either one room or two rooms
    if two random positions have the same x coordinate, only create one vertical room
    if two random positions have the same y coordinate, only create one horizontal room
    if two random positions have different x & y coordinate, then first generate a "common"
    position, then create one vertical room and one horizontal room, this is the L shape
     */
    public static ArrayList<Room> hallwayHelper(Room r1, Room r2, Random random) {
        ArrayList<Room> existingHallway = new ArrayList<>();
        Position rand1 = r1.randomPos(random);
        Position rand2 = r2.randomPos(random);
        if (rand1.getXpos() == rand2.getXpos()) {
            Room hallway = verticalRoom(rand1, rand2);
            existingHallway.add(hallway);
        } else if (rand1.getYpos() == rand2.getYpos()) {
            Room hallway = horizontalRoom(rand1, rand2);
            existingHallway.add(hallway);
        } else {
            int choice = RandomUtils.uniform(random, 0, 2);
            if (choice == 0) {
                Position newPos = Position.option1(rand1, rand2);
                Room hallway1 = verticalRoom(rand1, newPos);
                Room hallway2 = horizontalRoom(rand2, newPos);
                existingHallway.add(hallway1);
                existingHallway.add(hallway2);
            } else {
                Position newPos = Position.option2(rand1, rand2);
                Room hallway1 = horizontalRoom(rand1, newPos);
                Room hallway2 = verticalRoom(rand2, newPos);
                existingHallway.add(hallway1);
                existingHallway.add(hallway2);
            }
        }
        return existingHallway;
    }

    //given two Position with the same x position, general a vertical hallway
    public static Room verticalRoom(Position a, Position b) {
        Position temp = Position.compareVertical(a, b);
        int tempheight = Math.abs(a.getYpos() - b.getYpos()) + 3;
        Room hallway = new Room(3, tempheight, temp);
        return hallway;
    }

    //given two Position with the same y position, general a horizontal hallway
    public static Room horizontalRoom(Position a, Position b) {
        Position temp = Position.compareHorizontal(a, b);
        int tempwidth = Math.abs(a.getXpos() - b.getXpos()) + 3;
        Room hallway = new Room(tempwidth, 3, temp);
        return hallway;
    }

    //check whether it's out of boundary
    public boolean checkBound() {
        return finalX() < Game.WIDTH && finalY() < Game.HEIGHT;
    }

    //check whether it's overlapped
    public boolean checkOverlap(List<Room> list) {
        if (list.isEmpty()) {
            return true;
        }
        for (Room item : list) {
            if (item.initialX() < finalX() && initialX() < item.finalX()
                    && item.initialY() < finalY() && initialY() < item.finalY()) {
                return false;
            }
        }
        return true;
    }

    //check if it's out of bound or overlapped
    public boolean checkBoundOverlap(List<Room> list) {
        return checkBound() && checkOverlap(list);
    }

    @Override
    public int compareTo(Room anotherRoom) {
        return initialX() - anotherRoom.initialX();
    }

    //Generate a random position inside the room
    public Position randomPos(Random random) {
        int x = RandomUtils.uniform(random, initialX() + 1, finalX());
        int y = RandomUtils.uniform(random, initialY() + 1, finalY());
        return new Position(x, y);
    }

    //plot the floor
    public void plotFloor(TETile[][] world) {
        for (int x = initialX() + 1; x < finalX(); x += 1) {
            for (int y = initialY() + 1; y < finalY(); y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    //plot the wall, if it's floor, then don't plot
    public void plotWall(TETile[][] world) {
        for (int x = initialX(); x <= finalX(); x += 1) {
            if (world[x][initialY()].character() != '·') {
                world[x][initialY()] = Tileset.WALL;
            }
        }

        for (int x = initialX(); x <= finalX(); x += 1) {
            if (world[x][finalY()].character() != '·') {
                world[x][finalY()] = Tileset.WALL;
            }
        }

        for (int y = initialY(); y <= finalY(); y += 1) {
            if (world[initialX()][y].character() != '·') {
                world[initialX()][y] = Tileset.WALL;
            }
        }

        for (int y = initialY(); y <= finalY(); y += 1) {
            if (world[finalX()][y].character() != '·') {
                world[finalX()][y] = Tileset.WALL;
            }
        }
    }

    //plot the floor and wall
    public void plotFloorWall(TETile[][] world) {
        plotWall(world);
        plotFloor(world);
    }
}
