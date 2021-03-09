package ghost;

import org.json.simple.parser.ParseException;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * main controller of the game, control the logic of the game.
 */
public class GameParser {
    private String[] maps = new String[36];
    private int remain = 0;
    private long lives = 3;
    private long gameSpeed;
    private long frightenedLength;
    private long sodaLength;
    private boolean frightenedModeOn = false;
    private boolean sodaCanModeOn = false;
    private long[] ghostMode;
    private int ghostModeLength;
    /** Count the current frame of the game. {@value}*/
    public int frame = 0;
    private boolean haveChaser = false;
    private boolean haveWhim = false;

    private PImage horizontal;
    private PImage downLeft;
    private PImage downRight;
    private PImage upLeft;
    private PImage upRight;
    private PImage vertical;
    private PImage fruit;
    private PImage wakaState_Close;
    private PImage wakaState_Down;
    private PImage wakaState_Left;
    private PImage wakaState_Right;
    private PImage wakaState_Up;
    private PImage ambusherImage;
    private PImage chaserImage;
    private PImage ignorantImage;
    private PImage whimImage;
    private PImage superFruit;
    private PImage frightenedImage;
    private PImage sodaCan;
    private PImage youWin;
    private PImage gameOver;

    private PFont char_Font;

    private Message playerPrePosition;

    private Player player;
    private Message playerStartPoint;

    private ArrayList<Ghosts> ghosts;
    private ArrayList<Message> ghostsStartPoint;

    private int currentState = 0;
    private int stateStartTime = 0;

    /** If this variables is true, draw the line. {@value}*/
    public boolean debugMode = false;
    private int frightenedModeStartTime = 0;
    private int sodaCanModeStartTime = 0;


    Message chaserPosition = null;

    private App gameWindow;

    /**
     * set a game's screen for this game parser
     */
    public GameParser(App gameWindow){
        this.gameWindow = gameWindow;
    }

    /**
     * reading the files of images.
     */
    public void resourcesBinding(){
        System.out.println(this.getClass().getClassLoader().getResource("horizontal.png").getPath());
        horizontal = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("horizontal.png")).getPath());
        downLeft = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("downLeft.png")).getPath());
        downRight = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("downRight.png")).getPath());
        upLeft = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("upLeft.png")).getPath());
        upRight = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("upRight.png")).getPath());
        vertical = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("vertical.png")).getPath());
        fruit = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("fruit.png")).getPath());
        wakaState_Close = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("playerClosed.png")).getPath());
        wakaState_Down = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("playerDown.png")).getPath());
        wakaState_Left = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("playerLeft.png")).getPath());
        wakaState_Right = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("playerRight.png")).getPath());
        wakaState_Up = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("playerUp.png")).getPath());
        ambusherImage = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("ambusher.png")).getPath());
        chaserImage = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("chaser.png")).getPath());
        ignorantImage = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("ignorant.png")).getPath());
        whimImage = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("whim.png")).getPath());
        frightenedImage = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("frightened.png")).getPath());
        superFruit = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("superFruit.png")).getPath());
        sodaCan = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("sodacan.png")).getPath());
    }

    /**
     * reading the files of fonts.
     * */
    public void fontBinding(){
        gameOver = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("over.png")).getPath());
        youWin = gameWindow.loadImage(Objects.requireNonNull(this.getClass().getClassLoader().getResource("win.png")).getPath());
    }

    /**
     * For Junit test, adding ghost to ghost arraylist.
     * @param new_Ghost the starting position of the ghost
     */
    public void addGhostStartPoint(Message new_Ghost){
        ghostsStartPoint.add(new_Ghost);
    }

    /**
     * Called at the start of each time that game starting, reset the ghost mode, reset the frame, adding all ghost again.
     */
    public void gameInit(){
        debugMode = false;
        frightenedModeOn = false;
        sodaCanModeOn = false;
        frame = 0;
        gameWindow.keyCode = gameWindow.RIGHT;
        currentState = 0;
        stateStartTime = 0;

        if(playerStartPoint == null) {
            System.out.println("Map error: player no found");
        }
        player = new Player(gameWindow, playerStartPoint.X, playerStartPoint.Y, gameSpeed);
        for(Message eachGhost : ghostsStartPoint){
            if(eachGhost.ghostTypes == Ghosts.AMBUSHER){
                System.out.println("add ambusher");
                ghosts.add(new Ambusher(gameWindow, eachGhost.X, eachGhost.Y,gameSpeed));
            } else if (eachGhost.ghostTypes == Ghosts.NORMAL){
                ghosts.add(new Chaser(gameWindow, eachGhost.X, eachGhost.Y,gameSpeed));
            } else if (eachGhost.ghostTypes == Ghosts.CHASER){
                ghosts.add(new Chaser(gameWindow, eachGhost.X, eachGhost.Y,gameSpeed));
            } else if (eachGhost.ghostTypes == Ghosts.IGNORANT){
                ghosts.add(new Ignorant(gameWindow, eachGhost.X, eachGhost.Y,gameSpeed));
            } else if (eachGhost.ghostTypes == Ghosts.WHIM){
                ghosts.add(new Whim(gameWindow, eachGhost.X, eachGhost.Y,gameSpeed));
            }
        }
    }

    /*
    backup:
    if(allRowZero && gameWindow.gameMaps[i][j] != 0){
                        allRowZero = false;
                        colStart = j;
                    }
     */

    /**
     * reading the map file and read the map, render the map using the integer which represent different object. if it is the first time to read the file, then calculate the edge value.
     * @param firstTime if it is true it means it is the first time to render this map
     */
    public void mapRender(boolean firstTime){
        boolean firstLineBorder = true;
        int rowStart = 0;
        int rowEnd = 0;
        int colStart = 0;
        int colEnd = 0;
        for(int i = 0; i < 36; i++){
            boolean allRowZero = true;
            for(int j = 0; j < 28; j++){

                if(firstTime && maps[i].charAt(j) == 'p'){
                    System.out.println("is player");
                    gameWindow.gameMaps[i][j] = 0;
                    playerStartPoint = new Message(j, i, PApplet.RIGHT, null);
                } else if (firstTime && maps[i].charAt(j) == 'g'){
                    //ghost location
                    System.out.println("is ghost");
                    gameWindow.gameMaps[i][j] = 0;
                    Message newMsg = new Message(j, i, 0, null);
                    newMsg.ghostTypes = Ghosts.NORMAL;
                    ghostsStartPoint.add(newMsg);
                } else if(firstTime && maps[i].charAt(j) == 'a'){
                    //ghost location
                    System.out.println("is ambusher");
                    gameWindow.gameMaps[i][j] = 0;
                    Message newMsg = new Message(j, i, 0, null);
                    newMsg.ghostTypes = Ghosts.AMBUSHER;
                    ghostsStartPoint.add(newMsg);
                } else if(firstTime && maps[i].charAt(j) == 'c'){
                    //ghost location
                    System.out.println("is chaser");
                    gameWindow.gameMaps[i][j] = 0;
                    Message newMsg = new Message(j, i, 0, null);
                    newMsg.ghostTypes = Ghosts.CHASER;
                    haveChaser = true;
                    ghostsStartPoint.add(0, newMsg);
                } else if(firstTime && maps[i].charAt(j) == 'i'){
                    //ghost location
                    System.out.println("is ignorant");
                    gameWindow.gameMaps[i][j] = 0;
                    Message newMsg = new Message(j, i, 0, null);
                    newMsg.ghostTypes = Ghosts.IGNORANT;
                    ghostsStartPoint.add(newMsg);
                } else if(firstTime && maps[i].charAt(j) == 'w'){
                    //ghost location
                    System.out.println("is whim");
                    gameWindow.gameMaps[i][j] = 0;
                    Message newMsg = new Message(j, i, 0, null);
                    newMsg.ghostTypes = Ghosts.WHIM;
                    haveWhim = true;
                    ghostsStartPoint.add(newMsg);
                } else {
                    if(firstTime) {
                        gameWindow.gameMaps[i][j] = Integer.parseInt(String.valueOf(maps[i].charAt(j)));
                    }
                    if(allRowZero && gameWindow.gameMaps[i][j] != 0){
                        if(firstLineBorder)
                            rowStart = i;
                        allRowZero = false;
                        colStart = j;
                    }
                    if(firstLineBorder && !allRowZero && gameWindow.gameMaps[i][j] != 0){
                        colEnd = j;
                    }
                }
            }
            if(!allRowZero){
                if(firstLineBorder){
                    firstLineBorder = false;
                }
                rowEnd = i;
            }
        }
        if(!haveChaser && haveWhim){
            System.out.println("There is a whim but no chaser in the game");
            gameWindow.exit();
        }
        gameWindow.mapLeft = colStart;
        gameWindow.mapRight = colEnd;
        gameWindow.mapUp = rowStart;
        gameWindow.mapDown = rowEnd;
    }

    /**
     * Render each grid, if it is the first time to render the map, counting the number of fruit and superfruit
     * @param x x-axis of the position
     * @param y y-axis of the position
     * @param firstTime if it is true it means it is the first time to render this map
     */
    public void mapElementRenderSwitch(int x, int y, boolean firstTime){
        switch (gameWindow.gameMaps[y][x]){
            case 0:{
                case0(x * 16, y * 16);
                break;
            }
            case 1:{
                case1(x * 16, y * 16);
                break;
            }
            case 2:{
                case2(x * 16, y * 16);
                break;
            }
            case 3:{
                case3(x * 16, y * 16);
                break;
            }
            case 4:{
                case4(x * 16, y * 16);
                break;
            }
            case 5:{
                case5(x * 16, y * 16);
                break;
            }
            case 6:{
                case6(x * 16, y * 16);
                break;
            }
            case 7:{
                if(firstTime)
                    remain++;
                case7(x * 16, y * 16);
                break;
            }
            case 8:{
                if(firstTime)
                    remain++;
                case8(x * 16, y * 16);
                break;
            }
            case 9:{
                case9(x * 16, y * 16);
                break;
            }
        }
    }

    private void case0(int x, int y){
        gameWindow.fill(0, 0, 0);
        gameWindow.stroke(0, 0, 0);
        gameWindow.rect(x, y, 16, 16);
    }

    private void case1(int x, int y){
        gameWindow.image(horizontal, x, y);
    }

    private void case2(int x, int y){
        gameWindow.image(vertical, x, y);
    }

    private void case3(int x, int y){
        gameWindow.image(upLeft, x, y);
    }

    private void case4(int x, int y){
        gameWindow.image(upRight, x, y);
    }

    private void case5(int x, int y){
        gameWindow.image(downLeft, x, y);
    }

    private void case6(int x, int y){
        gameWindow.image(downRight, x, y);
    }

    private void case7(int x, int y){
        gameWindow.image(fruit, x, y);
    }

    private void case8(int x, int y){ gameWindow.image(superFruit, x, y);}

    private void case9(int x, int y){ gameWindow.image(sodaCan, x, y);}

    private void renderLives(int x, int y){gameWindow.image(wakaState_Right, x, y);}

    /**
     * If player is at the same position of fruit, superfruit or soda can, eat this item, if it is fruit, reduce the remain number, if it is a superfruit, reduce the remain number, turn player  into invincible and ghost become frightened, if it is a soda can, the ghost will become invisible.
     * @param position position of player
     */
    public void playerEatSomething(Message position){
        int currentMatrixX = position.X / 16;
        int currentMatrixY = position.Y / 16;
        if(position.X % 16 == 0 && position.Y % 16 == 0 && gameWindow.gameMaps[currentMatrixY][currentMatrixX] == 7) {
            gameWindow.gameMaps[currentMatrixY][currentMatrixX] = 0;
            remain--;
            System.out.println(remain);
        }
        if(position.X % 16 == 0 && position.Y % 16 == 0 && gameWindow.gameMaps[currentMatrixY][currentMatrixX] == 8) {
            gameWindow.gameMaps[currentMatrixY][currentMatrixX] = 0;
            frightenedModeOn = true;
            frightenedModeStartTime = frame / 60;
            System.out.println("frightened mode start at "+ frightenedModeStartTime);
            remain--;
            System.out.println(remain);
        }
        if(position.X % 16 == 0 && position.Y % 16 == 0 && gameWindow.gameMaps[currentMatrixY][currentMatrixX] == 9) { // eat soda can
            gameWindow.gameMaps[currentMatrixY][currentMatrixX] = 0;
            sodaCanModeOn = true;
            sodaCanModeStartTime = frame / 60;
            System.out.println("soda mode start at "+ sodaCanModeStartTime);
        }
    }

    /**
     * Render the image of player according to player's current direction
     * @param position position of player
     */
    public void playerRender(Message position){
        if(frame % 16 < 8){
            gameWindow.image(wakaState_Close, position.X - 4, position.Y - 5);
        } else {
            switch (position.direction){
                case PApplet.RIGHT:{
                    gameWindow.image(wakaState_Right, position.X - 4, position.Y - 5);
                    break;
                }
                case PApplet.LEFT:{
                    gameWindow.image(wakaState_Left, position.X - 4, position.Y - 5);
                    break;
                }
                case PApplet.DOWN:{
                    gameWindow.image(wakaState_Down, position.X - 5, position.Y - 4);
                    break;
                }
                case PApplet.UP:{
                    gameWindow.image(wakaState_Up, position.X - 5, position.Y - 4);
                    break;
                }
            }
        }
        playerEatSomething(position);
    }

    private void ghostRender(Message position, int ghost_Type) {
        if (frightenedModeOn) {
            gameWindow.image(frightenedImage, position.X - 6, position.Y - 6);
        } else {
            if (ghost_Type == Ghosts.AMBUSHER) {
                gameWindow.image(ambusherImage, position.X - 6, position.Y - 6);
            } else if (ghost_Type == Ghosts.CHASER) {
                gameWindow.image(chaserImage, position.X - 6, position.Y - 6);
            } else if (ghost_Type == Ghosts.IGNORANT) {
                gameWindow.image(ignorantImage, position.X - 6, position.Y - 6);
            } else if (ghost_Type == Ghosts.WHIM) {
                gameWindow.image(whimImage, position.X - 6, position.Y - 6);
            }
        }
    }

    /**
     * Clean the image and redraw it
     */
    public void clearLastFrame(){
        gameWindow.background(0, 0, 0);
        mapRender(false);
        for(int i = 0; i < 36; i++) {
            for (int j = 0; j < 28; j++) {
                mapElementRenderSwitch(j, i, false);
            }
        }
        for(int i = 0; i < lives; i++){
            renderLives(i * 30, 34 * 16);
        }
    }

    /**
     * For Junit test, turn on or off the frightened mode
     * @param onOrOff if it is true it means turn on the frightened mode
     */
    public void setFrightenedModeOn(boolean onOrOff){
        frightenedModeOn = onOrOff;
    }

    /**
     * Update the counter of ghost mode, change the mode to normal if it's over
     */
    public void updateGhostMode(){
        if(frame % 60 == 0){
            if(frightenedModeOn){
                if(frame / 60  > frightenedModeStartTime + frightenedLength){
                    frightenedModeOn = false;
                    stateStartTime += frightenedLength;
                }
            }
            if(sodaCanModeOn){
                if(frame / 60  > sodaCanModeStartTime + sodaLength){
                    sodaCanModeOn = false;
                    stateStartTime += sodaLength;
                }
            }
            if(frame / 60 > stateStartTime + ghostMode[currentState]){
                currentState++;
                currentState = currentState % ghostModeLength;
                stateStartTime = frame / 60;
                System.out.println("Mode change!");
            }
        }
    }

    /**
     * Draw the debug line between ghost and it's target
     * @param ghostMessage a message contain the position of the ghost and the position of ghost's current target
     */
    public void drawDebugLine(Message ghostMessage){
        gameWindow.stroke(255, 255, 255);
        gameWindow.line(ghostMessage.X + 8, ghostMessage.Y + 8, ghostMessage.target.X + 8, ghostMessage.target.Y + 8);
    }

    /**
     * reload the ghosts, refresh the map, replace the props and reset the basic properties (lives, speed, length of the mode) by reading the file.
     */
    public void refreshData(){
        ghostsStartPoint = new ArrayList<>();
        String mapPath = null;
        ghosts = new ArrayList<>();
        remain = 0;
        frame = 0;

        try {
            ConfigReader configReader = new ConfigReader();
            gameSpeed = configReader.getSpeed();
            ghostMode = configReader.getGhostMode();
            lives = configReader.getLives();
            frightenedLength = configReader.getFrightenedLength();
            sodaLength = configReader.getSodaLength();
            mapPath = configReader.getMapFile();
            ghostModeLength = 0;
            for(long time: ghostMode){
                ghostModeLength += time;
            }
        } catch (IOException | ParseException e){
            e.printStackTrace();
            System.exit(0);
        }

        File map = new File(mapPath);
        try {
            Scanner scanner = new Scanner(map);
            for(int i = 0; i < 36; i++){
                maps[i] = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run the first frame of the game, refresh the data and draw the map.
     */
    public void runStartFrame(){
        System.out.println("run new game!");
        gameWindow.background(0,0,0);
        refreshData();
        System.out.println(lives);
        drawStartMap();
    }

    /**
     * read every grids and draw they out, initialize the position of player.
     */
    public void drawStartMap(){
        gameWindow.frameRate(60);
        mapRender(true);
        for(int i = 0; i < 36; i++) {
            for (int j = 0; j < 28; j++) {
                mapElementRenderSwitch(j, i, true);
            }
        }
        gameInit();
        playerPrePosition = new Message(playerStartPoint.X, playerStartPoint.Y, 0, null);
    }

    /**
     * See if player is caught by a ghost
     * @param ghostPosition the position of the ghost
     * @param playerPosition the position of the player
     * @return true when one is run into another
     */
    public boolean catchTarget(Message ghostPosition, Message playerPosition){
        if((ghostPosition.X <= playerPosition.X && playerPosition.X < ghostPosition.X + 16) && (ghostPosition.Y <= playerPosition.Y && playerPosition.Y < ghostPosition.Y + 16)){
            System.out.println("error 1");
            System.out.println("ghost: X: " + ghostPosition.X + ", Y: "+ghostPosition.Y+", and player: Y: "+playerPosition.X+", Y: "+ playerPosition.Y);
            return true;
        }
        if((playerPosition.X <= ghostPosition.X && ghostPosition.X < playerPosition.X + 16) && (playerPosition.Y <= ghostPosition.Y && ghostPosition.Y < playerPosition.Y + 16)){
            System.out.println("error 2");
            System.out.println("ghost: X: " + ghostPosition.X + ", Y: "+ghostPosition.Y+", and player: Y: "+playerPosition.X+", Y: "+ playerPosition.Y);
            return true;
        }
        return false;
    }

    private int startFrame;
    private boolean startSleep = false;

    /**
     * Count specific seconds
     * @param seconds Seconds need to wait
     * @return If it's already delay the specific seconds, return true
     */
    public boolean sleep(int seconds){
        if(!startSleep){
            startSleep = true;
            startFrame = frame;
            System.out.println("Start counting");
        } else {
            if(frame % 60 == 0){
                System.out.println(frame / 60 + "s");
            }
            if(frame > startFrame + seconds * 60){
                System.out.println("over counting!");
                startSleep = false;
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Start a new game
     */
    public void runNewGame(){
        if(sleep(10)){
            System.out.println("it's time to start new game");
            runStartFrame();
        }
    }

    /**
     * Run in each frame and renew the information
     * If there is not remaining fruit or lives, game restart after 10 seconds
     * In each frame, ghost and player run and this system will detect whether they are in the same grids (collision)
     * In debug mode, it will draw a white line
     * In frightened mode, ghost will change the image and if they encounter the player, they will die
     * In soda can mode, ghost will not display but debug line still works
     */
    public void runEachFrame(){
        frame++;
        if(remain == 0){
            fontBinding();
            gameWindow.background(0, 0, 0);
            gameWindow.fill(255, 255, 255);
            gameWindow.image(youWin, 20, 250);
            runNewGame();
        } else if (lives == 0){
            fontBinding();
            gameWindow.background(0, 0, 0);
            gameWindow.fill(255, 255, 255);
            gameWindow.image(gameOver, 20, 250);
            runNewGame();
        } else {
            clearLastFrame();
            updateGhostMode();
            playerPrePosition = player.run();
            for(int i = 0; i < ghosts.size(); i++){
                Message ghostPosition;
                int ghost_Type = ghosts.get(i).getGhost_Type();

                if(ghost_Type == Ghosts.WHIM){ // if ghost is type whim, we have to pass the position of chaser
                    Whim whim = (Whim) ghosts.get(i);
                    whim.passChaserPosition(chaserPosition);
                }

                if(frightenedModeOn){// different behaviour when in frightened mode and normal two modes
                    ghostPosition = ghosts.get(i).escape(frame);
                } else {
                    if (currentState % 2 == 0){
                        ghostPosition = ghosts.get(i).run(Ghosts.SCATTERMODE, playerPrePosition);
                    } else{
                        ghostPosition = ghosts.get(i).run(Ghosts.CHASEMODE, playerPrePosition);
                    }
                }

                if(i == 0 && ghost_Type == Ghosts.CHASER){
                    chaserPosition = ghostPosition;
                } else if (i == 0){
                    chaserPosition = null;
                }

                if(debugMode) // in debug mode, need to draw line
                    drawDebugLine(ghostPosition);
                if(catchTarget(ghostPosition, playerPrePosition)){
                    if(frightenedModeOn){
                        ghosts.remove(i);
                        i--;
                        continue;

                    } else {
                        lives--;
                        if(lives > 0){
                            gameWindow.background(0, 0, 0);
                            ghosts.clear();
                            mapRender(false);
                            for(int k = 0; k < 36; k++) {
                                for (int j = 0; j < 28; j++) {
                                    mapElementRenderSwitch(j, k, false);
                                }
                            }
                            gameInit();
                        }
                    }
                }
                if(!sodaCanModeOn)
                    ghostRender(ghostPosition, ghost_Type);
                else
                    System.out.println("don't render");
            }


            playerRender(playerPrePosition);
        }
    }
}
