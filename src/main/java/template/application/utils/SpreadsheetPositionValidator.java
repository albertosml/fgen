package template.application.utils;

import shared.application.utils.RegexValidator;

/**
 * Validate the spreadsheet position.
 */
public class SpreadsheetPositionValidator {

    /**
     * Check whether the spreadsheet position is valid or not.
     *
     * @param position The spreadsheet position to validate.
     * @return true if it has a valid format, otherwise false.
     */
    public static boolean isValid(String position) {
        if (position == null) {
            return false;
        }

        // Example valid position: B13
        String validPositionRegex = "^[A-Z]+[1-9]+[0-9]*$";

        return RegexValidator.matches(position, validPositionRegex);
    }

}
