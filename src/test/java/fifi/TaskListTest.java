package fifi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import fifi.exception.ExcessiveTaskException;
import fifi.task.Deadline;
import fifi.task.Event;
import fifi.task.Task;
import fifi.task.ToDo;

/**
 * Tests task list operations that change or filter the chatbot's tasks.
 */
public class TaskListTest {

    @Test
    public void add_emptyList_taskAdded() throws Exception {
        TaskList tasks = new TaskList();
        ToDo todo = new ToDo(false, "read book");

        tasks.add(todo);

        assertEquals(1, tasks.size());
        assertEquals(todo, tasks.get(0));
    }

    @Test
    public void add_fullList_exceptionThrown() throws Exception {
        TaskList tasks = new TaskList();
        for (int i = 0; i < 100; i++) {
            tasks.add(new ToDo(false, "task " + i));
        }

        assertThrows(ExcessiveTaskException.class, () -> tasks.add(new ToDo(false, "overflow task")));
        assertEquals(100, tasks.size());
    }

    @Test
    public void delete_existingTask_taskRemovedAndReturned() {
        TaskList tasks = new TaskList(getSampleTasks());

        Task deletedTask = tasks.delete(1);

        assertInstanceOf(Deadline.class, deletedTask);
        assertEquals(2, tasks.size());
        assertEquals("[T][ ] read book", tasks.get(0).toString());
        assertEquals("[E][ ] project meeting (from: Dec 02 2019 to: Dec 04 2019)", tasks.get(1).toString());
    }

    @Test
    public void getTasksOccurringOn_matchingDeadlineAndEvent_matchingTasksReturned() {
        TaskList tasks = new TaskList(getSampleTasks());

        TaskList matchingTasks = tasks.getTasksOccurringOn(LocalDate.of(2019, 12, 2));

        assertEquals("""

                1. [D][ ] return book (by: Dec 02 2019)
                2. [E][ ] project meeting (from: Dec 02 2019 to: Dec 04 2019)""",
                matchingTasks.toDisplayString());
    }

    @Test
    public void getTasksOccurringOn_eventMiddleDate_eventReturned() {
        TaskList tasks = new TaskList(getSampleTasks());

        TaskList matchingTasks = tasks.getTasksOccurringOn(LocalDate.of(2019, 12, 3));

        assertEquals("""

                1. [E][ ] project meeting (from: Dec 02 2019 to: Dec 04 2019)""",
                matchingTasks.toDisplayString());
    }

    @Test
    public void getTasksOccurringOn_noMatchingDate_emptyTaskListReturned() {
        TaskList tasks = new TaskList(getSampleTasks());

        TaskList matchingTasks = tasks.getTasksOccurringOn(LocalDate.of(2019, 12, 5));

        assertEquals(0, matchingTasks.size());
        assertEquals("", matchingTasks.toDisplayString());
    }

    @Test
    public void toDisplayString_multipleTasks_numberedTaskStringReturned() {
        TaskList tasks = new TaskList(getSampleTasks());

        assertEquals("""

                1. [T][ ] read book
                2. [D][ ] return book (by: Dec 02 2019)
                3. [E][ ] project meeting (from: Dec 02 2019 to: Dec 04 2019)""",
                tasks.toDisplayString());
    }

    private ArrayList<Task> getSampleTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo(false, "read book"));
        tasks.add(new Deadline(false, "return book", LocalDate.of(2019, 12, 2)));
        tasks.add(new Event(false, "project meeting", LocalDate.of(2019, 12, 2),
                LocalDate.of(2019, 12, 4)));
        return tasks;
    }
}
