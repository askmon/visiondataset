package br.usp.ime.vision.dataset.util;

import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Constants utilized in the system.
 * 
 * @author Bruno Klava
 */
public class Constants {

    /**
     * Pattern for visualization for <code>Date</code>s
     */
    public static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

    // User permissions

    /**
     * Associated to {@link User}s with system administration permission
     */
    public static final int ADMIN_PERMISSION = 100;

    /**
     * Associated to {@link User}s with content creation permission
     */
    public static final int CREATE_PERMISSION = 10;

    /**
     * Associated to {@link User}s with content visualization permission
     */
    public static final int VIEW_PERMISSION = 0;

    // Album view permission

    /**
     * Associated to {@link Album}s that can be viewed only by the owner and
     * selected {@link User}s
     */
    public static final int ALBUM_PRIVATE_VIEW = 100;

    /**
     * Associated to {@link Album}s that can be viewed only by logged
     * {@link User}s
     */
    public static final int ALBUM_USERS_VIEW = 10;

    /**
     * Associated to {@link Album}s that can be viewed by everyone (including
     * anonymous)
     */
    public static final int ALBUM_PUBLIC_VIEW = 0;

    // Album add permission

    /**
     * Associated to {@link Album}s that can be modified only by the owner and
     * selected {@link User}s
     */
    public static final int ALBUM_PRIVATE_CREATE = 10;

    /**
     * Associated to {@link Album}s that can be modified only by logged
     * {@link User}s
     */
    public static final int ALBUM_USERS_CREATE = 0;

    public int getAdminPermission() {
        return ADMIN_PERMISSION;
    }

    public int getAlbumPrivateCreate() {
        return ALBUM_PRIVATE_CREATE;
    }

    public int getAlbumPrivateView() {
        return ALBUM_PRIVATE_VIEW;
    }

    public int getAlbumPublicView() {
        return ALBUM_PUBLIC_VIEW;
    }

    public int getAlbumUsersCreate() {
        return ALBUM_USERS_CREATE;
    }

    public int getAlbumUsersView() {
        return ALBUM_USERS_VIEW;
    }

    public int getCreatePermission() {
        return CREATE_PERMISSION;
    }

    public String getDatePattern() {
        return DATE_PATTERN;
    }

    public int getViewPermission() {
        return VIEW_PERMISSION;
    }

}
