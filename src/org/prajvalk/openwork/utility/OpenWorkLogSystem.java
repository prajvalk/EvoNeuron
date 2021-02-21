package org.prajvalk.openwork.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class OpenWorkLogSystem {

    private String outputfile;

    public void log(String message) {
        try {
            Files.write(Paths.get(outputfile), (message+"\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException ioException) {
            // Nothing
        }
    }

}
