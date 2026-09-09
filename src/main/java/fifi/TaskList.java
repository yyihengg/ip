package fifi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fifi.exception.ExcessiveTaskException;
import fifi.task.Task;

/**
 * Stores the chatbot's tasks and provides operations on the task list.
 */
public class TaskList {
    private static final int MAX_TASKS = 100;

    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a task list with tasks loaded from storage.
     *
     * @param tasks the tasks to keep in this list
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a task to the list if the list is not full.
     *
     * @param task the task to add
     * @throws ExcessiveTaskException if the task list already has too many tasks
     */
    public void add(Task task) throws ExcessiveTaskException {
        if (tasks.size() >= MAX_TASKS) {
            throw new ExcessiveTaskException(
                    """
                    You have exceeded the cap of 100 tasks! Delete old tasks in order to make space for new tasks.\
                    """);
        }
        tasks.add(task);
        // The capacity check above must keep every successful addition within the limit.
        assert tasks.size() <= MAX_TASKS : "Adding a task must not exceed the task limit";
    }

    /**
     * Returns the task at the given zero-based index.
     *
     * @param taskIndex the zero-based task index
     * @return the task at that index
     */
    public Task get(int taskIndex) {
        return tasks.get(taskIndex);
    }

    /**
     * Deletes and returns the task at the given zero-based index.
     *
     * @param taskIndex the zero-based task index
     * @return the deleted task
     */
    public Task delete(int taskIndex) {
        int previousSize = tasks.size();
        Task removedTask = tasks.remove(taskIndex);
        // Deletion must remove exactly one entry so subsequent task numbers stay consistent.
        assert tasks.size() == previousSize - 1 : "Deleting a task must reduce the task count by one";
        return removedTask;
    }

    /**
     * Returns a task list containing tasks that occur on the given date.
     *
     * @param showDate the date to match against
     * @return a task list containing matching deadlines and events
     */
    public TaskList getTasksOccurringOn(LocalDate showDate) {
        ArrayList<Task> occurringTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.occursOn(showDate)) {
                occurringTasks.add(task);
            }
        }
        return new TaskList(occurringTasks);
    }

    /**
     * Returns a task list containing tasks with descriptions that contain the keyword.
     *
     * @param keyword the keyword to search for
     * @return a task list containing matching tasks
     */
    public TaskList findTasksByKeyword(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return new TaskList(matchingTasks);
    }

    /**
     * Returns the numbered text shown by the list and show commands.
     *
     * @return the display text for this task list
     */
    public String toDisplayString() {
        StringBuilder taskString = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskString.append("\n");
            taskString.append(i + 1)
                    .append(". ")
                    .append(tasks.get(i).toString());
        }
        return taskString.toString();
    }

    /**
     * Returns the underlying tasks as a read-only list for saving.
     *
     * @return the tasks in this list
     */
    public List<Task> asList() {
        return List.copyOf(tasks);
    }
}
