package graph_utilities;

import java.util.Set;
import java.util.HashSet;

import java.util.Map;

/**
 * An IndexGraph is a MultiGraph (implemented through MapMultiGraph)
 * that allows for non-unique containment of vertices by automatically
 * associating every vertex with an index. If a vertex is not in the
 * MultiGraph, it is placed in the graph with index 0, otherwise it is
 * placed with index n+1 where n is the highest index associated with that
 * vertex in the graph. All other functions are the same. One may use the 
 * IndexGraph by calling MultiGraph methods on Indexables
 * @author Tristan Johnson
 */
public class IndexGraph<V extends Indexable> extends MapMultiGraph<V> implements java.io.Serializable
{
    public int numVertexTypes()
    {
    	return ((Set) this.getVertexTypes()).size();
    }

    public int numOfType(V vertex)
    {
        int total = 0;
        if(vertex == null)
        {
            for(V v : super.getVertices())
            {
                if(v == null)
                {
                    total++;
                }
            }
        }else
        {
            for(V v : super.getVertices())
            {
                if(vertex.typeEquals(v))
                {
                    total++;
                }
            }
        }
        return total;
    }
    
    /**
     * Adds a vertex into the graph. If its type is not already
     * in the graph, is added with index 0, otherwise it is
     * added with index n+1 where n is the highest index associated
     * with the given vertex.
     *
     * @param vertex the vertex data to be added.
     */
    public void addVertex(V vertex)
    {
    	int index = this.numOfType(vertex);
        vertex.setIndex(index);
        super.addVertex(vertex);
    }

    /** 
     * Returns an iterable of the types of vertices in the graph
     *
     * @return an iterable of type V data, which is the set of data
     * in the graph (no copies)
     */
    public Iterable<V> getVertexTypes()
    {
    	Set<V> vertexTypes = new HashSet<V>();
    	for(V toAdd : super.getVertices())
    	{
    	    for(V toCheck : vertexTypes)
            {
                if(toAdd.typeEquals(toCheck))
                {
                    vertexTypes.add(toCheck);
                }
            }
    	}
    	return vertexTypes;
    }

    /**
     * Gets the pointer for the equivalent piece of data in the graph.
     * Used to update mutable objects in the graph.
     * @param vertex An object equivalent to the object you are getting
     * @return a pointer to the equivalent object (null if non-existent)
     */
    public V getPointer(V vertex)
    {
        for(V poss : this.getVertices())
        {
            if(poss.equals(vertex))
            {
                return poss;
            }
        }
    	return null;
    }
}
