package validation;

public class PostalCode {

    /**
     * Formats the input postal code to a uniform output in the form
     * nnnn<space>MM, where nnnn is numeric and > 999 and MM are 2 capital
     * letters. Spaces before and after the input string are trimmed.
     * 
     * @param postalCode The input postal code to be formatted.
     * @return The formatted postal code.
     * @throws NullPointerException if postalCode is null.
     * @throws IllegalArgumentException if the postalCode is not valid.
     * 
     * @subcontract null postalCode {
     *     @requires postalCode == null;
     *     @signals (NullPointerException) postalCode == null;
     * }
     * 
     * @subcontract valid postalCode {
     *     @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     *               Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     *               postalCode.trim().substring(4).trim().length == 2 &&
     *               'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z' &&
     *               'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z';
     *     @ensures \result = postalCode.trim().substring(0, 4) + " " +
     *                        postalCode.trim().substring(4).trim().toUpperCase();
     * }
     * 
     * @subcontract invalid postalCode {
     *     @requires no other valid precondition;
     *     @signals (IllegalArgumentException);
     * }
     */
    public static String formatPostalCode(String postalCode) {
        // Check if postalCode is null
        if (postalCode == null) {
            throw new NullPointerException("postalCode cannot be null");
        }

        // Trim any leading or trailing spaces
        postalCode = postalCode.trim();

        // Check if postalCode is 6 characters long
        if (postalCode.length() != 6) {
            throw new IllegalArgumentException("postalCode must be 6 characters long");
        }

        // Extract the first four digits
        int firstFourDigits = Integer.parseInt(postalCode.substring(0, 4));

        // Check if the first four digits are within the valid range (1000 to 9999)
        if (firstFourDigits <= 999 || firstFourDigits > 9999) {
            throw new IllegalArgumentException("first four digits of postalCode must be between 1000 and 9999");
        }

        // Extract the last two characters and convert them to uppercase
        String lastTwoCharacters = postalCode.substring(4).trim().toUpperCase();

        // Check if the last two characters are exactly 2 letters
        if (lastTwoCharacters.length() != 2) {
            throw new IllegalArgumentException("last two characters of postalCode must be 2 characters long");
        }

        // Check if the last two characters are both capital letters
        char firstLetter = lastTwoCharacters.charAt(0);
        char secondLetter = lastTwoCharacters.charAt(1);
        if (firstLetter < 'A' || firstLetter > 'Z' || secondLetter < 'A' ||
                secondLetter > 'Z') {
            throw new IllegalArgumentException("last two characters of postalCode must be capital letters");
        }

        // Format and return the postal code
        return firstFourDigits + " " + lastTwoCharacters;
    }

}
