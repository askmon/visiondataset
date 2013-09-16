package br.usp.ime.vision.dataset.actions.user;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to confirm the identity (e-mail ownership) of the {@link User}.
 * 
 * @author Bruno Klava
 */
public class ConfirmEmail extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(ConfirmEmail.class);

    private String username;

    private String code;

    @Override
    public String execute() throws Exception {

        if (StringUtils.isEmpty(username)) {
            logger.info("Email confirmation without username supplied");
            return INVALID_REQUEST;
        }

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(code)) {
            logger.info("Email confirmation without code supplied");
            return INVALID_REQUEST;
        }

        final User user = DAOFactory.getUserDao().getUserByUsername(username);
        if (user == null) {
            logger.info("Email confirmation with wrong username");
            return INVALID_REQUEST;
        }

        if (!user.getConfirmationCode().equals(code)) {
            logger.info("Email confirmation with wrong code");
            return INVALID_REQUEST;
        }

        addActionMessage(Messages.getMessage("emailConfirmed"));

        DAOFactory.getUserDao().confirmEmail(username);

        if (user.isAccountAuthorized()) {
            addActionMessage(Messages.getMessage("emailConfirmed.ok"));
        } else {
            addActionMessage(Messages.getMessage("emailConfirmed.waitingAdmin"));
        }

        return SUCCESS;

    }

    public String getCode() {
        return code;
    }

    public String getUsername() {
        return username;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
