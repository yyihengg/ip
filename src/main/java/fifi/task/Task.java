package fifi.task;

/**
 * Represents one task in the chatbot's task list.
 */
public class Task {
    protected boolean marked;
    protected final String description;

    Task(boolean marked, String description) {
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
     * Returns the text format used to save this task on disk.
     *
     * @return the saved representation of this task
     */
    public String toFileString() {
        return String.format("T | %s | %s", getMarkedStatus(), getDescription());
    }

    @Override
    public String toString() {
        if (this.isMarked()) {
            return String.format("[X] %s", getDescription());
        }
        return String.format("[ ] %s", getDescription());
    }
}
