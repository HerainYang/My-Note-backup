package ghost;

public class Ignorant extends Ghosts{
    /**
     * set ghost type as Ignorant
     * @see Ghosts#Ghosts(App, int, int, long)
     * @param windowsFrame Current window of the game
     * @param origX the starting position of the ghost
     * @param origY the starting position of the ghost
     * @param speed the speed of the ghost
     */
    public Ignorant(App windowsFrame, int origX, int origY, long speed) {
        super(windowsFrame, origX, origY, speed);
        setGhost_Type(Ghosts.IGNORANT);
    }

    /**
     * Chase mode: If more than 8 units away from Waka (straight line distance) target location is Waka.
     * Scatter mode: Bottom left corner
     * @param mode current ghost mode
     * @param playerPosition  position of the player
     * @return the target position
     */
    @Override
    public Message findTarget(int mode, Message playerPosition) {
        System.out.println("ignorant find target");
        current_Matrix_X = X/16;
        current_Matrix_Y = Y/16;

        if(mode == SCATTERMODE){
            return leftDown;
        } else {
            int playerMatrixX = playerPosition.X / 16;
            int playerMatrixY = playerPosition.Y / 16;
            int distance = (int) Math.sqrt(Math.pow((current_Matrix_X - playerMatrixX), 2) + Math.pow((current_Matrix_Y - playerMatrixY), 2));
            if(distance > 8){
                return leftDown;
            } else {
                return playerPosition;
            }
        }
    }
}
