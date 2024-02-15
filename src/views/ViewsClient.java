package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ViewsClient extends JFrame {

    JPanel panelContenImages;
    JButton butonSend;
    JButton butonSolicitar;
    ActionListener listener;
    JPanel contenPageCenter;
    JPanel imageSelected;
    JScrollPane scroll;

    public ViewsClient(ActionListener listener) {
        setSize(500, 500);
        setLayout(new BorderLayout());
        this.listener = listener;

        contenPageCenter = new JPanel();
        contenPageCenter.setBackground(Color.GRAY);
        contenPageCenter.setLayout(new BorderLayout());

        imageSelected = new JPanel();
        imageSelected.setBackground(Color.GRAY);

        panelContenImages = new JPanel();
        scroll = new JScrollPane(panelContenImages);

        contenPageCenter.add(imageSelected, BorderLayout.CENTER);
        contenPageCenter.add(scroll, BorderLayout.PAGE_END);

        JPanel panelContenButons = new JPanel();
        panelContenButons.setLayout(new GridLayout(1, 2));
        butonSend = new JButton("Enviar Imagen");
        butonSend.addActionListener(listener);
        butonSend.setActionCommand("ENVIAR");
        butonSolicitar = new JButton("Solicitar imagenes");
        butonSolicitar.addActionListener(listener);
        butonSolicitar.setActionCommand("SOLICITAR");
        panelContenButons.add(butonSend);
        panelContenButons.add(butonSolicitar);

        add(contenPageCenter, BorderLayout.CENTER);
        add(panelContenButons, BorderLayout.PAGE_END);
        setVisible(true);
    }

    public void paintImages(ArrayList<ImageIcon> images) {

        panelContenImages = new JPanel();
        panelContenImages.setBackground(Color.RED);

        for (ImageIcon imageIcon : images) {
            ImageIcon img = new ImageIcon(imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
            panelContenImages.add(new JLabel(img));
        }
        contenPageCenter.remove(scroll);
        scroll = new JScrollPane(panelContenImages);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        contenPageCenter.add(scroll, BorderLayout.PAGE_END);
        contenPageCenter.revalidate();
        repaint();
        repaint();
    }
}
