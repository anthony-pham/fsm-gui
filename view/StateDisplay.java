package view;

import fsm.State;
import fsm.StepState;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Color;


/**
 * State Display contain states with their position on the canvas and draws the states to
 * the canvas.
 */
public class StateDisplay
{
    private static final double CIRC_RATIO = .2;
    private static final double INV_ROOT2 = .71;
    
    private StepState s;
    private Point p;
    private boolean isEntry;
    private boolean isCurrent;

    /**
     * Constructor
     * @param state, state in FSM to display
     * @param point, location of state
     * @param isEntry, if the state is an entry state
     */
    public StateDisplay(StepState state, Point point, boolean isEntry, boolean isCurrent)
    {
    	s = state;
    	p = FSMView.convertFromFSMUnits(point);
    	this.isEntry = isEntry;
	this.isCurrent = isCurrent;
    }

    /**
     * @return x position of state as an int
     */
    public Point getPos()
    {
    	return p;
    }

    /**
     * @return s, state of the State Display
     */
    public StepState getState()
    {
    	return s;
    }

    /**
     * Draws state on canvas
     * @param g, Graphics to draw on
     */
    public void displaySelf(Graphics g)
    {
    	int unitSize = FSMView.PIXELS_TO_FSM_UNITS;
    	int xPos = (int) p.getX();
    	int yPos = (int) p.getY();
    	this.drawState(g, xPos, yPos, unitSize);
    	this.drawLabel(g, xPos, yPos, unitSize);

    	if (isEntry)
    	    {
	        this.drawEntry(g, xPos, yPos, unitSize);
    	    }

	if (isCurrent)
	    {
		this.drawCurrent(g, xPos, yPos, unitSize);
	    }
	
    	if (s.getNeutrality() == State.ACCEPT)
    	    {
        		this.drawAccept(g, xPos, yPos, unitSize);
    	    }
    	else if (s.getNeutrality() == State.REJECT)
    	    {
        		this.drawReject(g, xPos, yPos, unitSize);
    	    }
    }

    /**
     * Helper Method
     * Draws State
     * @param g, Graphics to draw on
     * @param xPos, x position of state
     * @param yPos, y position of state
     * @param radius, radius of state to be drawn
     */
    private void drawState(Graphics g, int xPos, int yPos, int radius)
    {
    	g.drawOval(xPos, yPos, radius, radius);
    }

    /**
     * Helper Method
     * Draws State Label
     * @param g, Graphics to draw on
     * @param xPos, x position of state
     * @param yPos, y position of state
     * @param radius, radius of state to be drawn
     */
    private void drawLabel(Graphics g, int xPos, int yPos, int radius)
    {
    	String label = s.getName();
    	FontMetrics f = g.getFontMetrics();
    	int labelWidth = f.stringWidth(label);
    	
    	g.drawString(s.getName(), (int) (xPos + (radius/2) - labelWidth/2), (int) (yPos + (4*radius/3)));
    }

    /**
     * Helper Method
     * Draws Accepting State 
     * @param g, Graphics to draw on
     * @param xPos, x position of state
     * @param yPos, y position of state
     * @param radius, radius of state to be drawn
     */
    private void drawAccept(Graphics g, int xPos, int yPos, int radius)
    {
    	g.drawOval((int) (xPos-(radius*CIRC_RATIO/2)), (int) (yPos-(radius*CIRC_RATIO/2)), (int) (radius*(1+CIRC_RATIO)), (int) (radius*(1+CIRC_RATIO)));
    }

    /**
     * Helper Method
     * Draws Rejecting State
     * @param g, Graphics to draw on
     * @param xPos, x position of state
     * @param yPos, y position of state
     * @param radius, radius of state to be drawn
     */
    private void drawReject(Graphics g, int xPos, int yPos, int radius)
    {
      	g.drawRect((int) (xPos-(radius*CIRC_RATIO/2)), (int) (yPos-(radius*CIRC_RATIO/2)), (int) (radius*(1+CIRC_RATIO)), (int) (radius*(1+CIRC_RATIO)));
    }

    /**
     * Helper Method
     * Draws Entry State
     * @param g, Graphics to draw on
     * @param xPos, x position of state
     * @param yPos, y position of state
     * @param radius, radius of state to be drawn
     */
    private void drawEntry(Graphics g, int xPos, int yPos, int radius)
    {
    	g.drawLine((int) (xPos + radius/10), (int) (yPos - radius/3), (int) (xPos + radius/2), yPos);
    	g.drawLine((int) (xPos + radius - radius/10), (int) (yPos - radius/3), (int) (xPos + radius/2), yPos);
    }

    /**
     * Helper Method
     * Draws Current State by filling it with a color
     * @param g, Graphics to draw on
     * @param xPos, x position of state
     * @param yPos, y position of state
     * @param radius, radius of state to be drawn
     */
    private void drawCurrent(Graphics g, int xPos, int yPos, int radius)
    {

		Graphics g2 = g.create();
		g2.setColor(Color.GREEN);
		g2.fillOval(xPos, yPos, radius, radius);
    }

    /**
     * @return String of state location and name
     */
    public String toString()
    {
    	return p.toString() + ":" + s.toString();
    }
}

