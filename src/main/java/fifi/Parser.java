package fifi;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import fifi.command.AddDeadlineCommand;
import fifi.command.AddEventCommand;
import fifi.command.AddTodoCommand;
import fifi.command.Command;
import fifi.command.DeleteCommand;
import fifi.command.ExitCommand;
import fifi.command.ListCommand;
import fifi.command.MarkCommand;
import fifi.command.ShowCommand;
import fifi.command.UnmarkCommand;
import fifi.exception.InvalidCommandException;
import fifi.exception.InvalidDescriptionException;
import fifi.task.Deadline;
import fifi.task.Event;
import fifi.task.Task;
import fifi.task.ToDo;

/**
 * Converts raw user input into commands, task numbers, and task objects.
 */
public class Parser {
    private static final String DEADLINE_DATE_MARKER = "/by";
    private static final String EVENT_START_MARKER = "/from";
    private static final String EVENT_END_MARKER = "/to";

    private static final DateTimeFormatter INPUT_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    /**
     * Converts the user's full input into an executable command.
     *
     * @param input the full line typed by the user
     * @return the command represented by the input
     * @throws InvalidCommandException if the command word is not recognized
     * @throws InvalidDescriptionException if a command description is missing
     */
    public static Command parse(String input) throws InvalidCommandException, InvalidDescriptionException {
        String command = parseCommand(input);
        String arguments = parseArguments(input, command);
        return switch (command) {
            case "bye" -> new ExitCommand();
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(parseTaskIndex(arguments));
            case "unmark" -> new UnmarkCommand(parseTaskIndex(arguments));
            case "todo" -> new AddTodoCommand(parseToDo(arguments));
            case "deadline" -> new AddDeadlineCommand(parseDeadline(arguments));
            case "event" -> new AddEventCommand(parseEvent(arguments));
            case "delete" -> new DeleteCommand(parseTaskIndex(arguments));
            case "show" -> new ShowCommand(parseDateArgument(arguments));
            default -> throw new InvalidCommandException("""
                        UhOh, this command is invalid, please enter a valid one!
                        Valid commands include "list, todo, event, deadline, mark, unmark, delete, show"\
                        """);
        };
    }

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
        return parseDateArgument(parseArguments(input, "show"));
    }

    private static LocalDate parseDateArgument(String argument) throws InvalidDescriptionException {
        if (argument.isBlank()) {
            throw new InvalidDescriptionException("Oops! You did not provide a date to show");
        }
        return parseDate(argument);
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
    private static String parseCommand(String input) {
        return input.split(" ", 2)[0];
    }

    private static String parseArguments(String input, String command) {
        return input.substring(command.length()).trim();
    }

    /**
     * Returns the zero-based task index from a command such as {@code mark 1}.
     *
     * @param arguments the text following the command word
     * @return the zero-based task index
     */
    private static int parseTaskIndex(String arguments) {
        return Integer.parseInt(arguments) - 1;
    }

    /**
     * Creates a ToDo task from the user's input.
     *
     * @param arguments the text following the todo command
     * @return the ToDo task described by the arguments
     * @throws InvalidDescriptionException if the task name is missing
     */
    private static Task parseToDo(String arguments) throws InvalidDescriptionException {
        if (arguments.isBlank()) {
            throw new InvalidDescriptionException("Oops! You cannot have an empty todo name");
        }
        return new ToDo(false, arguments);
    }

    /**
     * Creates a deadline task from the user's input.
     *
     * @param arguments the text following the deadline command
     * @return the deadline task described by the arguments
     * @throws InvalidDescriptionException if the deadline name or date is missing
     * @throws DateTimeException if the date does not match the required format
     */
    private static Task parseDeadline(String arguments) throws InvalidDescriptionException, DateTimeException {
        int byIndex = arguments.indexOf(DEADLINE_DATE_MARKER);
        if (byIndex == -1
                || arguments.substring(byIndex + DEADLINE_DATE_MARKER.length()).trim().isEmpty()) {
            throw new InvalidDescriptionException("Oops! You did not provide a date for the deadline");
        }

        String name = arguments.substring(0, byIndex).trim();
        if (name.isBlank()) {
            throw new InvalidDescriptionException("Oops! You cannot have an empty deadline name");
        }

        String deadlineDate = arguments.substring(byIndex + DEADLINE_DATE_MARKER.length()).trim();
        return new Deadline(false, name, parseDate(deadlineDate));
    }

    /**
     * Creates an event task from the user's input.
     *
     * @param arguments the text following the event command
     * @return the event task described by the arguments
     * @throws InvalidDescriptionException if the event name, start date, or end date is missing
     */
    private static Task parseEvent(String arguments) throws InvalidDescriptionException {
        int fromIndex = arguments.indexOf(EVENT_START_MARKER);
        int toIndex = arguments.indexOf(EVENT_END_MARKER);
        boolean hasEndMarker = toIndex != -1;
        String to = hasEndMarker ? arguments.substring(toIndex + EVENT_END_MARKER.length()).trim() : "";
        if (to.isEmpty()) {
            throw new InvalidDescriptionException("Oops! You did not provide an end date for the event");
        }

        boolean hasOrderedDateMarkers = fromIndex != -1 && fromIndex < toIndex;
        String from = hasOrderedDateMarkers
                ? arguments.substring(fromIndex + EVENT_START_MARKER.length(), toIndex).trim()
                : "";
        if (from.isEmpty()) {
            throw new InvalidDescriptionException("Oops! You did not provide a start date for the event");
        }

        String name = arguments.substring(0, fromIndex).trim();
        if (name.isBlank()) {
            throw new InvalidDescriptionException("Oops! You cannot have an empty event name");
        }

        return new Event(false, name, parseDate(from), parseDate(to));
    }
}
