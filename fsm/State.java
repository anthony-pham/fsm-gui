package fsm;

/**
 * States are vertices of FSMs. States have Neutrality (Accepting, Rejecting, or neutral (neither)).
 * States have also have string labels.
 * @author Tristan Johnson
 */
public class State implements java.io.Serializable
{
	/**
	 * A state may be Accepting, Neutral, or Rejecting.
	 * These are all uniquely represented by the interface.
	 */	
	public static interface Neutrality extends java.io.Serializable {} 

	private static class Accept implements Neutrality
	{
	    public String toString(){return "ACCEPT";}
	}

	private static class Neutral implements Neutrality
	{
	    public String toString(){return "NEUTRAL";}
	}
	private static class Reject implements Neutrality
	{
	    public String toString(){return "REJECT";}
	}

	/** ACCEPT neutrality. One of three options that States may have */
	public static final Neutrality ACCEPT = new State.Accept();
	/** NEUTRAL neutrality. One of three options that States may have */
	public static final Neutrality NEUTRAL = new State.Neutral();
	/** REJECT neutrality. One of three options that States may have */
	public static final Neutrality REJECT = new State.Reject();


	private String name;
	private Neutrality neutrality;

	/**
	 * Constructs a new neutral state with the given name
	 * @throws IllegalArgumentException if the name is null
	 */
	public State(String name)
	{
		this.rename(name);
		this.neutrality = State.NEUTRAL;
	}
  
    /**
	 * Constructs a new state with the given name and neutrality
	 * @throws IllegalArgumentException if the given neutrality is not one of the statics or name is null
	 */
	public State(String name, Neutrality n)
	{
		this.rename(name);
		if(!(n == State.ACCEPT || n == State.NEUTRAL || n == State.REJECT))
		{
			throw new IllegalArgumentException("Invalid neutrality");
		}
		this.neutrality = n;
	}

	/**
	 * @throws IllegalArgumentException if the name is null
	 */
	public void rename(String newName)
	{
		if(newName != null)
		{
			this.name = newName;
		}
		else
		{
			throw new IllegalArgumentException("Name may not be null");
		}
	}
	/**
	 * Getter for name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets this state's neutrality to be accepting
	 */
	public void setAccept()
	{
		this.neutrality = State.ACCEPT;
	}

	/**
	 * Sets this state's neutrality to be rejecting
	 */
	public void setReject()
	{
		this.neutrality = State.REJECT;
	}

	/**
	 * Sets this state's neutrality to be neutral
	 */
	public void neutralize()
	{
		this.neutrality = State.NEUTRAL;
	}


	/**
	 * Gets this states neutrality.
	 * @return State.ACCEPT, State.REJECT, or State.NEUTRAL depending on this
	 */
	public State.Neutrality getNeutrality()
	{
		return this.neutrality;
	}

	/**
	 * @return true iff o is a state and the names are equal.
	 */ 
	public boolean equals(Object o)
	{
		if(this == o){
			return true;
		}
		if(o == null || o.getClass() != this.getClass()){
			return false;
		}
		State other = (State) o;
		    
		return this.name.equals(other.name);
	}

	/** hash of the name */
	public int hashCode()
	{
	    return name.hashCode();
	}

	/**
	 * @return "name: neutrality"
	 */
    public String toString()
    {
		return (this.getName() + ": " + this.getNeutrality());
    }
}
