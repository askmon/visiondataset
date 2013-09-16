package br.usp.ime.vision.dataset.actions.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to logout an {@link User} from the system.
 * 
 * @author Bruno Klava
 */
public class Logout extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(Logout.class);

    @Override
    public String execute() throws Exception {

        if (getLoggedUser() == null) {
            return UNAUTHORIZED_ACTION;
        }

        final User user = (User) getSession().remove(USER_SESSION_PARAMETER);
        addActionMessage(Messages.getMessage("success.logout"));
        logger.info("Logout: {}", user);
        return SUCCESS;
    }

}
