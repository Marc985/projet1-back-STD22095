package org.event.backendpatrimoine.util;

import org.event.backendpatrimoine.modal.Patrimony;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class File {
    public void writeFile(Patrimony patrimoiny)
            throws IOException {
        String str = patrimoiny.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter(patrimoiny.getName()));
        writer.write(str);

        writer.close();
    }
}
