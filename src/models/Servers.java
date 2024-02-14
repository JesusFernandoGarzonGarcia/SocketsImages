package models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Servers {

    ServerSocket serverSocket;
    OutputStream output;
    InputStream input;
    ArrayList<Client> listClients;
    ArrayList<ImageIcon> listImages;

    public Servers(int port) {
        listClients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor escuchando en el puerto " +
                    serverSocket.getLocalPort() + " " + LocalDateTime.now());
            while (true) {
                Socket clientSocket = serverSocket.accept();
                int longName = clientSocket.getInputStream().read();
                byte[] name = new byte[longName];
                clientSocket.getInputStream().read(name);
                String nameClient = new String(name, StandardCharsets.UTF_8);
                System.out.println(nameClient + " datos del cliente");
                listClients.add(new Client(clientSocket, nameClient));
                System.out.println(
                        " Cliente " + clientSocket.getInetAddress() + "conectado al servidor: "
                                + LocalDateTime.now());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String clientAddress, String message) {
        for (Client socket : listClients) {
            if (!message.contains(socket.getNombre())) {
                try {
                    output.write(message.getBytes().length);
                    output.write(message.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendImages(String clientAddress, ArrayList<ImageIcon> images) throws IOException {
        output.write(images.size());
        for (ImageIcon imageIcon : images) {
            byte[] bytesImage = imageToBytes(imageIcon);
            output.write(bytesImage.length);
            output.write(bytesImage);
        }
    }

    public void recibeImagen() throws IOException {
        int bytesIn = input.read();
        byte[] byteImages = new byte[bytesIn];
        listImages.add(bytesToImage(byteImages));
    }

    private ImageIcon bytesToImage(byte[] imageBytes) {
        return new ImageIcon(imageBytes);
    }

    private byte[] imageToBytes(ImageIcon image) {

        BufferedImage bufferedImage = new BufferedImage(
                image.getIconWidth(),
                image.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image.getImage(), 0, 0, null);
        byte[] imageBytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            imageBytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }
}