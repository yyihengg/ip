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
    private final int taskNumber;

    /**
     * Creates a command that deletes the task at the given zero-based index.
     *
     * @param taskNumber the zero-based task number
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task removedTask = tasks.delete(taskNumber);
        storage.saveTasks(tasks);
        ui.showResponse(
                "Got it. I've removed this task:",
                "    " + removedTask,
                String.format("Now you have %d tasks in the list ^^.", tasks.size()));
    }
}
