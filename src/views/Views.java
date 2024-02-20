package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.ACTIONS;

/**
 * clase Views encargada de mostrar el menu inicial al usuario
 */

public class Views extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton jButton1;
	private JLabel jLabel1;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private ActionListener action;

	private Boolean validate;

	/**
	 * constructor de la clase views
	 * 
	 * @param action hilo de las acciones realizadas por el usuario
	 */

	public Views(ActionListener action) {
		this.action = action;
		validate = true;
		initComponents();
		mensajeServer();
	}

	/**
	 * inicializa los parametros de la views
	 */

	private void initComponents() {

		setLocationRelativeTo(rootPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Galeria online");

		getContentPane().setBackground(Color.WHITE);
		setIconImage(new ImageIcon(getClass().getResource("/img/logo.png")).getImage());

		jLabel1 = new JLabel();
		jTextField1 = new JTextField();
		jTextField2 = new JTextField();
		jButton1 = new JButton();

		jLabel1.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setText("Bienvenido");

		jTextField1.setBorder(BorderFactory.createTitledBorder("Ingresa Nickname"));

		jTextField2.setBorder(BorderFactory.createTitledBorder("IP del servidor 0.0.0.0:0000"));

		jButton1.setText("Ingresar");
		jButton1.setActionCommand(ACTIONS.ingresarAServidor.toString());
		jButton1.addActionListener(action);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGap(233, 233, 233)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
										.addComponent(jTextField2)
										.addComponent(jTextField1)
										.addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
								.addContainerGap(226, Short.MAX_VALUE))
						.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
								.addGap(217, 217, 217)));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGap(20, 20, 20)
								.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(32, 32, 32)
								.addComponent(jButton1)
								.addContainerGap(33, Short.MAX_VALUE)));

		pack();
	}

	/**
	 * @return datos ingresados para la coneccion al servidor
	 */
	public String[] getDataConection() {
		return new String[] { jTextField2.getText(), jTextField1.getText() };
	}

	private void mensajeServer() {
		jButton1.setEnabled(false);
		Thread t = new Thread(() -> {
			while (validate) {
				if (jTextField1.getText() != null && jTextField2.getText() != null) {
					String ip = jTextField1.getText();
					String name = jTextField2.getText();
					if (!ip.equals("") && !name.equals("")) {
						jButton1.setEnabled(true);
					} else {
						jButton1.setEnabled(false);
					}
				}
			}
		});
		t.start();
	}

}
