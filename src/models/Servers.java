package models;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class Servers {

    ServerSocket serverSocket;
    InputStream input;
    boolean serverOn = true;

    LogicaServer logica;

    public Servers(int port) {

        try {
            serverSocket = new ServerSocket(port);
            logica = new LogicaServer();
            System.out.println("Servidor escuchando en el puerto " +
                    serverSocket.getLocalPort() + " " + LocalDateTime.now());
            while (serverOn) {
                Socket clientSocket = serverSocket.accept();
                int longName = clientSocket.getInputStream().read();
                byte[] name = new byte[longName];
                clientSocket.getInputStream().read(name);
                String nameClient = new String(name, StandardCharsets.UTF_8);
                System.out.println(nameClient + " datos del cliente");
                logica.addClient(clientSocket, nameClient);
                System.out.println(
                        " Cliente " + clientSocket.getInetAddress() + "conectado al servidor: "
                                + LocalDateTime.now());
                Thread thread = new Thread(new ConnectionHandler(clientSocket, logica));
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(Socket clientAddress, String message) {
        for (Client socket : logica.getListClients()) {
            if (!message.contains(socket.getNombre())) {
                try {
                    clientAddress.getOutputStream().write(message.getBytes().length);
                    clientAddress.getOutputStream().write(message.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}