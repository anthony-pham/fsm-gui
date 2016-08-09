package graph_utilities;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;


/**
 * A Bijection is a two-way map. 
 * Elements of the domain and codomain must be unique, as opposed
 * to just the domain. Call elements of the domain elements, elements
 * of the coDomain coElements. Functionally equivalent to a set of ordered
 * pairs where any pair is equal iff at least one of the elements are
 * equal. 
 * @author Tristan Johnson
 */
public class Bijection<X,Y> implements java.io.Serializable
{
	public static class Pair<X,Y> implements java.io.Serializable
	{
		X element;
		Y coElement;

		public Pair(X x, Y y)
		{
			element = x; coElement = y;
		}

		public X getX(){return element;}
		public Y getY(){return coElement;}
	}

	private Map<X,Y> f;
	private Map<Y,X> finv;

	public Bijection()
	{
		f = new HashMap<X,Y>();
		finv = new HashMap<Y,X>();
	}

	/**
	 * Returns the element associated with the given coElement
	 * @param coElement the element of Y to be matched
	 * @return the element of X that maps to the given coElement
	 */
	public X getElement(Y coElement)
	{
		return finv.get(coElement);
	}

	/**
	 * Returns the coElement associated with the given element
	 * @param element the element of X to be matched
	 * @return the coElement (in Y) that element maps to
	 */
	public Y getCoElement(X element)
	{
		return f.get(element);
	}

	/**
	 * @param element the element in X to check containment for
	 * @return true iff the bijection contains element
	 */
	public boolean hasElement(X element)
	{
		return f.containsKey(element);
	}

	/**
	 * @param coElement the coElement (in Y) to check containment for
	 * @return true iff the bijection contains element
	 */
	public boolean hasCoElement(Y coElement)
	{
		return finv.containsKey(coElement);
	}

	/**
	 * Places the mapping within the bijection. If
	 * either of the two given elements are already contained,
	 * does nothing
	 * @param element the element of X to be mapped
	 * @param coElement the coElement in Y to be mapped to
	 */
	public void put(X element, Y coElement)
	{
		/*
		System.out.println("Placing map");
		System.out.println("element: "+ element);
		System.out.println("coElement: "+coElement);
		System.out.println("has coElement? " + this.hasCoElement(coElement));
		*/
		if(!(this.hasElement(element) || this.hasCoElement(coElement)))
		{
			f.put(element, coElement);
			finv.put(coElement, element);
		}
	}

	/**
	 * Removes the given element and returns its mapping
	 * @param element the element to be removed
	 * @return the coElement the removed element mapped to
	 */
	public Y removeElement(X element)
	{
		Y toReturn = f.remove(element);
		finv.remove(toReturn);
		return toReturn;
	}

	/**
	 * Removes the given coElement and returns its mapping
	 * @param coElement the coElement to be removed
	 * @return the element the removed coElement mapped to
	 */
	public X removeCoElement(Y coElement)
	{
		X toReturn = finv.remove(coElement);
		f.remove(toReturn);
		return toReturn;
	}

	/**
	 * Returns the direct mapping of the bijection
	 * @return the direct mapping. (x,y) are in the map
	 * iff (x,y) is in the bijection
	 */
	public Map<X,Y> direct(){
		return f;
	}

	/**
	 * Returns the inverse mapping of the bijection
	 * @return the inverse mapping. (y,x) are in the map
	 * iff (x,y) is in the bijection
	 */
	public Map<Y,X> inverse(){
		return finv;
	}

	/**
	 * @return all elements in the bijection
	 */
	public Set<X> domain()
	{
		return f.keySet();
	}

	/**
	 * @return all coElements in the bijection
	 */
	public Set<Y> coDomain()
	{
		return finv.keySet();
	}

	public boolean equals(Object o)
	{
		if(this == o){
			return true;
		}
		if(o == null || o.getClass() != this.getClass()){
			return false;
		}
		return f.equals(((Bijection) o).direct());
	}

	public String toString()
	{
		return f.toString();
	}
}