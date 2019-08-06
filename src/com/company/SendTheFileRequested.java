package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Date;

class SendTheFileRequested {
    void sendFile(DataOutputStream dos, String FileName) {
        try {
            File file = new File(FileName);
            int numOfBytes = (int) file.length();
            FileInputStream inFile = new FileInputStream(FileName);
            byte[] fileInBytes = new byte[numOfBytes];
            inFile.read(fileInBytes);
            dos.writeBytes("HTTP/1.1 200 Document Follows\r\n");
            //dos.writeBytes("Server: Java HTTP Server from pixel : 1.0\r\n");
            //dos.writeBytes("Date: "+ new Date()+"\r\n");
            dos.writeBytes("Content-Type: image/apng\r\n");
            dos.writeBytes("Content-Length: " + numOfBytes + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(fileInBytes, 0, numOfBytes);
            dos.flush();
            //dos.close();
        } catch (FileNotFoundException e) {
            sendFile(dos, "404.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}