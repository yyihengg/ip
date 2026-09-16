package fifi.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Provides common state and behavior for tasks in the chatbot's task list.
 */
public abstract class Task {
    private static final String UNKNOWN_TIMESTAMP = "-";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private boolean marked;
    private final String description;
    private final LocalDateTime createdAt;
    private LocalDateTime lastMarkedAt;

    protected Task(boolean marked, String description) {
        this(marked, description, LocalDateTime.now(), null);
        if (marked) {
            this.lastMarkedAt = this.createdAt;
        }
    }

    protected Task(boolean marked, String description, LocalDateTime createdAt, LocalDateTime lastMarkedAt) {
        if (description == null || description.isBlank() || description.contains("|")
                || description.chars().anyMatch(character -> Character.isISOControl(character) && character != '\t')) {
            throw new IllegalArgumentException("Task descriptions must be nonblank "
                    + "and contain no | or control characters.");
        }
        if (createdAt != null && lastMarkedAt != null && lastMarkedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Completion time cannot be earlier than creation time.");
        }
        this.marked = marked;
        this.description = description;
        this.createdAt = createdAt;
        this.lastMarkedAt = lastMarkedAt;
    }

    public boolean isMarked() {
        return this.marked;
    }

    public String getDescription() {
        return this.description;
    }

    public Optional<LocalDateTime> getCreatedAt() {
        return Optional.ofNullable(this.createdAt);
    }

    public Optional<LocalDateTime> getLastMarkedAt() {
        return Optional.ofNullable(this.lastMarkedAt);
    }

    /**
     * Returns whether another task has the same type and case-sensitive description.
     * Completion status and timestamps do not distinguish otherwise identical tasks.
     *
     * @param other the task to compare
     * @return whether the task details match
     */
    public boolean hasSameDetails(Task other) {
        return other != null && getClass().equals(other.getClass())
                && description.strip().equals(other.description.strip());
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
     * Returns the task timestamps in their saved representation.
     *
     * @return the creation and last-completion fields used when saving the task
     */
    protected String getTimestampFields() {
        return String.format("%s | %s", formatTimestamp(createdAt), formatTimestamp(lastMarkedAt));
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        this.marked = true;
        this.lastMarkedAt = LocalDateTime.now();
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        this.marked = false;
    }

    private String formatTimestamp(LocalDateTime timestamp) {
        return timestamp == null ? UNKNOWN_TIMESTAMP : timestamp.format(TIMESTAMP_FORMATTER);
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
     * Checks that a task date fits the four-digit positive-year save format.
     *
     * @param date the task date to validate
     */
    protected static void validateDate(LocalDate date) {
        if (date == null || date.getYear() < 1 || date.getYear() > 9999) {
            throw new IllegalArgumentException("Task dates must have a year between 0001 and 9999.");
        }
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
