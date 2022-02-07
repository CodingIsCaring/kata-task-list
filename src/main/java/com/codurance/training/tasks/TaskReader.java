package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;

public class TaskReader {

    private BufferedReader reader;

    public TaskReader(BufferedReader reader) {
        this.reader = reader;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

}
