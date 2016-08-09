package fsm;

import graph_utilities.EdgeId;

/**
 * A transition is an edgeId used in Finite State Machines
 * Knows its source and sink states, and has a label. Indexable
 * to allow multiple edges.
 * @author Tristan Johnson
 */
public class Transition<S extends State> implements EdgeId<S>, java.io.Serializable
{
	private String name;
	private int index = 0;

	private S source;
	private S sink;

	public Transition(int index, String name, S source, S sink)
	{
		if(name == null)
		{
			throw new IllegalArgumentException("Transitions have non-null name");
		}
		this.name = name;
		this.index = index;
		this.source = source;
		this.sink = sink;
	} 	

	/** Assumes 0 index */
	public Transition(String name, S source, S sink)
	{
		this.name = name;
		this.source = source;
		this.sink = sink;
	}

	public String getName(){return this.name;}

	public void rename(String newName)
	{
		if(newName != null)
		{
			this.name = newName;
		}
		else
		{
			throw new RuntimeException("Name cannot be null");
		}
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getIndex(){return index;}

	public S getSource(){return source;}

	public S getSink(){return sink;}

	/**
	 * true iff o is a transition with equivalent source and sink,
	 * and equal names and indices.
	 * @override
	 */
	public boolean equals(Object o)
	{
		if(this == o){
			return true;
		}
		if(o == null || o.getClass() != this.getClass()){
			return false;
		}
		Transition other = (Transition) o;
	    
	    S thisSource = this.getSource();
	    S thisSink = this.getSink();

		Object otherSource = other.getSource();
	    Object otherSink = other.getSink();

	    if(thisSource == null)
	    {
	    	if(otherSource != null)
	    	{
	    		return false;
	    	}
	    }
	    else
	    {
	    	if(!thisSource.equals(otherSource))
	    	{
	    		return false;
	    	}
	    }

	    if(thisSink == null)
	    {
	    	if(otherSink != null)
	    	{
	    		return false;
	    	}
	    }
	    else
	    {
	    	if(!thisSink.equals(otherSink))
	    	{
	    		return false;
	    	}
	    }

		return this.getName().equals(other.getName()) && other.getIndex() == this.getIndex();
	}

	/**
	 * Builds based on exponentials to avoid conflicts
	 * @override
	 */
	public int hashCode()
	{
		int n1 = name.hashCode();
		int n2 = index;
		int n3 = source.hashCode();
		int n4 = sink.hashCode();

		if(n1 == 0){
			if(n2 == 0){
				if(n3 == 0){
					if(n4 == 0){
						return 0;
					}else{
						return n4;
					}
				}else{
					if(n4 == 0){
						return n3;
					}else{
						return comb(n3, n4);
					}
				}
			}
			else{
				if(n3 == 0){
					if(n4 == 0){
						return n2;
					}else{
						return comb(n2, n4);
					}
				}else{
					if(n4 == 0){
						return comb(n2, n3);
					}else{
						return comb(n2, comb(n3, n4));
					}
				}
			}
		}else{
			if(n2 == 0){
				if(n3 == 0){
					if(n4 == 0){
						return n1;
					}else{
						return comb(n1, n4);
					}
				}else{
					if(n4 == 0){
						return n3;
					}else{
						return comb(n1, comb(n3, n4));
					}
				}
			}
			else{
				if(n3 == 0){
					if(n4 == 0){
						return n2;
					}else{
						return comb(n1, comb(n2, n4));
					}
				}else{
					if(n4 == 0){
						return comb(n2, n3);
					}else{
						return comb(n1, comb(n2, comb(n3, n4)));
					}
				}
			}
		}
	}

	private int comb(int n1, int n2)
	{
		if(n1 != 0 && n2 != 0){
			return (int) Math.pow(n1+n2, n1+n2);
		}
		if(n1 == 0){
			return n2;
		}
		return n1;
	}

	/**
	 * @return true iff o is a transition and has the same name
	 */
	public boolean typeEquals(Object o)
	{
		return o != null && o.getClass() == this.getClass() && name.equals(((Transition) o).getName());
	}

	/**
	 * @return true iff o has same source, sink, index
	 */
	public boolean eqId(EdgeId o)
	{
		if(o == null){
			return false;
		}

		S thisSource = this.getSource();
	    S thisSink = this.getSink();

		Object otherSource = o.getSource();
	    Object otherSink = o.getSink();

	    if(thisSource == null){
	    	if(otherSource != null){
	    		return false;
	    	}
	    } 
	    else{
	    	if(otherSource == null || !otherSource.equals(thisSource)){
	    		return false;
	    	}
	    }

	    return this.getIndex() == o.getIndex();

	}

	/**
	 * @return "source -> name -> sink"
	 */
	public String toString()
	{
		return source + " -> " + this.getName() + " -> " + sink;
	}
}