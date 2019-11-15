import java.awt.*;
import java.io.IOException;
import java.net.SocketException;

public class Main{
    public static void main(String[] args) throws AWTException, InterruptedException, IOException {
        
        // server accept commands from client
        Server server = new Server();

        // screensender sends screenshots to client
        ScreenSender screenSender = new ScreenSender();

        // run servers
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                server.run();
            }
        });

        Thread senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		screenSender.run();
            	}catch(Exception e){
            		return;
            	}
            }
        });

        serverThread.start();
        senderThread.start();

        // wait for finish working
        senderThread.wait();

        // close connection
        server.closeServer();
        
        serverThread.interrupt();
        System.out.println("Closed");
    }

    static void test() throws InterruptedException, AWTException {
    	Desktop d = new Desktop();
        d.mouseMove(0,0);
        d.mouseMove(100,100);
        Thread.sleep(500);
        d.mouseMove(200,200);
        Thread.sleep(500);
        d.mouseMove(300,300);
        d.click(300, 300);

        d.mouseMove(600,600);
        Thread.sleep(1000);
        
        d.pressKey(87);
        d.pressKey(88);
        d.pressKey(10);
        d.pressKey(87);
    }
}