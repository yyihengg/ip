package fifi.command;

import java.io.IOException;

import fifi.Storage;
import fifi.TaskList;
import fifi.Ui;
import fifi.exception.FifiException;
import fifi.task.Task;

/**
 * Adds one new task to the task list.
 */
public abstract class AddTaskCommand extends Command {
    private final Task task;

    /**
     * Creates a command that adds the given task.
     *
     * @param task the task to add
     */
    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FifiException, IOException {
        tasks.add(task);
        storage.saveTasks(tasks);
        ui.showResponse(
                "Got it. I've added this task:",
                task.toString(),
                String.format("Now you have %d tasks in the list.", tasks.size()));
    }
}
