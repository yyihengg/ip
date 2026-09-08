package fifi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import fifi.task.Deadline;
import fifi.task.Event;
import fifi.task.Task;
import fifi.task.ToDo;

/**
 * Handles loading and saving chatbot tasks on the hard disk.
 */
public class Storage {
    private static final String FIELD_SEPARATOR_REGEX = " \\| ";
    private static final String MARKED_STATUS = "1";
    private static final String DEADLINE_TASK_CODE = "D";
    private static final String EVENT_TASK_CODE = "E";

    private static final int TASK_TYPE_INDEX = 0;
    private static final int STATUS_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int DATE_INDEX = 3;
    private static final int END_DATE_INDEX = 4;

    private final Path filePath;

    /**
     * Creates a storage handler for the given task data file.
     *
     * @param filePath the path of the task data file
     */
    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    /**
     * Loads the saved task list from the hard disk.
     *
     * @return the tasks saved in the data file, or an empty list if the file does not exist
     * @throws IOException if the file cannot be read
     */
    public List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        List<String> savedTasks = Files.readAllLines(filePath);
        for (String savedTask : savedTasks) {
            tasks.add(parseTask(savedTask));
        }
        return tasks;
    }

    /**
     * Saves the full task list to the hard disk.
     *
     * @param tasks the current tasks in the chatbot
     * @throws IOException if the file or its parent directory cannot be written
     */
    public void saveTasks(TaskList tasks) throws IOException {
        Path parentDirectory = filePath.getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }

        StringJoiner savedTasks = new StringJoiner(System.lineSeparator());
        for (Task task : tasks.asList()) {
            savedTasks.add(task.toFileString());
        }

        Files.writeString(filePath, savedTasks.toString());
    }

    /**
     * Converts one saved line from the data file back into the correct task type.
     *
     * @param savedTask one line from the save file
     * @return the task represented by that line
     */
    private Task parseTask(String savedTask) {
        String[] parts = savedTask.split(FIELD_SEPARATOR_REGEX);
        boolean isMarked = parts[STATUS_INDEX].equals(MARKED_STATUS);

        return switch (parts[TASK_TYPE_INDEX]) {
            case DEADLINE_TASK_CODE -> new Deadline(isMarked, parts[NAME_INDEX],
                    Parser.parseDate(parts[DATE_INDEX]));
            case EVENT_TASK_CODE -> new Event(isMarked, parts[NAME_INDEX],
                    Parser.parseDate(parts[DATE_INDEX]), Parser.parseDate(parts[END_DATE_INDEX]));
            default -> new ToDo(isMarked, parts[NAME_INDEX]);
        };
    }
}
