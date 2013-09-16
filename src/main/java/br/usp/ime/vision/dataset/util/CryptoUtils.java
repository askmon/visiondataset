package br.usp.ime.vision.dataset.util;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for data encryption.
 * 
 * @author Bruno Klava
 */
public class CryptoUtils {

    private static Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    private static String byteArrayToHexString(final byte[] b) {
        final StringBuffer sb = new StringBuffer(b.length * 2);
        for (final byte element : b) {
            final int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] computeHash(final String x) {
        try {
            java.security.MessageDigest d = null;
            d = java.security.MessageDigest.getInstance("SHA-1");
            d.reset();
            d.update(x.getBytes());
            return d.digest();
        } catch (final NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Encrypts the <code>input</code>
     * 
     * @param input
     *            an <code>String</code> to be encrypted
     * @return the <code>input</code> encrypted
     */
    public static String encrypt(final String input) {
        return byteArrayToHexString(CryptoUtils.computeHash(input));
    }
}
