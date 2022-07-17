package byog.Core;

import java.awt.Color;
import java.awt.Font;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        drawMainPage();
        initializeUI();
        char c = getChar();
        if (c == 'n') {
            newGameKeyboard();
        } else if (c == 'l') {
            loadGameKeyboard();
        } else if (c == 'q') {
            System.exit(0);
        }
    }

    //load new game from the saved game
    public void loadGameKeyboard() {
        World newWorld = new World();
        ter.initialize(WIDTH, HEIGHT + 2);
        TETile[][] worldFrame = getSavedGame(newWorld);
        ter.renderFrame(worldFrame);
        playGame(newWorld,worldFrame);
    }

    //start play a new game
    public void newGameKeyboard() {
        enterSeed();
        long seed = getSeed();
        World newWorld = new World();
        ter.initialize(WIDTH, HEIGHT + 2);
        TETile[][] worldFrame = newWorld.newGame(seed);
        ter.renderFrame(worldFrame);
        //strangeMouse(worldFrame);
        playGame(newWorld,worldFrame);
    }

    public void strangeMouse(TETile[][] WorldFrame) {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String hoverMessage = "Nothing";
        if (x < 0 || y < 0 || WIDTH <= x || HEIGHT <= y) {
            hoverMessage = "Nothing";
        } else if (WorldFrame[x][y].character() == '@') {
            hoverMessage = "Player";
        } else if (WorldFrame[x][y].character() == '#') {
            hoverMessage = "Wall";
        } else if (WorldFrame[x][y].character() == '·') {
            hoverMessage = "Floor";
        }
        Font currentFont = StdDraw.getFont();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 10));
        StdDraw.textLeft(10, HEIGHT + 1, hoverMessage);
        StdDraw.setFont(currentFont);
        StdDraw.show();
    }

    //use the keyboard to play the game, win when player reach the locked door, save when type ":Q"
    public TETile[][] playGame(World world, TETile[][] WorldFrame) {
        boolean gameWin = false;
        while(!gameWin) {
            //strangeMouse(WorldFrame);
            char c = getChar();
            if (c == 'w' || c == 'a' || c == 's' || c == 'd' ){
                WorldFrame = world.oneStep(WorldFrame, c);
                ter.renderFrame(WorldFrame);
            }
            if (world.getPlayerPos().equal(world.getDoorPos())) {
                winGame(world, WorldFrame);
            }
            if (c == ':') {
                char newChar = getChar();
                if (newChar == 'q'){
                    saveGame(world, WorldFrame);
                    System.exit(0);
                } else {
                    if (newChar == 'w' || newChar == 'a' || newChar == 's' || newChar == 'd' ){
                        WorldFrame = world.oneStep(WorldFrame, newChar);
                        ter.renderFrame(WorldFrame);
                    }
                    if (world.getPlayerPos().equal(world.getDoorPos())) {
                        winGame(world, WorldFrame);
                    }
                }
            }
        }
        return WorldFrame;
    }

    //this is the page that pop up when you win the game and quit
    public void winGame(World world, TETile[][] WorldFrame) {
        WorldFrame[world.getPlayerPos().getXpos()][world.getPlayerPos().getYpos()] = Tileset.UNLOCKED_DOOR;
        ter.renderFrame(WorldFrame);
        drawMainPage();
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 2, "You win the game!");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Press Q to quit");
        StdDraw.show();
        while (getChar() == 'q') {
            System.exit(0);
        }
    }

    //concatenate the input digits into the seed
    public long getSeed() {
        String input = "";
        char seed = '!';
        while (seed != 's') {
            seed = getChar();
            if (Character.isDigit(seed)) {
                input += String.valueOf(seed);
                StdDraw.clear();
                StdDraw.clear(Color.black);
                initializeUI();
                enterSeed();
                StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
                StdDraw.text(WIDTH / 2, HEIGHT / 2 - 6, input);
                StdDraw.show();
            } else {
                continue;
            }
        }
        return Long.parseLong(input);
    }

    //the message pop up that require player to enter a seed
    public void enterSeed() {
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 4, "Enter Seed");
        StdDraw.show();
    }

    //the method to get char input from the game
    public char getChar() {
        char input;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            input = StdDraw.nextKeyTyped();
            input = Character.toLowerCase(input);
            if (Character.isDigit(input) || Character.isLetter(input) || input == ':') {
                break;
            }
        }
        return input;
    }

    //initialize the initial user interface
    public void initializeUI () {
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);

        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 4, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Quit (Q)");

        StdDraw.show();
    }

    //draw the canvas and set up the basic stuffs
    public void drawMainPage () {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.white);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        World newWorld = new World();
        TETile[][] finalWorldFrame = null;
        input = input.toLowerCase();
        char firstChar = input.charAt(0);
        if (firstChar == 'n' && !input.endsWith(":q")) {
            finalWorldFrame = newGameInputString(newWorld, input);
        } else if (firstChar == 'n' && input.endsWith(":q")) {
            finalWorldFrame = newGameInputString(newWorld, input);
            saveGame(newWorld, finalWorldFrame);
        } else if (firstChar == 'l' && !input.endsWith(":q")) {
            finalWorldFrame = loadGameInputString(newWorld, input);
        } else if (firstChar == 'l' && input.endsWith(":q")) {
            finalWorldFrame = loadGameInputString(newWorld, input);
            saveGame(newWorld, finalWorldFrame);
        }

        return finalWorldFrame;
    }

    //create a new game and play the game (input string version)
    public TETile[][] newGameInputString (World newWorld, String input) {
        long seed = Long.parseLong(input.replaceAll("[^0-9]", ""));
        TETile[][] WorldFrame = newWorld.newGame(seed);
        input = extractMovement(input, seed);
        WorldFrame = playInputString(newWorld, WorldFrame, input);
        return WorldFrame;
    }

    //load a new game and play the game (input string version)
    public TETile[][] loadGameInputString (World newWorld, String input) {
        TETile[][] WorldFrame = getSavedGame(newWorld);
        input = extractMovement(input);
        WorldFrame = playInputString(newWorld, WorldFrame, input);
        return WorldFrame;
    }

    //create a new game from the input string
    public TETile[][] playInputString (World world, TETile[][] WorldFrame, String input) {
        if (input.length() == 0) {
            return WorldFrame;
        } else {
            char[] ch = input.toCharArray();
            for(char step: ch) {
                WorldFrame = world.oneStep(WorldFrame, step);
            }
        }
        return WorldFrame;
    }

    //extract the movement string from the input string (new game version)
    public String extractMovement(String input, long seed) {
        input = input.substring(1);
        if (input.endsWith(":q")) {
            input = input.substring(0, input.length() - 2);
        }
        String temp = Long.toString(seed);
        input = input.substring(temp.length(),input.length());
        input = input.substring(1);
        return input;
    }

    //extract the movement string from the input string (load game version)
    public String extractMovement(String input) {
        input = input.substring(1);
        if (input.endsWith(":q")) {
            input = input.substring(0, input.length() - 2);
        }
        return input;
    }

    //save the game to a txt file, save the world
    private void saveGame(World world, TETile[][] finalWorldFrame) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savefile.txt"));
            out.writeObject(finalWorldFrame);
            out.writeObject(world.getPlayerPos());
            out.writeObject(world.getDoorPos());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get the game from a txt file, restore the world
    private TETile[][] getSavedGame(World world) {
        TETile[][] finalWorldFrame = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("savefile.txt"));
            finalWorldFrame = (TETile[][]) in.readObject();
            world.setPlayPos((Position) in.readObject());
            world.setDoorPos((Position) in.readObject());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return finalWorldFrame;
    }
}
