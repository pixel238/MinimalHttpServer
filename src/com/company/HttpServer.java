package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer implements Runnable {

    private Socket newClient;
    private sendFile s = new sendFile();
    private getFileName g = new getFileName();

    public HttpServer(Socket clientSocket) {
        newClient = clientSocket;
    }

    @Override
    public void run() {
        try {
                s.sendFile(newClient, g.getFileName(newClient));
        } finally {
            try {
                newClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server Listening");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected to User");
                Thread t = new Thread(new HttpServer(socket));
                t.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}