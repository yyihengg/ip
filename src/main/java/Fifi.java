import java.util.Scanner;
import java.util.ArrayList;

/**
 * Entry point for the Fifi chatbot application.
 */


public class Fifi {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
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
            String command = input.split(" ", 2)[0];
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
                        int taskNumber = Integer.parseInt(input.substring(4).trim()) - 1; // -1 to convert back from 1-indexed (for user) to 0-indexed for tasks arraylist
                        Task currentTask = tasks.get(taskNumber);
                        currentTask.mark();
                        System.out.printf(responseFormat, String.format("Nice! I've marked this task as done:\n%s", currentTask));
                    }

                    case "unmark" -> {
                        int taskNumber = Integer.parseInt(input.substring(6).trim()) - 1; // -1 to convert back from 1-indexed (for user) to 0-indexed for tasks arraylist
                        Task currentTask = tasks.get(taskNumber);
                        currentTask.unmark();
                        System.out.printf(responseFormat, String.format("OK, I've marked this task as not done yet:\n%s", currentTask));

                    }

                    case "todo" -> {
                        Task newTask = getToDo(tasks, input);
                        tasks.add(newTask);
                        System.out.printf(responseFormat, String.format(
                                "Got it. I've added this task:%n"
                                        + "%s%n"
                                        + "Now you have %d tasks in the list.",
                                newTask,
                                tasks.size()
                        ));
                    }

                    case "deadline" -> {
                        Task newTask = getDeadline(tasks, input);
                        tasks.add(newTask);
                        System.out.printf(responseFormat, String.format(
                                "Got it. I've added this task:%n"
                                        + "%s%n"
                                        + "Now you have %d tasks in the list.",
                                newTask,
                                tasks.size()
                        ));
                    }

                    case "event" -> {
                        Task newTask = getEvent(tasks, input);
                        tasks.add(newTask);
                        System.out.printf(responseFormat, String.format(
                                "Got it. I've added this task:%n"
                                        + "%s%n"
                                        + "Now you have %d tasks in the list.",
                                newTask,
                                tasks.size()
                        ));
                    }

                    default -> throw new InvalidCommandException("""
                                UhOh, this command is invalid, please enter a valid one!
                                Valid commands include "list, todo, event, deadline, mark, unmark"\
                                """);

                }
            } catch (FiFiException e) {
                System.out.printf(responseFormat, e.getMessage());
            }
        }
    }

    private static Task getEvent(ArrayList<Task> tasks, String input) throws ExcessiveTaskException, InvalidDescriptionException{
        if (tasks.size() >= 100) {
            throw new ExcessiveTaskException(
                    """
                    You have exceeded the cap of 100 tasks! Delete old tasks in order to make space for new tasks.\
                    """);
        }
        String name = input.substring("event ".length(), input.indexOf("/from")).trim();
        String from = input.substring(input.indexOf("/from") + 5, input.indexOf("/to")).trim();
        String to = input.substring(input.indexOf("/to") + 3).trim();
        if (name.isBlank()) {
            throw new InvalidDescriptionException("Oops! You cannot have an empty event name");
        }
        return new Event(false, name, from, to);
    }

    private static Task getToDo(ArrayList<Task> tasks, String input) throws ExcessiveTaskException, InvalidDescriptionException {
        if (tasks.size() >= 100) {
            throw new ExcessiveTaskException(
                    """
                    You have exceeded the cap of 100 tasks! Delete old tasks in order to make space for new tasks.\
                    """);
        }
        String name = input.substring("todo ".length());
        if (name.isBlank()) {
            throw new InvalidDescriptionException("Oops! You cannot have an empty todo name");
        }
        return new ToDo(false, name);
    }

    private static Task getDeadline(ArrayList<Task> tasks, String input) throws ExcessiveTaskException, InvalidDescriptionException {
        if (tasks.size() >= 100) {
            throw new ExcessiveTaskException(
                    """
                    You have exceeded the cap of 100 tasks! Delete old tasks in order to make space for new tasks.\
                    """);
        }
        String name = input.substring("deadline ".length(), input.indexOf("/by")).trim();
        String deadline = input.substring(input.indexOf("/by") + 3).trim();
        if (name.isBlank()) {
            throw new InvalidDescriptionException("Oops! You cannot have an empty deadline name");
        }
        return new Deadline(false, name, deadline);
    }
}
