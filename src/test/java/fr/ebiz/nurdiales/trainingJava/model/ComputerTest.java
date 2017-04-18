package fr.ebiz.nurdiales.trainingJava.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.sql.Date;

public class ComputerTest {
    /**
     * Object Constructor.
     */
    public ComputerTest() {
    }

    /**
     * @Test Check if checkDates return true if introduced and discontinued are null.
     */
    @Test
    public void testCheckDates1() {
        Computer computer1 = new Computer(0, null, null, null, null);
        assertTrue(computer1.checkDates());
    }

    /**
     * @Test Check if checkDates return true if discontinued is null.
     */
    @Test
    public void testCheckDates2() {
        Date before = Date.valueOf("1998-01-01");
        Computer computer2 = new Computer(0, null, before, null, null);
        assertTrue(computer2.checkDates());
    }

    /**
     * @Test Check if checkDates return true if introduced is null.
     */
    @Test
    public void testCheckDates3() {
        Date after = Date.valueOf("1999-01-01");
        Computer computer3 = new Computer(0, null, null, after, null);
        assertTrue(computer3.checkDates());
    }

    /**
     * @Test Check if checkDates return true if introduced is before discontinued.
     */
    @Test
    public void testCheckDates4() {
        Date before = Date.valueOf("1998-01-01");
        Date after = Date.valueOf("1999-01-01");
        Computer computer4 = new Computer(0, null, before, after, null);
        assertTrue(computer4.checkDates());
    }

    /**
     * @Test Check if checkDates return false if discontinued is before introduced.
     */
    @Test
    public void testCheckDates5() {
        Date before = Date.valueOf("1998-01-01");
        Date after = Date.valueOf("1999-01-01");
        Computer computer5 = new Computer(0, null, after, before, null);
        assertFalse(computer5.checkDates());
    }

    /**
     * @Test Check if checkDates return false if discontinued is equals to introduced.
     */
    @Test
    public void testCheckDates6() {
        Date before = Date.valueOf("1998-01-01");
        Computer computer6 = new Computer(0, null, before, before, null);
        assertFalse(computer6.checkDates());
    }
}
