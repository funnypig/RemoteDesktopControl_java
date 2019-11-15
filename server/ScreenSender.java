import javax.imageio.ImageIO;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ScreenSender implements Runnable {

    private String Host = "10.0.2.15";
    private Integer Port = 22222;
    static Desktop desktop;
    BufferedImage lastScreen;

    public ScreenSender() throws AWTException {
        ScreenSender.desktop = new Desktop();
    }

    @Override
    public void run() {

        try (ServerSocket server = new ServerSocket(Port)) {
            System.out.println("Screen Sender started");

            while (true) {
                Socket socket = server.accept();
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                while (!socket.isClosed()) {
                	
                	// Save current screen to byte array
                	ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(desktop.getScreen(), "jpg", baos);
                    byte[] bytes = baos.toByteArray();
                    int byteLength = bytes.length;
                    
                    // Send number of bytes
                    out.writeInt(byteLength);
                    
                    // Send 'byteLength' bytes
                    out.write(bytes);

                    // wait a little to not load server and client too much
                    Thread.sleep(100);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
