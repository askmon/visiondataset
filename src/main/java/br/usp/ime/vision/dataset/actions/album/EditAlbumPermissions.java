package br.usp.ime.vision.dataset.actions.album;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to change the {@link User}s creation/visualization permissions on an
 * {@link Album}.
 * 
 * @author Bruno Klava
 */
public class EditAlbumPermissions extends AlbumPermissions {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory
            .getLogger(EditAlbumPermissions.class);

    private Album album;

    private String idsUsersPermission;

    @Override
    public String execute() throws Exception {

        final User user = getLoggedUser();

        final Album album = DAOFactory.getAlbumDao().getAlbum(
                getAlbum().getId());

        if ((user == null)
                || !((album.getOwnerId() == user.getId()) || isLoggedUserAdmin())) {
            logger.debug("User {} cannot edit permissions on album {}", user,
                    album);
            return UNAUTHORIZED_ACTION;
        }

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

        boolean success = true;
        if (DAOFactory.getAlbumDao().setAlbumPermissions(getAlbum().getId(),
                getAlbum().getViewPermission(),
                getAlbum().getCreatePermission())) {
            for (final int idUser : intIds) {
                if (!DAOFactory.getAlbumDao().addRestrictedPermission(
                        getAlbum().getId(), idUser)) {
                    success = false;
                    break;
                }
            }
        } else {
            success = false;
        }

        if (success) {
            addActionMessage(Messages.getMessage("success.album.permissions"));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.album.permissions"));
            return ERROR;
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
