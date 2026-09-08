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
    private final int taskIndex;

    /**
     * Creates a command that marks the task at the given zero-based index.
     *
     * @param taskIndex the zero-based task index
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task currentTask = tasks.get(taskIndex);
        currentTask.mark();
        storage.saveTasks(tasks);
        ui.showResponse(String.format("Nice! I've marked this task as done:\n%s", currentTask));
    }
}
