package models;

import java.net.Socket;

public class Client {

    Socket socket;
    String nombre;

    public Client(Socket socket, String nombre) {
        this.nombre = nombre;
        this.socket = socket;
    }

    public String getNombre() {
        return nombre;
    }

    public Socket getSocket() {
        return socket;
    }

}
