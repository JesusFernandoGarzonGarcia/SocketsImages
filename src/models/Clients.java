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
    String name = "fernando";
    ArrayList<ImageIcon> listImages;

    public Clients(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            listImages = new ArrayList<ImageIcon>();
            output = socket.getOutputStream();
            input = socket.getInputStream();
            output.write(name.getBytes().length);
            output.write(name.getBytes());
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
        output.write(0);
        int images = input.read();
        for (int i = 0; i < images; i++) {
            int bytesIn = input.read();
            byte[] byteImages = new byte[bytesIn];
            listImages.add(bytesToImage(byteImages));
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
}
