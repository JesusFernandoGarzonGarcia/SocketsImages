package models;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Clients {

    Socket socket;
    OutputStream output;
    InputStream input;
    String name = "Fernando";
    ArrayList<ImageIcon> listImages;

    public Clients(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            listImages = new ArrayList<ImageIcon>();
            output = socket.getOutputStream();
            input = socket.getInputStream();
            output.write(name.getBytes().length);
            output.write(name.getBytes());
            // solicitarImagenes();
            // enviarImagen(new ImageIcon("C:\\Users\\yudy lopez\\Pictures/Nueva
            // invitacionDG.png"));
            // solicitarImagenes();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(int name) {
        try {
            // output.write(name.getBytes().length);
            output.write(name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarImagen(ImageIcon image) throws IOException {
        byte[] bytesImage = imageToBytes(image);
        System.out.println("TAmanio real de la imagen" + bytesImage.length);
        output.write(String.valueOf(bytesImage.length).getBytes().length);
        output.write(String.valueOf(bytesImage.length).getBytes());
        System.out.println("Datos a enviar ()" + bytesImage.length);
        output.write(bytesImage);
        output.flush();
    }

    public ImageIcon convertirStringAImagen(String data) {
        return new ImageIcon(new ImageIcon(data).getImage().getScaledInstance(700, 700, Image.SCALE_AREA_AVERAGING));
    }

    public void solicitarImagenes() throws IOException {
        listImages.clear();
        int cantidadImagenes = input.read();
        for (int i = 0; i < cantidadImagenes; i++) {
            int longName = input.read();
            byte[] name = new byte[longName];
            input.read(name);
            String nameClient = new String(name);
            System.out.println(nameClient + " estos son los recibidos");
            byte[] tamanioImage = new byte[Integer.parseInt(nameClient)];
            input.read(tamanioImage);
            System.out.println(tamanioImage.length + "estos son los leidos");
            listImages.add(bytesToImage(tamanioImage));
        }
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

    public ArrayList<ImageIcon> getListImages() {
        return listImages;
    }
}
