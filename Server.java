import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public ServerSocket serverSocket;
    public Socket socket;
    public ArrayList<ConnectionThread> connectionThreads = new ArrayList<>();
    public Thread findConnections;

    public Server(int port)
    {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("[SERVER] Server Socket Initialized");
        } catch (IOException e) {
            System.out.println("[SERVER] Server Socket failed to Initialize");
        }
        findConnections = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    createConnection();
                }
            }
        });
        findConnections.start();
    }

    public void sendMessage(String message)
    {
        for(ConnectionThread conn :connectionThreads)
        {
            try {
                conn.dos.writeUTF(message);
                System.out.println("[SERVER] Sucessfully sent message!");
            } catch (IOException e) {
                System.out.println("[SERVER] Message failed to send!");
            }
        }
    }

    public void createConnection()
    {
        try {
                socket = serverSocket.accept();
                System.out.println("[SERVER] Socket Initialized");
        } catch (IOException e) {
            System.out.println("[SERVER] Socket failed to Initialize");
        }
        System.out.println("User Connected");
        ConnectionThread connectionThread = new ConnectionThread(socket,this);
        connectionThreads.add(connectionThread);
    }

    public static void main(String[] args)
    {
        Server server = new Server(2254);
    }
}
