package fifi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import fifi.command.AddDeadlineCommand;
import fifi.command.AddEventCommand;
import fifi.command.AddTodoCommand;
import fifi.command.Command;
import fifi.command.DeleteCommand;
import fifi.command.ExitCommand;
import fifi.command.FindCommand;
import fifi.command.ListCommand;
import fifi.command.MarkCommand;
import fifi.command.ShowCommand;
import fifi.command.UnmarkCommand;
import fifi.exception.InvalidCommandException;
import fifi.exception.InvalidDescriptionException;

/**
 * Tests the conversion of raw user input into commands and dates.
 */
public class ParserTest {

    // ---------- parse: valid commands ----------

    @Test
    public void parse_byeCommand_exitCommandReturned() throws Exception {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
        assertTrue(command.isExit());
    }

    @Test
    public void parse_listCommand_listCommandReturned() throws Exception {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
        assertFalse(command.isExit());
    }

    @Test
    public void parse_markCommand_markCommandReturned() throws Exception {
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
    }

    @Test
    public void parse_unmarkCommand_unmarkCommandReturned() throws Exception {
        assertInstanceOf(UnmarkCommand.class, Parser.parse("unmark 2"));
    }

    @Test
    public void parse_deleteCommand_deleteCommandReturned() throws Exception {
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 3"));
    }

    @Test
    public void parse_todoWithDescription_addTodoCommandReturned() throws Exception {
        assertInstanceOf(AddTodoCommand.class, Parser.parse("todo read book"));
    }

    @Test
    public void parse_deadlineWithDescriptionAndDate_addDeadlineCommandReturned() throws Exception {
        assertInstanceOf(AddDeadlineCommand.class, Parser.parse("deadline return book /by 2025-10-15"));
    }

    @Test
    public void parse_eventWithDescriptionAndDates_addEventCommandReturned() throws Exception {
        assertInstanceOf(AddEventCommand.class,
                Parser.parse("event career fair /from 2025-10-01 /to 2025-10-03"));
    }

    @Test
    public void parse_showWithDate_showCommandReturned() throws Exception {
        assertInstanceOf(ShowCommand.class, Parser.parse("show 2025-10-15"));
    }

    @Test
    public void parse_findWithKeyword_findCommandReturned() throws Exception {
        assertInstanceOf(FindCommand.class, Parser.parse("find book"));
    }

    // ---------- parse: unrecognised commands ----------

    @Test
    public void parse_unknownCommand_exceptionThrown() {
        assertThrows(InvalidCommandException.class, () -> Parser.parse("blah"));
    }

    @Test
    public void parse_emptyInput_exceptionThrown() {
        assertThrows(InvalidCommandException.class, () -> Parser.parse(""));
    }

    // ---------- parse: todo ----------

    @Test
    public void parse_todoWithoutDescription_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class, () -> Parser.parse("todo"));
    }

    @Test
    public void parse_todoWithBlankDescription_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class, () -> Parser.parse("todo    "));
    }

    // ---------- parse: deadline ----------

    @Test
    public void parse_deadlineWithoutByKeyword_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class, () -> Parser.parse("deadline return book"));
    }

    @Test
    public void parse_deadlineWithBlankDate_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class, () -> Parser.parse("deadline return book /by   "));
    }

    @Test
    public void parse_deadlineWithoutDescription_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class, () -> Parser.parse("deadline /by 2025-10-15"));
    }

    @Test
    public void parse_deadlineWithInvalidDateFormat_exceptionThrown() {
        assertThrows(DateTimeException.class, () -> Parser.parse("deadline return book /by 15-10-2025"));
    }

    // ---------- parse: event ----------

    @Test
    public void parse_eventWithoutToKeyword_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class,
                () -> Parser.parse("event career fair /from 2025-10-01"));
    }

    @Test
    public void parse_eventWithoutFromKeyword_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class,
                () -> Parser.parse("event career fair /to 2025-10-03"));
    }

    @Test
    public void parse_eventWithFromAfterTo_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class,
                () -> Parser.parse("event career fair /to 2025-10-03 /from 2025-10-01"));
    }

    @Test
    public void parse_eventWithoutDescription_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class,
                () -> Parser.parse("event /from 2025-10-01 /to 2025-10-03"));
    }

    @Test
    public void parse_eventWithInvalidDateFormat_exceptionThrown() {
        assertThrows(DateTimeException.class,
                () -> Parser.parse("event career fair /from 01-10-2025 /to 03-10-2025"));
    }

    // ---------- parse: show ----------

    @Test
    public void parse_showWithoutDate_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class, () -> Parser.parse("show"));
    }

    @Test
    public void parse_showWithInvalidDateFormat_exceptionThrown() {
        assertThrows(DateTimeException.class, () -> Parser.parse("show 15-10-2025"));
    }

    // ---------- parse: find ----------

    @Test
    public void parse_findWithoutKeyword_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class, () -> Parser.parse("find"));
    }

    @Test
    public void parse_findWithBlankKeyword_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class, () -> Parser.parse("find    "));
    }

    // ---------- parseDate ----------

    @Test
    public void parseDate_validDate_dateReturned() {
        assertEquals(LocalDate.of(2025, 10, 15), Parser.parseDate("2025-10-15"));
    }

    @Test
    public void parseDate_wrongFormat_exceptionThrown() {
        assertThrows(DateTimeException.class, () -> Parser.parseDate("15-10-2025"));
    }

    @Test
    public void parseDate_nonExistentDate_exceptionThrown() {
        assertThrows(DateTimeException.class, () -> Parser.parseDate("2025-02-30"));
    }

    // ---------- parseShowDate ----------

    @Test
    public void parseShowDate_validInput_dateReturned() throws Exception {
        assertEquals(LocalDate.of(2025, 10, 15), Parser.parseShowDate("show 2025-10-15"));
    }

    @Test
    public void parseShowDate_missingDate_exceptionThrown() {
        assertThrows(InvalidDescriptionException.class, () -> Parser.parseShowDate("show"));
    }

    // ---------- date formatting ----------

    @Test
    public void formatDateForStorage_validDate_isoStringReturned() {
        assertEquals("2025-10-15", Parser.formatDateForStorage(LocalDate.of(2025, 10, 15)));
    }

    @Test
    public void formatDateForDisplay_validDate_displayStringReturned() {
        assertEquals("Oct 15 2025", Parser.formatDateForDisplay(LocalDate.of(2025, 10, 15)));
    }

    @Test
    public void formatDateForStorage_parsedBack_originalDateReturned() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        assertEquals(date, Parser.parseDate(Parser.formatDateForStorage(date)));
    }
}
