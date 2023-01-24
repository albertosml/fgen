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

        // Example valid position range: B13:B19
        String validRangeRegex = "^[A-Z]+[1-9]+[0-9]*[:]{1}[A-Z]+[1-9]+[0-9]*$";

        String spreadsheetPositionRegex = String.format("%s|%s", validPositionRegex, validRangeRegex);

        return RegexValidator.matches(position, spreadsheetPositionRegex);
    }

}
