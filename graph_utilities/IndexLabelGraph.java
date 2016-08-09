package graph_utilities;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * An IndexLabelGraph is an IndexGraph that implements a LabelGraph.
 * That is, it is a MultiGraph that allows for non-unique containment and
 * may associate labels with its edges.
 * @author Tristan Johnson
 */
public class IndexLabelGraph<V extends Indexable,L extends EdgeId<V>> 
                extends IndexGraph<V> 
                implements LabelGraph<V,L>, java.io.Serializable
{
    private Set<L> labels;
    
    public IndexLabelGraph()
    {
    	super();
    	labels = new HashSet<L>();
    }
    
    public void labelEdge(L label)
    {
        if(label.getIndex() < this.numEdges(label.getSource(), label.getSink()))
        {
            labels.add(label);
        }
        else{
            throw new RuntimeException("Non-existent edge");
        }
    }

    public L getLabelPointer(EdgeId<V> edge)
    {
    	for(L label : labels)
        {
            if(label.eqId(edge))
            {
                return label;
            }
        }
        return null;
    }

    public Iterable<L> getLabels(V from, V to)
    {
        Set<L> toReturn = new HashSet<L>();

        for(L label : this.labels)
        {
            V source = label.getSource();
            V sink = label.getSink();
            if(source.equals(from) && sink.equals(to))
            {
                toReturn.add(label);
            }
        }
        return toReturn;
    }

    public void removeEdge(V from, V to)
    {
        int maxIndex = this.numEdges(from, to)-1;
        this.removeEdge(from, to, maxIndex);
    }

    public void removeEdge(V from, V to, int edgeIndex)
    {
        int maxIndex = numEdges(from, to)-1;
        if(edgeIndex <= maxIndex && edgeIndex >= 0)
        {
            super.removeEdge(from, to);

            Iterator<L> finder = labels.iterator();
            L toRemove = null;
            while(finder.hasNext())
            {
                L label = finder.next();
                V source = label.getSource();
                V sink = label.getSink();
                int index = label.getIndex();
                if(edgeIndex == index && source.equals(from) && sink.equals(to)){
                    toRemove = label;
                }
                if(label.getIndex() > edgeIndex){
                    this.shiftDown(label, edgeIndex);
                }
            }
            labels.remove(toRemove);
        }
    }

    private void shiftDown(L label, int index)
    {
        label.setIndex(label.getIndex()-1);
    }

    public void removeEdge(EdgeId<V> edge)
    {
        this.removeEdge(edge.getSource(), edge.getSink(), edge.getIndex());
    }

    public void removeAllEdges(V from, V to)
    {
        int limit = numEdges(from, to);
        for(int i = 0; i < limit; i++)
        {
            this.removeEdge(from, to);
        }
    }

    public void removeVertex(V toRemove)
    {
        for(V other : this.getVertices())
        {
            this.removeAllEdges(other, toRemove);
            this.removeAllEdges(toRemove, other);
        }
        super.removeVertex(toRemove);
    }
}
