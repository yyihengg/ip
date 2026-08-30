package fifi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the displayed and saved forms of todo tasks.
 */
public class ToDoTest {

    @Test
    public void toString_unmarkedTodo_unmarkedTodoStringReturned() {
        ToDo todo = new ToDo(false, "read book");

        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void toString_markedTodo_markedTodoStringReturned() {
        ToDo todo = new ToDo(true, "read book");

        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void toString_markThenUnmarkTodo_unmarkedTodoStringReturned() {
        ToDo todo = new ToDo(false, "read book");

        todo.mark();
        todo.unmark();

        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void toFileString_unmarkedTodo_unmarkedStorageStringReturned() {
        ToDo todo = new ToDo(false, "read book");

        assertEquals("T | 0 | read book", todo.toFileString());
    }

    @Test
    public void toFileString_markedTodo_markedStorageStringReturned() {
        ToDo todo = new ToDo(true, "read book");

        assertEquals("T | 1 | read book", todo.toFileString());
    }

    @Test
    public void toFileString_markThenUnmarkTodo_unmarkedStorageStringReturned() {
        ToDo todo = new ToDo(false, "read book");

        todo.mark();
        todo.unmark();

        assertEquals("T | 0 | read book", todo.toFileString());
    }
}
