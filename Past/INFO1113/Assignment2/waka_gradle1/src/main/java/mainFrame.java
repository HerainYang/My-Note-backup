import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class mainFrame extends PApplet{
    public int[][] gameMaps = new int[36][28];
    private final String[] maps = new String[36];

    private int remain = 0;
    private int lives = 3;
    private int gameSpeed;
    private int[] ghostMode;
    private int ghostModeLength;
    private int frame = 0;

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
    private PImage ghostImage;

    private message playerPrePosition;
    private ArrayList<message> ghostPrePosition;

    public int mapLeft;
    public int mapRight;
    public int mapUp;
    public int mapDown;

    private player player;
    private message playerStartPoint;

    private ArrayList<ghost> ghosts;
    private ArrayList<message> ghostsStartPoint;

    private int currentState = 0;
    private int stateStartTime = 0;

    private boolean debugMode = false;

    private void resourcesBonding(){
        horizontal = loadImage("src/main/resources/horizontal.png");
        downLeft = loadImage("src/main/resources/downLeft.png");
        downRight = loadImage("src/main/resources/downRight.png");
        upLeft = loadImage("src/main/resources/upLeft.png");
        upRight = loadImage("src/main/resources/upRight.png");
        vertical = loadImage("src/main/resources/vertical.png");
        fruit = loadImage("src/main/resources/fruit.png");
        wakaState_Close = loadImage("src/main/resources/playerClosed.png");
        wakaState_Down = loadImage("src/main/resources/playerDown.png");
        wakaState_Left = loadImage("src/main/resources/playerLeft.png");
        wakaState_Right = loadImage("src/main/resources/playerRight.png");
        wakaState_Up = loadImage("src/main/resources/playerUp.png");
        ghostImage = loadImage("src/main/resources/ghost.png");
    }

    private void gameInit(){
        debugMode = false;
        frame = 0;
        keyCode = RIGHT;
        currentState = 0;
        stateStartTime = 0;

        if(playerStartPoint == null) {
            System.out.println("Map error: player no found");
        }
        player = new player(this, playerStartPoint.X, playerStartPoint.Y, gameSpeed);
        for(message eachGhost : ghostsStartPoint){
            ghosts.add(new ghost(this, eachGhost.X, eachGhost.Y,gameSpeed));
            ghostPrePosition.add(new message(eachGhost.X, eachGhost.Y, 0, null));
        }
    }

    private void mapRender(boolean firstTime){
        boolean firstLineBorder = true;
        int rowStart = 0;
        int rowEnd = 0;
        int colStart = 0;
        int colEnd = 0;
        for(int i = 0; i < 36; i++){
            boolean allRowZero = true;
            for(int j = 0; j < 28; j++){

                if(firstTime && maps[i].charAt(j) == 'p'){
                    //player location
                    if(allRowZero && gameMaps[i][j] != 0){
                        allRowZero = false;
                        colStart = j;
                    }
                    gameMaps[i][j] = 0;
                    playerStartPoint = new message(j, i, 0, null);
                } else if (firstTime && maps[i].charAt(j) == 'g'){
                    //ghost location
                    if(allRowZero && gameMaps[i][j] != 0){
                        allRowZero = false;
                        colStart = j;
                    }
                    gameMaps[i][j] = 0;
                    ghostsStartPoint.add(new message(j, i, 0, null));
                } else {
                    if(firstTime)
                        gameMaps[i][j] = Integer.parseInt(String.valueOf(maps[i].charAt(j)));
                    if(allRowZero && gameMaps[i][j] != 0){
                        if(firstLineBorder)
                            rowStart = i;
                        allRowZero = false;
                        colStart = j;
                    }
                    if(firstLineBorder && !allRowZero && gameMaps[i][j] != 0){
                        colEnd = j;
                    }
                    mapElementRenderSwitch(j, i);
                }
                System.out.print(gameMaps[i][j]+" ");
            }
            if(!allRowZero){
                if(firstLineBorder){
                    firstLineBorder = false;
                }
                rowEnd = i;
            }
            System.out.println();
        }
        mapLeft = colStart;
        mapRight = colEnd;
        mapUp = rowStart;
        mapDown = rowEnd;
    }

    public void mapElementRenderSwitch(int x, int y){
        switch (gameMaps[y][x]){
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
                remain++;
                case7(x * 16, y * 16);
                break;
            }
        }
    }

    @Override
    public void settings() {
        size(448, 576);
    }

    @Override
    public void setup() {
        background(0,0,0);
        ghostsStartPoint = new ArrayList<>();
        String mapPath;
        ghosts = new ArrayList<>();
        ghostPrePosition = new ArrayList<>();

        try {
            configReader configReader = new configReader();
            gameSpeed = configReader.getSpeed();
            ghostMode = configReader.getGhostMode();
            lives = configReader.getLives();
            mapPath = configReader.getMapFile();
            ghostModeLength = 0;
            for(int time: ghostMode){
                ghostModeLength += time;
            }
        } catch (Exception e){
            System.out.println("config file not found");
            return;
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

        resourcesBonding();
        frameRate(60);
        surface.setTitle("Pacman");

        mapRender(true);
        gameInit();
        playerPrePosition = new message(playerStartPoint.X, playerStartPoint.Y, 0, null);

        for(int i = 0; i < lives; i++){
            case8(i * 32, (gameMaps.length - 1) * 16);
        }

    }

    private void case0(int x, int y){
        fill(0, 0, 0);
        stroke(0, 0, 0);
        rect(x, y, 16, 16);
    }

    private void case1(int x, int y){
        image(horizontal, x, y);
    }

    private void case2(int x, int y){
        image(vertical, x, y);
    }

    private void case3(int x, int y){
        image(upLeft, x, y);
    }

    private void case4(int x, int y){
        image(upRight, x, y);
    }

    private void case5(int x, int y){
        image(downLeft, x, y);
    }

    private void case6(int x, int y){
        image(downRight, x, y);
    }

    private void case7(int x, int y){
        image(fruit, x, y);
    }

    private void case8(int x, int y){image(wakaState_Right, x, y, 16, 16);}


    private void playerRender(message position){
        //clear(position.X, position.Y);
        frame++;
        if(frame % 16 < 8){
            image(wakaState_Close, position.X, position.Y, 16, 16);
        } else {
            switch (position.direction){
                case RIGHT:{
                    image(wakaState_Right, position.X, position.Y, 16, 16);
                    break;
                }
                case LEFT:{
                    image(wakaState_Left, position.X, position.Y, 16, 16);
                    break;
                }
                case DOWN:{
                    image(wakaState_Down, position.X, position.Y, 16, 16);
                    break;
                }
                case UP:{
                    image(wakaState_Up, position.X, position.Y, 16, 16);
                    break;
                }
            }
        }
        int currentMatrixX = position.X / 16;
        int currentMatrixY = position.Y / 16;
        if(position.X % 16 == 0 && position.Y % 16 == 0 && gameMaps[currentMatrixY][currentMatrixX] == 7) {
            gameMaps[currentMatrixY][currentMatrixX] = 0;
            remain--;
        }
    }

    private void ghostRender(message position){
        clear(position.X, position.Y);
        image(ghostImage, position.X, position.Y, 16, 16);
    }

    private void clear(int x, int y){
        int currentMatrixX = x / 16;
        int currentMatrixY = y / 16;

        int nextGirdX = currentMatrixX;
        int nextGirdY = currentMatrixY;

        if(keyCode % 2 == 0)
            nextGirdY++;
        else
            nextGirdX++;

        if(gameMaps[currentMatrixY][currentMatrixX] == 7){
            fill(0, 0, 0);
            stroke(0, 0, 0);
            rect(x, y, 16, 16);
            case7(currentMatrixX * 16, currentMatrixY * 16);
        } else {
            fill(0, 0, 0);
            stroke(0, 0, 0);
            rect(x, y, 16, 16);
        }

        if(gameMaps[nextGirdY][nextGirdX] == 7){
            fill(0, 0, 0);
            stroke(0, 0, 0);
            rect(x, y, 16, 16);
            case7(nextGirdX * 16, nextGirdY * 16);
        } else if(gameMaps[nextGirdY][nextGirdX] == 0) {
            fill(0, 0, 0);
            stroke(0, 0, 0);
            rect(x, y, 16, 16);
        }
    }

    private void ghostClear(int x, int y, int direction, message target){
        int currentMatrixX = x / 16;
        int currentMatrixY = y / 16;

        int nextGirdX = currentMatrixX;
        int nextGirdY = currentMatrixY;

        if(direction % 2 == 0)
            nextGirdY++;
        else
            nextGirdX++;
        if(gameMaps[currentMatrixY][currentMatrixX] == 7){
            fill(0, 0, 0);
            stroke(0, 0, 0);
            rect(x, y, 16, 16);
            case7(currentMatrixX * 16, currentMatrixY * 16);

        } else {
            fill(0, 0, 0);
            stroke(0, 0, 0);
            rect(x, y, 16, 16);
        }

        if(gameMaps[nextGirdY][nextGirdX] == 7){
            fill(0, 0, 0);
            stroke(0, 0, 0);
            rect(x, y, 16, 16);
            case7(nextGirdX * 16, nextGirdY * 16);
        } else if(gameMaps[nextGirdY][nextGirdX] == 0) {
            fill(0, 0, 0);
            stroke(0, 0, 0);
            rect(x, y, 16, 16);
        }

        if(debugMode){
            clearDebugLine();
        }
    }

    private void clearDebugLine(){
        for(message eachGhost : ghostPrePosition){
            int lineStartMatrixX = (eachGhost.X + 8) / 16;
            int lineStartMatrixY = (eachGhost.Y + 8) / 16;
            int lineEndMatrixX = (eachGhost.target.X + 8) / 16;
            int lineEndMatrixY = (eachGhost.target.Y + 8) / 16;
            int minimalX = min(lineStartMatrixX, lineEndMatrixX);
            int minimalY = min(lineStartMatrixY, lineEndMatrixY);
            int maximalX = max(lineStartMatrixX, lineEndMatrixX);
            int maximalY = max(lineStartMatrixY, lineEndMatrixY);


            for(int i = minimalX; i <= maximalX; i++){
                for(int j = minimalY; j <= maximalY; j++){
                    case0(i * 16, j * 16);
                    mapElementRenderSwitch(i, j);
                }
            }
        }

    }

    private void clearLastFrame(){
        clear(playerPrePosition.X, playerPrePosition.Y);
        for(message eachGhost : ghostPrePosition){
            ghostClear(eachGhost.X, eachGhost.Y, eachGhost.direction, eachGhost.target);
        }
    }

    private void getGhostMode(){
        if(frame % 60 == 0){
            if(frame / 60 > stateStartTime + ghostMode[currentState]){
                currentState++;
                currentState = currentState % ghostModeLength;
                stateStartTime = frame / 60;
                System.out.println("Mode change!");
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == ' '){
            if(debugMode){
                debugMode = false;
                clearDebugLine();
                System.out.println("debug mode off");
            } else {
                debugMode = true;
                System.out.println("debug mode on");
            }
        }
    }


    private void drawDebugLine(message ghostMessage){
        stroke(255, 255, 255);
        line(ghostMessage.X + 8, ghostMessage.Y + 8, ghostMessage.target.X + 8, ghostMessage.target.Y + 8);
    }

    @Override
    public void draw() {
        System.out.println("Now I have "+ghosts.size()+" ghosts");

        if(remain == 0){
            background(0, 0, 0);
        } else if (lives == 0){
            background(255, 255, 255);
        } else {

            clearLastFrame();

            getGhostMode();

            message playerPosition = player.run();
            playerPrePosition.X = playerPosition.X;
            playerPrePosition.Y = playerPosition.Y;
            for(int i = 0; i < ghosts.size(); i++){
                message ghostPosition;
                if(currentState % 2 == 0){
                    ghostPosition = ghosts.get(i).run(ghost.SCATTERMODE, playerPosition);
                } else {
                    ghostPosition = ghosts.get(i).run(ghost.CHASEMODE, playerPosition);
                }
                if(debugMode)
                    drawDebugLine(ghostPosition);
                ghostPrePosition.get(i).X = ghostPosition.X;
                ghostPrePosition.get(i).Y = ghostPosition.Y;
                ghostPrePosition.get(i).direction = ghostPosition.direction;
                ghostPrePosition.get(i).target = new message(ghostPosition.target.X, ghostPosition.target.Y, 0, null);
                if(ghostPosition.X == playerPosition.X && ghostPosition.Y == playerPosition.Y){
                    System.out.println("same");
                    lives--;
                    if(lives > 0){
                        background(0, 0, 0);
                        ghosts.clear();
                        System.out.println("I die and I have " +ghosts.size());
                        ghostPrePosition.clear();
                        mapRender(false);
                        gameInit();
                        case0(32 * lives, (gameMaps.length - 1) * 16);
                        System.out.println("I return and I have " +ghosts.size());
                    }
                }
                ghostRender(ghostPrePosition.get(i));
            }


            playerRender(playerPosition);
        }
    }



}
