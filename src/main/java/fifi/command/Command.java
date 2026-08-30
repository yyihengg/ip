package fifi.command;

import java.io.IOException;

import fifi.Storage;
import fifi.TaskList;
import fifi.Ui;
import fifi.exception.FiFiException;

/**
 * Represents one executable user command.
 */
public abstract class Command {
    /**
     * Executes this command using the chatbot's main collaborators.
     *
     * @param tasks the current task list
     * @param ui the console UI
     * @param storage the storage used to save task changes
     * @throws FiFiException if the command cannot be completed
     * @throws IOException if changed tasks cannot be saved
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws FiFiException, IOException;

    /**
     * Returns whether this command should end the chatbot.
     *
     * @return true if the chatbot should exit
     */
    public boolean isExit() {
        return false;
    }
}
