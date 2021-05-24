import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionThread {
    public Thread thread;
    public Socket socket;
    public DataInputStream dataInputStream;
    public DataOutputStream dos;
    public Server server;

    public ConnectionThread(Socket socket,Server server)
    {
        this.server = server;
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    readSocket();
                }
            }
        });
        thread.start();
    }

    public void readSocket()
    {
        try {
            server.sendMessage(dataInputStream.readUTF());
        } catch (IOException e) {
        }
    }
}
