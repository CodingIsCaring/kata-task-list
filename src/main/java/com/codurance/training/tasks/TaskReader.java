package com.codurance.training.tasks;

import java.io.*;

public class TaskReader {

    private BufferedReader reader;

    public TaskReader(PipedInputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void read(char[] buffer, int offset, int length) throws IOException {
        reader.read(buffer, offset, length);
    }

}
