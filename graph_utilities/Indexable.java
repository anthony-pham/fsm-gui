package graph_utilities;

/**
 * Indexable objects for use in graphs with non-unique containment.
 * @author Tristan Johnson
 */
public interface Indexable
{
    public int getIndex();

    public void setIndex(int index);

    /** Test for equality ignoring index */
    public boolean typeEquals(Object o);

    /** Equals should depend on index (equal => this.getIndex() == o.getIndex()) */
    public boolean equals(Object o);

    /** Should include index in hashCode */
    public int hashCode();
}
