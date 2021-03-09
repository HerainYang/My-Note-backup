package ghost;
import processing.core.PApplet;
import processing.event.KeyEvent;

/**
 * main class of the game, inherit from PApplet
 * @see PApplet
 */
public class App extends PApplet {
    /** width of the game's screen. {@value} */
    public static final int WIDTH = 448;
    /** height of the game's screen. {@value}*/
    public static final int HEIGHT = 576;
    /** The map of the game, store each grid as a integer. {@value}*/
    public int[][] gameMaps = new int[36][28];
    /** The edge value of the wall of the map. {@value}*/
    public int mapLeft, mapDown, mapRight, mapUp;
    /** The game parser of the game. {@value} */
    public GameParser gameParser;

    /** Create a game parser of the game. */
    public App(){
        gameParser = new GameParser(this);
    }

    /** Setting the width and height of the game's window, called before the game's window is created
     * @see PApplet#settings() */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /** Read the resource files of images and fonts, read the configuration file of the game, initialize the basic game attributes like lives, speed and length of each mode, called at the first frame of the game.
     * @see PApplet#setup() */
    @Override
    public void setup() {
        gameParser.resourcesBinding();
        gameParser.fontBinding();
        gameParser.runStartFrame();
        System.out.println(sketchOutputStream());
    }

    /** Listening to the keyboard and response to every action.
     * @see PApplet#keyPressed(KeyEvent)  */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 0x20){ // 0x20 is space
            if(gameParser.debugMode){
                gameParser.clearLastFrame();
                gameParser.debugMode = false;
            } else {
                gameParser.debugMode = true;
            }
        }
    }

    /** Executed in each frame, update the information of the game and refresh the image, called at every frame.
     * @see PApplet#draw()  */
    @Override
    public void draw() {
        gameParser.runEachFrame();
    }

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{"something"}, new App());
    }
}
