package ghost;

public class Chaser extends Ghosts{
    /**
     * set ghost type as Chaser
     * @see Ghosts#Ghosts(App, int, int, long)
     * @param windowsFrame Current window of the game
     * @param origX the starting position of the ghost
     * @param origY the starting position of the ghost
     * @param speed the speed of the ghost
     */
    public Chaser(App windowsFrame, int origX, int origY, long speed) {
        super(windowsFrame, origX, origY, speed);
        setGhost_Type(Ghosts.CHASER);
    }

    /**
     * Chase mode: Waka’s position
     * Scatter mode: Top left corner
     * @param mode current ghost mode
     * @param playerPosition  position of the player
     * @return the target position
     */
    @Override
    public Message findTarget(int mode, Message playerPosition) {
        current_Matrix_X = X/16;
        current_Matrix_Y = Y/16;

        if(mode == SCATTERMODE){
            return leftUp;
        } else {
            return playerPosition;
        }
    }
}
