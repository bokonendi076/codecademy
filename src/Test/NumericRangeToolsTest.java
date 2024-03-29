package Test;

import validation.NumericRangeTools;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NumericRangeToolsTest {

    @Test
    public void validatePercentageValidPercentage50ReturnsTrue() {
        // Arrange
        int validPercentage = 50;

        // Act / Assert
        assertTrue(NumericRangeTools.isValidPercentage(validPercentage));
    }

    @Test
    public void validatePercentageValidPercentage0ReturnsTrue() {
        // Arrange
        int validPercentage = 0;

        // Act / Assert
        assertTrue(NumericRangeTools.isValidPercentage(validPercentage));
    }

    @Test
    public void validatePercentageValidPercentage100ReturnsTrue() {
        // Arrange
        int validPercentage = 100;

        // Act / Assert
        assertTrue(NumericRangeTools.isValidPercentage(validPercentage));
    }

    @Test
    public void validatePercentageInvalidPercentageLowReturnsFalse() {
        // Arrange
        int invalidPercentage = -5;

        // Act / Assert
        assertFalse(NumericRangeTools.isValidPercentage(invalidPercentage));
    }


    @Test
    public void validatePercentageValidPercentage1ReturnsTrue() {
        // Arrange
        int validPercentage = 1;

        // Act / Assert
        assertTrue(NumericRangeTools.isValidPercentage(validPercentage));
    }

    @Test
    public void validatePercentageValidPercentage99ReturnsTrue() {
        // Arrange
        int validPercentage = 99;

        // Act / Assert
        assertTrue(NumericRangeTools.isValidPercentage(validPercentage));
    }

    @Test
    public void validatePercentageInvalidPercentageNegativeOneReturnsFalse() {
        // Arrange
        int invalidPercentage = -1;

        // Act / Assert
        assertFalse(NumericRangeTools.isValidPercentage(invalidPercentage));
    }

}
