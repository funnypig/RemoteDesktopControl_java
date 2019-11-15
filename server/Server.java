
import java.awt.AWTException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;

public class Server implements Runnable{

    private String Host = "10.0.2.15";
    private Integer Port = 11111;
    static Desktop desktop;
    private Socket socket;

    public Server() throws AWTException{
        Server.desktop = new Desktop();
    }
    
    public void closeServer() {
    	try {
    		if (socket.isConnected())
    			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    void processCommand(String command) throws AWTException{
        // process received commands
        String[] coms = command.split(" ");

        // command: "keyPressed Code"
        // example: "keyPressed 10"
        if (coms[0].equals("keyPressed")){
            int code = Integer.parseInt(coms[1]);
            desktop.pressKey(code);
            System.out.println(String.format("Pressed: %d", code));
        }

        // command: "mouseClicked X Y"
        // example: "mouseClicked 117 321"
        if (coms[0].equals("mouseClicked")){
            int x = Integer.parseInt(coms[1]), y = Integer.parseInt(coms[2]);
            desktop.click(x, y);

            System.out.println(String.format("Clicked: %d %d", x, y));
        }

        // command: "mousePosition X Y"
        // example: "mousePosition 117 321"
        if (coms[0].equals("mousePosition")){
            int x = Integer.parseInt(coms[1]), y = Integer.parseInt(coms[2]);
            desktop.mouseMove(x, y);
        }
    }

    @Override
    public void run(){

        try(ServerSocket server = new ServerSocket(Port)){
            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                System.out.println("Connected:");

                while (!socket.isClosed()) {

                    // wait for command
                    String command = in.readLine();

                    if (command != null){
                        System.out.println(command);
                        // run command
                        processCommand(command);
                    }
                }
            }
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }
}
