package fifi.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import fifi.Storage;
import fifi.TaskList;
import fifi.Ui;
import fifi.task.Deadline;
import fifi.task.Event;
import fifi.task.Task;
import fifi.task.ToDo;

/**
 * Tests command execution side effects on tasks, storage, and output.
 */
public class CommandTest {
    private final PrintStream originalOut = System.out;

    @TempDir
    private Path temporaryDirectory;

    @AfterEach
    public void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    public void isExit_addTodoCommand_falseReturned() {
        Command command = new AddTodoCommand(new ToDo(false, "read book"));

        assertFalse(command.isExit());
    }

    @Test
    public void execute_addTodoCommand_taskAddedAndSaved() throws Exception {
        Path dataFile = temporaryDirectory.resolve("duke.txt");
        TaskList tasks = new TaskList();
        Command command = new AddTodoCommand(new ToDo(false, "read book"));

        command.execute(tasks, new Ui(), new Storage(dataFile.toString()));

        assertEquals(1, tasks.size());
        assertEquals("T | 0 | read book", Files.readString(dataFile));
    }

    @Test
    public void execute_addDeadlineCommand_deadlineAddedAndSaved() throws Exception {
        Path dataFile = temporaryDirectory.resolve("duke.txt");
        TaskList tasks = new TaskList();
        Command command = new AddDeadlineCommand(new Deadline(false, "return book", LocalDate.of(2019, 12, 2)));

        command.execute(tasks, new Ui(), new Storage(dataFile.toString()));

        assertEquals("D | 0 | return book | 2019-12-02", Files.readString(dataFile));
    }

    @Test
    public void execute_addEventCommand_eventAddedAndSaved() throws Exception {
        Path dataFile = temporaryDirectory.resolve("duke.txt");
        TaskList tasks = new TaskList();
        Command command = new AddEventCommand(new Event(false, "meeting", LocalDate.of(2019, 12, 2),
                LocalDate.of(2019, 12, 4)));

        command.execute(tasks, new Ui(), new Storage(dataFile.toString()));

        assertEquals("E | 0 | meeting | 2019-12-02 | 2019-12-04", Files.readString(dataFile));
    }

    @Test
    public void execute_listCommand_existingTasks_taskListPrinted() throws Exception {
        ByteArrayOutputStream output = replaceSystemOut();
        Command command = new ListCommand();

        command.execute(new TaskList(getSampleTasks()), new Ui(), getUnusedStorage());

        assertOutputContains(output, "Here are the tasks in your list:\n"
                + "1. [T][ ] read book\n"
                + "2. [D][ ] return book (by: Dec 02 2019)\n"
                + "3. [E][ ] project meeting (from: Dec 02 2019 to: Dec 04 2019)");
    }

    @Test
    public void execute_markCommand_existingTask_taskMarkedAndSaved() throws Exception {
        Path dataFile = temporaryDirectory.resolve("duke.txt");
        TaskList tasks = new TaskList(getSampleTasks());

        new MarkCommand(0).execute(tasks, new Ui(), new Storage(dataFile.toString()));

        assertEquals("T | 1 | read book", firstSavedLine(dataFile));
    }

    @Test
    public void execute_unmarkCommand_existingTask_taskUnmarkedAndSaved() throws Exception {
        Path dataFile = temporaryDirectory.resolve("duke.txt");
        ArrayList<Task> sampleTasks = getSampleTasks();
        sampleTasks.get(0).mark();
        TaskList tasks = new TaskList(sampleTasks);

        new UnmarkCommand(0).execute(tasks, new Ui(), new Storage(dataFile.toString()));

        assertEquals("T | 0 | read book", firstSavedLine(dataFile));
    }

    @Test
    public void execute_deleteCommand_existingTask_taskDeletedAndSaved() throws Exception {
        Path dataFile = temporaryDirectory.resolve("duke.txt");
        TaskList tasks = new TaskList(getSampleTasks());

        new DeleteCommand(1).execute(tasks, new Ui(), new Storage(dataFile.toString()));

        assertEquals(2, tasks.size());
        assertEquals(String.join(System.lineSeparator(),
                "T | 0 | read book",
                "E | 0 | project meeting | 2019-12-02 | 2019-12-04"),
                Files.readString(dataFile));
    }

    @Test
    public void execute_showCommand_matchingDate_matchingTasksPrinted() throws Exception {
        ByteArrayOutputStream output = replaceSystemOut();
        Command command = new ShowCommand(LocalDate.of(2019, 12, 3));

        command.execute(new TaskList(getSampleTasks()), new Ui(), getUnusedStorage());

        assertOutputContains(output, "Here are the tasks occurring on your specified date:\n"
                + "1. [E][ ] project meeting (from: Dec 02 2019 to: Dec 04 2019)");
    }

    @Test
    public void execute_findCommand_matchingKeyword_matchingTasksPrinted() throws Exception {
        ByteArrayOutputStream output = replaceSystemOut();
        Command command = new FindCommand("book");

        command.execute(new TaskList(getSampleTasks()), new Ui(), getUnusedStorage());

        assertOutputContains(output, "Here are the matching tasks in your list:\n"
                + "1. [T][ ] read book\n"
                + "2. [D][ ] return book (by: Dec 02 2019)");
    }

    @Test
    public void execute_exitCommand_noInput_exitMessagePrintedAndExitTrue() throws Exception {
        ByteArrayOutputStream output = replaceSystemOut();
        Command command = new ExitCommand();

        command.execute(new TaskList(), new Ui(), getUnusedStorage());

        assertTrue(command.isExit());
        assertOutputContains(output, "BaiBai! Hope to see you soon ^^");
    }

    private Storage getUnusedStorage() {
        return new Storage(temporaryDirectory.resolve("unused.txt").toString());
    }

    private String firstSavedLine(Path dataFile) throws Exception {
        return Files.readString(dataFile).split("\\R", 2)[0];
    }

    private void assertOutputContains(ByteArrayOutputStream output, String expectedText) {
        assertTrue(normalizeLineEndings(output.toString(StandardCharsets.UTF_8))
                .contains(normalizeLineEndings(expectedText)));
    }

    private ByteArrayOutputStream replaceSystemOut() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
        return output;
    }

    private ArrayList<Task> getSampleTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo(false, "read book"));
        tasks.add(new Deadline(false, "return book", LocalDate.of(2019, 12, 2)));
        tasks.add(new Event(false, "project meeting", LocalDate.of(2019, 12, 2),
                LocalDate.of(2019, 12, 4)));
        return tasks;
    }

    private String normalizeLineEndings(String text) {
        return text.replace("\r\n", "\n");
    }
}
