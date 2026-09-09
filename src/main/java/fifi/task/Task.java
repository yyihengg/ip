package fifi.task;

import java.time.LocalDate;

/**
 * Provides common state and behavior for tasks in the chatbot's task list.
 */
public abstract class Task {
    private boolean marked;
    private final String description;

    protected Task(boolean marked, String description) {
        this.marked = marked;
        this.description = description;
    }

    public boolean isMarked() {
        return this.marked;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns 1 when the task is done and 0 when it is not done.
     *
     * @return the status value used when saving the task to disk
     */
    protected String getMarkedStatus() {
        return this.isMarked() ? "1" : "0";
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        this.marked = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        this.marked = false;
    }

    /**
     * Returns whether this task occurs on the given date.
     *
     * @param date the date to check
     * @return false because a basic task has no associated date
     */
    public boolean occursOn(LocalDate date) {
        return false;
    }

    /**
     * Returns the text format used to save this task on disk.
     *
     * @return the saved representation of this task
     */
    public abstract String toFileString();

    @Override
    public String toString() {
        if (this.isMarked()) {
            return String.format("[X] %s", getDescription());
        }
        return String.format("[ ] %s", getDescription());
    }
}
