
import java.io.*;
import java.net.*;

class server {

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    // constructor...
    public server() {
        try {
            server = new ServerSocket(2020);

            System.out.println("server is ready to accept connection");
            System.out.println("waiting.....");
            socket = server.accept();
            // br is used to read the data
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // out is usedd to send data to the client..
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace(); // to priny which line gives the exception
        }

    }

    public void startReading() {
        // thread read karake deta reahenga!

        Runnable r1 = () -> {
            System.out.println("reader started..");

            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("client terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("CLIENT: " + msg);

                }
            } catch (Exception e) {
                System.out.println("conection closed..");
            }

        };

        new Thread(r1).start();

    }

    public void startWriting() {

        // thread data user lega and send karenga client tak
        Runnable r2 = () -> {

            System.out.println("writer started...");

            try {
                while (!socket.isClosed()) {

                    // br1 is used to get data from console..
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();

                    out.println(content);
                    out.flush();// to send dara forcefully...
                    if (content.equals("exit")) {

                        socket.close();
                        break;
                    }

                }
            } catch (Exception e) {
                System.out.println("conection closed..");
            }

        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("this is server.... going to start!");

        new server();
    }
}