package fsm;

import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.Collection;
    
import java.awt.Point;

import graph_utilities.Bijection;
import view.TextListener;


/**
 * A ViewableFSM is an SimulatorFSM that has a bijection
 * between states and points. Used to give a spacial representation
 * of a finite state machine. Uses string input for simulation.
 * @author Tristan Johnson
 */
public class ViewableFSM
				extends SimulatorFSM<String>
				implements java.io.Serializable
{

    private Bijection<Point, StepState> locations;
    private LinkedList<FSMListener> listeners;
    private LinkedList<TextListener> textListeners;

    public ViewableFSM()
    { 
    	super();
    	locations = new Bijection<Point, StepState>();
    	listeners = new LinkedList<FSMListener>();
	textListeners = new LinkedList<TextListener>();
    }

    private void updateTextListeners()
    {
	
	 for (TextListener tl: textListeners)
	 {
	     tl.passInput("Result: " + this.getResult().toString());
	 }
	 for (TextListener tl: textListeners)
	    {
		tl.simulate();
	    }
    }

    public void addTextListener(TextListener tl)
    {
	if(textListeners == null)
	    {
		textListeners = new LinkedList<TextListener>();
	    }
	textListeners.add(tl);
	this.updateListeners();
    }

    public void removeTextListener(TextListener tl)
    {
	this.textListeners.remove(tl);
    }

    public void addListener(FSMListener l)
    {
        if(listeners == null)
        {
            listeners = new LinkedList<FSMListener>();
        }
    	listeners.add(l);

        this.updateListeners();
    }

    public void removeListener(FSMListener l)
    {
        listeners.remove(l);
    }

    public void removeAllTextListeners()
    {
	textListeners = new LinkedList<TextListener>();
    }

    public Iterable<FSMListener> removeAllListeners()
    {
        Iterable<FSMListener> toReturn = listeners;
        listeners.clear();
        return toReturn;
    }

    public void addAllListeners(Iterable<FSMListener> newListeners)
    {
        if(newListeners != null)
        {
            for(FSMListener l : newListeners)
            {
                this.addListener(l);
            }
        }
    }

    public void updateListeners()
    {
    	for(FSMListener l : this.listeners)
    	{
    		l.update();
    	}
    }

    public void clearTextListeners()
    {
	for(TextListener tl : textListeners)
	{
	    tl.clearText();
	}
    }

    private  boolean isValid(Point position)
    {
        return position.getX() >= 0 && position.getY() >= 0;
    }

    /**
     * @return the mapping of states to locations.
     */
    public Map<Point, StepState> getLocationMap()
    {
		return locations.direct();
    }

	/**
     * @return the mapping of locations to states
     */
    public Map<StepState, Point> getStateMap()
    {
        return locations.inverse();
    }

    /**
     * @return the position of the entry state; null if none
     */
    public Point getEntryPos()
    {
		for (Map.Entry<Point, StepState> pair: locations.direct().entrySet())
	    {
			if (pair.getValue().equals(this.getEntry()))
		    {
				return pair.getKey();
		    }	
	    }
		return null;
    }

    /**
     * Adds a new state with the given name at the given position.
     * @param position the position associated with the state to add
     * @param name the name of the new state
     */
    public void addState(Point position, String name)
    {
    	if(isValid(position) && !locations.hasElement(position))		 
    	{
    	    StepState toAdd = new StepState(name);
    	    int index = this.getNumStates(toAdd);
            super.addState(toAdd);
    	    this.locations.put(position, toAdd);
    	    this.updateListeners();
        }
    }

    /** 
     * @override 
     * @throws UnsupportedOperationException
     */
    public void addState(StepState s)
    {
    	throw new UnsupportedOperationException("States must be mapped to positions");
    }

    /**
     * Equivalent to addTransition in an FSM, where positions represent the states.
     * If source or sink do not contain states at the given positions, does nothing
     * @param sourcePos the position of the source state for the transition
     * @param sinkPos the position of the sink state for the transition
     * @param name the name of the transition
     */
    public void addTransition(Point sourcePos, Point sinkPos, String name)
    {
        StepState source = locations.getCoElement(sourcePos);
        StepState sink = locations.getCoElement(sinkPos);
        
        if(source != null && sink != null)
        {
            super.addTransition(new StepTransition(name, source, sink));
            this.updateListeners();
        }
    }

    /**
     * Removes the state at the given position, if able.
     * Catches the exception thrown if the given position represents entry, 
     * and does nothing if the position has no state.
     * @param position the position of the state to remove.
     */
    public void removeState(Point position)
    {
    	try{
	        StepState toRemove = locations.getCoElement(position);
	        super.removeState(toRemove);
	        locations.removeElement(position);
	    } catch(RuntimeException e)
	    {
	    	System.out.println(e);
	    }
	    this.updateListeners();
    }

    /** 
     * Overrides as in SimulatorFSM, but also removes location
     * @override 
     */
    public void removeState(StepState s)
    {
    	this.locations.removeCoElement(s);
    	super.removeState(s);
    	this.updateListeners();
    }

    /**
     * Removes the current entry and replaces it with the given replacement, if able
     * Catches the exception where the replacement will not exist after removal.
     * @param position the position of the state to replace the current entry
     */
    public void removeEntry(Point position)
    {
    	try
    	{
    		Point oldEntry = this.getEntryPos();
	        this.setEntry(position);
	        this.removeState(oldEntry);
	    } 
	    catch(RuntimeException e)
	    {
	    	System.out.println(e);
	    }
	    this.updateListeners();
    }

    /**
     * Removes the transition at the between the given positions with the given name
     *@param sourcePos the position of the source of the transition to remove
     *@param sinkPos the position of the sink of the transition to remove
     *@param name the name of the transition to remove
     */
    public void removeTransition(Point sourcePos, Point sinkPos, String name)
    {
        StepState source = locations.getCoElement(sourcePos);
        StepState sink = locations.getCoElement(sinkPos);

        if(source != null && sink != null){
            super.removeTransition(new StepTransition(name, source, sink));
            this.updateListeners();
        }
    }

    /**
     * Sets the state at the given position to be accepting
     * @param position the position of the state to change
     */
    public void setAccept(Point position)
    {
    	StepState toMakeAccept = locations.getCoElement(position);
    	super.setAccept(toMakeAccept);
    	this.updateListeners();
    }
		
	/**
     * Sets the state at the given position to be rejecting
     * @param position the position of the state to change
     */	  
    public void setReject(Point position)
    {
    	StepState toMakeReject = locations.getCoElement(position);
    	super.setReject(toMakeReject);
    	this.updateListeners();
    }

	/**
     * Sets the state at the given position to be neutral
     * @param position the position of the state to change
     */
    public void setNeutral(Point position)
    {
        StepState toMakeNeutral = locations.getCoElement(position);
        super.neutralizeState(toMakeNeutral);
        this.updateListeners();
    }

	/**
     * Sets the state at the given position to be the entry state
     * @param position the position of the state to change
     */
    public void setEntry(Point position)
    {
        StepState toMakeEntry = locations.getCoElement(position);
        try
        {
	        super.setEntry(toMakeEntry);
	    }
	    catch(RuntimeException e)
	    {
	    	System.out.println(e);
	    }
	    this.updateListeners();
    }

    /**
     * Removes the state at the fromPos and adds it again at toPos
     * @param fromPos the position of the state to move
     * @param toPos the position to move the state to
     */
    public void moveState(Point fromPos, Point toPos)
    {
        StepState from = locations.getCoElement(fromPos);
        StepState to = locations.getCoElement(toPos);
        if(from != null && to == null)
        {
			this.locations.removeCoElement(from);
        	this.locations.put(toPos, from);
            this.updateListeners();
        }
    }

    public void simulate()
    {
	super.simulate();
	this.updateListeners();
	if(this.hasResult())
	    {
		this.updateTextListeners();
	    }
    }

    public void setCurrentSimulators(Collection<Simulator> newCurrent)
    {
	super.setCurrentSimulators(newCurrent);
	this.updateListeners();
    }

    public void passInput(String input)
    {
	super.passInput(input);
	this.updateListeners();
    }

    public void reset()
    {
	super.reset();
	this.updateListeners();
    }

    public String toString()
    {
	 return super.toString() + "\n" + this.locations.toString();
    }
}
