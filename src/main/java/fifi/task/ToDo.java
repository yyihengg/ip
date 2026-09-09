package fifi.task;

import java.time.LocalDateTime;

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

    /**
     * Creates a todo task using timestamps loaded from storage.
     *
     * @param marked whether the todo has been completed
     * @param description description of the todo
     * @param createdAt exact time when the todo was created, or null when unknown
     * @param lastMarkedAt exact time when the todo was last completed, or null when never completed or unknown
     */
    public ToDo(boolean marked, String description, LocalDateTime createdAt, LocalDateTime lastMarkedAt) {
        super(marked, description, createdAt, lastMarkedAt);
    }

    @Override
    public String toFileString() {
        return String.format("T | %s | %s | %s", getMarkedStatus(), getDescription(), getTimestampFields());
    }

    @Override
    public String toString() {
        return String.format("[T]" + super.toString());
    }
}
