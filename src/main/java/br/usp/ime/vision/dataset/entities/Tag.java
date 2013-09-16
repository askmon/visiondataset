package br.usp.ime.vision.dataset.entities;

/**
 * Abstract class that represents an {@link Tag}.
 * 
 * @author Bruno Klava
 */
public abstract class Tag {

    private int id;

    private String tagName;

    /**
     * Deletes this {@link Tag} from its parent element..
     * 
     * @return <code>true</code> if the {@link Tag} deletion was successful
     */
    public abstract boolean deleteTag();

    public int getId() {
        return id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setTagName(final String tagName) {
        this.tagName = tagName;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Tag other = (Tag) obj;
        if (id != other.id) return false;
        if (tagName == null) {
            if (other.tagName != null) return false;
        } else if (!tagName.equals(other.tagName)) return false;
        return true;
    }
    

}
