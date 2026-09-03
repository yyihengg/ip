package fifi.command;

import java.io.IOException;

import fifi.Storage;
import fifi.TaskList;
import fifi.Ui;
import fifi.task.Task;

/**
 * Marks a task as done.
 */
public class MarkCommand extends Command {
    private final int taskNumber;

    /**
     * Creates a command that marks the task at the given zero-based index.
     *
     * @param taskNumber the zero-based task number
     */
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task currentTask = tasks.get(taskNumber);
        currentTask.mark();
        storage.saveTasks(tasks);
        ui.showResponse("Nice! I've marked this task as done:", currentTask.toString());
    }
}
