package controllers;

import javax.swing.JOptionPane;

public class ControladorGeneral {

    public ControladorGeneral() {
        Object[] options = { " Servidor", " Cliente" };

        // Mostrar el JOptionPane con las opciones
        int choice = JOptionPane.showOptionDialog(null, "Elige una opción:", "Título del JOptionPane",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        // Verificar la opción seleccionada
        if (choice == JOptionPane.YES_OPTION) {
            try {
                Integer port = Integer.parseInt(JOptionPane.showInputDialog("Ingresa un puerto disponible"));
                new Controller(port);
            } catch (Exception e) {
                System.err.println("Error al iniciar el servidor Ingresa un puerto valido");
                System.exit(0);
            }

        } else if (choice == JOptionPane.NO_OPTION) {
            new ControllerCopy();
        } else {
            System.exit(0);
        }

    }

    public static void main(String[] args) {
        new ControladorGeneral();
    }

}
