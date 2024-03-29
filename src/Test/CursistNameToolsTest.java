package Test;

import validation.CursistNameTools;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CursistNameToolsTest {

    @Test
    public void testValidCursistNameReturnsTrue() {
        String validName = "JohnDoe";
        assertTrue(CursistNameTools.validateCursistName(validName));
    }

    @Test
    public void testEmptyCursistNameReturnsFalse() {
        String emptyName = "";
        assertFalse(CursistNameTools.validateCursistName(emptyName));
    }

    @Test
    public void testCursistNameWithDigitsReturnsFalse() {
        String nameWithDigits = "John123";
        assertFalse(CursistNameTools.validateCursistName(nameWithDigits));
    }

    @Test
    public void testNameTooLongReturnsFalse() {
        String longName = "qwertyQwertyQwertyQwertyQwertyQwertyQwertyQwertyQwertyQwertyQwertyQwerty";
        assertFalse(CursistNameTools.validateCursistName(longName));
    }
    
}
