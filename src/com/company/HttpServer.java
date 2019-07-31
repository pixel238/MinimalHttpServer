package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer implements Runnable {

    static final String FILE_NOT_FOUND = "404.html";
    private Socket connect;

    public HttpServer(Socket clientSocket) {
        connect = clientSocket;
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String line = br.readLine();
            System.out.println(line);
            Pattern pattern = Pattern.compile("GET /(.*) HTTP/1.1");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String fileRequested = matcher.group(1);
                System.out.println(fileRequested);

                File file = new File(fileRequested);
                int numOfBytes = (int) file.length();
                FileInputStream inFile = new FileInputStream(fileRequested);
                byte[] fileInBytes = new byte[numOfBytes];
                inFile.read(fileInBytes);

                dos = new DataOutputStream(connect.getOutputStream());

                dos.writeBytes("HTTP/1.1 200 Document Follows\r\n");
                dos.writeBytes("Content-Type: html\r\n");
                dos.writeBytes("Content-Length: " + numOfBytes + "\r\n");
                dos.writeBytes("\r\n");
                dos.write(fileInBytes, 0, numOfBytes);
                dos.flush();
                connect.close();
            }
        } catch (FileNotFoundException e) {
            try {
                File file = new File(FILE_NOT_FOUND);
                int numOfBytes = (int) file.length();
                FileInputStream inFile = new FileInputStream(FILE_NOT_FOUND);
                byte[] fileInBytes = new byte[numOfBytes];
                inFile.read(fileInBytes);

                dos = new DataOutputStream(connect.getOutputStream());

                dos.writeBytes("HTTP/1.1 200 Document Follows\r\n");
                dos.writeBytes("Content-Type: html\r\n");
                dos.writeBytes("Content-Length: " + numOfBytes + "\r\n");
                dos.writeBytes("\r\n");
                dos.write(fileInBytes, 0, numOfBytes);
                dos.flush();
                connect.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("Server error ");
        } finally {
            try {
                dos.close();
                connect.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = null;

            serverSocket = new ServerSocket(8080);

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