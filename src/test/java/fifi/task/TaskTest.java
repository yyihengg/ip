package fifi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests the base task behavior shared by all task types.
 */
public class TaskTest {

    @Test
    public void mark_unmarkedTask_taskMarked() {
        Task task = new TestTask(false, "read book");

        task.mark();

        assertTrue(task.isMarked());
        assertEquals("[X] read book", task.toString());
        assertEquals("1 | read book", task.toFileString());
    }

    @Test
    public void unmark_markedTask_taskUnmarked() {
        Task task = new TestTask(true, "read book");

        task.unmark();

        assertFalse(task.isMarked());
        assertEquals("[ ] read book", task.toString());
        assertEquals("0 | read book", task.toFileString());
    }

    @Test
    public void toString_unmarkedTask_unmarkedTaskStringReturned() {
        Task task = new TestTask(false, "read book");

        assertEquals("[ ] read book", task.toString());
    }

    @Test
    public void toFileString_markedTask_markedStorageStringReturned() {
        Task task = new TestTask(true, "read book");

        assertEquals("1 | read book", task.toFileString());
    }

    @Test
    public void occursOn_basicTask_falseForAnyDate() {
        Task task = new TestTask(false, "read book");

        assertFalse(task.occursOn(LocalDate.of(2019, 12, 2)));
    }

    /**
     * Provides the smallest concrete task needed to test shared task behavior.
     */
    private static class TestTask extends Task {
        private TestTask(boolean marked, String name) {
            super(marked, name);
        }

        @Override
        public String toFileString() {
            return String.format("%s | %s", getMarkedStatus(), getName());
        }
    }
}
