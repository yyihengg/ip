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
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
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
        String[] parts = savedTask.split(" \\| ");
        boolean isMarked = parts[1].equals("1");

        return switch (parts[0]) {
            case "D" -> new Deadline(isMarked, parts[2], Parser.parseDate(parts[3]));
            case "E" -> new Event(isMarked, parts[2], Parser.parseDate(parts[3]),
                    Parser.parseDate(parts[4]));
            default -> new ToDo(isMarked, parts[2]);
        };
    }
}
