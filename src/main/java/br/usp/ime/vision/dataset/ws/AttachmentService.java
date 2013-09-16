package br.usp.ime.vision.dataset.ws;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.ImageAttachment;
import br.usp.ime.vision.dataset.util.JsonUtils;
import br.usp.ime.vision.dataset.util.MimeTypeUtils;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/attachment/")
/**
 * Webservice rest for the attachment.
 * @author Rafael
 *
 */
public class AttachmentService {
    private static Logger logger = LoggerFactory
            .getLogger(AttachmentService.class);

    /**
     * Get the the {@link ImageAttachment} data.
     * 
     * @param attachmentId
     *            <code>id</code> of the attachment.
     * @return A json representation of the {@link ImageAttachment} fields.
     */
    @GET
    @Path("/{attachmentId}")
    @Produces("application/json")
    public String getAttachment(
            @PathParam("attachmentId") final Integer attachmentId) {
        logger.debug("attachment : {}", attachmentId);
        final ImageAttachment attachment = DAOFactory.getAttachmentDAO()
                .getAttachment(attachmentId);
        final String json = JsonUtils.getJsonConverter().toJson(attachment);
        logger.debug("{}", json);
        return json;

    }

    /**
     * Gets a {@link ImageAttachment}'s file.
     * 
     * @param attachmentId
     *            <code>id</code> of the associated {@link ImageAttachment}
     * @return the {@link File} attached.
     */
    @GET
    @Path("/{attachmentId}/file")
    public Response getAttachmentFile(
            @PathParam("attachmentId") final Integer attachmentId) {
        logger.debug("get attachment : {}", attachmentId);
        File f;
        Response response;
        final ImageAttachment attachment = DAOFactory.getAttachmentDAO()
                .getAttachment(attachmentId);
        if (attachment == null) {
            response = Response.noContent().build();
        } else {
            f = attachment.getFile();
            if ((f == null) || !f.exists()) {
                logger.debug("File not found: f.toString()");
                response = Response.noContent().build();
            } else {
                final String contentType = MimeTypeUtils.guessMimeType(f);
                logger.debug("Response with file {} of contentype {}.",
                        f.toString(), contentType);
                final String contentDisposition = String.format(
                        "attachment; filename=%s", attachment.getName());
                response = Response.ok(f, contentType)
                        .header("Content-Disposition", contentDisposition)
                        .build();
            }
        }
        return response;
    }

    /**
     * Updates the <code>file</code> of {@link ImageAttachment}
     * 
     * @param id
     *            <code>id</code> of the {@link ImageAttachment}
     * @param file
     *            a {@link File} to overwrite the current.
     * @return
     * @throws IOException
     */
    @PUT
    @Produces("application/json")
    @Consumes("multipart/form-data")
    @Path("/{attachmentId}")
    public String updateAttachmentFile(
            @PathParam("attachmentId") final Integer id,
            @FormDataParam("file") final File file,
            @FormDataParam("file") final FormDataContentDisposition fcdsFile)
            throws IOException {
        logger.debug("update attachment : {}", id);
        final ImageAttachment attachment = DAOFactory.getAttachmentDAO()
                .getAttachment(id);
        attachment.saveAttachment(file);
        logger.debug("Updating attachment {}", id);
        return JsonUtils.getJsonConverter().toJson(attachment.getUri());
    }
}
