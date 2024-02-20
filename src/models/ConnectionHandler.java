package models;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * clase ConnectionHandler
 */
class ConnectionHandler implements Runnable {
	private Socket clientSocket;
	private LogicaServer logica;
	final Logger LOG = Logger.getLogger("models.ConnectionHandler");

	/**
	 * constructor de la clase ConnetionHandler, la cual se encarga de escuchar las
	 * peticiones de los clientes
	 * 
	 * @param clientSocket es el socket al cual se va a escuchar
	 * @param logica       es la instancia de la clase donde se ejecuta t¿la logica
	 *                     total del juego
	 */
	public ConnectionHandler(Socket clientSocket, LogicaServer logica) {
		this.clientSocket = clientSocket;
		this.logica = logica;
	}

	/**
	 * ejecuta el hilo el cual escucha las peticiones de los clientes
	 */
	public void run() {
		// Aquí va el código que maneja la conexión con el cliente.
		// Por ejemplo, leer y escribir datos en el socket.
		try {
			while (true) {
				int longRequest = clientSocket.getInputStream().read();

				switch (longRequest) {
					case 1:
						try {
							LOG.log(Level.INFO, "La direccion IP " + clientSocket
									+ " a solicitado recibir las imagenes almacenadas " + " " + LocalDateTime.now());
							logica.sendImages(clientSocket, logica.getListImages());
							LOG.log(Level.INFO, "Imagenes Enviadas a la direccion IP " + clientSocket + " con exito"
									+ " " + LocalDateTime.now());
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case 0:
						LOG.log(Level.INFO, "La direccion IP " + clientSocket
								+ " a solicitado enviar una imagen" + " " + LocalDateTime.now());
						logica.recibeImagen(clientSocket);
						LOG.log(Level.INFO,
								"Imagenes recibidas con exito desde la IP " + clientSocket + " " + LocalDateTime.now());
						break;
					default:
						break;
				}
			}
		} catch (IOException e) {

		}

	}
}