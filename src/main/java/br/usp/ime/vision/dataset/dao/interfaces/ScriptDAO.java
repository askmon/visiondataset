package br.usp.ime.vision.dataset.dao.interfaces;

import java.util.List;

import br.usp.ime.vision.dataset.entities.Attachment;
import br.usp.ime.vision.dataset.entities.Image;
import br.usp.ime.vision.dataset.entities.ImageScript;

/**
 * @author ask
 * 
 */
public interface ScriptDAO {

    public abstract int addScript(int imageId, String name);

    //public abstract boolean deleteScript(int scriptFileId);

    public abstract ImageScript getScript(int scriptId);

    public abstract List<ImageScript> getImageScriptList(int imageId);

}
