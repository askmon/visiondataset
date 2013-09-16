package br.usp.ime.vision.dataset.actions.album;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.SubAlbum;
import br.usp.ime.vision.dataset.util.FileUtils;
import br.usp.ime.vision.dataset.util.ImageUtils;
import br.usp.ime.vision.dataset.util.Messages;
import br.usp.ime.vision.dataset.util.MimeTypeUtils;

/**
 * Action to upload content to an {@link Album}.
 * 
 * @author Bruno Klava
 */
public class UploadImage extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory
            .getLogger(UploadImage.class);

    private static final Comparator<File> FILE_COMPARATOR = new Comparator<File>() {
        public int compare(final File o1, final File o2) {
            return o1.compareTo(o2);
        }
    };

    private File[] upload;

    private String[] uploadContentType;

    private String[] uploadFileName;

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

        for (int i = 0; i < getUpload().length; i++) {

            logger.info(
                    "New file uploaded: {} content-type: {} original name: {}",
                    new String[] { getUpload()[i].getName(),
                            getUploadContentType()[i], getUploadFileName()[i] });

            if (MimeTypeUtils.getExtension(getUploadContentType()[i]) == null) {

                return INPUT;

            } else {

                // XXX if file is too big, it is not using message from
                // struts.messages.error.file.too.large
                // MultiPartRequestWrapper multipartRequest =
                // ((MultiPartRequestWrapper) ServletActionContext
                // .getRequest());
                // if (multipartRequest.hasErrors()) {
                // for (String error : multipartRequest.getErrors()) {
                // addActionError("error: " + error);
                // }
                // return INPUT;
                // }

                if (getUploadContentType()[i].startsWith("image")) {

                    if (saveImage(getUpload()[i], getAlbumId(),
                            getUploadContentType()[i])) {
                        addActionMessage(Messages.getMessage(
                                "success.uploadImage", getUploadFileName()[i]));
                    } else {
                        addActionError(Messages.getMessage("error.uploadImage",
                                getUploadFileName()[i]));
                        return INPUT;
                    }

                } else {

                    boolean success = true;

                    try {

                        final File compressedFile = getUpload()[i];

                        final File tempDir = new File(
                                compressedFile.getParent() + File.separator
                                        + getLoggedUser().getId() + "_"
                                        + uploadFileName[i].hashCode());

                        tempDir.mkdir();

                        FileUtils.decompress(compressedFile.getAbsolutePath(),
                                getUploadContentType()[i],
                                tempDir.getAbsolutePath());

                        final File[] files = tempDir.listFiles();
                        Arrays.sort(files, FILE_COMPARATOR);

                        for (final File file : files) {
                            if (file.isFile()) {
                                if (!saveImage(file, getAlbumId(),
                                        MimeTypeUtils.getMimeType(file))) {
                                    addActionError(Messages
                                            .getMessage("error.uploadImage",
                                                    file.getName()));
                                    success = false;
                                }
                            } else {
                                saveDir(file, getAlbumId());
                            }
                        }

                        org.apache.commons.io.FileUtils
                                .deleteDirectory(tempDir);

                    } catch (final Exception e) {
                        // XXX process exception according to its type
                        logger.error(e.getMessage(), e);
                        addActionError(Messages.getMessage("error.uploadImage",
                                getUploadFileName()[i]));
                        return INPUT;
                    }

                    if (success) {
                        addActionMessage(Messages
                                .getMessage("success.uploadImages"));
                    }

                }

            }

        }

        return SUCCESS;

    }

    public File[] getUpload() {
        return upload;
    }

    public String[] getUploadContentType() {
        return uploadContentType;
    }

    public String[] getUploadFileName() {
        return uploadFileName;
    }

    private void saveDir(final File dir, final int parentAlbumId) {

        final SubAlbum album = new SubAlbum();
        album.setName(dir.getName());
        album.setOwnerId(getLoggedUser().getId());
        album.setParentId(parentAlbumId);

        final int albumId = DAOFactory.getAlbumDao().createAlbum(album);

        final File[] files = dir.listFiles();
        Arrays.sort(files, FILE_COMPARATOR);

        for (final File file : files) {
            if (file.isFile()) {
                saveImage(file, albumId, MimeTypeUtils.getMimeType(file));
            } else {
                saveDir(file, albumId);
            }
        }

    }

    private boolean saveImage(final File image, final int albumId,
            final String mimeType) {

        if (!mimeType.startsWith("image")) {
            logger.info("Ignoring file {} of type {}", image.getName(),
                    mimeType);
            return false;
        }

        final int id = DAOFactory.getAlbumDao().addImageToAlbum(albumId,
                getLoggedUser().getId(), mimeType);

        if (id == 0) {
            return false;
        }

        try {
            ImageUtils.saveImage(id, image);
            ImageUtils.createThumbnail(id);
        } catch (final Exception e) {
            ImageUtils.removeImageAndThumbnail(id);
            DAOFactory.getAlbumDao().getImage(id).deleteImage();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

    public void setUpload(final File[] upload) {
        this.upload = upload;
    }

    public void setUploadContentType(final String[] uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public void setUploadFileName(final String[] uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

}
