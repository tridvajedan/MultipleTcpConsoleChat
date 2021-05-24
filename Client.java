import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Client {
    public String username = "";
    public Thread readMessage;
    public Socket socket;
    public BufferedReader bufferedReader;
    public DataOutputStream dos;
    public DataInputStream dis;

    public Client(String ip,int port) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a username - ");
        username = scanner.nextLine().toUpperCase(Locale.ROOT);
        try {
            socket = new Socket(ip, port);
            System.out.println("[" + username + "] Socket has been opened");
        } catch (IOException e) {
            System.out.println("[" + username + "] Socket has failed to open on set circumstances");
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("[" + username + "] Buffered Reader initialized");
        } catch (IOException e) {
            System.out.println("[" + username + "] Buffered Reader has failed to initialize");
        }
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("[" + username + "] DOS initialized");
        } catch (IOException e) {
            System.out.println("[" + username + "] DOS has failed to initialize");
        }
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        System.out.println(dis.readUTF());
                    } catch (IOException e) {
                    }
                }
            }
        });
        readMessage.start();
        while (true) {
            try {
                String msg = scanner.nextLine();
                dos.writeUTF("[" + username + "] " + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        Client client = new Client("192.168.1.11",2254);
    }
}
