package models;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servers {

    ServerSocket serverSocket;
    InputStream input;
    boolean serverOn = true;

    LogicaServer logica;

    final Logger LOG = Logger.getLogger("models.Servers");

    public Servers(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logica = new LogicaServer();
            LOG.log(Level.INFO, "Servidor escuchando en el puerto " +
                    serverSocket.getLocalPort() + " " + LocalDateTime.now());
            System.out.println();
            while (serverOn) {
                Socket clientSocket = serverSocket.accept();
                int longName = clientSocket.getInputStream().read();
                byte[] name = new byte[longName];
                clientSocket.getInputStream().read(name);
                String nameClient = new String(name, StandardCharsets.UTF_8);
                // LOG.log(Level.INFO, nameClient + " datos del cliente");
                logica.addClient(clientSocket, nameClient);
                LOG.log(Level.INFO,
                        "El Cliente " + nameClient + " con direccion IP " + clientSocket.getInetAddress()
                                + " se a conectado al servidor: "
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