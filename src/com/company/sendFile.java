package com.company;
import java.io.*;
import java.net.Socket;
import java.util.Date;

public class sendFile {
    DataOutputStream dos = null;
    public void sendFile(Socket connect, String FileName){
        try {
            File file = new File(FileName);
            int numOfBytes = (int) file.length();
            FileInputStream inFile = new FileInputStream(FileName);
            byte[] fileInBytes = new byte[numOfBytes];
            inFile.read(fileInBytes);
            dos = new DataOutputStream(connect.getOutputStream());
            dos.writeBytes("HTTP/1.1 200 Document Follows\r\n");
            //dos.writeBytes("Server: Java HTTP Server from pixel : 1.0\r\n");
            //dos.writeBytes("Date: "+ new Date()+"\r\n");
            dos.writeBytes("Content-Type: text/html\r\n");
            dos.writeBytes("Content-Length: " + numOfBytes + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(fileInBytes, 0, numOfBytes);
            dos.flush();
            connect.close();
        } catch (FileNotFoundException e) {
            sendFile(connect, "404.html");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
                //connect.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}