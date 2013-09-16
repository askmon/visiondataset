package br.usp.ime.vision.dataset.actions;

import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action utilized in case of invalid requests (wrong parameters).
 * 
 * @author Bruno Klava
 */
public class InvalidRequest extends AbstractAction {

    private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {
        addActionError(Messages.getMessage("invalidRequest"));
        return SUCCESS;
    }

}
