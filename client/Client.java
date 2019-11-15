
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client implements Runnable{

    private static Socket clientDialog;
    private String Host = "";
    private Integer Port = 11111;
    public boolean abortConnectionFlag = false;

    public void sendMsg(String msg) throws IOException {
        /*
        *   Sends message to server to execute commands
        * */
        clientDialog.getOutputStream().write((msg+"\n").getBytes());
        clientDialog.getOutputStream().flush();
    }

    public void abortConnection() throws IOException {
        abortConnectionFlag = true;
        if (!clientDialog.isClosed())
            clientDialog.close();
    }

    @Override
    public void run(){
        /*
        *   Runs client to send commands to server
        * */

        // try to connect to server. if it is not started yet it's ok
        try {
            clientDialog = new Socket(Host, Port);
            System.out.println("Client started");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!clientDialog.isClosed() & !abortConnectionFlag){
            // Messages are sent from sendMsg function from GUI class
        }
    }
}
