import java.util.Objects;

public class message {
    int X;
    int Y;
    int direction;
    message target;

    public message(int X, int Y, int direction, message target){
        this.X = X;
        this.Y = Y;
        this.direction = direction;
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        return (this.X == ((message) o).X) && (this.Y == ((message) o).Y);
    }
}
