package br.usp.ime.vision.dataset.actions.user;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Email;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to send an e-mail for an {@link User} to edit his/her password if
 * he/she forgot it.
 * 
 * @author Bruno Klava
 */
public class PasswordLost extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private String username;

    @Override
    public String execute() throws Exception {

        if (getLoggedUser() != null) {
            return UNAUTHORIZED_ACTION;
        }

        final User user = DAOFactory.getUserDao().getUserByUsername(
                getUsername());

        final String subject = Messages.getMessage("passwordLost.subject");
        final String link = getLinkAction("ChangePasswordData", user);
        final String message = Messages
                .getMessage("passwordLost.message", link);

        if (Email.sendEmail(user.getEmail(), subject, message)) {
            addActionMessage(Messages.getMessage("passwordLost.link.sent",
                    user.getEmail()));
            return SUCCESS;
        } else {
            addActionMessage(Messages
                    .getMessage("error.email", user.getEmail()));
            return "errorEmail";
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
