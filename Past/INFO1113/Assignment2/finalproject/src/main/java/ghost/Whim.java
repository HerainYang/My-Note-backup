package ghost;

public class Whim extends Ghosts{

    /** position of the chaser {@value}*/
    public Message chaser_Position;

    /**
     * set ghost type as Chaser
     * @see Ghosts#Ghosts(App, int, int, long)
     * @param windowsFrame Current window of the game
     * @param origX the starting position of the ghost
     * @param origY the starting position of the ghost
     * @param speed the speed of the ghost
     */
    public Whim(App windowsFrame, int origX, int origY, long speed) {
        super(windowsFrame, origX, origY, speed);
        setGhost_Type(Ghosts.WHIM);
    }

    /**
     * Double the vector from ghost's position to 2 grid spaces ahead of target's position.
     * @param targetMatrixX
     * @param targetMatrixY
     * @param ghostMatrixX
     * @param ghostMatrixY
     * @return
     */
    public Message doubleVector(int targetMatrixX, int targetMatrixY, int ghostMatrixX, int ghostMatrixY){
        int newTargetMatrixX = targetMatrixX * 2 - ghostMatrixX;
        int newTargetMatrixY = targetMatrixY * 2 - ghostMatrixY;
        newTargetMatrixX = Math.min(windowsFrame.mapRight, newTargetMatrixX);
        newTargetMatrixX = Math.max(windowsFrame.mapLeft, newTargetMatrixX);
        newTargetMatrixY = Math.min(windowsFrame.mapDown, newTargetMatrixY);
        newTargetMatrixY = Math.max(windowsFrame.mapUp, newTargetMatrixY);
        return new Message(newTargetMatrixX * 16, newTargetMatrixY * 16, 0, null);
    }

    /**
     * @param chaser_Position chaser's position
     */
    public void passChaserPosition(Message chaser_Position){
        this.chaser_Position = chaser_Position;
    }

    /**
     * Chase mode: Double the vector from Chaser to 2 grid spaces ahead of Waka.
     * Scatter mode: Bottom right corner.
     * @param mode current ghost mode
     * @param playerPosition  position of the player
     * @return the target position
     */
    @Override
    public Message findTarget(int mode, Message playerPosition) {
        if(mode == SCATTERMODE){
            return rightDown;
        } else {
            if(chaser_Position == null){
                return playerPosition;
            }
            int chaser_Matrix_X = chaser_Position.X / 16;
            int chaser_Matrix_Y = chaser_Position.Y / 16;
            int playerMatrixX = playerPosition.X / 16;
            int playerMatrixY = playerPosition.Y / 16;
            int targetMatrixX;
            int targetMatrixY;
            if(playerPosition.direction % 2 == 0){
                targetMatrixX = playerMatrixX;
                targetMatrixY = playerMatrixY + (playerPosition.direction - 39) * 2;
            } else {
                targetMatrixX = playerMatrixX + (playerPosition.direction - 38) * 2;
                targetMatrixY = playerMatrixY;
            }
            return doubleVector(targetMatrixX, targetMatrixY, chaser_Matrix_X, chaser_Matrix_Y);
        }
    }
}
