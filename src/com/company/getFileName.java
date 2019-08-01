package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getFileName {
    public String getFileName(Socket connect) {
        try {
            InputStreamReader br = (new InputStreamReader(connect.getInputStream()));
            char []buff=new char[2048];
             br.read(buff);
             String line=String.valueOf(buff);
            System.out.println(line);
            Pattern pattern = Pattern.compile("GET /(.*) HTTP/1.1");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String fileRequested = matcher.group(1);
                System.out.println(fileRequested);
                return fileRequested;
            }
        } catch (IOException e) {
            System.err.println("Server error ");
        }
        return "Not Found";
    }
}