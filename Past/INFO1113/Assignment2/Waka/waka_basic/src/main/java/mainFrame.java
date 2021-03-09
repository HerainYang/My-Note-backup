import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class mainFrame extends PApplet{
    String[] maps = new String[36];
    int[][] gameMaps = new int[36][28];
    int remain = 0;
    int lives = 3;
    int gameSpeed;
    int[] ghostMode;
    int ghostModeLength;
    int frame = 0;

    PImage horizontal;
    PImage downLeft;
    PImage downRight;
    PImage upLeft;
    PImage upRight;
    PImage vertical;
    PImage fruit;
    PImage wakaState_Close;
    PImage wakaState_Down;
    PImage wakaState_Left;
    PImage wakaState_Right;
    PImage wakaState_Up;
    PImage ghostImage;

    message playerPrePosition;

    ArrayList<message> ghostPrePosition;

    int mapLeft;
    int mapRight;
    int mapUp;
    int mapDown;

    player player;
    message playerStartPoint;

    ArrayList<ghost> ghosts;
    ArrayList<message> ghostsStartPoint;

    int currentState = 0;
    int stateStartTime = 0;


    @Override
    public void settings() {

        size(448, 576);

    }

    @Override
    public void setup() {
        background(0,0,0);
        ghostsStartPoint = new ArrayList<>();

        try {
            configReader configReader = new configReader();
            gameSpeed = configReader.getSpeed();
            ghostMode = configReader.getGhostMode();
            lives = configReader.getLives();
            ghostModeLength = 0;
            for(int time: ghostMode){
                ghostModeLength += time;
            }
        } catch (Exception e){
            System.out.println("config file not found");
            return;
        }

        File map = new File("map.txt");
        try {
            Scanner scanner = new Scanner(map);
            for(int i = 0; i < 36; i++){
                maps[i] = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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

        frameRate(60);

        surface.setTitle("wutou");
        keyCode = RIGHT;
        boolean firstLineBorder = true;
        int rowStart = 0;
        int rowEnd = 0;
        int colStart = 0;
        int colEnd = 0;
        for(int i = 0; i < 36; i++){
            boolean allRowZero = true;
            for(int j = 0; j < 28; j++){

                if(maps[i].charAt(j) == 'p'){
                    //player location
                    if(allRowZero && gameMaps[i][j] != 0){
                        allRowZero = false;
                        colStart = j;
                    }
                    gameMaps[i][j] = 0;
                    playerStartPoint = new message(j, i, 0);
                } else if (maps[i].charAt(j) == 'g'){
                    //ghost location
                    if(allRowZero && gameMaps[i][j] != 0){
                        allRowZero = false;
                        colStart = j;
                    }
                    gameMaps[i][j] = 0;
                    ghostsStartPoint.add(new message(j, i, 0));
                } else {
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
                    switch (gameMaps[i][j]){
                        case 0:{
                            case0(j * 16, i * 16);
                            break;
                        }
                        case 1:{
                            case1(j * 16, i * 16);
                            break;
                        }
                        case 2:{
                            case2(j * 16, i * 16);
                            break;
                        }
                        case 3:{
                            case3(j * 16, i * 16);
                            break;
                        }
                        case 4:{
                            case4(j * 16, i * 16);
                            break;
                        }
                        case 5:{
                            case5(j * 16, i * 16);
                            break;
                        }
                        case 6:{
                            case6(j * 16, i * 16);
                            break;
                        }
                        case 7:{
                            remain++;
                            case7(j * 16, i * 16);
                            break;
                        }
                    }


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

        if(playerStartPoint == null) {
            System.out.println("Map error: player no found");
        }


        System.out.println("Player start at "+playerStartPoint.X+", "+playerStartPoint.Y);
        player = new player(this, playerStartPoint.X, playerStartPoint.Y, gameSpeed);
        ghosts = new ArrayList<>();
        ghostPrePosition = new ArrayList<>();
        playerPrePosition = new message(playerStartPoint.X, playerStartPoint.Y, 0);
        for(message eachGhost : ghostsStartPoint){
            ghosts.add(new ghost(this, eachGhost.X, eachGhost.Y, gameSpeed));
            ghostPrePosition.add(new message(eachGhost.X, eachGhost.Y, 0));
        }

        System.out.println(rowEnd);
        for(int i = 0; i < lives; i++){
            case8(i * 32, (gameMaps.length - 1) * 16);
        }

    }

    public void case0(int x, int y){
        fill(0, 0, 0);
        rect(x, y, 16, 16);
    }

    public void case1(int x, int y){
        image(horizontal, x, y);
    }

    public void case2(int x, int y){
        image(vertical, x, y);
    }

    public void case3(int x, int y){
        image(upLeft, x, y);
    }

    public void case4(int x, int y){
        image(upRight, x, y);
    }

    public void case5(int x, int y){
        image(downLeft, x, y);
    }

    public void case6(int x, int y){
        image(downRight, x, y);
    }

    public void case7(int x, int y){
        image(fruit, x, y);
    }

    public void case8(int x, int y){image(wakaState_Right, x, y, 16, 16);}


    public void playerRender(message position){
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

    public void ghostRender(message position){
        clear(position.X, position.Y);
        image(ghostImage, position.X, position.Y, 16, 16);
    }

    public void clear(int x, int y){
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
            rect(x, y, 16, 16);
            case7(currentMatrixX * 16, currentMatrixY * 16);
        } else {
            fill(0, 0, 0);
            rect(x, y, 16, 16);
        }

        if(gameMaps[nextGirdY][nextGirdX] == 7){
            fill(0, 0, 0);
            rect(x, y, 16, 16);
            case7(nextGirdX * 16, nextGirdY * 16);
        } else if(gameMaps[nextGirdY][nextGirdX] == 0) {
            fill(0, 0, 0);
            rect(x, y, 16, 16);
        }
    }

    public void ghostClear(int x, int y, int direction){
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
            rect(x, y, 16, 16);
            case7(currentMatrixX * 16, currentMatrixY * 16);

        } else {
            fill(0, 0, 0);
            rect(x, y, 16, 16);
        }

        if(gameMaps[nextGirdY][nextGirdX] == 7){
            fill(0, 0, 0);
            rect(x, y, 16, 16);
            case7(nextGirdX * 16, nextGirdY * 16);
        } else if(gameMaps[nextGirdY][nextGirdX] == 0) {
            fill(0, 0, 0);
            rect(x, y, 16, 16);
        }
    }



    @Override
    public void draw() {

        if(remain == 0){
            background(0, 0, 0);
        } else if (lives == 0){
            background(255, 255, 255);
        } else {
            clear(playerPrePosition.X, playerPrePosition.Y);
            for(message eachGhost : ghostPrePosition){
                ghostClear(eachGhost.X, eachGhost.Y, eachGhost.direction);
            }

            if(frame % 60 == 0){
                System.out.println(frame / 60 + " second");
                if(frame / 60 > stateStartTime + ghostMode[currentState]){
                    currentState++;
                    currentState = currentState % ghostModeLength;
                    stateStartTime = frame / 60;
                    System.out.println("Mode change!");
                }
            }

            message position = player.run();
            playerPrePosition.X = position.X;
            playerPrePosition.Y = position.Y;

            int playerMatrixX = playerPrePosition.X / 16;
            int playerMatrixY = playerPrePosition.Y / 16;
            for(int i = 0; i < ghosts.size(); i++){
                message ghostPosition;
                if(currentState % 2 == 0){
                    ghostPosition = ghosts.get(i).run(ghost.SCATTERMODE, playerPrePosition);
                } else {
                    ghostPosition = ghosts.get(i).run(ghost.CHASEMODE, playerPrePosition);
                }
                ghostPrePosition.get(i).X = ghostPosition.X;
                ghostPrePosition.get(i).Y = ghostPosition.Y;
                ghostPrePosition.get(i).direction = ghostPosition.direction;
                int ghostMatrixX = ghostPrePosition.get(i).X / 16;
                int ghostMatrixY = ghostPrePosition.get(i).Y / 16;
                if(ghostMatrixX == playerMatrixX && ghostMatrixY == playerMatrixY){
                    System.out.println("same");
                    lives--;
                    if(lives > 0){
                        frame = 0;
                        player = new player(this, playerStartPoint.X, playerStartPoint.Y, gameSpeed);
                        ghosts.clear();
                        for(message eachGhost : ghostsStartPoint){
                            ghosts.add(new ghost(this, eachGhost.X, eachGhost.Y,gameSpeed));
                            ghostPrePosition.add(new message(eachGhost.X, eachGhost.Y, 0));
                        }
                        currentState = 0;
                        stateStartTime = 0;
                    }

                }
                ghostRender(ghostPrePosition.get(i));
            }


            playerRender(position);
        }
    }


    public static void main(String[] args) {
        mainFrame mainFrame = new mainFrame();
        String[] mainFrameArgs = {"mainFrame"};
        PApplet.runSketch(mainFrameArgs, mainFrame);
    }
}
