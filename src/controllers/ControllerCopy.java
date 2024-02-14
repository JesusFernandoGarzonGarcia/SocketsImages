package controllers;

import models.Clients;

public class ControllerCopy {

    public static void main(String[] args) {
        // new Servers(15555);
        Clients client = new Clients("localhost", 15555);

        client.solicitarImagenes();
    }

}
