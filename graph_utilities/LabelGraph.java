package graph_utilities;

/**
 * A LabelGraph is a Multigraph that can associate labels
 * with its edges. That is, edges have associated objects
 * as well as vertices.
 * @author Tristan Johnson
 */
public interface LabelGraph<V, L extends EdgeId<V>> extends MultiGraph<V>
{
    /**
     * Labels the edge identified by the label in the graph
     */
	public void labelEdge(L label);

    /**
     * The getter complement of the labelEdge setter,
     * getLabel returns the label associated with the edge
     * e.
     *
     * @param e the edge to retrieve the label from
     * @return the label associated with e
     */
	public L getLabelPointer(EdgeId<V> e);
}
