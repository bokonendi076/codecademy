package validation;

import org.junit.Test;

public class MailTools {

    /**
     * @desc Validates if mailAddress is valid. It should be in the form of:
     *       <at least 1 character>@<at least 1 character>.<at least 1 character>
     * 
     * @subcontract no mailbox part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[0].length < 1;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract subdomain-tld delimiter {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".").length > 2;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract no subdomain part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[0].length < 1;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract no tld part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[1].length < 1;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract valid email {
     * @requires no other precondition
     * @ensures \result = true;
     *          }
     * 
     */
    // check if email is correct according to below standards
    public static boolean validateMailAddress(String mailAddress) {

        if (!mailAddress.contains("@") || mailAddress.split("@")[0].length() < 1) {
            return false;

        } else if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.", 2).length > 2) {
            return false;

        } else if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.")[0].length() < 1) {
            return false;

        } else if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.")[1].length() < 1) {
            return false;
        }
        String domain = mailAddress.split("@")[1];
        String[] domainParts = domain.split("\\.");

        // Check if there is at least one part before the dot and at least one part
        // after the dot
        if (domainParts.length != 2 || domainParts[0].isEmpty() || domainParts[1].isEmpty()) {
            return false;
        }

        // Check if the part after the dot contains only letters
        if (!domainParts[1].matches("[a-zA-Z]+")) {
            return false;
        }

        return true;

    }
}
