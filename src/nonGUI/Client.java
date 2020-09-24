package nonGUI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


// nonGUI.Client is an abstract class that models a chat client. Each client uses 2 threads: one to send messages to
// the server and one thread to receive messages

public abstract class Client {

    public static void main(String[] args) {

        SendThread sendThread;              // Thread to send
        ReceiveThread receiveThread;        // Thread to receive
        InetAddress ia;                     // For localhost IP

        try {

            ia = InetAddress.getByName("localhost");                // get localhost IP

            try(Socket socket = new Socket(ia, 9999)) {        // connect to server port 9999

                sendThread = new SendThread(socket);                // create new sendThread
                receiveThread = new ReceiveThread(socket);          // create new receiveThread

                sendThread.start();                                 // start the threads, wait for them to die
                receiveThread.start();
                sendThread.join();
                receiveThread.join();
            }

        } catch (Throwable t) {                                     // catch any error/exception

            t.printStackTrace();
        }
    }

    private static class SendThread extends Thread {                // sendThread is made static and nested for code
                                                                    // clarity

        Socket socket;                                              // socket for connecting to server

        public SendThread(Socket socket) {

            this.socket = socket;
        }

        @Override
        public void run() {                                         // run method of the thread


            // try block initializes 2 resources: a PrintWriter for sending messages, and a BufferedReader for
            // receiving messages
            try(PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

                // continuously loop until client exits
                while(true) {

                    System.out.print("Enter message: ");            // prompt the user for input
                    pw.println(br.readLine());                      // read input, send input to server
                }

            } catch(Throwable t) {                                  // catch any error/exception

                t.printStackTrace();
            }
        }
    }

    private static class ReceiveThread extends Thread {             // receiveThread is made static and nested for code
                                                                    // clarity

        Socket socket;

        public ReceiveThread(Socket socket) {

            this.socket = socket;
        }

        @Override
        public void run() {

            try {

                // BufferedReader is used to read input from the server
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {

                    String received = br.readLine();                // readLine from the server
                    if (received != null) {                         // if the null is not null, print it to screen

                        System.out.println("\n" + received);
                    }

                }

            } catch(Throwable t){

                t.printStackTrace();
            }
        }
    }

}

