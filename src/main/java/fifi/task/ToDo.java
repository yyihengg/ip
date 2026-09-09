package fifi.task;

/**
 * Represents a task without any date or time.
 */
public class ToDo extends Task {
    /**
     * Creates a todo task with its completion status and description.
     *
     * @param marked whether the todo has been completed
     * @param description description of the todo
     */
    public ToDo(boolean marked, String description) {
        super(marked, description);
    }

    @Override
    public String toFileString() {
        return String.format("T | %s | %s", getMarkedStatus(), getDescription());
    }

    @Override
    public String toString() {
        return String.format("[T]" + super.toString());
    }
}
