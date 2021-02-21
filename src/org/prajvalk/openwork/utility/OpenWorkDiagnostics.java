package org.prajvalk.openwork.utility;

import org.prajvalk.openwork.exceptions.OpenWorkDiagnosticIOException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Vector;

public class OpenWorkDiagnostics {

    private Vector<String> diagnosticNodes;

    private boolean isEnabled;

    public OpenWorkDiagnostics() {
        diagnosticNodes = new Vector<>(1,1);
    }

    public void newDiagnosticNode(String nodename, String... header) throws OpenWorkDiagnosticIOException {
        if(!isEnabled) return;
        diagnosticNodes.addElement(nodename);
        Vector<String[]> vector = new Vector<>(1,1);
        vector.addElement(header);
        try {
            File file = new File(nodename + ".owdiag.csv");
            if (!file.exists()) file.createNewFile();
            else {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException ioException) {
            throw new OpenWorkDiagnosticIOException(ioException.getMessage());
        }
        addDiagnosticData(nodename, header);
    }

    public void addDiagnosticData(String nodename, String... data) throws OpenWorkDiagnosticIOException {
        if(!isEnabled) return;
        for(int i=0;i<diagnosticNodes.size();i++) if(diagnosticNodes.elementAt(i).equals(nodename)) {
            String csvrecord = "";
            for(String element:data) csvrecord += element + ",";
            if(csvrecord.endsWith(",")) csvrecord = csvrecord.substring(0, csvrecord.length()-1);
            try {
                Files.write(Paths.get(nodename + ".owdiag.csv"), (csvrecord + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            } catch (IOException ioException) {
                throw new OpenWorkDiagnosticIOException(ioException.getMessage());
            }
        }
    }

    public void enable() {
        isEnabled = true;
    }

}
