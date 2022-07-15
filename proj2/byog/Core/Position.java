package byog.Core;

import byog.TileEngine.TETile;

import java.util.Random;

public class Position {
    private int xpos;
    private int ypos;

    //get the x coordinate of P
    public int getXpos() {
        return xpos;
    }

    //get the y coordinate of P
    public int getYpos() {
        return ypos;
    }

    //create random positions
    public Position(Random random) {
        xpos = RandomUtils.uniform(random, Game.WIDTH);
        ypos = RandomUtils.uniform(random, Game.HEIGHT);
    }

    //the normal constructor of Postion
    public Position(int x, int y) {
        xpos = x;
        ypos = y;
    }

    //given two Position, return the up-right Position, a=(3,3) b=(1,5) p=(3,5)
    public static Position option1(Position a, Position b) {
        return new Position(a.getXpos(), b.getYpos());
    }

    //given two Position, return the down-left Position, a=(3,3) b=(1,5) p=(1,3)
    public static Position option2(Position a, Position b) {
        return new Position(b.getXpos(), a.getYpos());
    }

    //given two Position with same x coordinate, return new start Position
    public static Position compareVertical(Position a, Position b) {
        if (a.getYpos() < b.getYpos()) {
            return new Position(a.getXpos() - 1, a.getYpos() - 1);
        } else {
            return new Position(b.getXpos() - 1, b.getYpos() - 1);
        }
    }

    //given two Position with same y coordinate, return new start Position
    public static Position compareHorizontal(Position a, Position b) {
        if (a.getXpos() < b.getXpos()) {
            return new Position(a.getXpos() - 1, a.getYpos() - 1);
        } else {
            return new Position(b.getXpos() - 1, b.getYpos() - 1);
        }
    }

    public boolean checkDoor(TETile[][] world) {
        if (world[getXpos()][getYpos()].character() == '#') {
            if (world[getXpos()][getYpos() - 1].character() == '·'
                    && world[getXpos()][getYpos() + 1].character() == ' ') {
                return true;
            }
            if (world[getXpos()][getYpos() - 1].character() == ' '
                    && world[getXpos()][getYpos() + 1].character() == '·') {
                return true;
            }
            if (world[getXpos() - 1][getYpos()].character() == ' '
                    && world[getXpos() + 1][getYpos()].character() == '·') {
                return true;
            }
            if (world[getXpos() - 1][getYpos()].character() == '·'
                    && world[getXpos() + 1][getYpos()].character() == ' ') {
                return true;
            }
        }
        return false;
    }
}
