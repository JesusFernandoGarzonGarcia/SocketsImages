package controllers;

import models.Servers;

public class Controller {

    public Controller(int port) {
        new Servers(port);
    }
}
