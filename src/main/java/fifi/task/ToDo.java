package fifi.task;

/**
 * Represents a task without any date or time.
 */
public class ToDo extends Task {
    /**
     * Creates a todo task with its completion status and name.
     *
     * @param marked whether the todo has been completed
     * @param name name of the todo
     */
    public ToDo(boolean marked, String name) {
        super(marked, name);
    }

    @Override
    public String toFileString() {
        return String.format("T | %s | %s", getMarkedStatus(), getName());
    }

    @Override
    public String toString() {
        return String.format("[T]" + super.toString());
    }
}

