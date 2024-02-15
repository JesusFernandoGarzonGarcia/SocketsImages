package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ViewsClient extends JFrame
        implements MouseListener {

    JPanel panelContenImages;
    JButton butonSend;
    JButton butonSolicitar;
    ActionListener listener;
    JPanel contenPageCenter;
    JPanel imageSelected;
    JScrollPane scroll;
    ArrayList<JButton> listButons;

    public ViewsClient(ActionListener listener) {
        super("Galeria Online");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(rootPane);
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon(getClass().getResource("/img/logo.png")).getImage());
        setBackground(Color.WHITE);
        setResizable(false);
        this.listener = listener;

        Font font = new Font("ARIAL", Font.BOLD, 15);
        contenPageCenter = new JPanel();
        contenPageCenter.setBackground(Color.GRAY);
        contenPageCenter.setLayout(new BorderLayout());
        contenPageCenter.setBorder(null);
        imageSelected = new JPanel();
        imageSelected.setBackground(Color.WHITE);
        imageSelected.setBorder(null);
        panelContenImages = new JPanel();
        panelContenImages.setBackground(Color.WHITE);
        panelContenImages.setBorder(null);
        scroll = new JScrollPane(panelContenImages);
        scroll.setBackground(Color.WHITE);
        scroll.setBorder(null);
        contenPageCenter.setBorder(null);
        contenPageCenter.setBackground(Color.WHITE);
        contenPageCenter.add(imageSelected, BorderLayout.CENTER);
        contenPageCenter.add(scroll, BorderLayout.PAGE_END);

        JPanel panelContenButons = new JPanel();
        panelContenButons.setLayout(new GridLayout(1, 2));
        panelContenButons.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelContenButons.setBackground(Color.WHITE);

        butonSend = new JButton("ENVIAR IMAGEN");
        butonSend.setFont(font);
        butonSend.setContentAreaFilled(false);
        butonSend.setBorder(null);
        butonSend.setFocusPainted(false);
        butonSend.addActionListener(listener);
        butonSend.setActionCommand("ENVIAR");
        butonSend.setBackground(Color.BLUE);
        butonSend.addMouseListener(this);
        butonSolicitar = new JButton("DESCARGAR GALERIA");
        butonSolicitar.setFont(font);
        butonSolicitar.setContentAreaFilled(false);
        butonSolicitar.setFocusPainted(false);
        butonSolicitar.setBorder(null);
        butonSolicitar.addActionListener(listener);
        butonSolicitar.addMouseListener(this);
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
        panelContenImages.setBackground(Color.WHITE);
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
        scroll.setBackground(Color.WHITE);
        contenPageCenter.setBackground(Color.WHITE);
        contenPageCenter.add(scroll, BorderLayout.PAGE_END);
        contenPageCenter.revalidate();
        repaint();
    }

    public void paintImageSelected(ImageIcon image) {
        ImageIcon img = new ImageIcon(image.getImage().getScaledInstance(imageSelected.getWidth() - 50,
                imageSelected.getHeight() - 50, Image.SCALE_SMOOTH));
        imageSelected.removeAll();
        JLabel viewImage = new JLabel(img);
        viewImage.setBackground(Color.WHITE);
        imageSelected.setBackground(Color.WHITE);
        imageSelected.add(viewImage);
        viewImage.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
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

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        e.getComponent().setForeground(Color.BLUE);
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        e.getComponent().setForeground(Color.BLACK);
    }

}
