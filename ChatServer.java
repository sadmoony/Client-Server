package Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class ChatServer implements Runnable{

    private Map<Integer, Socket> mapClient = new TreeMap<Integer, Socket>();

    public void sendMessageForAllClients(int numberClient, String clientMessage){
        for (int i = 1; i < mapClient.size(); i++){
            if(numberClient != i){
                System.out.println("Надіслати повідомлення клієнтам " + numberClient + "\n");

                BufferedWriter outputUser = null;
                try {
                    outputUser = new BufferedWriter(new OutputStreamWriter(mapClient.get(i).getOutputStream()));
                } catch (IOException e){
                    throw  new RuntimeException(e);
                }

                try {
                    outputUser.write("Клієнт # " + numberClient + ": " + clientMessage + "\n");
                    outputUser.flush();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void run(){

        ServerSocket server = null;
        try {
            server = new ServerSocket(6185);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Сервер запущений. Очікування підключення клієнтів...");

        int numberClient = 1;
        Socket client = null;

        while (true){
            try {
                client = server.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Thread clientThread = new Thread(new ClientThread(client, this, numberClient));
            clientThread.setDaemon(true);
            clientThread.start();
            mapClient.put(numberClient, client);
            numberClient++;
        }
    }
}
