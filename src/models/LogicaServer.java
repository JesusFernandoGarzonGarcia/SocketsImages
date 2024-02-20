package models;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LogicaServer {

    ArrayList<Client> listClients;
    ArrayList<ImageIcon> listImages;

    public LogicaServer() {
        listClients = new ArrayList<>();
        listImages = new ArrayList<>();
        // imagenesPrecargadas();
    }

    public void addClient(Socket socket, String name) {
        listClients.add(new Client(socket, name));
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

    private void imagenesPrecargadas() {
        ImageIcon im = new ImageIcon("C:\\Users\\yudy lopez\\Pictures/1949.jpg");
        listImages.add(im);
        ImageIcon iml = new ImageIcon("C:\\Users\\yudy lopez\\Pictures/1948.jpg");
        listImages.add(iml);

    }

    public ArrayList<ImageIcon> getListImages() {
        return listImages;
    }

    public ArrayList<Client> getListClients() {
        return listClients;
    }

    public void sendImages(Socket clientAddress, ArrayList<ImageIcon> images) throws IOException {
        clientAddress.getOutputStream().write(images.size());
        for (ImageIcon imageIcon : images) {
            byte[] bytesImage = imageToBytes(imageIcon);
            clientAddress.getOutputStream().write(String.valueOf(bytesImage.length).getBytes().length);
            clientAddress.getOutputStream().write(String.valueOf(bytesImage.length).getBytes());
            clientAddress.getOutputStream().write(bytesImage);
        }
        clientAddress.getOutputStream().flush();
    }

    public void recibeImagen(Socket socket) {
        try {

            int longName = socket.getInputStream().read();
            byte[] name = new byte[longName];
            socket.getInputStream().read(name);
            String nameClient = new String(name);
            System.out.println(nameClient + " datos recibidos por el cliente");
            byte[] tamanioImage = new byte[Integer.parseInt(nameClient)];
            socket.getInputStream().read(tamanioImage);
            listImages.add(bytesToImage(tamanioImage));
            System.out.println(nameClient + " datos leidos por el SERVER");
            sendImages(socket, listImages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void accionesCliente(String data, Socket ip) {

        switch (data) {
            case "SOLICITAR":
                try {
                    sendImages(ip, listImages);
                    System.out.println("envia las imagenes al cliente");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "ENVIAR":
                System.out.println("recibe la imagen del cliente");
                // try {
                recibeImagen(ip);
                // } catch (IOException e) {
                // e.printStackTrace();
                // }
                break;
            default:
                break;
        }

    }

}
