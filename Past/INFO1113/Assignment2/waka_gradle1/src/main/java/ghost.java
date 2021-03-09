public class ghost {
    int X;
    int Y;
    int currentMatrixX = 0;
    int currentMatrixY = 0;
    int speed;
    int nextMatrixX = 2;
    int nextMatrixY = 4;
//    int previousDirection;
    mainFrame windowsFrame;

    message leftUp;
    message rightUp;
    message leftDown;
    message rightDown;

    static final int SCATTERMODE = 0;
    static final int CHASEMODE = 1;

    int currentDirection;

    public ghost(mainFrame windowsFrame, int origX, int origY, int speed){
        this.windowsFrame = windowsFrame;
        currentDirection = windowsFrame.RIGHT;
        X = origX * 16;
        Y = origY * 16;

        leftUp = new message(windowsFrame.mapLeft * 16, windowsFrame.mapUp * 16, 0, null);
        rightUp = new message(windowsFrame.mapRight * 16, windowsFrame.mapUp * 16, 0, null);
        leftDown = new message(windowsFrame.mapLeft * 16, windowsFrame.mapDown * 16, 0, null);
        rightDown = new message(windowsFrame.mapRight * 16, windowsFrame.mapDown * 16, 0, null);
        this.speed = speed;
    }

    public void walk(){
        if(currentDirection == windowsFrame.UP){
            Y -= speed;
        } else if (currentDirection == windowsFrame.DOWN){
            Y += speed;
        } else if (currentDirection == windowsFrame.RIGHT){
            X += speed;
        } else if (currentDirection == windowsFrame.LEFT){
            X -= speed;
        }
    }

    public boolean accessible(int direction){
        if(direction % 2 == 0){
            int move = direction - 39;
            return windowsFrame.gameMaps[currentMatrixY + move][currentMatrixX] == 0 || windowsFrame.gameMaps[currentMatrixY + move][currentMatrixX] == 7;
        } else {
            int move = direction - 38;
            return windowsFrame.gameMaps[currentMatrixY][currentMatrixX + move] == 0 || windowsFrame.gameMaps[currentMatrixY][currentMatrixX + move] == 7;
        }
    }

    public message findTarget(int mode, message playerPosition){
        currentMatrixX = X/16;
        currentMatrixY = Y/16;

        if(mode == SCATTERMODE){
            int x_axis = currentMatrixX - (windowsFrame.mapLeft + windowsFrame.mapRight) / 2;
            int y_axis = currentMatrixY - (windowsFrame.mapUp + windowsFrame.mapDown) / 2;
            if(x_axis <= 0 && y_axis <= 0){
                return leftUp;
            }
            if(x_axis > 0 && y_axis <= 0){
                return rightUp;
            }
            if(x_axis <= 0){
                return leftDown;
            }
            return rightDown;
        } else {
            return new message(playerPosition.X, playerPosition.Y, 0, null);
        }
    }

    public double countingDistance(message nextPosition,message targetPosition){
        currentMatrixX = X / 16;
        currentMatrixY = Y / 16;
        return (int) Math.sqrt(Math.pow((targetPosition.X >> 4) - nextPosition.X, 2)+Math.pow((targetPosition.Y >> 4) - nextPosition.Y, 2));
    }

    public message predict(int nextWay){
        currentMatrixX = X / 16;
        currentMatrixY = Y / 16;
        if(nextWay % 2 == 0) {
            nextMatrixX = currentMatrixX;
            nextMatrixY = currentMatrixY + (nextWay - 39);
        } else {
            nextMatrixY = currentMatrixY;
            nextMatrixX = currentMatrixX + (nextWay - 38);
        }
        return new message(nextMatrixX, nextMatrixY, 0, null);
    }

    public int judgement(message targetPosition){
        int nextDirection = -1;
        double predictDistance;
        double distance = Integer.MAX_VALUE;

        if(currentDirection % 2 == 0){
            if(accessible(currentDirection)){
                predictDistance = countingDistance(predict(currentDirection), targetPosition);
                if(predictDistance < distance) {
                    nextDirection = currentDirection;
                    distance = predictDistance;
                }
            }
            if (accessible(windowsFrame.RIGHT)){
                predictDistance = countingDistance(predict(windowsFrame.RIGHT), targetPosition);
                if(predictDistance < distance) {
                    nextDirection = windowsFrame.RIGHT;
                    distance = predictDistance;
                }
            }
            if (accessible(windowsFrame.LEFT)){
                predictDistance = countingDistance(predict(windowsFrame.LEFT), targetPosition);
                if(predictDistance < distance) {
                    nextDirection = windowsFrame.LEFT;
                }
            }
        } else {
            if(accessible(currentDirection)){
                predictDistance = countingDistance(predict(currentDirection), targetPosition);
                if(predictDistance < distance) {
                    nextDirection = currentDirection;
                    distance = predictDistance;
                }
            }
            if (accessible(windowsFrame.UP)){
                predictDistance = countingDistance(predict(windowsFrame.UP), targetPosition);
                if(predictDistance < distance) {
                    nextDirection = windowsFrame.UP;
                    distance = predictDistance;
                }
            }
            if (accessible(windowsFrame.DOWN)){
                predictDistance = countingDistance(predict(windowsFrame.DOWN), targetPosition);
                if(predictDistance < distance) {
                    nextDirection = windowsFrame.DOWN;
                }
            }
        }
        return nextDirection;
    }

    public boolean intersection(){
        if(currentDirection % 2 == 0){
            return accessible(windowsFrame.RIGHT) || accessible(windowsFrame.LEFT);
        } else {
            return accessible(windowsFrame.UP) || accessible(windowsFrame.DOWN);
        }
    }

    /**
     * if(intersection)
     *   ->judgement
     * else if(no wall)
     *   ->go ahead
     * else if(is wall)
     *   ->turn back
     * @param mode
     * @param playerPosition
     */
    public message run(int mode, message playerPosition){
        message target = findTarget(mode, playerPosition);
        if(X % 16 == 0 && Y % 16 == 0){
            if(intersection()){
                currentDirection = judgement(target);
                walk();
            } else if (accessible(currentDirection)) {
                walk();
            } else {
                //turn back
                System.out.println("turn back!");

            }
        } else {
            walk();
        }
        return new message(X, Y, currentDirection, target);
    }
}
