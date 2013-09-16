package br.usp.ime.vision.dataset.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class with utilities for file manipulation.
 * 
 * @author Bruno Klava
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory
            .getLogger(FileUtils.class);

    /**
     * Decompress a file using a bash command.
     * 
     * @param compressedFilePath
     *            the path to the file to be decompressed
     * @param mimeType
     *            the mimeType of the the file to be decompressed
     * @param dstDirPath
     *            the path to the directory where the file has to be
     *            decompressed
     * @throws Exception
     *             if the file decompression was not successful
     */
    public static void decompress(final String compressedFilePath,
            final String mimeType, final String dstDirPath) throws Exception {

        final String command = getDecompressCommand(compressedFilePath,
                mimeType, dstDirPath);

        logger.info("Executing command: {}", command);
        final Process process = Runtime.getRuntime().exec(command);
        final int exitCode = process.waitFor();
        logger.debug("Exit code: {}", exitCode);

        if (exitCode == 0) {
            logger.info("{} decompressed at {}", compressedFilePath, dstDirPath);
        } else {
            throw new Exception("Error while decompressing "
                    + compressedFilePath + " (exitCode " + exitCode + ")");
        }

    }

    private static String getDecompressCommand(final String compressedFileName,
            final String mimeType, final String dstDirName) {

        if (mimeType.equals("application/x-tar")) {
            return "tar -xf " + compressedFileName + " --directory="
                    + dstDirName;
        } else if (mimeType.equals("application/x-gzip")) {
            return "tar -xzf " + compressedFileName + " --directory="
                    + dstDirName;
        } else if (mimeType.equals("application/zip")) {
            return "unzip " + compressedFileName + " -d " + dstDirName;
        } else if (mimeType.equals("application/rar")
                || mimeType.equals("application/x-rar")) {
            return "unrar x " + compressedFileName + " " + dstDirName;
        } else {
            return null;
        }

    }

}
