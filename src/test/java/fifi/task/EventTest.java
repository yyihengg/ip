package fifi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests the displayed and saved forms of event tasks.
 */
public class EventTest {

    @Test
    public void toString_unmarkedEvent_eventStringReturned() {
        Event event = new Event(false, "project meeting", LocalDate.of(2019, 12, 2),
                LocalDate.of(2019, 12, 4));

        assertEquals("[E][ ] project meeting (from: Dec 02 2019 to: Dec 04 2019)", event.toString());
    }

    @Test
    public void toString_markedEvent_markedEventStringReturned() {
        Event event = new Event(true, "project meeting", LocalDate.of(2019, 12, 2),
                LocalDate.of(2019, 12, 4));

        assertEquals("[E][X] project meeting (from: Dec 02 2019 to: Dec 04 2019)", event.toString());
    }

    @Test
    public void toFileString_unmarkedEvent_eventStorageStringReturned() {
        Event event = new Event(false, "project meeting", LocalDate.of(2019, 12, 2),
                LocalDate.of(2019, 12, 4));

        assertEquals("E | 0 | project meeting | 2019-12-02 | 2019-12-04", event.toFileString());
    }

    @Test
    public void toFileString_markedEvent_markedEventStorageStringReturned() {
        Event event = new Event(true, "project meeting", LocalDate.of(2019, 12, 2),
                LocalDate.of(2019, 12, 4));

        assertEquals("E | 1 | project meeting | 2019-12-02 | 2019-12-04", event.toFileString());
    }
}
