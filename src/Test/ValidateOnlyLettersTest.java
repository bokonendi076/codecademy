package Test;

import validation.ValidateOnlyLetters;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ValidateOnlyLettersTest {

    @Test
    public void validateOnlyLettersReturnsTrueForValidInput() {
        String validInput = "abcdef";
        assertTrue(ValidateOnlyLetters.validateOnlyLetters(validInput));
    }

    @Test
    public void validateOnlyLettersReturnsTrueForUpperCaseLetters() {
        String validInput = "ABCDEF";
        assertTrue(ValidateOnlyLetters.validateOnlyLetters(validInput));
    }

    @Test
    public void validateOnlyLettersReturnsFalseForDigits() {
        String invalidInput = "12345";
        assertFalse(ValidateOnlyLetters.validateOnlyLetters(invalidInput));
    }

    @Test
    public void validateOnlyLettersReturnsFalseForSpecialCharacters() {
        String invalidInput = "!@#$%";
        assertFalse(ValidateOnlyLetters.validateOnlyLetters(invalidInput));
    }

    @Test
    public void validateOnlyLettersReturnsFalseForMixOfLettersAndDigits() {
        String invalidInput = "abc123";
        assertFalse(ValidateOnlyLetters.validateOnlyLetters(invalidInput));
    }

    @Test
    public void validateOnlyLettersReturnsFalseForNullInput() {
        assertFalse(ValidateOnlyLetters.validateOnlyLetters(null));
    }
}
