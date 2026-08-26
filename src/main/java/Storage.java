import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;
import java.util.List;

/**
 * Handles saving chatbot tasks to the hard disk.
 */
public class Storage {
    private static final Path FILE_PATH = Path.of("data", "duke.txt");

    /**
     * Saves the full task list to the hard disk.
     *
     * @param tasks the current tasks in the chatbot
     * @throws IOException if the file or its parent directory cannot be written
     */
    public static void saveTasks(List<Task> tasks) throws IOException {
        Files.createDirectories(FILE_PATH.getParent());

        StringJoiner savedTasks = new StringJoiner(System.lineSeparator());
        for (Task task : tasks) {
            savedTasks.add(task.toFileString());
        }

        Files.writeString(FILE_PATH, savedTasks.toString());
    }
}
