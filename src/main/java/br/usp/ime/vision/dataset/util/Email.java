package br.usp.ime.vision.dataset.util;

import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.DefaultAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Class for sending e-mail notifications.
 * 
 * @author Bruno Klava
 */
public class Email {

    private static Logger logger = LoggerFactory.getLogger(Email.class);

    /**
     * Sends an e-mail.
     * 
     * @param emailTo
     *            the destination e-mail
     * @param subject
     *            the subject of the e-mail
     * @param message
     *            the message of the e-mail
     * @return <code>true</code>if the e-mail was sent successfully
     */
    public static boolean sendEmail(final String emailTo, final String subject,
            final String message) {
        try {
            final SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
	    email.setSmtpPort(465);
	    email.setAuthenticator(new DefaultAuthenticator("vdataset", "s3nh4VDSm4il"));
            email.setSSLOnConnect(true);
            // TODO refactor to load email configuration
            final String projectName = Messages.getMessage("projectName");
            email.setFrom("vdataset@gmail.com", projectName);
            email.addTo(emailTo);
            email.setSubject("[" + projectName + "] " + subject);
            // TODO add automatic email info (do no reply)
            email.setMsg(message);
            email.send();
            logger.info("Sent email to " + emailTo + " with subject [{}]: {}",
                    subject, message);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * Sends an e-mail to the {@link User} with <code>username</code> admin.
     * 
     * @param subject
     *            the subject of the e-mail
     * @param message
     *            the message of the e-mail
     * @return <code>true</code>if the e-mail was sent successfully
     */
    public static boolean sendEmailToAdmin(final String subject,
            final String message) {
        try {
            final User admin = DAOFactory.getUserDao().getUserByUsername(
                    "admin");
            sendEmail(admin.getEmail(), subject, message);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

}
