package Test;

import validation.PostalCode;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PostalCodeTest {

    @Test
    public void formatPostalCodeNullPostalCodeThrowsNullPointerException() {
        // Act / Assert
        assertThrows(NullPointerException.class, () -> PostalCode.formatPostalCode(null));
    }

    @Test
    public void formatPostalCodeValidPostalCodeReturnsFormattedPostalCode() {
        // Arrange
        String inputPostalCode = "3456YZ";
        String expectedFormattedPostalCode = "3456 YZ";

        // Act
        String result = PostalCode.formatPostalCode(inputPostalCode);

        // Assert
        assertEquals(expectedFormattedPostalCode, result);
    }

    @Test
    public void formatPostalCodeInvalidPostalCodeThrowsIllegalArgumentException() {
        // Arrange
        String invalidPostalCode = "invalid";

        // Act / Assert
        assertThrows(IllegalArgumentException.class, () -> PostalCode.formatPostalCode(invalidPostalCode));
    }

    @Test
    public void formatPostalCodeTrimsLeadingAndTrailingSpaces() {
        // Arrange
        String inputPostalCode = "  1234AB  ";
        String expectedFormattedPostalCode = "1234 AB";

        // Act
        String result = PostalCode.formatPostalCode(inputPostalCode);

        // Assert
        assertEquals(expectedFormattedPostalCode, result);
    }
}
