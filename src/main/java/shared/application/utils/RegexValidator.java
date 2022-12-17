package shared.application.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate any regular expression against a value.
 */
public class RegexValidator {

    /**
     * Check whether the given value matches against the received expression.
     *
     * @param value The value to match.
     * @param expression The regular expression pattern.
     * @return true if the value matches with the given expression, otherwise
     * false.
     */
    public static boolean matches(String value, String expression) {
        if (value == null || expression == null) {
            return false;
        }

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }
}
