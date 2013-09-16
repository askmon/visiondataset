package br.usp.ime.vision.dataset.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates usernames according to the regex <code>^[a-zA-Z0-9_.]+$</code>.
 * 
 * @author Bruno Klava
 */
public class UsernameValidator {

    private static Logger logger = LoggerFactory
            .getLogger(UsernameValidator.class);

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_.]+$";

    private static Pattern pattern;

    /**
     * Returns if the given <code>username</code> is not null and valid.
     * 
     * @param username
     * @return <code>true</code> if <code>username</code> is not null and valid
     */
    public static boolean validate(final String username) {

        boolean valid = false;

        if (username != null) {
            if (pattern == null) {
                pattern = Pattern.compile(USERNAME_PATTERN);
            }
            final Matcher matcher = pattern.matcher(username);
            valid = matcher.matches();
        }

        logger.debug("{} is valid: {}", username, valid);

        return valid;
    }

}
