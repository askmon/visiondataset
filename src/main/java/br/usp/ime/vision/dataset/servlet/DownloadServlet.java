package br.usp.ime.vision.dataset.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Servlet implementation class DownloadServlet
 */
public abstract class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory
            .getLogger(DownloadServlet.class);

    private static final String USER_PARAMETER = "user";
    private static final String USER_PASSWORD_PARAMETER = "pwd";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
    }

    protected void doFileResponse(final HttpServletResponse response,
            final File file) throws IOException, FileNotFoundException {
        final String filename = file.getAbsolutePath();
        if (!file.exists()) {
            logger.debug("File {} does not exists.", filename);
            redirectToNotFound(response);
        } else {
            response.setContentType(getServletContext().getMimeType(filename));
            response.setContentLength((int) file.length());

            logger.debug("Seetting response with file {} with lenght {}.",
                    filename, file.length());

            final FileInputStream input = new FileInputStream(file);
            final OutputStream output = response.getOutputStream();
            final byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = input.read(buffer)) >= 0) {
                output.write(buffer, 0, count);
            }
            input.close();
            output.close();
        }

    }

    protected void doFileResponse(final HttpServletResponse response,
            final String filename) throws IOException, FileNotFoundException {
        final File file = new File(filename);
        doFileResponse(response, file);
    }

    @Override
    protected abstract void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException;

    /**
     * Returns the user stored in session or the specified by user and password
     * parameters.
     * 
     * @param request
     * @return
     */
    protected User getUserFromRequest(final HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(
                AbstractAction.USER_SESSION_PARAMETER);
        if (user == null) {
            final String userLogin = request.getParameter(USER_PARAMETER);
            final String userPassword = request
                    .getParameter(USER_PASSWORD_PARAMETER);
            if ((userLogin != null) && (userPassword != null)) {
                user = DAOFactory.getUserDao().getUserByUsername(userLogin);
                if (user.samePassword(userPassword)) {
                    request.getSession().setAttribute(
                            AbstractAction.USER_SESSION_PARAMETER, user);
                    return user;
                }
            }
        }
        return user;

    }

    protected abstract void redirectToNotFound(HttpServletResponse response)
            throws IOException;

    protected boolean userHasPermission(final HttpServletRequest request,
            final int imageId) {

        final Album album = DAOFactory.getAlbumDao().getAlbumForImage(imageId);
        if (album == null) {
            return false;
        }

        if (album.isPublicView()) {
            return true;
        }

        final User user = getUserFromRequest(request);
        if (user == null) {
            return false;
        }

        if (user.isAdmin()) {
            return true;
        }

        if (album.isUsersView() && (user != null)) {
            return true;
        }

        if (album.isRestrictedView()
                && DAOFactory.getAlbumDao().userHasPermissionOnAlbum(
                        album.getId(), user.getId())) {
            return true;
        }

        return false;

    }

}
