package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import models.Clients;
import views.ViewsClient;

public class ControllerCopy implements ActionListener {
    ViewsClient viewClient;
    ArrayList<ImageIcon> images;

    public ControllerCopy() {

        ImageIcon im = new ImageIcon("C:\\Users\\yudy lopez\\Pictures/1949.jpg");
        images = new ArrayList<>();
        images.add(im);
        viewClient = new ViewsClient(this);
        // new Servers(15555);
        // Clients client = new Clients("localhost", 15555);

        // try {
        // client.solicitarImagenes();
        // } catch (IOException e) {

        // e.printStackTrace();
        // }
    }

    public static void main(String[] args) {
        new ControllerCopy();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (ACTIONS.valueOf(e.getActionCommand())) {
                case ENVIAR:
                    System.out.println("solicita enviar imagen");
                    break;
                case SOLICITAR:
                    ImageIcon im = new ImageIcon("C:\\Users\\yudy lopez\\Pictures/1949.jpg");
                    images.add(im);
                    ImageIcon iml = new ImageIcon("C:\\Users\\yudy lopez\\Pictures/1948.jpg");
                    images.add(iml);
                    viewClient.paintImages(images);
                    System.out.println("solicita recibir imagen");
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            int position = Integer.parseInt(e.getActionCommand().toString());
            System.out.println(position);
            viewClient.paintImageSelected(images.get(position));

        }

    }

}
