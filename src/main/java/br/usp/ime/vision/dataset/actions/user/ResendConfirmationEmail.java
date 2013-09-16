package br.usp.ime.vision.dataset.actions.user;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to re-send an e-mail for an {@link User} to confirm his/her account.
 * 
 * @author Bruno Klava
 */
public class ResendConfirmationEmail extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory
            .getLogger(ResendConfirmationEmail.class);

    private String username;

    @Override
    public String execute() throws Exception {

        if (StringUtils.isEmpty(username)) {
            logger.info("Resend confirmation email without username supplied");
            return INVALID_REQUEST;
        }

        final User user = DAOFactory.getUserDao().getUserByUsername(username);
        if (user == null) {
            logger.info("Resend confirmation email with wrong username");
            return INVALID_REQUEST;
        }

        sendConfirmationEmail(user);
        addActionMessage(Messages.getMessage("confirmationEmail.resent",
                user.getEmail()));

        return SUCCESS;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
