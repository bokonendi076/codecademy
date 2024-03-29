package validation;

public class ValidateStreetTools {

        // validate the street input
        public static boolean validateStreet(String input) {
            if (input == null) {
                return false;
            }
    
            return input.matches("[a-zA-Z\\s]+\\s\\d+");
        }
    
}
