package br.usp.ime.vision.dataset.actions.user;

import org.apache.commons.lang.xwork.StringUtils;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;
import br.usp.ime.vision.dataset.validators.UsernameValidator;

/**
 * Action to login an {@link User} in the system.
 * 
 * @author BrunoKlava
 */
public class Login extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private String usernameLogin;

    private String passwordLogin;

    @Override
    public String execute() throws Exception {

        if (getLoggedUser() != null) {
            return UNAUTHORIZED_ACTION;
        }

        final User user = DAOFactory.getUserDao().getUserByUsername(
                getUsernameLogin());
        if (user == null) {
            addActionError(Messages.getMessage("error.wrong.username",
                    getUsernameLogin()));
            setUsernameLogin(null);
            return INPUT;
        }

        if (!user.isActive()) {
            addActionError(Messages.getMessage("account.inactive"));
            return ERROR;
        }

        if (user.samePassword(getPasswordLogin())) {

            if (user.isEmailConfirmed()) {

                if (user.isAccountAuthorized()) {

                    getSession().put(USER_SESSION_PARAMETER, user);
                    addActionMessage(Messages.getMessage("success.login",
                            user.getName()));
                    return SUCCESS;
                } else {
                    addActionError(Messages
                            .getMessage("waiting.admin.authorization"));
                    return ERROR;
                }

            } else {
                final String link = getLinkResendConfirmationEmail(user);
                addActionError(Messages.getMessage(
                        "waiting.email.confirmation", link));
                return ERROR;
            }

        } else {
            addActionError(Messages.getMessage("error.wrong.password"));
            return INPUT;
        }

    }

    protected String getLinkResendConfirmationEmail(final User user) {
        return getLinkAction("ResendConfirmationEmail", user);
    }

    public String getPasswordLogin() {
        return passwordLogin;
    }

    public String getUsernameLogin() {
        return usernameLogin;
    }

    public void setPasswordLogin(final String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }

    public void setUsernameLogin(final String usernameLogin) {
        this.usernameLogin = usernameLogin;
    }

    @Override
    public void validate() {

        if (StringUtils.isEmpty(getUsernameLogin())) {
            addActionError(Messages.getMessage("error.username.required",
                    getUsernameLogin()));
        } else if (!UsernameValidator.validate(getUsernameLogin())) {
            addActionError(Messages.getMessage("validation.username"));
        }

        if (StringUtils.isEmpty(getPasswordLogin())) {
            addActionError(Messages.getMessage("error.password.required",
                    getUsernameLogin()));
        }

    }

}
