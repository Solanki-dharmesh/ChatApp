import java.net.*;
import java.io.*;;

class client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public client() {
        try {

            System.out.println("sending request to server....");
            socket = new Socket("127.0.0.1", 2020);
            System.out.println("connection done!");

            // br is used to read the data
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // out is usedd to send data to the client..
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {

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
                        System.out.println("server terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("SERVER: " + msg);

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
        System.out.println("this is client");

        new client();
    }
}
