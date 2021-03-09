package ghost;

public abstract class Ghosts {
    int X;
    int Y;
    int currentMatrixX = 0;
    int currentMatrixY = 0;
    long speed;
    int nextMatrixX = 2;
    int nextMatrixY = 4;
    //    int previousDirection;
    App windowsFrame;

    Message leftUp;
    Message rightUp;
    Message leftDown;
    Message rightDown;

    static final int SCATTERMODE = 0;
    static final int CHASEMODE = 1;

    static final int NORMAL = 0;
    static final int AMBUSHER = 1;
    static final int CHASER = 2;
    static final int IGNORANT = 3;
    static final int WHIM = 4;

    int currentDirection;

    public int ghostType;

    public Ghosts(App windowsFrame, int origX, int origY, long speed) {
        this.windowsFrame = windowsFrame;
        currentDirection = windowsFrame.RIGHT;
        X = origX * 16;
        Y = origY * 16;

        leftUp = new Message(windowsFrame.mapLeft * 16, windowsFrame.mapUp * 16, 0, null);
        rightUp = new Message(windowsFrame.mapRight * 16, windowsFrame.mapUp * 16, 0, null);
        leftDown = new Message(windowsFrame.mapLeft * 16, windowsFrame.mapDown * 16, 0, null);
        rightDown = new Message(windowsFrame.mapRight * 16, windowsFrame.mapDown * 16, 0, null);
        this.speed = speed;
    }

    public void walk() {
        if (currentDirection == windowsFrame.UP) {
            Y -= speed;
        } else if (currentDirection == windowsFrame.DOWN) {
            Y += speed;
        } else if (currentDirection == windowsFrame.RIGHT) {
            X += speed;
        } else if (currentDirection == windowsFrame.LEFT) {
            X -= speed;
        }
    }

    public boolean accessible(int direction) {
        if (direction % 2 == 0) {
            int move = direction - 39;
            return windowsFrame.gameMaps[currentMatrixY + move][currentMatrixX] == 0 || windowsFrame.gameMaps[currentMatrixY + move][currentMatrixX] == 7;
        } else {
            int move = direction - 38;
            return windowsFrame.gameMaps[currentMatrixY][currentMatrixX + move] == 0 || windowsFrame.gameMaps[currentMatrixY][currentMatrixX + move] == 7;
        }
    }

    public double countingDistance(Message nextPosition, Message targetPosition) {
        currentMatrixX = X / 16;
        currentMatrixY = Y / 16;
        return (int) Math.sqrt(Math.pow((targetPosition.X >> 4) - nextPosition.X, 2) + Math.pow((targetPosition.Y >> 4) - nextPosition.Y, 2));
    }

    public Message predict(int nextWay) {
        currentMatrixX = X / 16;
        currentMatrixY = Y / 16;
        if (nextWay % 2 == 0) {
            nextMatrixX = currentMatrixX;
            nextMatrixY = currentMatrixY + (nextWay - 39);
        } else {
            nextMatrixY = currentMatrixY;
            nextMatrixX = currentMatrixX + (nextWay - 38);
        }
        return new Message(nextMatrixX, nextMatrixY, 0, null);
    }

    public int judgement(Message targetPosition) {
        int nextDirection = -1;
        double predictDistance;
        double distance = Integer.MAX_VALUE;

        if (currentDirection % 2 == 0) {
            if (accessible(currentDirection)) {
                predictDistance = countingDistance(predict(currentDirection), targetPosition);
                if (predictDistance < distance) {
                    nextDirection = currentDirection;
                    distance = predictDistance;
                }
            }
            if (accessible(windowsFrame.RIGHT)) {
                predictDistance = countingDistance(predict(windowsFrame.RIGHT), targetPosition);
                if (predictDistance < distance) {
                    nextDirection = windowsFrame.RIGHT;
                    distance = predictDistance;
                }
            }
            if (accessible(windowsFrame.LEFT)) {
                predictDistance = countingDistance(predict(windowsFrame.LEFT), targetPosition);
                if (predictDistance < distance) {
                    nextDirection = windowsFrame.LEFT;
                }
            }
        } else {
            if (accessible(currentDirection)) {
                predictDistance = countingDistance(predict(currentDirection), targetPosition);
                if (predictDistance < distance) {
                    nextDirection = currentDirection;
                    distance = predictDistance;
                }
            }
            if (accessible(windowsFrame.UP)) {
                predictDistance = countingDistance(predict(windowsFrame.UP), targetPosition);
                if (predictDistance < distance) {
                    nextDirection = windowsFrame.UP;
                    distance = predictDistance;
                }
            }
            if (accessible(windowsFrame.DOWN)) {
                predictDistance = countingDistance(predict(windowsFrame.DOWN), targetPosition);
                if (predictDistance < distance) {
                    nextDirection = windowsFrame.DOWN;
                }
            }
        }
        return nextDirection;
    }

    public boolean intersection() {
        if (currentDirection % 2 == 0) {
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
    public Message run(int mode, Message playerPosition){
        Message target = findTarget(mode, playerPosition);
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
        return new Message(X, Y, currentDirection, target);
    }

    abstract public Message findTarget(int mode, Message playerPosition);
}
