package fifi.task;

import java.time.LocalDate;

import fifi.Parser;

/**
 * Represents a task that happens from one date or time to another.
 */
public class Event extends Task {
    protected LocalDate start;
    protected LocalDate end;

    /**
     * Creates an event task with its completion status, description, start date, and end date.
     *
     * @param marked whether the event has been completed
     * @param description description of the event
     * @param start date when the event starts
     * @param end date when the event ends
     */
    public Event(boolean marked, String description, LocalDate start, LocalDate end) {
        super(marked, description);
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    /**
     * Returns the text format used to save this event on disk.
     *
     * @return the saved representation of this event
     */
    @Override
    public String toFileString() {
        return String.format("E | %s | %s | %s | %s", getMarkedStatus(), getDescription(),
                Parser.formatDateForStorage(getStart()), Parser.formatDateForStorage(getEnd()));
    }

    @Override
    public String toString() {
        return String.format("[E]" + super.toString() + " (from: %s to: %s)",
                Parser.formatDateForDisplay(getStart()), Parser.formatDateForDisplay(getEnd()));
    }
}
