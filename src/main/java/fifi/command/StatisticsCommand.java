package fifi.command;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

import fifi.Parser;
import fifi.Storage;
import fifi.TaskList;
import fifi.TaskStatistics;
import fifi.Ui;

/**
 * Shows a status breakdown for all tasks or tasks created since a given date.
 */
public class StatisticsCommand extends Command {
    private static final String ALL_TASKS_HEADING = "Task statistics for all tasks:";

    private final LocalDate startDate;
    private final Clock clock;

    /**
     * Creates a command that reports statistics for all tasks.
     */
    public StatisticsCommand() {
        this(null, Clock.systemDefaultZone());
    }

    /**
     * Creates a command that reports statistics for tasks created since the given date.
     *
     * @param startDate first creation date included in the report
     */
    public StatisticsCommand(LocalDate startDate) {
        this(startDate, Clock.systemDefaultZone());
    }

    StatisticsCommand(LocalDate startDate, Clock clock) {
        this.startDate = startDate;
        this.clock = clock;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        LocalDateTime now = LocalDateTime.now(clock);
        TaskStatistics statistics;
        String heading;
        if (startDate == null) {
            statistics = tasks.getStatistics(now);
            heading = ALL_TASKS_HEADING;
        } else {
            statistics = tasks.getStatisticsSince(startDate, now);
            heading = String.format("Task statistics for tasks created since %s:",
                    Parser.formatDateForDisplay(startDate));
        }
        ui.showResponse(statistics.toDisplayString(heading));
    }
}
