package validation;

public class NumericRangeTools {

    /**
     * Validates if the input is within the range of 0-100%.
     * 
     * @param percentage The percentage value to be validated.
     * @return true if the percentage is within the valid range, false otherwise.
     * @throws IllegalArgumentException if the percentage is out of range (less than 0 or greater than 100).
     * 
     * @subcontract value within valid range {
     *     @requires 0 <= percentage <= 100;
     *     @ensures \result = true;
     * }
     * 
     * @subcontract value out of range low {
     *     @requires percentage < 0;
     *     @ensures \result = false;
     * }
     * 
     * @subcontract value out of range high {
     *     @requires percentage > 100;
     *     @signals \result = false;
     * }
     */
    public static boolean isValidPercentage(int percentage) {
        // Check if the percentage is within the valid range
        if (percentage >= 0 && percentage <= 100) {
            return true; // Percentage is valid
        } else if (percentage < 0) {
            return false; // Percentage is less than 0, not valid
        } else {
            // Percentage is greater than 100, throw an exception
            throw new IllegalArgumentException("Percentage must be within the range of 0-100%");
        }
    }
}
