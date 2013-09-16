package br.usp.ime.vision.dataset.actions.album;

import java.util.ArrayList;
import java.util.List;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.util.Constants;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action with methods related to {@link Album} permissions.
 * 
 * @author Bruno Klava
 */
public class AlbumPermissions extends AbstractAction {

    public class Permission {

        private int key;

        private String value;

        public Permission(final int key, final String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public void setKey(final int key) {
            this.key = key;
        }

        public void setValue(final String value) {
            this.value = value;
        }

    }

    private static final long serialVersionUID = 1L;

    public List<Permission> getAlbumCreatePermissions() {
        final List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(new Permission(Constants.ALBUM_USERS_CREATE, Messages
                .getMessage("album.permission.create.users")));
        permissions.add(new Permission(Constants.ALBUM_PRIVATE_CREATE, Messages
                .getMessage("album.permission.create.private")));
        return permissions;
    }

    public List<Permission> getAlbumViewPermissions() {
        final List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(new Permission(Constants.ALBUM_PUBLIC_VIEW, Messages
                .getMessage("album.permission.view.public")));
        permissions.add(new Permission(Constants.ALBUM_USERS_VIEW, Messages
                .getMessage("album.permission.view.users")));
        permissions.add(new Permission(Constants.ALBUM_PRIVATE_VIEW, Messages
                .getMessage("album.permission.view.private")));
        return permissions;
    }

}
