package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

//import java.awt.*;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static class Position {
        private int xpos;
        private int ypos;

        Position(int x, int y) {
            this.xpos = x;
            this.ypos = y;
        }
    }

    public int columnNum(int x, int row) {
        if (row < x) {
            return x + 2 * row;
        } else {
            return x + 2 * (x * 2 - 1 - row);
        }
    }

    @Test
    public void testColumn() {
        assertEquals(2, columnNum(2, 0));
        assertEquals(4, columnNum(2, 1));
        assertEquals(4, columnNum(2, 2));
        assertEquals(2, columnNum(2, 3));
        assertEquals(3, columnNum(3, 0));
        assertEquals(5, columnNum(3, 1));
        assertEquals(7, columnNum(3, 2));
        assertEquals(7, columnNum(3, 3));
        assertEquals(5, columnNum(3, 4));
        assertEquals(3, columnNum(3, 5));
    }

    public int spaceNum(int x, int row) {
        if (row < x) {
            return x - (row + 1);
        } else {
            return row - x;
        }
    }

    @Test
    public void testSpace() {
        assertEquals(1, spaceNum(2, 0));
        assertEquals(0, spaceNum(2, 1));
        assertEquals(0, spaceNum(2, 2));
        assertEquals(1, spaceNum(2, 3));
        assertEquals(2, spaceNum(3, 0));
        assertEquals(1, spaceNum(3, 1));
        assertEquals(0, spaceNum(3, 2));
        assertEquals(0, spaceNum(3, 3));
        assertEquals(1, spaceNum(3, 4));
        assertEquals(2, spaceNum(3, 5));
    }

    public void addRow(TETile[][] world, Position p, int column) {
        for (int i = 0; i < column; i++) {
            int xc = p.xpos + i;
            int yc = p.ypos;
            world[xc][yc] = Tileset.WALL;
        }
    }

    public void addHexagon(TETile[][] world, Position p, int x) {
        if (x < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int i = 0; i < x * 2; i++) {
            int rowNum = p.ypos + i;
            int columnNum = p.xpos + spaceNum(x, i);
            Position startpoint = new HexWorld.Position(columnNum, rowNum);
            addRow(world, startpoint, columnNum(x, i));
        }
        updatePos(p, x);
    }

    public void updatePos(Position p, int x) {
        p.xpos = p.xpos + 3 * x - 2 - (x - 1);
        p.ypos = p.ypos + x;
    }

    public static void main(String args) {
        HexWorld temp = new HexWorld();
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Position p = new Position(WIDTH / 2, HEIGHT / 2);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        temp.addHexagon(world, p, 4);

        //temp.addHexagon(world, p, 4);

        ter.renderFrame(world);
    }
}
