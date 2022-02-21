package com.codurance.training.tasks;

import java.io.*;

public class TaskPrinter {

    private PrintWriter printer;

    public TaskPrinter(PipedOutputStream insStream, Boolean autoFlush) {
        this.printer = new PrintWriter(insStream, autoFlush);
    }

    public TaskPrinter(PipedInputStream out) throws IOException {
        this.printer = new PrintWriter(new PipedOutputStream(out), true);

    }
    public TaskPrinter(PrintWriter out) {
        this.printer = out;
    }

    public void print(String task) {
        printer.print(task);
    }

    public void println() {
        printer.println();
    }

    public void println(String task) {
        printer.println(task);
    }

    public void printf(String task, Object ... args) {
        printer.printf(task, args);
    }

    public void flush() {
        printer.flush();
    }

}
