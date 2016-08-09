package fsm;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import graph_utilities.IndexLabelGraph;
import graph_utilities.EdgeId;

/**
 * A LabelGraphFSM is a FiniteStateMachine that uses an IndexLabelGraph to store
 * states and transitions. If it is not empty, it is guaranteed to have exactly
 * one entry state.
 * @author Tristan Johnson
 */
public class LabelGraphFSM<S extends IndexState,T extends Transition<S>> 
				implements FiniteStateMachine<S,T>, java.io.Serializable
{
	private IndexLabelGraph<S,T> data;
	private S entry;

	/**
	 * 
	 */
    public LabelGraphFSM()
    {
		data = new IndexLabelGraph<S,T>();
		entry = null;
    }
    
    public int getNumStates()
    {
    	return data.numVertices();
    }

	public int getNumStates(S stateType)
	{
		if (stateType == null)
		{
		    throw new RuntimeException("Null state");
		}

		int total = 0;
		for(S sPair : this.getStates())
		{
		    if(stateType.typeEquals(sPair)){
				total++;
			}
		}
		return total;
	}

	public int getNumTransitions(S source, S sink)
	{
		if (source == null || sink == null)
		{
			throw new RuntimeException("Null state");
		}
		
		int total = 0;
		for(T t : this.getTransitions(source, sink))
		{
			total++;
		}
		return total;
	}

	public int getEdgeIndex(T transition)
	{
	    for(T toCompare: this.getTransitions(transition.getSource(), transition.getSink()))
		{
			if(toCompare.typeEquals(transition))
			{
				return toCompare.getIndex();
			}
		}
	    return -1;
	}

	public Iterable<S> getStates()
	{
	    return this.data.getVertices();
	}

	public void addState(S newState)
	{
		if(newState == null)
		{
			throw new RuntimeException("Null state");
		}
		if(data.isEmpty())
		{
		    entry = newState;
		}

		int index = this.getNumStates(newState);
		newState.setIndex(index);
		data.addVertex(newState);
	}

	public boolean hasState(S s)
	{
		return data.contains(s);
	}

	public boolean hasStateType(S s)
	{
	    for(S toCompare : this.getStates())
	    {
	    	if(toCompare.typeEquals(s))
	    	{
	    		return true;
	    	}
	    }
	    return false;
	}

	public void removeState(S s)
	{   
		if(s != null)
		{
			if(s.equals(this.getEntry()))
			{
				if(this.getNumStates() > 1)
				{
					throw new RuntimeException("Must set new entry state");
				}
				else
				{
					this.removeEntry(s);
				}
			}
			else
			{
				data.removeVertex(s);
			}
		}
	}

	public void removeEntry(S newEntry)
	{
		if(this.getNumStates() == 1)
		{
			this.data = new IndexLabelGraph<S,T>();
		}
		else
		{
			S tmp = this.getEntry();
			if(tmp != null)
			{
				if(tmp.equals(newEntry))
				{
					throw new RuntimeException("Need new entry replacement");
				}
				this.setEntry(newEntry);
				data.removeVertex(tmp);	
			}
		}
	}

	public void addTransition(T transition)
	{
		S from = transition.getSource();
		S to = transition.getSink();
		int edgeIndex = transition.getIndex();
		if(edgeIndex < 0)
		{
			transition.setIndex(0);
			edgeIndex = 0;
		}
		int numTransitions = this.getNumTransitions(from, to);

		if(numTransitions > 0)
		{
			if(edgeIndex >= numTransitions)
			{
				transition.setIndex(numTransitions);
			}

			data.labelEdge(transition);
		} 
		else if(edgeIndex == numTransitions)
		{
		    data.addEdge(from, to);
		    data.labelEdge(transition);
		}
	}

	public boolean hasTransition(T transition)
	{
		T cur = data.getLabelPointer(transition);
		return cur != null;
	}

	public void removeTransition(T transition)
	{
		if(transition != null)
		{
			S from = transition.getSource();
			S to = transition.getSink();

			Iterator<T> finder = this.getTransitions(from, to).iterator();
			boolean found = false;
			T inGraph = null;

		    while(finder.hasNext() && !found)
		    {
		    	inGraph = finder.next();
				if(inGraph.getName().equals(transition.getName()))
				{
				    found = true;
				}
		    }
		    
		    if(inGraph != null)
		    {
		    	data.removeEdge(inGraph);
		    }
		}
	}

	public Iterable<T> getTransitions()
	{
		Set<T> transitions = new HashSet<T>();
		for(S from : data.getVertices())
		{
			for(S to : data.getVertices())
			{
				 for(T t : getTransitions(from, to))
				 {
				 	transitions.add(t);
				 }
			}
		}
		return transitions;
	}

    public Iterable<T> getOutgoing(S source)
    {
    	Set<T> toReturn = new HashSet<T>();
    	for(S sink : data.adjacentTo(source))
    	{
    		toReturn.addAll((Set) this.getTransitions(source, sink));
    	}
    	return toReturn;
    }

	public Iterable<T> getTransitions(S from, S to)
	{
		return data.getLabels(from, to);
	}

	public void setAccept(S state)
	{
		S inGraph = data.getPointer(state);
		if(inGraph != null)
		{
			inGraph.setAccept();
		}
	}

	public void setReject(S state)
	{
		S inGraph = data.getPointer(state);
		if(inGraph != null)
		{
			inGraph.setReject();
		}
	}

	public void neutralizeState(S state)
	{
		S inGraph = data.getPointer(state);
		if(inGraph != null)
		{
			inGraph.neutralize();
		}
	}

    public S getEntry()
    {
	return this.entry;
    }

	public void setEntry(S state)
	{
		if(this.getNumStates() > 1)
		{
			if(state == null || !this.hasState(state))
			{
				throw new RuntimeException("Entry not in FSM");
			}
			entry = state;
		}
	}

	public S getPointer(S s)
	{
	    return data.getPointer(s);
	}

	public boolean isEmpty()
	{
		return data.isEmpty();
	}

	public boolean equals(Object o)
	{
		if(this == o){
			return true;
		}
		if(o == null || o.getClass() != this.getClass()){
			return false;
		}
		LabelGraphFSM other = (LabelGraphFSM) o;
		return this.data.equals(other.data) && this.entry.equals(other.entry);
	}

	public String toString()
	{
	    return data.toString();
	}
}
