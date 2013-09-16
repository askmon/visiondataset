package br.usp.ime.vision.dataset.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.entities.Attachment;

public class AttachmentUtils {

    private static final Logger logger = LoggerFactory
            .getLogger(AttachmentUtils.class);

    /**
     * Removes the attached file from the FileSystem.
     * 
     * @param id
     */
    public static void deleteAttachment(final int attachmentId) {
        final File attachment = new File(getAttachmentFilePath(attachmentId));
        attachment.delete();
    }

    private static String getAttachmentFilePath(final int attachmentId) {
        return Configs.getAttachmentDir() + File.separatorChar + attachmentId;
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
    public static File getFile(final int attachmentId,
            final String attachmentName) throws IOException {
        final File attachment = new File(getAttachmentFilePath(attachmentId));
        final File tempFile = File.createTempFile("tmp", attachmentName);
        FileUtils.copyFile(attachment, tempFile);
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
    public static void saveAttachment(final int attachmentId,
            final File attachmentFile) throws IOException {
        final File attachmentDir = new File(Configs.getAttachmentDir());
        if (!attachmentDir.exists()) {
            attachmentDir.mkdir();
            logger.info("attachment directory created: {}",
                    attachmentDir.getAbsolutePath());
        }

        final File file = new File(getAttachmentFilePath(attachmentId));
        FileUtils.copyFile(attachmentFile, file);
        logger.info("Attachment saved at {}", file.getAbsolutePath());
    }
}
