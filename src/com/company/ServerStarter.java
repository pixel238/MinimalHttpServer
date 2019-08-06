package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ServerStarter {
    static Logger logger = Logger.getLogger(HttpServer.class.getName());
    static boolean isServerRunning = true;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            FileHandler handler = new FileHandler("default.log", true);
            logger.addHandler(handler);
            logger.info("Server Listening");
            while (isServerRunning) {
                Socket socket = serverSocket.accept();
                logger.info("User connected");
                Thread t = new Thread(new HttpServer(socket));
                t.start();
            }
            serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}