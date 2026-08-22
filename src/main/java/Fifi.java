import java.util.Scanner;
import java.util.ArrayList;

/**
 * Entry point for the Fifi chatbot application.
 */


public class Fifi {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        String responseFormat = """
                    ____________________________________________________________
                %s
                    ____________________________________________________________
                """;
        String banner = """
                     _____ _  __ __
                    |  ___(_)/ _(_)
                    | |_  | | |_| |
                    |  _| | |  _| |
                    |_|   |_|_| |_|
                """;
        System.out.print(banner);
        String greetings = """
                    ____________________________________________________________
                    Hello! My name is Fifi ^^
                    How may I help?
                    ____________________________________________________________
                """;
        System.out.print(greetings);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String command = input.split(" ", 2)[0];
            switch (command) {
                case "bye" -> {
                    System.out.printf(responseFormat, "BaiBai! Hope to see you soon ^^");
                    return;
                }

                case "list" -> {
                    StringBuilder printedTasks = new StringBuilder();
                    for (int i = 0; i < tasks.size(); i++) {
                        printedTasks.append("\n    ");
                        printedTasks.append(i + 1)
                                .append(". ")
                                .append(tasks.get(i).toString());
                    }
                    System.out.printf(responseFormat, String.format("    Here are the tasks in your list:%s", printedTasks));
                }

                case "mark" -> {
                    int taskNumber = Integer.parseInt(input.substring(4).trim()) - 1; // -1 to convert back from 1-indexed (for user) to 0-indexed for tasks arraylist
                    Task currentTask = tasks.get(taskNumber);
                    currentTask.mark();
                    System.out.printf(responseFormat, String.format("    Nice! I've marked this task as done:\n    %s", currentTask));
                }

                case "unmark" -> {
                    int taskNumber = Integer.parseInt(input.substring(6).trim()) - 1; // -1 to convert back from 1-indexed (for user) to 0-indexed for tasks arraylist
                    Task currentTask = tasks.get(taskNumber);
                    currentTask.unmark();
                    System.out.printf(responseFormat, String.format("    OK, I've marked this task as not done yet:\n    %s", currentTask));

                }

                case "todo" -> {
                    if (tasks.size() >= 100) {
                        continue;
                    }
                    String name = input.substring("todo ".length());
                    Task newTask = new ToDo(false, name);
                    tasks.add(newTask);
                    System.out.printf(responseFormat, String.format(
                            "    Got it. I've added this task:%n"
                                    + "        %s%n"
                                    + "    Now you have %d tasks in the list.",
                            newTask,
                            tasks.size()
                    ));
                }

                case "deadline" -> {
                    if (tasks.size() >= 100) {
                        continue;
                    }
                    String name = input.substring("deadline ".length(), input.indexOf("/by")).trim();
                    String deadline = input.substring(input.indexOf("/by")+3).trim();
                    Task newTask = new Deadline(false, name, deadline);
                    tasks.add(newTask);
                    System.out.printf(responseFormat, String.format(
                            "    Got it. I've added this task:%n"
                                    + "        %s%n"
                                    + "    Now you have %d tasks in the list.",
                            newTask,
                            tasks.size()
                    ));
                }

                case "event" -> {
                    if (tasks.size() >= 100) {
                        continue;
                    }
                    String name = input.substring("event ".length(), input.indexOf("/from")).trim();
                    String from = input.substring(input.indexOf("/from") + 5, input.indexOf("/to")).trim();
                    String to = input.substring(input.indexOf("/to") + 3).trim();
                    Task newTask = new Event(false, name, from, to);
                    tasks.add(newTask);
                    System.out.printf(responseFormat, String.format(
                            "    Got it. I've added this task:%n"
                                    + "        %s%n"
                                    + "    Now you have %d tasks in the list.",
                            newTask,
                            tasks.size()
                    ));
                }
            }
        }
    }
}
