package com.codurance.training.tasks;

import java.io.PrintWriter;

public class TaskPrinter {

    private PrintWriter printer;

    public TaskPrinter() {
        this.printer = new PrintWriter(System.out);
    }

    public TaskPrinter(Boolean autoFlush) {
        this.printer = new PrintWriter(System.out, autoFlush);
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
