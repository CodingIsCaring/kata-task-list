package com.codurance.training.tasks;

import java.io.*;

public class TaskPrinter {

    private PrintWriter printer;

    public TaskPrinter(PipedOutputStream outputStream, Boolean autoFlush) {
        this.printer = new PrintWriter(outputStream, autoFlush);
    }

    public TaskPrinter(PipedInputStream inputStream) throws IOException {
        this.printer = new PrintWriter(new PipedOutputStream(inputStream), true);
    }

    public TaskPrinter(PrintWriter printer) {
        this.printer = printer;
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
