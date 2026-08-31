package fifi;

import java.io.IOException;
import java.time.DateTimeException;

import fifi.command.Command;
import fifi.exception.FifiException;

/**
 * Entry point for the Fifi chatbot application.
 */
public class Fifi {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a chatbot that stores tasks at the given file path.
     *
     * @param filePath the path of the task data file
     */
    public Fifi(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = loadTasks();
    }

    /**
     * Starts reading and executing user commands.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command command = Parser.parse(input);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (FifiException e) {
                ui.showResponse(e.getMessage());
            } catch (IOException e) {
                ui.showResponse("Oops! I could not save your tasks to the hard disk.");
            } catch (DateTimeException e) {
                ui.showResponse("Oops! Please use yyyy-MM-dd for dates.");
            }
        }
    }

    /**
     * Starts Fifi using the default task data file.
     *
     * @param args command line arguments supplied by Java
     */
    public static void main(String[] args) {
        new Fifi("data/duke.txt").run();
    }

    private TaskList loadTasks() {
        try {
            return new TaskList(storage.loadTasks());
        } catch (IOException e) {
            return new TaskList();
        }
    }
}
