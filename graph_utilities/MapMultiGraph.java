package graph_utilities;

import java.util.Map;
import java.util.HashMap;
//using TreeMap to map vertices to adjacencies

import java.util.Set;
import java.util.HashSet;

import java.util.LinkedList;

/**
 * 
 * @author Tristan Johnson
 */
public class MapMultiGraph<V> implements MultiGraph<V>, java.io.Serializable
{
    private class MultiEdge implements java.io.Serializable
    {
		public V vertex;
		public int numEdges;

		public MultiEdge(V vertex, int numEdges)
		{
		    this.vertex = vertex;
		    this.numEdges = numEdges;
		}
	    
		public boolean equals(Object o)
		{
		    MultiEdge other = (MultiEdge) o;
		    if(vertex == null)
		    {
			return other.vertex == null;
		    }else
		    {
		        return vertex.equals(other.vertex);
		    }
		}

		public int hashCode()
		{
		    return vertex.hashCode();
		}
    }

    private Map<V,Set<MultiEdge>> vertexMap;

    /**
     * Create an empty graph.
     */
    public MapMultiGraph() 
    {
	   vertexMap = new HashMap<V,Set<MultiEdge>>();
    }

    /**
     * @return the number of vertices in the graph.
     */
    public int numVertices()
    {
        return vertexMap.size();
    }

    /**
     * @return the number of edges in the graph.
     */
    public int numEdges()
    {
        int numEdges = 0;
	
    	for(V vertex : vertexMap.keySet())
    	{
    	    numEdges += this.degree(vertex);
    	}
    	return numEdges;
    }

    /**
     * Gets the number of vertices connected by edges from a given
     * vertex.  If the given vertex is not in the graph, throws a
     * RuntimeException.
     *
     * @param vertex the vertex whose degree we want.
     * @return the degree of vertex 'vertex'
     */
    public int degree(V vertex)
    {
    	int total = 0;
    	Set<MultiEdge> adj = this.getAdjSet(vertex);
    	for(MultiEdge dest: adj)
    	{
    	    total += dest.numEdges;
    	}
    	return total;
    }

    /**
     * Similar to adjacentTo, but casts as Set for internal use,
     * using MultiEdge wrapper and throws RuntimeException for 
     * null adjacency
     *
     * @param vertex the vertex to get adjacencies from
     * @return the Set of vertices
     */
    private Set<MultiEdge> getAdjSet(V vertex)
    {
    	Set<MultiEdge> adjacencies = vertexMap.get(vertex);
    	
    	if(adjacencies == null)
    	{
    	    throw new RuntimeException("Vertex not in graph");
    	}
    	else
    	{
    	    return adjacencies;
    	}
    }

    /**
     * Adds a directed edge between two vertices.  If there is already an edge
     * between the given vertices, does nothing.  If either (or both)
     * of the given vertices does not exist, it is added to the
     * graph before the edge is created between them.
     *
     * @param from the source vertex for the added edge
     * @param to the destination vertex for the added edge
     */
    public void addEdge(V from, V to)
    {
    	if(!this.contains(from) || !this.contains(to)){
    	   this.addVertex(from);
    	   this.addVertex(to);
	}
	   
	   MultiEdge toPlace = new MultiEdge(to, this.numEdges(from, to)+1);
	   
	   Set<MultiEdge> adj = this.getAdjSet(from);
	   adj.remove(toPlace);
	   adj.add(toPlace);
    }

    /**
     * Adds a vertex to the graph.  If the vertex already exists in
     * the graph, does nothing.  If the vertex does not exist, it is
     * added to the graph, with no edges connected to it.
     *
     * @param vertex the vertex to add
     */
    public void addVertex(V vertex)
    {
    	if(!this.contains(vertex))
        {
            vertexMap.put(vertex, new HashSet<MultiEdge>());
        }
    }

    /**
     * @return the an iterable collection for the set of vertices of
     * the graph.
     */
    public Iterable<V> getVertices()
    {
        return vertexMap.keySet();
    }

    /**
     * Gets the vertices adjacent to a given vertex.  A vertex y is
     * "adjacent to" vertex x if there is an edge (x, y) in the graph.
     * Because edges are directed, if (x, y) is an edge but (y, x) is
     * not an edge, we would say that y is adjacent to x but that x is
     * NOT adjacent to y.
     *
     * @param from the source vertex
     * @return an iterable collection for the set of vertices that are
     * the destinations of edges for which 'from' is the source
     * vertex.  If 'from' is not a vertex in the graph, returns an
     * empty iterator.
     */
    public Iterable<V> adjacentTo(V from)
    {
    	Set<MultiEdge> adj = getAdjSet(from);
    	LinkedList<V> toReturn = new LinkedList();
    	for(MultiEdge dest: adj)
    	{
    	    int numEdges = dest.numEdges;
    	    for(int i = 0; i < numEdges; i++)
    	    {
        		toReturn.add(dest.vertex);
    	    }
    	}
        return toReturn;
    }

    /**
     * Tells whether or not a vertex is in the graph.
     *
     * @param vertex a vertex
     * @return true iff 'vertex' is a vertex in the graph.
     */
    public boolean contains(V vertex)
    {
        return vertexMap.containsKey(vertex);
    }

    /**
     * Tells whether an edge exists in the graph.
     *
     * @param from the source vertex
     * @param to the destination vertex
     *
     * @return true iff there is an edge from the source vertex to the
     * destination vertex in the graph.  If either of the given
     * vertices are not vertices in the graph, then there is no edge
     * between them.
     */
    public boolean hasEdge(V from, V to)
    {
	if(this.contains(from))
	{
	    return getAdjSet(from).contains(new MultiEdge(to, 0));
	}
	return false;

    }

    public int numEdges(V from, V to)
    {
	if(this.hasEdge(from, to))
	{
	    for(MultiEdge dest: this.getAdjSet(from))
	    {
		if(dest.equals(new MultiEdge(to, 0))){
			return dest.numEdges;
		}
	    }
	}
	return 0;
    }

    /**
     * Gives a string representation of the graph.  The representation
     * is a series of lines, one for each vertex in the graph.  On
     * each line, the vertex is shown followed by ": " and then
     * followed by a set of the vertices adjacent to that vertex.  In
     * this list of vertices, the vertices are separated by ", ".  For
     * example, for a graph with String vertices "A", "B", and "C", we
     * might have the following string representation:
     *
     * <PRE>
     * A->[(A,1),(B,2)]
     * B->[]
     * C->[(A,3),(B,2)]
     * </PRE>
     *
     * This representation would indicate that the following edges are
     * in the graph: (A, A), (A, B), (A, B), (C, A), (C, A), (C, A), (C, B), (C, B) and that B has no
     * adjacent vertices.
     *
     * @return the string representation of the graph
     */
    public String toString()
    {
        String toReturn = "";
    	for(V vertex : vertexMap.keySet())
    	{
    	    toReturn += vertex + ":[";

    	    for(MultiEdge dest : getAdjSet(vertex))
    	    {
        		toReturn += "(" + dest.vertex + "," + (Integer) dest.numEdges + "),";
    	    }

    	    if(!(toReturn.charAt(toReturn.length()-2) == ':'))
    	    {
        		toReturn = toReturn.substring(0,toReturn.length()-1);
    	    }

    	    toReturn += "]\n";
    	}

    	if(!toReturn.isEmpty())
    	{
    	    toReturn = toReturn.substring(0,toReturn.length()-1);
    	}

    	return toReturn;
    }

    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }
        if(o != null && this.getClass() == o.getClass())
        {

    	    MapMultiGraph other = (MapMultiGraph) o;
    	    
    	    Iterable otherVertices = other.getVertices();
    	    Iterable<V> thisVertices = this.getVertices();
    	    if(thisVertices.equals(otherVertices))
    	    {
        		for(V vertex : thisVertices)
        		{
        		    if(!this.getAdjSet(vertex).equals(other.getAdjSet(vertex)))
        		    {
            			return false;
        		    }
        		}
        		return true;
    	    }
        }
    	return false;
    }

    /**
     * Tells whether the graph is empty.
     *
     * @return true iff the graph is empty. A graph is empty if it has
     * no vertices and no edges.
     */
    public boolean isEmpty()
    {
    	return this.vertexMap.isEmpty();
    }

    /**
     * Removes and vertex from the graph.  Also removes any edges
     * connecting from the edge or to the edge.
     *
     * <p>Postconditions:
     *
     * <p>If toRemove was in the graph:
     * <ul>
     * <li>numVertices = numVertices' - 1
     * <li>toRemove is no longer a vertex in the graph
     * <li>for all vertices v: toRemove is not in adjacentTo(v)
     * </ul>
     *
     * @param toRemove the vertex to remove.
     */
    public void removeVertex(V toRemove)
    {
    	if(this.contains(toRemove))
    	{
    	    for(V vertex : vertexMap.keySet())
    		{
    		    this.removeAllEdges(vertex, toRemove);
    		}
    	    vertexMap.remove(toRemove);
    	}
    }
    
    /**
     * Removes an edge from the graph.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - 1
     */
    public void removeEdge(V from, V to)
    {
        if(this.contains(from))
	{
        int numEdges = this.numEdges(from, to);
	    Set<MultiEdge> adj = this.getAdjSet(from);
	    MultiEdge replace = new MultiEdge(to, numEdges-1);
	    adj.remove(replace);
	    if(numEdges > 1)
	    {
		adj.add(replace);
	    }
	}
    }

    /**
     * Removes n edges between the given nodes.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - n
     */
    public void removeEdges(V from, V to, int n)
    {
	if(this.contains(from))
	{
		for(int i = 0; i < n; i++)
		{
		    this.removeEdge(from, to);
		}
	}
    }

    /**
     * Removes all edges between the given nodes.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - n
     * where n is the number of edges between the two nodes.
     */
    public void removeAllEdges(V from, V to)
    {
	if(this.contains(from))
	{
	    Set<MultiEdge> adj = this.getAdjSet(from);
	    MultiEdge replace = new MultiEdge(to,0);
	    adj.remove(replace);
	}
    }
}
