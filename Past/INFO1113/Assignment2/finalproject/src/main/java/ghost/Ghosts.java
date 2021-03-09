package ghost;

/**
 * this class is the abstract class of all of the ghost, need to implement findTarget()
 */
public abstract class Ghosts {
    /** x-axis of the position of ghost {@value}*/
    public int X;
    /** y-axis of the position of ghost {@value}*/
    public int Y;
    /** x-axis of grid of ghost {@value}*/
    public int current_Matrix_X = 0;
    /** y-axis of grid of ghost {@value}*/
    public int current_Matrix_Y = 0;
    private long speed;
    private int next_Matrix_X = 2;
    private int next_Matrix_Y = 4;
    //    int previousDirection;
    /** The game's window, is used to access some public attributes {@value}*/
    public App windowsFrame;

    /** The top left corner of the map {@value}*/
    public Message leftUp;
    /** The top right corner of the map {@value}*/
    public Message rightUp;
    /** The bottom left corner of the map {@value}*/
    public Message leftDown;
    /** The bottom right corner of the map {@value}*/
    public Message rightDown;

    public static final int SCATTERMODE = 0;
    public static final int CHASEMODE = 1;

    public static final int NORMAL = 0;
    public static final int AMBUSHER = 1;
    public static final int CHASER = 2;
    public static final int IGNORANT = 3;
    public static final int WHIM = 4;

    public int currentDirection;
    private int ghost_Type;

    /**
     * @param windowsFrame Current window of the game
     * @param origX the starting position of the ghost
     * @param origY the starting position of the ghost
     * @param speed the speed of the ghost
     */
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

    /**
     * Set the type of the ghost
     * @param ghost_Type type of the ghost
     */
    public void setGhost_Type(int ghost_Type) {
        this.ghost_Type = ghost_Type;
    }

    /**
     * get the type of the ghost
     * @return type of the ghost
     */
    public int getGhost_Type() {
        return ghost_Type;
    }

    /**
     * walk one pixel according to the current direction
     */
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

    /**
     * test if the next step is wall or not
     * @param direction direction of the next pixel
     * @return if it is true it means the next pixel is not a wall
     */
    public boolean accessible(int direction) {
        current_Matrix_X = X / 16;
        current_Matrix_Y = Y / 16;
        if (direction % 2 == 0) {
            int move = direction - 39;
            if(current_Matrix_Y + move < 0){
                System.out.println("out of bound: inaccessible");
                return false;
            }
            return windowsFrame.gameMaps[current_Matrix_Y + move][current_Matrix_X] == 0 || windowsFrame.gameMaps[current_Matrix_Y + move][current_Matrix_X] == 7 || windowsFrame.gameMaps[current_Matrix_Y + move][current_Matrix_X] == 8 || windowsFrame.gameMaps[current_Matrix_Y + move][current_Matrix_X] == 9;
        } else {
            int move = direction - 38;
            if(current_Matrix_X + move < 0){
                System.out.println("out of bound: inaccessible");
                return false;
            }
            return windowsFrame.gameMaps[current_Matrix_Y][current_Matrix_X + move] == 0 || windowsFrame.gameMaps[current_Matrix_Y][current_Matrix_X + move] == 7 || windowsFrame.gameMaps[current_Matrix_Y][current_Matrix_X + move] == 8 || windowsFrame.gameMaps[current_Matrix_Y + move][current_Matrix_X] == 9;
        }
    }

    /**
     * counting the distance between next position and ghost
     * @param nextPosition next possible position
     * @param targetPosition the position of the target
     * @return the distance
     */
    public double countingDistance(Message nextPosition, Message targetPosition) {
        current_Matrix_X = X / 16;
        current_Matrix_Y = Y / 16;
        return (int) Math.sqrt(Math.pow((targetPosition.X >> 4) - nextPosition.X, 2) + Math.pow((targetPosition.Y >> 4) - nextPosition.Y, 2));
    }

    /**
     * predict the next position according to the direction
     * @param nextWay direction
     * @return next position
     */
    public Message predict(int nextWay) {
        current_Matrix_X = X / 16;
        current_Matrix_Y = Y / 16;
        if (nextWay % 2 == 0) {
            next_Matrix_X = current_Matrix_X;
            next_Matrix_Y = current_Matrix_Y + (nextWay - 39);
        } else {
            next_Matrix_Y = current_Matrix_Y;
            next_Matrix_X = current_Matrix_X + (nextWay - 38);
        }
        return new Message(next_Matrix_X, next_Matrix_Y, 0, null);
    }

    /**
     * judge which way is the closest way to the target
     * @param targetPosition position of the target
     * @return next direction ghost should go to
     */
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

    /**
     * @return if it is true it mean ghost encounter a intersection
     */
    public boolean intersection() {
        if (currentDirection % 2 == 0) {
            return accessible(windowsFrame.RIGHT) || accessible(windowsFrame.LEFT);
        } else {
            return accessible(windowsFrame.UP) || accessible(windowsFrame.DOWN);
        }
    }

    /**
     * turn backward
     */
    public void turnBack(){
        if (currentDirection % 2 == 0){
            currentDirection += (39 - currentDirection) * 2;
        } else {
            currentDirection += (38 - currentDirection) * 2;
        }
    }

    /**
     * if it is a intersection, make a choose and decide the next direction
     * if it isn't an intersection and current direction is movable, keep going
     * else it turn back
     * @param mode current mode
     * @param playerPosition position of the player
     * @return the next position of the ghost
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
                turnBack();
                walk();
            }
        } else {
            walk();
        }
        return new Message(X, Y, currentDirection, target);
    }


    /**
     * decide a direction which is accessible
     * @param frame
     */
    public void randomDirection(int frame){
        int random = frame % 3;
        //vertical
        if(currentDirection % 2 == 0){
            do{
                if(random == 0){
                    currentDirection = windowsFrame.RIGHT;
                } else if (random == 1){
                    currentDirection = windowsFrame.LEFT;
                }
                random = (random + 1) % 3;
            } while (!accessible(currentDirection));
        } else {
            do{
                if(random == 0){
                    currentDirection = windowsFrame.UP;
                } else if (random == 1){
                    currentDirection = windowsFrame.DOWN;
                }
                random = (random + 1) % 3;
            } while (!accessible(currentDirection));
        }
    }

    /**
     * decide the next direction randomly when encounter an intersection, using frame as seed of random number
     * @param frame use to generate random number
     * @return the next position
     */
    public Message escape(int frame){
        Message target = new Message(X, Y, currentDirection, null);
        if(X % 16 == 0 && Y % 16 == 0){
            if(intersection()){
                randomDirection(frame);
                System.out.println(currentDirection);
                walk();
            } else if (accessible(currentDirection)) {
                walk();
            } else {
                //turn back
                System.out.println("turn back!");
                turnBack();
                walk();
            }
        } else {
            walk();
        }
        return new Message(X, Y, currentDirection, target);
    }

    /**
     * find the target according to the characteristic of the ghost
     * @param mode current ghost mode
     * @param playerPosition  position of the player
     * @return position of the target
     */
    abstract public Message findTarget(int mode, Message playerPosition);
}
