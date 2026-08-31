package fifi.task;

/**
 * Represents a task without any date or time.
 */
public class ToDo extends Task {
    public ToDo(boolean marked, String description) {
        super(marked, description);
    }

    @Override
    public String toString() {
        return String.format("[T]" + super.toString());
    }
}

