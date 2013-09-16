package br.usp.ime.vision.dataset.actions;

import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action utilized in case of requests that the current user does'nt have the
 * necessary permissions.
 * 
 * @author Bruno Klava
 */
public class UnauthorizedAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {
        addActionError(Messages.getMessage("unauthorizedAction"));
        return SUCCESS;
    }

}
