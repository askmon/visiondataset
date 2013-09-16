package br.usp.ime.vision.dataset.dao;

import br.usp.ime.vision.dataset.dao.impl.AlbumDAOImpl;
import br.usp.ime.vision.dataset.dao.impl.AttachmentDAOImpl;
import br.usp.ime.vision.dataset.dao.impl.TagDAOImpl;
import br.usp.ime.vision.dataset.dao.impl.UserDAOImpl;
import br.usp.ime.vision.dataset.dao.impl.ScriptDAOImpl;
import br.usp.ime.vision.dataset.dao.interfaces.AlbumDAO;
import br.usp.ime.vision.dataset.dao.interfaces.AttachmentDAO;
import br.usp.ime.vision.dataset.dao.interfaces.TagDAO;
import br.usp.ime.vision.dataset.dao.interfaces.UserDAO;
import br.usp.ime.vision.dataset.dao.interfaces.ScriptDAO;

/**
 * Provides classes for data access.
 * 
 * @author Bruno Klava
 */
public class DAOFactory {

    private static UserDAO userDao;

    private static AlbumDAO albumDao;

    private static TagDAO tagDao;

    private static AttachmentDAO attachedFileDao;

    private static ScriptDAO scriptFileDao;

    /**
     * Returns an implementation of the {@link AlbumDAO} interface.
     * 
     * @return an implementation of the {@link AlbumDAO} interface
     */
    public static AlbumDAO getAlbumDao() {
        if (albumDao == null) {
            albumDao = new AlbumDAOImpl();
        }
        return albumDao;
    }

    /**
     * Returns an implementation of the {@link UserDAO} interface.
     * 
     * @return an implementation of the {@link UserDAO} interface
     */
    public static AttachmentDAO getAttachmentDAO() {
        if (attachedFileDao == null) {
            attachedFileDao = new AttachmentDAOImpl();
        }
        return attachedFileDao;
    }
    
    public static ScriptDAO getScriptDAO() {
        if (scriptFileDao == null) {
            scriptFileDao = new ScriptDAOImpl();
        }
        return scriptFileDao;
    }

    /**
     * Returns an implementation of the {@link TagDAO} interface.
     * 
     * @return an implementation of the {@link TagDAO} interface
     */
    public static TagDAO getTagDao() {
        if (tagDao == null) {
            tagDao = new TagDAOImpl();
        }
        return tagDao;
    }

    /**
     * Returns an implementation of the {@link UserDAO} interface.
     * 
     * @return an implementation of the {@link UserDAO} interface
     */
    public static UserDAO getUserDao() {
        if (userDao == null) {
            userDao = new UserDAOImpl();
        }
        return userDao;
    }
}
