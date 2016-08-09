package graph_utilities;

/**
 * Object used to identify an edge in a MultiGraph.
 * Any edge may be identified by its source, sink, 
 * and which of the n edges between the source and sink
 * it is
 * @author Tristan Johnson
 */
public interface EdgeId<V> extends Indexable
{
	public V getSource();

	public V getSink();

	/** returns true iff this and other have same source, sink */
	public boolean eqId(EdgeId other);
}
