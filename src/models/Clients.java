package models;

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
    String name = "jkhkkh";
    ArrayList<ImageIcon> listImages;

    public Clients(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            listImages = new ArrayList<ImageIcon>();
            output = socket.getOutputStream();
            input = socket.getInputStream();
            output.write(name.getBytes().length);
            output.write(name.getBytes());
            solicitarImagenes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarImagen(ImageIcon image) throws IOException {
        byte[] byteImage = imageToBytes(image);
        output.write(byteImage.length);
        output.write(byteImage);
    }

    public void solicitarImagenes() throws IOException {
        int cantidadImagenes = input.read();
        for (int i = 0; i < cantidadImagenes; i++) {
            int longName = input.read();
            byte[] name = new byte[longName];
            input.read(name);
            String nameClient = new String(name);
            System.out.println(nameClient + "");
            byte[] tamanioImage = new byte[Integer.parseInt(nameClient)];
            input.read(tamanioImage);
            listImages.add(bytesToImage(tamanioImage));
        }
        System.out.println(" final mente");

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
