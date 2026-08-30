package fifi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the base task behavior shared by all task types.
 */
public class TaskTest {

    @Test
    public void mark_unmarkedTask_taskMarked() {
        Task task = new Task(false, "read book");

        task.mark();

        assertTrue(task.isMarked());
        assertEquals("[X] read book", task.toString());
        assertEquals("T | 1 | read book", task.toFileString());
    }

    @Test
    public void unmark_markedTask_taskUnmarked() {
        Task task = new Task(true, "read book");

        task.unmark();

        assertFalse(task.isMarked());
        assertEquals("[ ] read book", task.toString());
        assertEquals("T | 0 | read book", task.toFileString());
    }

    @Test
    public void toString_unmarkedTask_unmarkedTaskStringReturned() {
        Task task = new Task(false, "read book");

        assertEquals("[ ] read book", task.toString());
    }

    @Test
    public void toFileString_markedTask_markedStorageStringReturned() {
        Task task = new Task(true, "read book");

        assertEquals("T | 1 | read book", task.toFileString());
    }
}
