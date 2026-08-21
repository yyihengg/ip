import java.util.Scanner;
import java.util.ArrayList;

/**
 * Entry point for the Fifi chatbot application.
 */


public class Fifi {
    public static void main(String[] args) {
        ArrayList<String> tasks = new ArrayList<>();
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
            if (input.equals("bye")) {
                System.out.printf(responseFormat, "BaiBai! Hope to see you soon ^^");
                break;
            }
            if (input.equals("list")) {
                StringBuilder listed_tasks = new StringBuilder();
                for (int i = 0; i < tasks.size(); i++) {
                    if (i > 0) {
                        listed_tasks.append("\n    ");
                    }
                    listed_tasks.append(i + 1)
                                .append(". ")
                                .append(tasks.get(i));
                }
                System.out.printf(responseFormat, listed_tasks);
            } else if (tasks.size() < 100) {
                tasks.add(input);
                System.out.printf(responseFormat, String.format("added: %s", input));
            }

        }
    }
}
