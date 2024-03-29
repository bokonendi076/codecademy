package validation;

public class CursistNameTools {

       /**
     * @desc Validates if a given date in the form of day, month, and year is valid.
     *
     * @subcontract 31 days in month {
     *     @requires (month == 1 || month == 3 || month == 5 || month == 7 ||
     *                month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
     *     @ensures \result = true;
     * }
     *
     * @subcontract 30 days in month {
     *     @requires (month == 4 || month == 6 || month == 9 || month == 11) &&
     *                1 <= day <= 30;
     *     @ensures \result = true;
     * }
     *
     * @subcontract 29 days in month {
     *     @requires month == 2 && 1 <= day <= 29 &&
     *                (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
     *     @ensures \result = true;
     * }
     *
     * @subcontract 28 days in month {
     *     @requires month == 2 && 1 <= day <= 28 &&
     *                (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0));
     *     @ensures \result = true;
     * }
     *
     * @subcontract all other cases {
     *     @requires no other accepting precondition;
     *     @ensures \result = false;
     * }
     *
     */

    // Check if the name is not empty and if the name contains only letters and not longer than 50 characters
    public static boolean validateCursistName(String cursistName) {
        if (cursistName.isEmpty()) {
            return false;
        }
    
        // Allow alphabets and spaces, up to a maximum length of 50 characters
        if (!cursistName.matches("[a-zA-Z\\s]+") || cursistName.length() > 50) {
            return false;
        }
    
        return true;
    }
    
    
}
