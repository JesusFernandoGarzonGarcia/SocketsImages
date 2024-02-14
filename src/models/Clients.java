package models;

import java.io.IOException;
import java.net.Socket;

public class Clients {

    Socket socket;

    public Clients(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            socket.getOutputStream().write(20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
