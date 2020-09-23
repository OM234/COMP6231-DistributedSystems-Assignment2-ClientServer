import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

// Server is a class that models a chat server. A server uses a thread for each client. A server contains a
// HashMap of all threads. The server uses the threads to receive client messages. When a message is received
// the server sends this same message back to all client threads.

public class Server {

    // Map of all the threads
    private static Map<Integer, ClientThread> clientThreadMap = new HashMap<>();
    // Number of clients, used for assigning client numbers
    private static int numClients = 0;

    public static void main(String[] args) {

        // create ServerSocket at port 9999
        try (ServerSocket serverSocket = new ServerSocket(9999)) {

            // continuously being in a state to accept new client connections
            while (true) {

                try {

                    // Socket for accepting connections
                    Socket socket = serverSocket.accept();
                    System.out.println("New connection from " + socket.getRemoteSocketAddress());

                    // Once connected, create a new client thread, pass it the connection socket, and start it
                    ClientThread clientThread = new ClientThread(socket);
                    clientThread.start();

                    // Add this thread to the map of threads, give it ID based on incrementing number of client threads
                    clientThreadMap.put(++numClients, clientThread);

                } catch (Throwable t) {

                    t.printStackTrace();
                }
            }

        } catch (Throwable t) {

            t.printStackTrace();
        }
    }

    // ClientThread is made static and nested for code clarity
    private static class ClientThread extends Thread {

        Socket socket;                          // socket for connecting to server
        BufferedReader bufferedReader;          // for reading messages from client
        PrintWriter printWriter;                // for sending messages to client

        // Constructor. Assigns values to socket, BufferedReader and PrintWriter
        public ClientThread(Socket socket) {

            this.socket = socket;

            try {

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // printWriter is given true parameter to enable autoFlushing of the buffer
                printWriter = new PrintWriter(socket.getOutputStream(), true);

            } catch (Throwable t) {

                t.printStackTrace();
            }
        }

        @Override
        public void run() {

            try {

                // continuously receive messages from the client
                while(true) {

                    String lineRead = bufferedReader.readLine();

                    // if the lineRead is not null from the bufferedReader, send it too all clients
                    if (lineRead != null) {
                        // System.out.println("server side: " + lineRead); // For debug purposes

                        // Find the clientNum from the map of clients
                        int clientNum = clientThreadMap.entrySet().stream().filter(e -> e.getValue() == this).
                                map(Map.Entry::getKey).collect(Collectors.toList()).get(0);
                        // print message to all clients
                        printToAllClients("client " + clientNum + ": " + lineRead);
                    }
                }

            } catch (Throwable t) {
                t.printStackTrace();
            }

        }

        private void printToAllClients(String lineRead) {

            // Calls the printToClient function for each client thread
            clientThreadMap.forEach((k, v) -> v.printToClient(lineRead));
        }

        private void printToClient(String lineRead) {

            // prints to the client
            printWriter.println(lineRead);
        }

    }
}
