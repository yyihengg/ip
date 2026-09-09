package fifi.command;

import java.io.IOException;

import fifi.Storage;
import fifi.TaskList;
import fifi.Ui;
import fifi.task.Task;

/**
 * Deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that deletes the task at the given zero-based index.
     *
     * @param taskIndex the zero-based task index
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task removedTask = tasks.delete(taskIndex);
        storage.saveTasks(tasks);
        ui.showResponse(
                "Got it. I've removed this task:",
                "    " + removedTask,
                String.format("Now you have %d tasks in the list ^^.", tasks.size()));
    }
}
