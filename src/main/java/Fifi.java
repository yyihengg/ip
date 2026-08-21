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
            String[] parts = input.split("\\s+");
            String command = parts[0];
            if (command.equals("bye")) {
                System.out.printf(responseFormat, "BaiBai! Hope to see you soon ^^");
                break;
            }
            if (command.equals("list")) {
                StringBuilder printedTasks = new StringBuilder();
                for (int i = 0; i < tasks.size(); i++) {
                    if (i > 0) {
                        printedTasks.append("\n    ");
                    }
                    printedTasks.append(i + 1)
                                .append(". ")
                                .append(tasks.get(i).toString());
                }
                System.out.printf(responseFormat, String.format("Here are the tasks in your list: \n    %s", printedTasks));

            } else if (command.equals("mark")) {
                int taskNumber = Integer.parseInt(parts[1]) - 1; // -1 to convert back from 1-indexed (for user) to 0-indexed for tasks arraylist
                Task currentTask = tasks.get(taskNumber);
                currentTask.mark();
                System.out.printf(responseFormat, String.format("Nice! I've marked this task as done:\n   %s", currentTask));

            } else if (command.equals("unmark")) {
                int taskNumber = Integer.parseInt(parts[1]) - 1; // -1 to convert back from 1-indexed (for user) to 0-indexed for tasks arraylist
                Task currentTask = tasks.get(taskNumber);
                currentTask.unmark();
                System.out.printf(responseFormat, String.format("OK, I've marked this task as not done yet:\n     %s", currentTask));

            } else if (tasks.size() < 100) {
                tasks.add(new Task(false, input));
                System.out.printf(responseFormat, String.format("added: %s", input));
            }
        }
    }
}
