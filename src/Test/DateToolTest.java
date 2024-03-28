package Test;

import validation.DateTools;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateToolTest {

    @Test
    public void validateDate31DaysInMonthReturnsTrue() {
        int day = 12, month = 12, year = 2022;
        assertTrue(DateTools.validateDate(day, month, year));

        day = 1;
        month = 1;
        year = 2023;
        assertTrue(DateTools.validateDate(day, month, year));

        day = 5;
        month = 7;
        year = 2021;
        assertTrue(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDate30DaysInMonthReturnsTrue() {
        int day = 30, month = 4, year = 2022;
        assertTrue(DateTools.validateDate(day, month, year));

        day = 1;
        month = 6;
        year = 2023;
        assertTrue(DateTools.validateDate(day, month, year));

        day = 31;
        month = 9;
        year = 2021;
        assertFalse(DateTools.validateDate(day, month, year));

        day = 31;
        month = 11;
        year = 2021;
        assertFalse(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDate29DaysInMonthReturnsTrueForLeapYear() {
        int day = 29, month = 2, year = 2024;
        assertTrue(DateTools.validateDate(day, month, year));

        day = 29;
        month = 2;
        year = 2023;
        assertFalse(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDate28DaysInMonthReturnsTrue() {
        int day = 28, month = 2, year = 2023;
        assertTrue(DateTools.validateDate(day, month, year));

        day = 29;
        month = 2;
        year = 2023;
        assertFalse(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDate30February2024ReturnsFalse() {
        int day = 30, month = 2, year = 2024;
        assertFalse(DateTools.validateDate(day, month, year));
    }
}

