package com.company;

import java.io.*;
import java.net.Socket;

public class HttpServer implements Runnable {

    private final Socket newClient;
    private SendTheFileRequested sendTheFileRequested = new SendTheFileRequested();
    private ExtractFileName extractFileName = new ExtractFileName();

    public HttpServer(Socket clientSocket) {
        newClient = clientSocket;
    }

    @Override
    public void run() {
        try (newClient;
             InputStreamReader br = (new InputStreamReader(newClient.getInputStream()));
             DataOutputStream dos = new DataOutputStream(newClient.getOutputStream())
        ) {
            String fileName = extractFileName.extractFileName(br);
            sendTheFileRequested.sendFile(dos, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}