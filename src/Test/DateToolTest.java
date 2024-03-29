package Test;

import validation.DateTools;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateToolTest {

    @Test
    public void validateValidDateReturnsTrue() {
        int day = 12, month = 5, year = 2022;
        assertTrue(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDateWithZeroDayReturnsFalse() {
        int day = 0, month = 5, year = 2022;
        assertFalse(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDateWithZeroMonthReturnsFalse() {
        int day = 12, month = 0, year = 2022;
        assertFalse(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDateWithZeroYearReturnsFalse() {
        int day = 12, month = 5, year = 0;
        assertFalse(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDateWithInvalidMonthReturnsFalse() {
        int day = 12, month = 13, year = 2022; // Month 13 doesn't exist
        assertFalse(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDateWithInvalidDayReturnsFalse() {
        int day = 32, month = 1, year = 2022; // January doesn't have 32 days
        assertFalse(DateTools.validateDate(day, month, year));
    }

    @Test
    public void validateDateWithInvalidFebruaryDayInLeapYearReturnsFalse() {
        int day = 29, month = 2, year = 2024; // 2024 is a leap year
        assertFalse(DateTools.validateDate(day, month, year)); // February 29th, 2024 is a valid date

        day = 29;
        month = 2;
        year = 2023; // 2023 is not a leap year
        assertFalse(DateTools.validateDate(day, month, year)); // February 29th, 2023 is an invalid date
    }

    @Test
    public void validateDateWithInvalidFebruaryDayInNonLeapYearReturnsFalse() {
        int day = 30, month = 2, year = 2021; // February 2021 doesn't have 30 days
        assertFalse(DateTools.validateDate(day, month, year)); // February 30th, 2021 is an invalid date
    }
}
