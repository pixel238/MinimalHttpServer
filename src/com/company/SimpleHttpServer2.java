package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.BitSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleHttpServer2 {

    public static void main(String[] args) {
        FileInputStream fis=null;
        BufferedInputStream bis=null;
        OutputStream os=null;
        try {
            final ServerSocket server = new ServerSocket(8080);
            System.out.println("Listening for connection on port 8080");
            while (true) {
                final Socket client;
                client = server.accept();
                System.out.println("Client Accepted");
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream());
                out.print("HTTP/1.1 200 \r\n");
                out.print("Content-Type: text/plain\r\n"); // The type of data
                out.print("Connection: close\r\n"); // Will close stream
                out.print("\r\n");
                System.out.println("Message sent");
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.length() == 0)
                        break;
                    out.print(line + "\r\n");
                }
                out.close();
                br.close();
                client.close();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}