package br.usp.ime.vision.dataset.actions.user;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to verify the parameters necessary for changing the {@link User}
 * password.
 * 
 * @author Bruno Klava
 */
public class ChangePasswordData extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory
            .getLogger(ChangePasswordData.class);

    private String username;

    private String code;

    @Override
    public String execute() throws Exception {

        if (getLoggedUser() != null) {
            return SUCCESS;
        } else {

            if (StringUtils.isEmpty(username)) {
                logger.info("Password recovery without username supplied");
                return INVALID_REQUEST;
            }

            if (StringUtils.isEmpty(code)) {
                logger.info("Password recovery without username supplied");
                return INVALID_REQUEST;
            }

            final User user = DAOFactory.getUserDao().getUserByUsername(
                    username);
            if (user == null) {
                logger.info("Password recovery with wrong username");
                return INVALID_REQUEST;
            }

            if (!user.getConfirmationCode().equals(code)) {
                logger.info("Password recovery with wrong code");
                return INVALID_REQUEST;
            }

            getSession().put(USER_SESSION_PARAMETER, user);

            addActionMessage(Messages.getMessage("password.reset"));

            return SUCCESS;

        }

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
