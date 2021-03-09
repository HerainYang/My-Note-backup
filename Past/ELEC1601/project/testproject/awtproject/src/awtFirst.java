import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.border.Border;

public class awtFirst
{
    private Frame frame;
    private Button bn,bs,be,bw,bc;

    public void createWindow()
    {
        frame = new Frame("awtFirst");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

    public static void main(String[] args)
    {
        awtFirst test = new awtFirst();
        test.createWindow();
    }

}