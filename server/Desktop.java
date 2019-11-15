
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class Desktop {

    int width, height;          // monitor resolution
    int mouseX, mouseY;         // mouse position
    BufferedImage curScreen;    // current screen state
    Rectangle screenRect;       // screen rectangle
    
    private Robot robot;
    private GraphicsDevice gd;
    
    public Desktop() throws AWTException {
        robot = new Robot();
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
        screenRect = new Rectangle(width, height);
    }

    public BufferedImage getScreen(){
        /*
        *   create screenshot
        * */
        return robot.createScreenCapture(screenRect);
    }

    public void click(int x, int y) throws AWTException{
    	Robot robot = new Robot();
	    robot.mouseMove(x, y);
	    robot.mousePress((int)InputEvent.MOUSE_EVENT_MASK);
	    robot.mouseRelease((int)InputEvent.MOUSE_EVENT_MASK);
    }

    public void mouseMove(int x, int y) throws AWTException{
        robot.mouseMove(x, y);
    }

    public void pressKey(int code) throws AWTException{
    	robot.keyPress(code);
    	robot.keyRelease(code);
    	System.out.println(String.format("Pressed %d", code));
    }

    public void releaseKey(int code){
        robot.keyRelease(code);
    }

    public void typeKey(int code){
        robot.keyPress(code);
        robot.keyRelease(code);
    }

    public void pressMouse(int x, int y) throws AWTException{
        mouseMove(x, y);
        robot.mousePress((int)InputEvent.BUTTON1_DOWN_MASK);
    }

    public void releaseMouse(int x, int y){
        robot.mouseRelease((int)InputEvent.BUTTON1_DOWN_MASK);
    }

    public void wheel(int amt){
        robot.mouseWheel(amt);
    }
}
