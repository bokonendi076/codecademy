package validation;

public class ValidateOnlyLetters {

    /**
     * @desc Validates if the input contains only letters (a-z, A-Z).
     * 
     * @subcontract validInput {
     * @requires input != null && input matches "^[a-zA-Z]+$";
     * @ensures \result = true;
     *          }
     * 
     * @subcontract invalidInput {
     * @requires input == null || input does not match "^[a-zA-Z]+$";
     * @ensures \result = false;
     *          }
     * 
     */

    // validate the input to be only letters
    public static boolean validateOnlyLetters(String input) {
        if (input == null) {
            return false;
        }
        return input.matches("^[a-zA-Z]+$");
    }
}
