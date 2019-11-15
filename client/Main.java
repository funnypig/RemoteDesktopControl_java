
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws AWTException {
        // Main program has to be started as runnable to process gui events
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // MAIN PROGRAM

                GUI frame = new GUI();
                frame.setSize(300, 500);

                frame.addMouseListener(frame);
                frame.addKeyListener(frame);
                frame.addMouseWheelListener(frame);

                System.out.println("Window opened");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                // run this thread to update image in GUI async
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        frame.run();
                    }
                });
                t.start();
            }
        });
    }
}
