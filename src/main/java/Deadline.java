import java.time.LocalDate;

/**
 * Represents a task that must be completed by a given date or time.
 */
public class Deadline extends Task{
    protected LocalDate dueDate;

    Deadline(boolean marked, String name, LocalDate dueDate) {
        super(marked, name);
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    /**
     * Returns the text format used to save this deadline on disk.
     *
     * @return the saved representation of this deadline
     */
    @Override
    public String toFileString() {
        return String.format("D | %s | %s | %s", getMarkedStatus(), getName(),
                Parser.formatDateForStorage(getDueDate()));
    }

    @Override
    public String toString() {
        return String.format("[D]" + super.toString() + " (by: %s)",
                Parser.formatDateForDisplay(getDueDate()));
    }
}
