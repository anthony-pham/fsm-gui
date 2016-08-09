package fsm;

import graph_utilities.Indexable;

public class IndexState extends State implements Indexable, java.io.Serializable
{
	private int index = 0;

	public IndexState(int index, String name)
	{
		super(name);
		this.index = index;
	} 	

	/** Assumes 0 index */
	public IndexState(String name)
	{
		super(name);
	}

	public IndexState(String name, Neutrality n)
	{
		super(name, n);
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public boolean equals(Object o)
	{
		return super.equals(o) && this.getClass() == o.getClass() && ((IndexState) o).getIndex() == this.getIndex();
	}

	public int hashCode()
	{
		int sup = super.hashCode();
		int sub = this.getIndex();
		int toReturn;
		if(sub == 0)
		{
			toReturn = sup;
		} 
		else if(sup == 0)
		{
			toReturn = sub;
		}
		else
		{
			toReturn = (int) Math.pow(sup+sub, sup+sub);
		}
		/*
		System.out.println("Hashing: " + this);
		System.out.println("Hash is: " + toReturn);
		*/
		return toReturn;
	}

	public boolean typeEquals(Object o)
	{
		return super.equals(o);
	}

	public String toString()
	{
		return "("+ super.toString() + "," + (new Integer(this.getIndex())).toString()+")";
	}
}