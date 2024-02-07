package validation;

public class NumericRangeTools {

    /**
     * @desc Validates if the input is within range of 0-100%
     * 
     * @subcontract value within valid range {
     * @requires 0 <= percentage <= 100;
     * @ensures \result = true;
     *          }
     * 
     * @subcontract value out of range low {
     * @requires percentage < 0;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract value out of range high {
     * @requires percentage > 100;
     * @signals \result = false;
     *          }
     * 
     */
    public static boolean isValidPercentage(int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            return true;
        } else if (percentage < 0) {
            return false;
        } else { // percentage > 100
            throw new IllegalArgumentException("Percentage must be within the range of 0-100%");
        }
    }
}
