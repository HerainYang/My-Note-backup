import java.util.Objects;

public class message {
    int X;
    int Y;
    int direction;


    public message(int X, int Y, int direction){
        this.X = X;
        this.Y = Y;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        return (this.X == ((message) o).X) && (this.Y == ((message) o).Y);
    }
}
