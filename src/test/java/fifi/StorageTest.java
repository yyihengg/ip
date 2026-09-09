package fifi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import fifi.task.Deadline;
import fifi.task.Event;
import fifi.task.Task;
import fifi.task.ToDo;

/**
 * Tests loading and saving tasks using temporary files.
 */
public class StorageTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    public void loadTasks_missingFile_emptyTaskListReturned() throws Exception {
        Storage storage = new Storage(temporaryDirectory.resolve("missing.txt").toString());

        List<Task> loadedTasks = storage.loadTasks();

        assertEquals(0, loadedTasks.size());
    }

    @Test
    public void loadTasks_savedTodoDeadlineAndEvent_matchingTaskTypesReturned() throws Exception {
        Path dataFile = temporaryDirectory.resolve("duke.txt");
        Files.writeString(dataFile, """
                T | 1 | read book
                D | 0 | return book | 2019-12-02
                E | 0 | project meeting | 2019-12-02 | 2019-12-04""");
        Storage storage = new Storage(dataFile.toString());

        List<Task> loadedTasks = storage.loadTasks();

        assertEquals(3, loadedTasks.size());
        assertInstanceOf(ToDo.class, loadedTasks.get(0));
        assertInstanceOf(Deadline.class, loadedTasks.get(1));
        assertInstanceOf(Event.class, loadedTasks.get(2));

        ToDo todo = (ToDo) loadedTasks.get(0);
        Deadline deadline = (Deadline) loadedTasks.get(1);
        Event event = (Event) loadedTasks.get(2);
        assertTrue(todo.isMarked());
        assertEquals("read book", todo.getDescription());
        assertFalse(deadline.isMarked());
        assertEquals("return book", deadline.getDescription());
        assertEquals(LocalDate.of(2019, 12, 2), deadline.getDueDate());
        assertFalse(event.isMarked());
        assertEquals("project meeting", event.getDescription());
        assertEquals(LocalDate.of(2019, 12, 2), event.getStart());
        assertEquals(LocalDate.of(2019, 12, 4), event.getEnd());

        assertEquals("[T][X] read book", loadedTasks.get(0).toString());
        assertEquals("[D][ ] return book (by: Dec 02 2019)", loadedTasks.get(1).toString());
        assertEquals("[E][ ] project meeting (from: Dec 02 2019 to: Dec 04 2019)",
                loadedTasks.get(2).toString());
    }

    @Test
    public void loadTasks_unknownTaskCode_exceptionThrownAndLaterValidDataLoads() throws Exception {
        Path dataFile = temporaryDirectory.resolve("duke.txt");
        Storage storage = new Storage(dataFile.toString());

        Files.writeString(dataFile, "T | 0 | read book");
        assertInstanceOf(ToDo.class, storage.loadTasks().get(0));

        Files.writeString(dataFile, "X | 0 | unsupported task");
        IOException exception = assertThrows(IOException.class, storage::loadTasks);
        assertEquals("Unsupported task type: X", exception.getMessage());

        Files.writeString(dataFile, "D | 0 | return book | 2019-12-02");
        assertInstanceOf(Deadline.class, storage.loadTasks().get(0));
    }

    @Test
    public void saveTasks_newParentDirectory_dataFileCreated() throws Exception {
        Path dataFile = temporaryDirectory.resolve("data").resolve("duke.txt");
        Storage storage = new Storage(dataFile.toString());
        TaskList tasks = new TaskList(getSampleTasks());

        storage.saveTasks(tasks);

        assertEquals(String.join(System.lineSeparator(),
                "T | 1 | read book",
                "D | 0 | return book | 2019-12-02",
                "E | 0 | project meeting | 2019-12-02 | 2019-12-04"),
                Files.readString(dataFile));
    }

    @Test
    public void saveThenLoadTasks_mixedTaskTypes_sameTaskDataReturned() throws Exception {
        Path dataFile = temporaryDirectory.resolve("data").resolve("duke.txt");
        Storage storage = new Storage(dataFile.toString());

        storage.saveTasks(new TaskList(getSampleTasks()));
        List<Task> loadedTasks = storage.loadTasks();

        assertEquals(3, loadedTasks.size());
        assertEquals("[T][X] read book", loadedTasks.get(0).toString());
        assertEquals("[D][ ] return book (by: Dec 02 2019)", loadedTasks.get(1).toString());
        assertEquals("[E][ ] project meeting (from: Dec 02 2019 to: Dec 04 2019)",
                loadedTasks.get(2).toString());
    }

    private ArrayList<Task> getSampleTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo(true, "read book"));
        tasks.add(new Deadline(false, "return book", LocalDate.of(2019, 12, 2)));
        tasks.add(new Event(false, "project meeting", LocalDate.of(2019, 12, 2),
                LocalDate.of(2019, 12, 4)));
        return tasks;
    }
}
