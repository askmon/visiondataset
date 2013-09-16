package br.usp.ime.vision.dataset.util;

import java.io.File;
import java.io.FileReader;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;

/**
 * Class with utilities for manipulation of mimeTypes.
 * 
 * @author Bruno Klava
 */
public class MimeTypeUtils {

    static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(MimeTypeUtils.class);

    private static final MimetypesFileTypeMap MIME_TYPES = new MimetypesFileTypeMap();

    private static MimeTypeUtils instance = null;

    /**
     * Returns an extension for the given <code>mimeType</code>.
     * 
     * @param mimeType
     *            a MimeType
     * @return an extension for the given <code>mimeType</code>,
     *         <code>null</code> if no extension was found for the given
     *         <code>mimeType</code>
     */
    public static String getExtension(final String mimeType) {
        try {
            return getInstance().getProperties().getProperty(mimeType);
        } catch (final MissingResourceException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    private static MimeTypeUtils getInstance() {
        if (instance == null) {
            instance = new MimeTypeUtils();
        }
        return instance;
    }

    /**
     * Returns the <code>mimeType</code> of the given <code>file</code>.
     * 
     * @param file
     *            a file
     * @return the <code>mimeType</code> of the given <code>file</code>
     */
    public static String getMimeType(final File file) {
        return MIME_TYPES.getContentType(file);
    }

    public static String guessMimeType(final File file) {
        final MagicMimeMimeDetector detector = new MagicMimeMimeDetector();
        final MimeType mimeType = MimeUtil.getMostSpecificMimeType(detector
                .getMimeTypesFile(file));
        if (mimeType != null) {
            return mimeType.toString();
        } else {
            return "application/octet-stream";
        }
    }

    private final Properties properties;

    private MimeTypeUtils() {
        properties = new Properties();
        loadExtensions(Configs.getMimeExtensionsFile());
        MIME_TYPES.addMimeTypes("image/png png PNG");
    }

    private Properties getProperties() {
        return properties;
    }

    /**
     * Loads extensions from the file at <code>path</code>.
     * 
     * @param path
     * @return <code>true</code> if the extensions from the file at
     *         <code>path</code> were successfully loaded
     */
    public boolean loadExtensions(final String path) {
        try {
            final File file = new File(path);
            getProperties().load(new FileReader(file));
            logger.info("Extensions for MimeTypes loaded from {}", path);
        } catch (final Exception e) {
            logger.error(
                    "Error while loading extensions for MimeTypes from {}",
                    path, e);
            return false;
        }
        return true;
    }

}
