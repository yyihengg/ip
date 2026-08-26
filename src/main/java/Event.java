/**
 * Represents a task that happens from one date or time to another.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    Event(boolean marked, String name, String from, String to) {
        super(marked, name);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    /**
     * Returns the text format used to save this event on disk.
     *
     * @return the saved representation of this event
     */
    @Override
    public String toFileString() {
        return String.format("E | %s | %s | %s | %s", getSaveStatus(), getName(), getFrom(), getTo());
    }

    @Override
    public String toString() {
        return String.format("[E]" + super.toString() + " (from: %s to: %s)", getFrom(), getTo());
    }
}
