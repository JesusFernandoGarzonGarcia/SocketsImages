package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import models.Clients;
import views.ViewsClient;

public class ControllerCopy implements ActionListener {
    ViewsClient viewClient;
    ArrayList<ImageIcon> images;
    Clients client;

    public ControllerCopy() {

        images = new ArrayList<>();
        viewClient = new ViewsClient(this);

        client = new Clients("localhost", 15555);

        images = client.getListImages();
        System.out.println(images.size() + " cantidad de imagenes recibidas " + " cantidad de imagenes almacenadas "
                + images.size());
        viewClient.paintImages(client.getListImages());

    }

    public static void main(String[] args) {
        new ControllerCopy();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (ACTIONS.valueOf(e.getActionCommand())) {
                case ENVIAR:
                    client.enviarImagen(client.convertirStringAImagen(viewClient.obtenerImagen()));
                    break;
                case SOLICITAR:
                    // client.solicitarImagenes();
                    // images = client.getListImages();
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
