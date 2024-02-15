package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
    ArrayList<JButton> listButons;

    public ViewsClient(ActionListener listener) {
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(rootPane);
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
        listButons = new ArrayList<>();
        panelContenImages = new JPanel();
        int position = -1;
        for (ImageIcon imageIcon : images) {
            position++;
            JButton button = new JButton();
            button.setContentAreaFilled(false);
            button.setBorder(null);
            panelContenImages.add(button);
            ImageIcon img = new ImageIcon(imageIcon.getImage().getScaledInstance(80, 70, Image.SCALE_AREA_AVERAGING));
            button.setIcon(img);
            button.setActionCommand(position + "");
            button.addActionListener(listener);
            listButons.add(button);
        }
        contenPageCenter.remove(scroll);
        scroll = new JScrollPane(panelContenImages);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        contenPageCenter.add(scroll, BorderLayout.PAGE_END);
        contenPageCenter.revalidate();
        repaint();
    }

    public void paintImageSelected(ImageIcon image) {
        ImageIcon img = new ImageIcon(image.getImage().getScaledInstance(imageSelected.getWidth(),
                imageSelected.getHeight(), Image.SCALE_SMOOTH));
        imageSelected.removeAll();
        JLabel viewImage = new JLabel(img);
        imageSelected.add(viewImage);
        contenPageCenter.add(imageSelected, BorderLayout.CENTER);
        imageSelected.repaint();
        contenPageCenter.revalidate();
        contenPageCenter.repaint();
    }

    public String obtenerImagen() {
        JFileChooser fileChooser = new JFileChooser();

        // Establecer la ruta predeterminada al directorio de imágenes del sistema
        String defaultDirectory = System.getProperty("user.home") + File.separator + "Pictures";
        fileChooser.setCurrentDirectory(new File(defaultDirectory));

        // Configurar el filtro para que solo muestre archivos de imágenes
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().toLowerCase().endsWith(".jpg")
                        || file.getName().toLowerCase().endsWith(".jpeg")
                        || file.getName().toLowerCase().endsWith(".png")
                        || file.getName().toLowerCase().endsWith(".gif");
            }

            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

}
