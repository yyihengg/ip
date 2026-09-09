package fifi.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import fifi.Parser;

/**
 * Represents a task that must be completed by a given date or time.
 */
public class Deadline extends Task {
    private final LocalDate dueDate;

    /**
     * Creates a deadline task with its completion status, description, and due date.
     *
     * @param marked whether the deadline has been completed
     * @param description description of the deadline
     * @param dueDate date when the deadline is due
     */
    public Deadline(boolean marked, String description, LocalDate dueDate) {
        super(marked, description);
        this.dueDate = dueDate;
    }

    /**
     * Creates a deadline using timestamps loaded from storage.
     *
     * @param marked whether the deadline has been completed
     * @param description description of the deadline
     * @param dueDate date when the deadline is due
     * @param createdAt exact time when the deadline was created, or null when unknown
     * @param lastMarkedAt exact time when the deadline was last completed, or null when never completed or unknown
     */
    public Deadline(boolean marked, String description, LocalDate dueDate,
            LocalDateTime createdAt, LocalDateTime lastMarkedAt) {
        super(marked, description, createdAt, lastMarkedAt);
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return dueDate.isEqual(date);
    }

    /**
     * Returns the text format used to save this deadline on disk.
     *
     * @return the saved representation of this deadline
     */
    @Override
    public String toFileString() {
        return String.format("D | %s | %s | %s | %s", getMarkedStatus(), getDescription(),
                Parser.formatDateForStorage(getDueDate()), getTimestampFields());
    }

    @Override
    public String toString() {
        return String.format("[D]" + super.toString() + " (by: %s)",
                Parser.formatDateForDisplay(getDueDate()));
    }
}
