package fifi.task;

import java.time.LocalDate;

import fifi.Parser;

/**
 * Represents a task that happens from one date or time to another.
 */
public class Event extends Task {
    private final LocalDate start;
    private final LocalDate end;

    /**
     * Creates an event task with its completion status, name, and date range.
     *
     * @param marked whether the event has been completed
     * @param name name of the event
     * @param start date when the event starts
     * @param end date when the event ends
     */
    public Event(boolean marked, String name, LocalDate start, LocalDate end) {
        super(marked, name);
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }

    /**
     * Returns the text format used to save this event on disk.
     *
     * @return the saved representation of this event
     */
    @Override
    public String toFileString() {
        return String.format("E | %s | %s | %s | %s", getMarkedStatus(), getName(),
                Parser.formatDateForStorage(getStart()), Parser.formatDateForStorage(getEnd()));
    }

    @Override
    public String toString() {
        return String.format("[E]" + super.toString() + " (from: %s to: %s)",
                Parser.formatDateForDisplay(getStart()), Parser.formatDateForDisplay(getEnd()));
    }
}
