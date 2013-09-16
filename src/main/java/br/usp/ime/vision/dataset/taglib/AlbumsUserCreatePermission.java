package br.usp.ime.vision.dataset.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Creates a tree of the {@link Album}s that the {@link User} has create
 * permission.
 * 
 * @author Bruno Klava
 */
public class AlbumsUserCreatePermission extends TagSupport {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory
            .getLogger(AlbumsUserCreatePermission.class);

    private int userId;

    private int currentAlbumId;

    @Override
    public int doStartTag() throws JspException {

        final User user = DAOFactory.getUserDao().getUserById(getUserId());

        List<Album> albums;

        if (user.isAdmin()) {
            albums = DAOFactory.getAlbumDao().getAllAlbums();
        } else {
            albums = DAOFactory.getAlbumDao().getAlbumsUserCreate(getUserId());
        }

        try {
            pageContext.getOut().write("<ul>");
            for (final Album album : albums) {

                // ignore current album
                if (album.getId() == getCurrentAlbumId()) {
                    continue;
                }

                listAlbum(album);

            }
            pageContext.getOut().write("</ul>");
        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspTagException("IO Error:" + e.getMessage(), e);
        }

        return SKIP_BODY;

    }

    public int getCurrentAlbumId() {
        return currentAlbumId;
    }

    public int getUserId() {
        return userId;
    }

    private void listAlbum(final Album album) throws JspException {

        // ignore current album
        if (album.getId() == getCurrentAlbumId()) {
            return;
        }

        try {
            pageContext.getOut().write("<li>");
            pageContext.getOut().write(album.getName());
            pageContext.getOut()
                    .write("<div class=\"dndContainer\" id=\"" + album.getId()
                            + "\">");
            pageContext.getOut().write("<ul>");
            for (final Album subAlbum : album.getSubAlbums()) {
                listAlbum(subAlbum);
            }
            pageContext.getOut().write("</ul>");
            pageContext.getOut().write("</div>");
            pageContext.getOut().write("</li>");

        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspTagException("IO Error:" + e.getMessage(), e);
        }
    }

    public void setCurrentAlbumId(final int currentAlbumId) {
        this.currentAlbumId = currentAlbumId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

}
