package models;

import java.io.IOException;
import java.net.Socket;

/**
 * clase ConnectionHandler
 */
class ConnectionHandler implements Runnable {
	private Socket clientSocket;
	private LogicaServer logica;

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
							logica.sendImages(clientSocket, logica.getListImages());
							System.out.println("envia las imagenes al cliente");
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case 0:

						logica.recibeImagen(clientSocket);

						break;
					default:
						break;
				}
			}
		} catch (IOException e) {

		}

	}
}