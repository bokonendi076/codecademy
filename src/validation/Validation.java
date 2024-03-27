package validation;

public class Validation {

    
    // check if email is correct according to below standards
    public boolean validateMailAddress(String mailAddress) {

        if (!mailAddress.contains("@") || mailAddress.split("@")[0].length() < 1) {
            return false;

        } else if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.", 2).length > 2) {
            return false;

        } else if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.")[0].length() < 1) {
            return false;

        } else if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.")[1].length() < 1) {
            return false;
        }

        return true;

    }

    // check if date is an actuall date
    public static boolean validateDate(int day, int month, int year) {

        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                && (1 <= day && day <= 31)) {

            return true;

        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && (1 <= day && day <= 30)) {
            return true;

        } else if (month == 2 && 1 <= day && day <= 29 && (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) {
            return true;

        } else if (month == 2 && 1 <= day && day <= 28 && (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0))) {
            return true;
        }

        return false;
    }

    // checks wheter the number is an actual percentage >= 0 && <== 100
    public static boolean isValidPercentage(int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            return true;
        } else if (percentage < 0) {
            return false;
        } else { // percentage > 100
            throw new IllegalArgumentException("Percentage must be within the range of 0-100%");
        }
    }

    // checks wheter the postal code is legit and to below standards
    public static String formatPostalCode(String postalCode) {
        if (postalCode == null) {
            throw new NullPointerException("postalCode cannot be null");
        }

        postalCode = postalCode.trim();

        if (postalCode.length() != 6) {
            throw new IllegalArgumentException("postalCode must be 6 characters long");
        }

        int firstFourDigits = Integer.parseInt(postalCode.substring(0, 4));

        if (firstFourDigits <= 999 || firstFourDigits > 9999) {
            throw new IllegalArgumentException("first four digits of postalCode must be between 1000 and 9999");
        }

        String lastTwoCharacters = postalCode.substring(4).trim().toUpperCase();

        if (lastTwoCharacters.length() != 2) {
            throw new IllegalArgumentException("last two characters of postalCode must be 2 characters long");
        }

        char firstLetter = lastTwoCharacters.charAt(0);
        char secondLetter = lastTwoCharacters.charAt(1);

        if (firstLetter < 'A' || firstLetter > 'Z' || secondLetter < 'A' || secondLetter > 'Z') {
            throw new IllegalArgumentException("last two characters of postalCode must be capital letters");
        }

        return firstFourDigits + " " + lastTwoCharacters;
    }
}
