package ghost;

/**
 * this class is for waka
 */
public class Player {
    /** x-axis of the position of ghost {@value}*/
    public int X;
    /** y-axis of the position of ghost {@value}*/
    public int Y;
    private long speed;
    private int currentMatrixX = 0;
    private int currentMatrixY = 0;

    /** x-axis of next grid of ghost {@value}*/
    public int nextMatrixX = 2;
    /** y-axis of next grid of ghost {@value}*/
    public int nextMatrixY = 4;
    /** previous direction  of ghost {@value}*/
    public int previousDirection;
    /** current direction  of ghost {@value}*/
    public int currentDirection;
    /** waiting direction  of ghost {@value}*/
    public int waitingDirection;
    private App windowsFrame;

    /**
     * @param windowsFrame Current window of the game
     * @param origX the starting position of the player
     * @param origY the starting position of the player
     * @param speed the speed of the player
     */
    public Player(App windowsFrame, int origX, int origY, long speed){
        this.windowsFrame = windowsFrame;
        previousDirection = windowsFrame.RIGHT;
        currentDirection = windowsFrame.keyCode;
        X = origX * 16;
        Y = origY * 16;
        this.speed = speed;
    }

    /**
     * @return current direction is opposite to previous direction
     */
    public boolean isOpposite(){
        if(previousDirection % 2 == 0){
            return currentDirection % 2 == 0;
        } else {
            return currentDirection % 2 == 1;
        }
    }

    /**
     * walk one pixel according to the current direction
     */
    public void walk(int input){
        if(input == windowsFrame.UP){
            Y -= speed;
        } else if (input == windowsFrame.DOWN){
            Y += speed;
        } else if (input == windowsFrame.RIGHT){
            X += speed;
        } else if (input == windowsFrame.LEFT){
            X -= speed;
        }
    }

    /**
     * @param nextX x-axis of next possible grid
     * @param nextY y-axis of next possible grid
     * @return if it is true, the next grid is a wall
     */
    public boolean isWall(int nextX, int nextY){
        return !(windowsFrame.gameMaps[nextY][nextX] == 0 || windowsFrame.gameMaps[nextY][nextX] == 7 || windowsFrame.gameMaps[nextY][nextX] == 8 || windowsFrame.gameMaps[nextY][nextX] == 9);
    }

    /**
     * what predict do:
     *   1.calculate current Grid
     *   2.calculate next Grid
     * @param nextWay
     */
    public void predict(int nextWay){
        currentMatrixX = X / 16;
        currentMatrixY = Y / 16;
        System.out.println(X);
        System.out.println(Y);
        System.out.println(nextWay);
        if(nextWay % 2 == 0) {
            nextMatrixX = currentMatrixX;
            nextMatrixY = currentMatrixY + (nextWay - 39);
        } else {
            nextMatrixY = currentMatrixY;
            nextMatrixX = currentMatrixX + (nextWay - 38);
        }
    }

    private int previousDirectNextMatrixX;
    private int previousDirectNextMatrixY;

    /**
     * if direction is unchanged, keep moving until it reach a wall
     * if direction is change and the next direction is opposite to the current one, turn around immediately
     * if direction is change but not opposite, if next direction is not a wall, turn immediately, if it is a wall, wait until this direction is not a wall
     * @return next position player will goto
     */
    public Message run() {
        if (windowsFrame.keyCode == windowsFrame.LEFT || windowsFrame.keyCode == windowsFrame.RIGHT || windowsFrame.keyCode == windowsFrame.UP || windowsFrame.keyCode == windowsFrame.DOWN) {
            currentDirection = windowsFrame.keyCode;
        }
        if(currentDirection != previousDirection){//direction is changed

            if(isOpposite() && waitingDirection == -1){//opposite direction
                walk(currentDirection);
                previousDirection = currentDirection;
            } else {
                waitingDirection = currentDirection;
                if(X % 16 == 0 && Y % 16 == 0){
                    predict(waitingDirection);
                    boolean result = isWall(nextMatrixX, nextMatrixY);
                    System.out.println(result);
                    if(result) {//next grid is wall
                        if(previousDirection % 2 == 0) {
                            previousDirectNextMatrixX = currentMatrixX;
                            previousDirectNextMatrixY = currentMatrixY + (previousDirection - 39);
                        } else {
                            previousDirectNextMatrixY = currentMatrixY;
                            previousDirectNextMatrixX = currentMatrixX + (previousDirection - 38);
                        }
                        System.out.println(previousDirectNextMatrixX);
                        System.out.println(previousDirectNextMatrixY);
                        if(!isWall(previousDirectNextMatrixX, previousDirectNextMatrixY))
                            walk(previousDirection);
                    } else {
                        previousDirection = waitingDirection;
                        walk(waitingDirection);
                        waitingDirection = -1;
                    }
                } else {
                    walk(previousDirection);
                }
                if(X % 16 == 0) {
                    currentMatrixX = X / 16;
                    nextMatrixX = currentMatrixX + (currentDirection - 38);
                }
                if(Y % 16 == 0) {
                    currentMatrixY = Y / 16;
                    nextMatrixY = currentMatrixY + (currentDirection - 39);
                }
            }
        } else {
            predict(previousDirection);
            if(!isWall(nextMatrixX, nextMatrixY) || X % 16 != 0 || Y % 16 !=0){
                walk(previousDirection);
            }
        }
        return new Message(X, Y, previousDirection, null);
    }

}

