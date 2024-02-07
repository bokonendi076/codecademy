package Test;

import validation.MailTools;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MailToolsTest {

    @Test
    public void testValidMailAddressReturnsTrue() {
        String validAddress = "valid@example.com";
        assertTrue(MailTools.validateMailAddress(validAddress));
    }

    @Test
    public void testSubdomainTldDelimiterReturnsTrue() {
        String invalidAddress = "valid@example.com";
        assertTrue(MailTools.validateMailAddress(invalidAddress));
    }

    @Test
    public void testNoSubdomainPartReturnsFalse() {
        String invalidAddress = "valid@.com";
        assertFalse(MailTools.validateMailAddress(invalidAddress));
    }

    @Test
    public void testInvalidMailAddressReturnsFalse() {
        String invalidAddress = "invalidemail";
        assertFalse(MailTools.validateMailAddress(invalidAddress));
    }
}
