package server;

import warehouse.Warehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final Warehouse warehouse = new Warehouse();
    private ServerSocket serverSocket;

    public Server(int startingPort) {
        while (true) {
            try {
                this.serverSocket = new ServerSocket(startingPort);
                System.err.println("Server socket created on port " + startingPort);
                break;
            } catch (IOException e) {
                System.err.println("Port " + startingPort + " occupied. Trying again...");
                startingPort++;
            }
        }
    }

    public Worker newWorker() throws IOException {
        return new Worker(serverSocket.accept(), warehouse);
    }

    private class Worker implements Runnable {
        private Socket socket;
        private Warehouse warehouse;

        PrintWriter out;
        BufferedReader in;

        Worker(Socket s, Warehouse w) throws IOException {
            socket = s;
            warehouse = w;

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
        }

        public void write(String msg){
            out.println(msg);
            out.flush();
        }

        public String read() throws IOException {
            return in.readLine();
        }

        @Override
        public void run() {
            // nothing yet
        }
    }

    public static void main(String[] args) throws IOException{
        Server server = new Server(4000);

        while(true)
            new Thread(server.newWorker()).start();
    }
}
