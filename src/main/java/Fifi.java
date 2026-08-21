/**
 * Entry point for the Fifi chatbot application.
 */

import java.util.Objects;
import java.util.Scanner;
public class Fifi {
    public static void main(String[] args) {
        String banner = " _____ _  __ _ \n"
                + "|  ___(_)/ _(_)\n"
                + "| |_  | | |_| |\n"
                + "|  _| | |  _| |\n"
                + "|_|   |_|_| |_|\n";
        System.out.println("____________________________________________________________");
        System.out.println(banner);
        String greetings = """
                Hello! My name is Fifi ^^
                How may I help?
                ____________________________________________________________
                """;
        System.out.print(greetings);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String response;
            String input = scanner.nextLine();
            if (input.equals("bye")) {

                System.out.print("""
                    ____________________________________________________________
                    BaiBai! Hope to see you soon ^^
                    ____________________________________________________________
                    """);
                break;
            }
            System.out.printf("""
                    ____________________________________________________________
                    %s
                    ____________________________________________________________
                    
                    """, input);
        }
    }
}
