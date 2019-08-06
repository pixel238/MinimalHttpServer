package com.company;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ExtractFileName {
    static Logger logger = Logger.getLogger(ExtractFileName.class.getName());

    String extractFileName(InputStreamReader br) {

        try {
            char[] buff = new char[2048];
            br.read(buff);
            String line = String.valueOf(buff);
            logger.info(line);
            Pattern pattern = Pattern.compile("GET /(.*) HTTP/1.1");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String fileRequested = matcher.group(1);
                logger.info(fileRequested);
                return fileRequested;
            }
        } catch (IOException e) {
            logger.info("Server error");
        }
        return "Not Found";
    }
}