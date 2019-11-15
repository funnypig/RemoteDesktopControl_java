import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class ScreenReciever implements Runnable {
    private String Host = "";
    private Integer Port = 22222;
    public static BufferedImage lastScreen = null;
    static final Object syncScreener = new Object();
    
    static void updateScreen(BufferedImage img) {
        /*
        *   Updates screen sent by server
        * */
    	synchronized (syncScreener) {
    		lastScreen = img;
    	}
    }

    public static BufferedImage getScreen() {
        /*
        *   returns last updated screen
        * */
    	synchronized (syncScreener) {
    		
    		if (lastScreen == null){
    			return null;
    		}
    		
    		ColorModel cm = lastScreen.getColorModel();
    		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    		WritableRaster raster = lastScreen.copyData(null);
    		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    	}
    }

    @Override
    public void run() {
        /*
        *   Runs client to receive screen image from another computer - server
        * */

        while (true) {
            try (Socket socket = new Socket(Host, Port)) {

                DataInputStream in = new DataInputStream(socket.getInputStream());
                int byteLen = -1;

                while (!socket.isClosed()) {
                    // get number of bytes to receive
                    byteLen = in.readInt();

                    // read bytes
                    byte[] imgBytes = in.readNBytes(byteLen);

                    // write bytes to img
                    ByteArrayInputStream is = new ByteArrayInputStream(imgBytes);
                    BufferedImage img = ImageIO.read(is);

                    // troubles happens. it's ok
                    if (img == null) {
                    	continue;
                    }

                    // save updated screen
                    updateScreen(img);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
