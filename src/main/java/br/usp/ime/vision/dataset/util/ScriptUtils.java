package br.usp.ime.vision.dataset.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.entities.Script;

public class ScriptUtils {

    private static final Logger logger = LoggerFactory
            .getLogger(ScriptUtils.class);

    /**
     * Removes the attached file from the FileSystem.
     * 
     * @param id
     */
    public static void deleteScript(final int scriptId) {
        final File script = new File(getScriptFilePath(scriptId));
        script.delete();
    }

    private static String getScriptFilePath(final int scriptId) {
        return Configs.getScriptDir() + File.separatorChar + scriptId;
    }

    /**
     * Gets a copy the associated file with the given id; The name of the file
     * will be some unique string followed by the attachment's name.
     * 
     * @param attachmentId
     *            <code>id</code> of the given {@link Attachment}
     * @return an {@link File} associated.
     * @throws IOException
     */
    public static File getFile(final int scriptId,
            final String scriptName) throws IOException {
        final File script = new File(getScriptFilePath(scriptId));
        final File tempFile = File.createTempFile("tmp", scriptName);
        FileUtils.copyFile(script, tempFile);
        return tempFile;
    }

    /**
     * Saves attachmentFile in the attachment directory for posterior
     * retrieaval.
     * 
     * @param attachmentId
     *            <code>id</code> of the attachment.
     * @param attachmentFile
     *            <code>File</code> to be saved.
     * @throws IOException
     *             if the attachment was not saved successfully.
     */
    public static void saveScript(final int scriptId,
            final File scriptFile) throws IOException {
        final File scriptDir = new File(Configs.getScriptDir());
        if (!scriptDir.exists()) {
            scriptDir.mkdir();
            logger.info("script directory created: {}",
                    scriptDir.getAbsolutePath());
        }

        final File file = new File(getScriptFilePath(scriptId));
        FileUtils.copyFile(scriptFile, file);
        logger.info("Script saved at {}", file.getAbsolutePath());
    }
}
