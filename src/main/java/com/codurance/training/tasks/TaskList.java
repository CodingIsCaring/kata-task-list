package com.codurance.training.tasks;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private TaskReader reader;
    private TaskPrinter printer;

    private long lastId = 0;

    public static void main(String[] args) {
        TaskReader taskReader = new TaskReader((PipedInputStream) System.in);
        TaskPrinter taskPrinter = new TaskPrinter(new PrintWriter(System.out));
        new TaskList(taskReader, taskPrinter).run();
    }

    public TaskList(TaskReader taskReader, TaskPrinter taskPrinter) {
        this.reader = taskReader;
        this.printer = taskPrinter;
    }

    public void run() {
        while (true) {
            printer.print("> ");
            printer.flush();
            String command;
            try {
                command = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            execute(command);
        }
    }

    private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                show();
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                help();
                break;
            default:
                error(command);
                break;
        }
    }

    private void show() {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            printer.println(project.getKey());
            for (Task task : project.getValue()) {
                printer.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            printer.println();
        }
    }

    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(projectTask[0], projectTask[1]);
        }
    }

    private void addProject(String name) {
        tasks.put(name, new ArrayList<>());
    }

    private void addTask(String project, String description) {
        List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            printer.printf("Could not find a project with the name \"%s\".", project);
            printer.println();
            return;
        }
        projectTasks.add(new Task(nextId(), description));
    }

    private void check(String idString) {
        setDone(idString, true);
    }

    private void uncheck(String idString) {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        printer.printf("Could not find a task with an ID of %d.", id);
        printer.println();
    }

    private void help() {
        printer.println("Commands:");
        printer.println("  show");
        printer.println("  add project <project name>");
        printer.println("  add task <project name> <task description>");
        printer.println("  check <task ID>");
        printer.println("  uncheck <task ID>");
        printer.println();
    }

    private void error(String command) {
        printer.printf("I don't know what the command \"%s\" is.", command);
        printer.println();
    }

    private long nextId() {
        return ++lastId;
    }
}
