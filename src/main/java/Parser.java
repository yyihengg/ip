import java.util.ArrayList;

/**
 * Converts raw user input into commands, task numbers, and task objects.
 */
public class Parser {
    /** Maximum number of tasks allowed in the chatbot's task list. */
    private static final int MAX_TASKS = 100;

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
            throws ExcessiveTaskException, InvalidDescriptionException {
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
        return new Deadline(false, name, deadlineDate);
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
        return new Event(false, name, from, to);
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
