import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Inherited;

public class GUI extends JFrame implements MouseListener, MouseMotionListener,
        MouseWheelListener, KeyListener, Runnable
{

    private Client client;

    public GUI(){
        // run client to send commands to server
        // f.e. if user pressed Enter, the command will be:
        // 'keyPressed 10' where 10 is Enter code
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                client = new Client();
                client.run();
            }
        });
        t.start();
    }

    @Override
    public void run() {
        // screen receiver get image from server 10 times per second
        ScreenReciever reciever = new ScreenReciever();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                reciever.run();
            }
        });
        t.start();

        while(true){
        	
        	BufferedImage img = reciever.getScreen();

        	// img is null when server is not connected
            if (img == null)
                continue;

            // Redraw image in Program
            Graphics g = getGraphics();
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

            // image receiving 10 times per second. no sense to continue faster than this
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.println(keyEvent.getKeyCode());
        try {
            client.sendMsg(String.format("keyPressed %d", keyEvent.getKeyCode()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        System.out.println(keyEvent.getKeyCode());
        try {
            client.sendMsg(String.format("keyPressed %d", keyEvent.getKeyCode()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        try {
            client.sendMsg(String.format("mouseClicked %d %d", (int)mouseEvent.getX(), (int)mouseEvent.getY()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        try {
            client.sendMsg(String.format("mouseClicked %d %d", (int)mouseEvent.getX(), (int)mouseEvent.getY()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // got same msg in pressed
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        System.out.println("m moved");
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        // TODO:
        System.out.println("m wheel");
        System.out.println(mouseWheelEvent.getPreciseWheelRotation());
        System.out.println(mouseWheelEvent.getScrollAmount());
        System.out.println(mouseWheelEvent.getScrollType());
    }
}
