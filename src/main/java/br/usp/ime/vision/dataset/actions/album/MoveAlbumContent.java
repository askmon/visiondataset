package br.usp.ime.vision.dataset.actions.album;

import org.apache.commons.lang.xwork.StringUtils;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to move the content of an {@link Album} to other {@link Album}s.
 * 
 * @author Bruno Klava
 */
public class MoveAlbumContent extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private String idsImagesDestinations;

    private String idsAlbunsDestinations;

    public String checkPermission() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        boolean success = true;

        if (!StringUtils.isEmpty(idsAlbunsDestinations)) {
            for (final String i : idsAlbunsDestinations.split(",")) {
                final String[] ids = i.split(":");
                success &= DAOFactory.getAlbumDao().moveSubAlbum(getAlbumId(),
                        Integer.parseInt(ids[1]), Integer.parseInt(ids[0]));
            }
        }

        if (!StringUtils.isEmpty(idsImagesDestinations)) {
            for (final String i : idsImagesDestinations.split(",")) {
                final String[] ids = i.split(":");
                success &= DAOFactory.getAlbumDao().moveImage(getAlbumId(),
                        Integer.parseInt(ids[1]), Integer.parseInt(ids[0]));
            }
        }

        if (success) {
            addActionMessage(Messages.getMessage("success.move.album.content"));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.move.album.content"));
            return INPUT;
        }

    }

    public String getIdsAlbunsDestinations() {
        return idsAlbunsDestinations;
    }

    public String getIdsImagesDestinations() {
        return idsImagesDestinations;
    }

    public void setIdsAlbunsDestinations(final String idsAlbunsDestinations) {
        this.idsAlbunsDestinations = idsAlbunsDestinations;
    }

    public void setIdsImagesDestinations(final String idsImagesDestinations) {
        this.idsImagesDestinations = idsImagesDestinations;
    }

}
