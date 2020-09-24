package GUI;

import javafx.stage.Stage;
import nonGUI.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ClientGUI {

    private SendThread sendThread;
    private ReceiveThread receiveThread;
    public static ChatBoxController chatBoxController;

    public ClientGUI(){

        connect();
    }

    public void connect() {

        try {
            InetAddress ia = InetAddress.getByName("localhost");

            Socket socket = new Socket(ia, 9999);
            sendThread = new SendThread(socket);
            receiveThread = new ReceiveThread(socket);

            sendThread.start();
            receiveThread.start();

        } catch (Throwable t) {

            t.printStackTrace();
        }
    }

    public void setChatBoxController(ChatBoxController chatBoxController) {

        this.chatBoxController = chatBoxController;
    }

    public void setToSend(String message) {

        sendThread.setToSend(message);
    }

    public class SendThread extends Thread {

        Socket socket;
        String toSend;

        public SendThread(Socket socket) {

            this.socket = socket;
            toSend = null;
        }

        @Override
        public void run() {

            try(PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {

                while(true) {

                    sleep(1);
                    if(toSend != null) {
                        pw.println(toSend + "\n");
                        toSend = null;
                    }

                    System.out.println("sending");
                }

            } catch(Throwable t) {

                t.printStackTrace();
            }
        }

        public void setToSend(String toSend) {

            this.toSend = toSend;
        }
    }

    private static class ReceiveThread extends Thread {

        Socket socket;

        public ReceiveThread(Socket socket) {

            this.socket = socket;
        }

        @Override
        public void run() {

            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {

                    String received = br.readLine();

                    if (received != null) {

                        chatBoxController.updateMessages(received);
                    }

                    System.out.println("receiving");

                }

            } catch(Throwable t){

                t.printStackTrace();
            }
        }

    }
}
