package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import models.Clients;
import views.Views;
import views.ViewsClient;

public class ControllerCopy implements ActionListener {
    ViewsClient viewClient;
    ArrayList<ImageIcon> images;
    Clients client;
    String[] data;
    Views v;

    public ControllerCopy() {

        v = new Views(this, null);
        data = v.getDataConection();
        v.setVisible(true);

    }

    public static void main(String[] args) {
        new ControllerCopy();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (ACTIONS.valueOf(e.getActionCommand())) {
                case ENVIAR:
                    ImageIcon imageSelected = client.convertirStringAImagen(viewClient.obtenerImagen());
                    try {
                        client.sendRequest(0);
                        client.enviarImagen(imageSelected);
                    } catch (Exception ex) {
                    }
                    break;
                case SOLICITAR:
                    try {
                        client.sendRequest(1);
                        client.solicitarImagenes();
                        images = client.getListImages();
                        viewClient.paintImages(images);
                    } catch (Exception exx) {
                        exx.printStackTrace();
                    }
                    break;
                case ingresarAServidor:
                    client = new Clients(data[0], 15555, data[1]);
                    if (client.isConection()) {
                        v.setVisible(false);
                        viewClient = new ViewsClient(this);
                        images = client.getListImages();
                    }
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
