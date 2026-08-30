package fifi.command;

import java.io.IOException;

import fifi.Storage;
import fifi.TaskList;
import fifi.Ui;
import fifi.task.Task;

/**
 * Marks a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int taskNumber;

    /**
     * Creates a command that unmarks the task at the given zero-based index.
     *
     * @param taskNumber the zero-based task number
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task currentTask = tasks.get(taskNumber);
        currentTask.unmark();
        storage.saveTasks(tasks);
        ui.showResponse(String.format("OK, I've marked this task as not done yet:\n%s", currentTask));
    }
}
