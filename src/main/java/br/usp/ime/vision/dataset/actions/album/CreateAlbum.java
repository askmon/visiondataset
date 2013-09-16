package br.usp.ime.vision.dataset.actions.album;

import org.apache.commons.lang.xwork.StringUtils;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to create an {@link Album}.
 * 
 * @author Bruno Klava
 */
public class CreateAlbum extends CreateAlbumData {

    private static final long serialVersionUID = 1L;

    private Album album;

    private String idsUsersPermission;

    @Override
    public String execute() throws Exception {

        final User user = getLoggedUser();

        if ((user == null) || !user.isCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        getAlbum().setOwnerId(user.getId());

        final int albumId = DAOFactory.getAlbumDao().createAlbum(getAlbum());

        boolean success = false;

        if (albumId != 0) {

            success = true;

            int[] intIds;
            if (!StringUtils.isEmpty(idsUsersPermission)) {
                final String[] stringIds = idsUsersPermission.split(",");
                intIds = new int[stringIds.length];
                int i = 0;
                for (final String s : stringIds) {
                    intIds[i++] = Integer.parseInt(s);
                }
            } else {
                intIds = new int[0];
            }

            for (final int idUser : intIds) {
                if (!DAOFactory.getAlbumDao().addRestrictedPermission(albumId,
                        idUser)) {
                    success = false;
                    break;
                }
            }

        }

        if (success) {
            addActionMessage(Messages.getMessage("success.create.album",
                    getAlbum().getName()));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.create.album", getAlbum()
                    .getName()));
            return INPUT;
        }

    }

    public Album getAlbum() {
        return album;
    }

    public String getIdsUsersPermission() {
        return idsUsersPermission;
    }

    public void setAlbum(final Album album) {
        this.album = album;
    }

    public void setIdsUsersPermission(final String idsUsersPermission) {
        this.idsUsersPermission = idsUsersPermission;
    }

}
