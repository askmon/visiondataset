package br.usp.ime.vision.dataset.util;

import java.io.File;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads messages from a file and format the messages.
 * 
 * @author Bruno Klava
 */
public class Messages {

    static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(Messages.class);

    private static Messages instance = null;

    private static Messages getInstance() {
        if (instance == null) {
            instance = new Messages();
        }
        return instance;
    }

    /**
     * Returns the message formed by the message given by <code>key</code>
     * formatted with the given <code>arguments</code>.
     * 
     * @param key
     *            a key of a message
     * @param arguments
     *            optional arguments
     * @return message given by <code>key</code> and formatted with the
     *         <code>arguments</code>
     */
    public static String getMessage(final String key, final Object... arguments) {
        try {
            final String msg = getInstance().getProperties().getProperty(key);
            if (arguments.length == 0) {
                return msg;
            } else {
                return MessageFormat.format(msg, arguments);
            }
        } catch (final MissingResourceException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private final Properties properties;

    private Messages() {
        properties = new Properties();
        loadMessages(Configs.getMessagesFile());
    }

    private Properties getProperties() {
        return properties;
    }

    /**
     * Loads messages from the file at <code>path</code>.
     * 
     * @param path
     * @return <code>true</code> if the messages from the file at
     *         <code>path</code> were successfully loaded
     */
    public boolean loadMessages(final String path) {
        try {
            final File file = new File(path);
            getProperties().load(new FileReader(file));
            logger.info("Messages loaded from {}", path);
        } catch (final Exception e) {
            logger.error("Error while loading messages from {}", path, e);
            return false;
        }
        return true;
    }

}
