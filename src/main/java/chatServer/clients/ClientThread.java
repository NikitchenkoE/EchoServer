package chatServer.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
    private final Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            while (true){
                String textFromServer = bufferedReader.readLine();
                System.out.println(textFromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
