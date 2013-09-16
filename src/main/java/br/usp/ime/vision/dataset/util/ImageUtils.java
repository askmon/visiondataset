package br.usp.ime.vision.dataset.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class with utilities for image file manipulation.
 * 
 * @author Bruno Klava
 */
public class ImageUtils {

    private static final Logger logger = LoggerFactory
            .getLogger(ImageUtils.class);

    /**
     * Creates a thumbnail of an image.
     * 
     * @param imageId
     *            the <code>id</code> associated to the image
     * @throws Exception
     *             if the thumbnail was not created successfully
     */
    public static void createThumbnail(final int imageId) throws Exception {

        final File thumbnailsDir = new File(Configs.getThumbnailsDir());
        if (!thumbnailsDir.exists()) {
            thumbnailsDir.mkdir();
            logger.info("Thumbnails directory created: {}",
                    thumbnailsDir.getAbsolutePath());
        }

        final String scriptFileName = Configs.getScriptsDir()
                + File.separatorChar + "thumbnail.sh";
        final String inputFileName = Configs.getImagesDir()
                + File.separatorChar + imageId;
        final String outputFileName = Configs.getThumbnailsDir()
                + File.separatorChar + imageId;

        final String[] scriptCommand = { scriptFileName, inputFileName,
                outputFileName };

        final Process process = Runtime.getRuntime().exec(scriptCommand);
        final int exitCode = process.waitFor();

        if (exitCode == 0) {
            logger.info("Thumbnail saved at {}", outputFileName);
        } else {
            throw new Exception(
                    "Error in thumbnail generation script (exitCode "
                            + exitCode + ")");
        }

    }

    /**
     * Get the image in filesystem.
     * 
     * @param imageId
     *            <code>id</code> of the image.
     * @return A {@link File}.
     */
    public static File getImageFile(final int imageId) {
        return new File(Configs.getImagesDir() + File.separatorChar + imageId);
    }

    private static void remove(final File file, final String type) {
        if (file.exists()) {
            if (file.delete()) {
                logger.info("{} deleted: {}", type, file.getAbsolutePath());
            } else {
                logger.error("Error while deleting {} {}", type.toLowerCase(),
                        file.getAbsolutePath());
            }
        } else {
            logger.info("{} doesn't exist: {}", type, file.getAbsolutePath());
        }
    }

    /**
     * Removes an image and its thumbnail from the filesystem.
     * 
     * @param imageId
     *            the <code>id</code> associated to the image/thumbnail
     */
    public static void removeImageAndThumbnail(final int imageId) {

        final File image = getImageFile(imageId);
        remove(image, "Image");

        final File thumbnail = new File(Configs.getThumbnailsDir()
                + File.separatorChar + imageId);
        remove(thumbnail, "Thumbnail");

    }

    /**
     * Saves an image in the appropriate directory.
     * 
     * @param imageId
     *            the <code>id</code> associated to the image
     * @param imageFile
     *            the<code>File</code> containing the image
     * @throws Exception
     *             if the image was not saved successfully
     */
    public static void saveImage(final int imageId, final File imageFile)
            throws Exception {

        final File imagesDir = new File(Configs.getImagesDir());
        if (!imagesDir.exists()) {
            imagesDir.mkdir();
            logger.info("Images directory created: {}",
                    imagesDir.getAbsolutePath());
        }

        final File file = getImageFile(imageId);
        FileUtils.copyFile(imageFile, file);
        logger.info("Image saved at {}", file.getAbsolutePath());

    }

}
