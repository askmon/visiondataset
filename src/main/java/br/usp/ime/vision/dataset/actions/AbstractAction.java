package br.usp.ime.vision.dataset.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Configs;
import br.usp.ime.vision.dataset.util.Email;
import br.usp.ime.vision.dataset.util.Messages;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Abstract action with common methods/constants.
 * 
 * @author Bruno Klava
 */
public class AbstractAction extends ActionSupport implements SessionAware,
        ServletRequestAware {

    private static final long serialVersionUID = 1L;
    public static String USER_SESSION_PARAMETER = "user";
    protected static final String UNAUTHORIZED_ACTION = "unauthorizedAction";
    protected static final String INVALID_REQUEST = "invalidRequest";
    private Map<String, Object> session;
    private HttpServletRequest servletRequest;

    // private String getLink(String actionURL) {
    // String url = getServletRequest().getRequestURL().toString();
    // url = url.substring(0,
    // url.lastIndexOf(getServletRequest().getServletPath()));
    // url += actionURL;
    // return url;
    // }
    public String cancel() {
        return "cancel";
    }

    protected String getLinkAction(final String action, final User user) {
        // String actionURL = "/" + action + "?username=" + user.getUsername()
        // + "&code=" + user.getConfirmationCode();
        // return getLink(actionURL);
        final String actionURL = action + "?username=" + user.getUsername()
                + "&code=" + user.getConfirmationCode();
        return Configs.getServerURL() + actionURL;
    }

    private String getLinkConfirmation(final User user) {
        return getLinkAction("ConfirmEmail", user);
    }

    public User getLoggedUser() {
        return (User) getSession().get(USER_SESSION_PARAMETER);
    }

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public boolean isLoggedUserAdmin() {
        final User user = getLoggedUser();
        if (user == null) {
            return false;
        }
        return user.isAdmin();
    }

    protected boolean sendConfirmationEmail(final User user) {
        final String subject = Messages.getMessage("emailConfirmation.subject");
        final String message = Messages.getMessage("emailConfirmation.message",
                getLinkConfirmation(user));

        return Email.sendEmail(user.getEmail(), subject, message);
    }

    public void setServletRequest(final HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public void setSession(final Map<String, Object> session) {
        this.session = session;
    }
}
