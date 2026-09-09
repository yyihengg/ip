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
    private final int taskIndex;

    /**
     * Creates a command that unmarks the task at the given zero-based index.
     *
     * @param taskIndex the zero-based task index
     */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task currentTask = tasks.get(taskIndex);
        currentTask.unmark();
        // Every task implementation must honor unmark() before its state is saved or reported.
        assert !currentTask.isMarked() : "A task must be unmarked after unmark()";
        storage.saveTasks(tasks);
        ui.showResponse("OK, I've marked this task as not done yet:", currentTask.toString());
    }
}
