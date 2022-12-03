package com.edu.filestats.contentreader.internal;

import com.edu.filestats.contentreader.ContentReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
@Slf4j
public class UTF8PlainTextReader implements ContentReader {

    @Override
    public String getName() {
        return "txt";
    }

    @Override
    public String read(String resourceUrl) {
        try (BufferedReader br = new BufferedReader(new FileReader(resourceUrl))) {

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            log.error("At reading. file=" + resourceUrl, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean canRead(String resourceUrl) {
        var file = new File(resourceUrl);
        var validExtension = resourceUrl.toLowerCase().endsWith(".txt");
        //TODO: implement an algorithm to determine if the content is UTF8
        return validExtension && file.exists() && file.canRead();
    }
}
