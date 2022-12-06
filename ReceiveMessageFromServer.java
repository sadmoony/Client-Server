import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReceiveMessageFromServer implements Runnable{

    private InputStream inputStreamServer;

    public ReceiveMessageFromServer(InputStream inputStream) {
        inputStreamServer = inputStream;
    }

    @Override
    public void run(){
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStreamServer));

        String serverMessage;
        while (true){
            try {
                serverMessage = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(serverMessage != null){
                System.out.print("\n" + serverMessage + "\nВведіть повідомлення: ");
            }
        }
    }
}
