package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the Event class
 * DISCLAIMER: Multiple methods and code structures are copied from CPSC210/AlarmSystem
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same second for this test to make sense and pass.

    //Initial setup
    @BeforeEach
    public void runBefore() {
        e = new Event("New galaxy Chungus created");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    //Ensure that Test constructor properly initializes and compares description and date
    @Test
    public void testEvent() {
        assertEquals("New galaxy Chungus created", e.getDescription());
        assertEquals(d.toString(), e.getDate().toString());
    }

    //Test that comparing event to null returns false
    @Test
    public void testNullComparison(){
        assertFalse(e.equals(null));
    }

    //Test that comparing event to different class returns false
    @Test
    public void testWrongClassComparison(){
        assertFalse(e.equals(new ArrayList<>()));
    }

    //Test that hash codes for two different objects with the same values is the same
    @Test
    public void testHashCodeMatch() {
        Event e1 = new Event("a");
        Event e2 = new Event("a");
        assertEquals(e1.hashCode(),e2.hashCode());
    }

    //Test that toString properly adds newline
    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "New galaxy Chungus created", e.toString());
    }
}
