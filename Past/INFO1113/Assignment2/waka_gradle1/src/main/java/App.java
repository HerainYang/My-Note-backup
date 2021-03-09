import processing.core.PApplet;

public class App {
    public static void main(String[] args) {
        mainFrame mainFrame = new mainFrame();
        String[] strings = {"anything"};
        PApplet.runSketch(strings, mainFrame);
    }
}
