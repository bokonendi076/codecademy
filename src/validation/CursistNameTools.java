package validation;

public class CursistNameTools {

    // Check if the name is not empty and if the name contains only letters and not longer than 50 characters
    public boolean validateCursistName(String cursistName) {
 
        if (cursistName.isEmpty()) {
            return false;
        }
        
        if (!cursistName.matches("[a-zA-Z]+") || cursistName.length() > 50) {
            return false;
        }
        
        return true;
    }   
    
}
