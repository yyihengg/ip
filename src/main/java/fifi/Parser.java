package fifi;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import fifi.exception.ExcessiveTaskException;
import fifi.exception.InvalidDescriptionException;
import fifi.task.Deadline;
import fifi.task.Event;
import fifi.task.Task;
import fifi.task.ToDo;

/**
 * Converts raw user input into commands, task numbers, and task objects.
 */
public class Parser {
    /**
     * Maximum number of tasks allowed in the chatbot's task list.
     */
    private static final int MAX_TASKS = 100;
    private static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    /**
     * Converts a date string into a {@code LocalDate}.
     *
     * @param input the date string in yyyy-MM-dd format
     * @return the date represented by the input
     * @throws DateTimeException if the input does not match the required format
     */
    public static LocalDate parseDate(String input) throws DateTimeException {
        return LocalDate.parse(input, INPUT_DATE_FORMATTER);
    }

    /**
     * Converts the date in a show command into a {@code LocalDate}.
     *
     * @param input the full line typed by the user
     * @return the date requested by the user
     * @throws InvalidDescriptionException if the show date is missing
     * @throws DateTimeException if the date does not match the required format
     */
    public static LocalDate parseShowDate(String input) throws InvalidDescriptionException, DateTimeException {
        String date = input.substring("show".length()).trim();
        if (date.isBlank()) {
            throw new InvalidDescriptionException("Oops! You did not provide a date to show");
        }
        return parseDate(date);
    }

    /**
     * Converts a date back into the format accepted by the chatbot.
     *
     * @param date the date to save
     * @return the saved date string
     */
    public static String formatDateForStorage(LocalDate date) {
        return date.format(INPUT_DATE_FORMATTER);
    }

    /**
     * Converts a date into the format printed to the user.
     *
     * @param date the date to display
     * @return the display date string
     */
    public static String formatDateForDisplay(LocalDate date) {
        return date.format(DISPLAY_DATE_FORMATTER);
    }

    /**
     * Returns the command word from the user's input.
     *
     * @param input the full line typed by the user
     * @return the command word at the start of the input
     */
    public static String parseCommand(String input) {
        return input.split(" ", 2)[0];
    }

    /**
     * Returns the zero-based task index from a command such as {@code mark 1}.
     *
     * @param input the full line typed by the user
     * @param command the command word before the task number
     * @return the zero-based task index
     */
    public static int parseTaskNumber(String input, String command) {
        return Integer.parseInt(input.substring(command.length()).trim()) - 1;
    }

    /**
     * Creates a todo task from the user's input.
     *
     * @param tasks the current task list
     * @param input the full line typed by the user
     * @return the todo task described by the input
     * @throws ExcessiveTaskException if the task list is already full
     * @throws InvalidDescriptionException if the todo name is empty
     */
    public static Task parseToDo(ArrayList<Task> tasks, String input)
            throws ExcessiveTaskException, InvalidDescriptionException {
        checkTaskLimit(tasks);

        String name = input.substring("todo".length()).trim();
        if (name.isBlank()) {
            throw new InvalidDescriptionException("Oops! You cannot have an empty todo name");
        }
        return new ToDo(false, name);
    }

    /**
     * Creates a deadline task from the user's input.
     *
     * @param tasks the current task list
     * @param input the full line typed by the user
     * @return the deadline task described by the input
     * @throws ExcessiveTaskException if the task list is already full
     * @throws InvalidDescriptionException if the deadline name or date is missing
     */
    public static Task parseDeadline(ArrayList<Task> tasks, String input)
            throws ExcessiveTaskException, InvalidDescriptionException, DateTimeException {
        checkTaskLimit(tasks);

        int byIndex = input.indexOf("/by");
        if (byIndex == -1 || input.substring(byIndex + "/by".length()).trim().isEmpty()) {
            throw new InvalidDescriptionException("Oops! You did not provide a date for the deadline");
        }

        String name = input.substring("deadline".length(), byIndex).trim();
        if (name.isBlank()) {
            throw new InvalidDescriptionException("Oops! You cannot have an empty deadline name");
        }

        String deadlineDate = input.substring(byIndex + "/by".length()).trim();
        return new Deadline(false, name, parseDate(deadlineDate));
    }

    /**
     * Creates an event task from the user's input.
     *
     * @param tasks the current task list
     * @param input the full line typed by the user
     * @return the event task described by the input
     * @throws ExcessiveTaskException if the task list is already full
     * @throws InvalidDescriptionException if the event name, start date, or end date is missing
     */
    public static Task parseEvent(ArrayList<Task> tasks, String input)
            throws ExcessiveTaskException, InvalidDescriptionException {
        checkTaskLimit(tasks);

        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");
        if (toIndex == -1 || input.substring(toIndex + "/to".length()).trim().isEmpty()) {
            throw new InvalidDescriptionException("Oops! You did not provide an end date for the event");
        }
        if (fromIndex == -1 || fromIndex > toIndex
                || input.substring(fromIndex + "/from".length(), toIndex).trim().isEmpty()) {
            throw new InvalidDescriptionException("Oops! You did not provide a start date for the event");
        }

        String name = input.substring("event".length(), fromIndex).trim();
        if (name.isBlank()) {
            throw new InvalidDescriptionException("Oops! You cannot have an empty event name");
        }

        String from = input.substring(fromIndex + "/from".length(), toIndex).trim();
        String to = input.substring(toIndex + "/to".length()).trim();
        return new Event(false, name, parseDate(from), parseDate(to));
    }

    private static void checkTaskLimit(ArrayList<Task> tasks) throws ExcessiveTaskException {
        if (tasks.size() >= MAX_TASKS) {
            throw new ExcessiveTaskException(
                    """
                    You have exceeded the cap of 100 tasks! Delete old tasks in order to make space for new tasks.\
                    """);
        }
    }
}
