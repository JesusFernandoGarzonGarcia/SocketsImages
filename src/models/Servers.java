package models;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Servers {

    ServerSocket serverSocket;

    public Servers(int port) {
        Thread subServerThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port); // Utiliza un puerto disponible
                System.out.println("Servidor escuchando en el puerto " +
                        serverSocket.getLocalPort() + " " + LocalDateTime.now());
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println(
                            " Cliente " + clientSocket.getInetAddress() + "conectado al servidor: "
                                    + LocalDateTime.now());
                    System.out.println(clientSocket.getInputStream().read());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        subServerThread.start();
    }
}