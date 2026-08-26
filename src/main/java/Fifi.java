import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Entry point for the Fifi chatbot application.
 */


public class Fifi {
    public static void main(String[] args) {
        ArrayList<Task> tasks = loadTasks();
        String line = "____________________________________________________________";
        String responseFormat = line + "%n"
                + "%s%n"
                + line + "%n";
        String banner = """
                _____ _  __ __
                |  ___(_)/ _(_)
                | |_  | | |_| |
                |  _| | |  _| |
                |_|   |_|_| |_|
                """;
        System.out.print(banner);
        String greetings = line + "\n"
                + "Hello! My name is Fifi ^^\n"
                + "How may I help?\n"
                + line + "\n";
        System.out.print(greetings);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String command = Parser.parseCommand(input);
            try {
                switch (command) {
                    case "bye" -> {
                        System.out.printf(responseFormat, "BaiBai! Hope to see you soon ^^");
                        return;
                    }

                    case "list" -> {
                        StringBuilder printedTasks = new StringBuilder();
                        for (int i = 0; i < tasks.size(); i++) {
                            printedTasks.append("\n");
                            printedTasks.append(i + 1)
                                    .append(". ")
                                    .append(tasks.get(i).toString());
                        }
                        System.out.printf(responseFormat, String.format("Here are the tasks in your list:%s", printedTasks));
                    }

                    case "mark" -> {
                        int taskNumber = Parser.parseTaskNumber(input, "mark");
                        Task currentTask = tasks.get(taskNumber);
                        currentTask.mark();
                        saveTasks(tasks);
                        System.out.printf(responseFormat, String.format("Nice! I've marked this task as done:\n%s", currentTask));
                    }

                    case "unmark" -> {
                        int taskNumber = Parser.parseTaskNumber(input, "unmark");
                        Task currentTask = tasks.get(taskNumber);
                        currentTask.unmark();
                        saveTasks(tasks);
                        System.out.printf(responseFormat, String.format("OK, I've marked this task as not done yet:\n%s", currentTask));

                    }

                    case "todo" -> {
                        Task newTask = Parser.parseToDo(tasks, input);
                        tasks.add(newTask);
                        saveTasks(tasks);
                        System.out.printf(responseFormat, String.format(
                                "Got it. I've added this task:%n"
                                        + "%s%n"
                                        + "Now you have %d tasks in the list.",
                                newTask,
                                tasks.size()
                        ));
                    }

                    case "deadline" -> {
                        Task newTask = Parser.parseDeadline(tasks, input);
                        tasks.add(newTask);
                        saveTasks(tasks);
                        System.out.printf(responseFormat, String.format(
                                "Got it. I've added this task:%n"
                                        + "%s%n"
                                        + "Now you have %d tasks in the list.",
                                newTask,
                                tasks.size()
                        ));
                    }

                    case "event" -> {
                        Task newTask = Parser.parseEvent(tasks, input);
                        tasks.add(newTask);
                        saveTasks(tasks);
                        System.out.printf(responseFormat, String.format(
                                "Got it. I've added this task:%n"
                                        + "%s%n"
                                        + "Now you have %d tasks in the list.",
                                newTask,
                                tasks.size()
                        ));
                    }

                    case "delete" -> {
                        int taskNumber = Parser.parseTaskNumber(input, "delete");
                        Task removedTask = tasks.remove(taskNumber);
                        saveTasks(tasks);
                        System.out.printf(responseFormat,
                                String.format(
                                """
                                Got it. I've removed this task:
                                    %s
                                Now you have %d tasks in the list ^^.\
                                """, removedTask, tasks.size()));
                    }

                    default -> throw new InvalidCommandException("""
                                UhOh, this command is invalid, please enter a valid one!
                                Valid commands include "list, todo, event, deadline, mark, unmark, delete"\
                                """);

                }
            } catch (FiFiException e) {
                System.out.printf(responseFormat, e.getMessage());
            } catch (IOException e) {
                System.out.printf(responseFormat, "Oops! I could not save your tasks to the hard disk.");
            }
        }
    }

    private static void saveTasks(ArrayList<Task> tasks) throws IOException {
        Storage.saveTasks(tasks);
    }

    private static ArrayList<Task> loadTasks() {
        try {
            return Storage.loadTasks();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
