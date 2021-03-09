package ghost;

import java.util.Objects;

/**
 * this class is used to transfer position data
 */
public class Message {
    /** x-axis of position {@value}*/
    int X;
    /** y-axis of position {@value}*/
    int Y;
    int direction;
    /** target's position (if it have a target) {@value}*/
    Message target;
    /** type of ghost (if it is a ghost) {@value}*/
    int ghostTypes;

    /**
     * @param X
     * @param Y
     * @param direction
     * @param target
     */
    public Message(int X, int Y, int direction, Message target){
        this.X = X;
        this.Y = Y;
        this.direction = direction;
        this.target = target;
    }

    /**
     * copy a message's information to another message
     * @param m1
     * @param m2
     */
    public static void copy(Message m1, Message m2){
        m1.X = m2.X;
        m1.Y = m2.Y;
        m1.direction = m2.direction;
        m1.target = m2.target;
        m1.ghostTypes = m2.ghostTypes;
    }
}

