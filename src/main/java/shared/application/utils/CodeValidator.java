package shared.application.utils;

/**
 * Validate a code.
 */
public class CodeValidator {

    /**
     * Whether the code is valid or not.
     *
     * @param code The code to validate.
     * @return true if the code is greater than 0, otherwise false.
     */
    public static boolean isValid(String code) {
        String codeRegex = "^[1-9][0-9]*$";
        return RegexValidator.matches(code, codeRegex);
    }

}
