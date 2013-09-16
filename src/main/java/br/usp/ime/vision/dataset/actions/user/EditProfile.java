package br.usp.ime.vision.dataset.actions.user;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to edit an {@link User} account.
 * 
 * @author Bruno Klava
 */
public class EditProfile extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(ConfirmEmail.class);

    private User user;

    @Override
    public String execute() throws Exception {

        if (getLoggedUser() == null) {
            return UNAUTHORIZED_ACTION;
        }

        logger.debug("Old e-mail: {} new e-mail: {}", getLoggedUser()
                .getEmail(), getUser().getEmail());

        boolean reconfirmEmail = false;

        if (!getLoggedUser().getEmail().equals(getUser().getEmail())) {
            getUser().setEmailConfirmed(false);
            reconfirmEmail = true;
        }

        if (DAOFactory.getUserDao().updateUser(getUser())) {
            addActionMessage(Messages.getMessage("success.profile.update"));
            if (reconfirmEmail) {
                getSession().remove(USER_SESSION_PARAMETER);
                addActionMessage(Messages.getMessage("reconfirmationEmail",
                        getUser().getEmail()));
                sendConfirmationEmail(getUser());
                return "reconfirmationEmail";
            } else {
                getSession().put(
                        USER_SESSION_PARAMETER,
                        DAOFactory.getUserDao().getUserByUsername(
                                user.getUsername()));
                return SUCCESS;
            }
        } else {
            addActionError(Messages.getMessage("error.profile.update"));
            return INPUT;
        }

    }

    public User getUser() {
        if (user == null) {
            user = new User();
            try {
                PropertyUtils.copyProperties(user, getLoggedUser());
            } catch (final Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return user;
    }

}
