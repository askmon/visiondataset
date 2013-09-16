package br.usp.ime.vision.dataset.util;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener utilized to load/store configurations from the web environment.
 * 
 * @author Bruno Klava
 */
public class Configs {

    private static final String CONF_FILE = "internal.properties";

    private static final Logger logger = LoggerFactory.getLogger(Configs.class);

    private static Configuration config = null;

    public static String getAttachmentDir() {
        return getConfig().getString("AttachmentDir");
    }

    public static String getScriptDir() {
        return getConfig().getString("ScriptDir");
    }

    public static String getConfDir() {
        return getConfig().getString("ConfDir");
    }

    public static Configuration getConfig() {
        if (config == null) {
            try {
                config = new PropertiesConfiguration(CONF_FILE);
            } catch (final ConfigurationException e) {
                logger.error("Could not get the configuration");
                e.printStackTrace();
            }
        }
        return config;
    }

    public static String getConnectionFactoryClassName() {
        return getConfig().getString("ConnectionFactoryClass");
    }

    public static String getImagesDir() {
        return getConfig().getString("ImagesDir");
    }

    public static String getMessagesFile() {
        return getConfDir() + File.separatorChar
                + getConfig().getString("MessagesFile");
    }

    public static String getMimeExtensionsFile() {
        return getConfDir() + File.separatorChar
                + getConfig().getString("ExtensionsFile");
    }

    public static String getScriptsDir() {
        return getConfig().getString("ScriptsDir");
    }

    public static String getServerURL() {
        return getConfig().getString("ServerURL");
    }

    public static String getThumbnailsDir() {
        return getConfig().getString("ThumbnailsDir");
    }

}
