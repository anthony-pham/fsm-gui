package view;

import javax.swing.JPanel;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

/**
 * FSMCanvas is a class where the FSM is displayed
 */
public class FSMCanvas extends JPanel
{
    Iterable<StateDisplay> s;
    Iterable<TransitionDisplay> t;

    /**
     * Default Constructor
     */
    public FSMCanvas()
    {
    	s = null;
    	t = null;
    }

    /**
     * Sets the states to display
     * @param states, states of FSM
     */
    public void setDisplayStates(Iterable<StateDisplay> states)
    {
    	s = states;
    }

    /**
     * Sets the transitions to display
     * @param transitions, transitions of FSM
     */
    public void setDisplayTransitions(Iterable<TransitionDisplay> transitions)
    {
    	t = transitions;
    }

    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);

    	RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_TEXT_ANTIALIASING,
             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        rh.add(new RenderingHints(
             RenderingHints.KEY_ANTIALIASING,
             RenderingHints.VALUE_ANTIALIAS_ON));

        ((Graphics2D) g).setRenderingHints(rh);
	if (s != null)
        {
    		for(StateDisplay state: s)
    	    {
    			state.displaySelf(g);
    	    } 
        }

    	if (t != null)
        {
    		for (TransitionDisplay transition: t)
    	    {
    			transition.displaySelf(g);
    	    }
        }
    }

    /**
     * Clears states and transitions to display
     */
    public void clearCanvas()
    {
    	s = null;
    	t = null;
    }
}
