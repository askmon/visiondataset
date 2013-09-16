package br.usp.ime.vision.dataset.ws;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Image;
import br.usp.ime.vision.dataset.entities.ImageAttachment;
import br.usp.ime.vision.dataset.util.ImageUtils;
import br.usp.ime.vision.dataset.util.JsonUtils;
import br.usp.ime.vision.dataset.util.MimeTypeUtils;

@Path("/image/{imageId}")
public class ImageService {
    private static Logger logger = LoggerFactory.getLogger(ImageService.class);

    /**
     * Creates an {@link ImageAttachment}
     * 
     * @param imageId
     *            <code>id</code> of the associated {@link Image}
     * @param name
     *            the name of the {@link ImageAttachment}
     * @return A Json representation of with the URI of the created element.
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String createAttachment(@PathParam("imageId") final Integer imageId,
            final String jsonName) throws IOException {
        logger.debug("create attachment {} to image {}", jsonName, imageId);
        final String name = JsonUtils.getJsonConverter().fromJson(jsonName,
                String.class);
        final int id = DAOFactory.getAttachmentDAO().addImageAttachment(
                imageId, name);
        if (id != 0) {
            logger.debug("id = {}", id);
            final ImageAttachment attachment = DAOFactory.getAttachmentDAO()
                    .getAttachment(id);
            return JsonUtils.getJsonConverter().toJson(attachment.getUri());
        }
        final WebApplicationException webApplicationException = new WebApplicationException(
                404);
        throw webApplicationException;
    }

    /**
     * Get a image file.
     * 
     * @param imageId
     *            <code>id</code> of the image.
     * @return a Response with the image {@link File} attached.
     */
    @GET
    public Response getImage(@PathParam("imageId") final Integer imageId) {
        logger.debug("Getting image with id: {}", imageId);
        final File imageFile = ImageUtils.getImageFile(imageId);
        if (!imageFile.exists()) {
            final WebApplicationException webApplicationException = new WebApplicationException(
                    404);
            throw webApplicationException;
        }
        final String mimeType = MimeTypeUtils.guessMimeType(imageFile);
        logger.debug("Image file type :{}", mimeType);
        return Response.ok(imageFile, mimeType).build();
    }

    /**
     * Gets the list of {@link URI} of the image's attachments.
     * 
     * @param imageId
     *            <code>id</code> of the image.
     * @return A json representation of the list of <code>id</code>s of
     *         {@link ImageAttachment} associated with the image.
     */
    @GET
    @Path("/attachments")
    @Produces("application/json")
    public String list(@PathParam("imageId") final Integer imageId) {
        logger.debug("list imageId : {}", imageId);
        final List<ImageAttachment> list = DAOFactory.getAttachmentDAO()
                .getImageAttachmentList(imageId);
        final List<URI> uriList = new ArrayList<URI>();
        for (final ImageAttachment a : list) {
            uriList.add(a.getUri());
        }
        final String json = JsonUtils.getJsonConverter().toJson(uriList);
        logger.debug("{}", json);
        return json;
    }

}
