package Test;

import validation.DateTools;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateToolTest {

    @Test
    public void testValidDate31DaysInMonth() {
        assertTrue(DateTools.validateDate(1, 1, 2022)); // January 1, 2022
        assertTrue(DateTools.validateDate(31, 1, 2022)); // January 31, 2022
        assertTrue(DateTools.validateDate(1, 3, 2022)); // March 1, 2022
        assertTrue(DateTools.validateDate(31, 3, 2022)); // March 31, 2022
        assertTrue(DateTools.validateDate(1, 5, 2022)); // May 1, 2022
        assertTrue(DateTools.validateDate(31, 5, 2022)); // May 31, 2022
        assertTrue(DateTools.validateDate(1, 7, 2022)); // July 1, 2022
        assertTrue(DateTools.validateDate(31, 7, 2022)); // July 31, 2022
        assertTrue(DateTools.validateDate(1, 8, 2022)); // August 1, 2022
        assertTrue(DateTools.validateDate(31, 8, 2022)); // August 31, 2022
        assertTrue(DateTools.validateDate(1, 10, 2022)); // October 1, 2022
        assertTrue(DateTools.validateDate(31, 10, 2022)); // October 31, 2022
        assertTrue(DateTools.validateDate(1, 12, 2022)); // December 1, 2022
        assertTrue(DateTools.validateDate(31, 12, 2022)); // December 31, 2022
    }

    @Test
    public void testValidDate30DaysInMonth() {
        assertTrue(DateTools.validateDate(1, 4, 2022)); // April 1, 2022
        assertTrue(DateTools.validateDate(30, 4, 2022)); // April 30, 2022
        assertTrue(DateTools.validateDate(1, 6, 2022)); // June 1, 2022
        assertTrue(DateTools.validateDate(30, 6, 2022)); // June 30, 2022
        assertTrue(DateTools.validateDate(1, 9, 2022)); // September 1, 2022
        assertTrue(DateTools.validateDate(30, 9, 2022)); // September 30, 2022
        assertTrue(DateTools.validateDate(1, 11, 2022)); // November 1, 2022
        assertTrue(DateTools.validateDate(30, 11, 2022)); // November 30, 2022
    }

    @Test
    public void testValidDate29DaysInMonthLeapYear() {
        assertTrue(DateTools.validateDate(1, 2, 2020)); // February 1, 2020 (leap year)
        assertTrue(DateTools.validateDate(29, 2, 2020)); // February 29, 2020 (leap year)
    }

    @Test
    public void testValidDate28DaysInMonthNonLeapYear() {
        assertTrue(DateTools.validateDate(1, 2, 2021)); // February 1, 2021 (non-leap year)
        assertTrue(DateTools.validateDate(28, 2, 2021)); // February 28, 2021 (non-leap year)
    }

    @Test
    public void testInvalidDate() {
        assertFalse(DateTools.validateDate(31, 4, 2022)); // April 31, 2022
        assertFalse(DateTools.validateDate(30, 2, 2022)); // February 30, 2022
    }
}
