package view;

import java.util.Iterator;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

import fsm.Transition; 
import fsm.StepTransition;
import fsm.StepState;
import control.EditFSMCommand;

/**
 * TransitionDisplay contains all the transitions within the model with their locations and
 * draws them to the canvas
 */
public class TransitionDisplay
{
    private static int ARROW_SIZE = 5;
    
    private String label;
    private Point fromPos;
    private Point toPos;

    /**
     * Constructor
     * @param transitions of model
     * @param fromPos, position transition is coming from
     * @param toPos, position transition is going to
     */
    public TransitionDisplay(Iterable<StepTransition> transitions, Point fromPos, Point toPos)

    {
		label = "";
		for(StepTransition t : transitions)
		{
			String toAdd = t.getName();
			if(!toAdd.isEmpty())
			{
				label= label + toAdd + ",";
			}
		}
		if(!label.isEmpty())
		{
			label = label.substring(0, label.length()-1);
		}
		this.fromPos = fromPos;
		this.toPos = toPos;
    }

    /**
     * Transition is drawn
     * @param g, Graphics to draw the transition on
     */
    public void displaySelf(Graphics g)
    {
	if (fromPos.equals(toPos))
	    {
		this.drawOwnTransition(g);
		this.drawLabel(g);
	    }

	else 
	    { 
		this.drawArrow(g);
		this.drawLabel(g);
	    }
    }

    private void drawOwnTransition(Graphics g)
    {

	int unitSize = FSMView.PIXELS_TO_FSM_UNITS;
		
	
	int diameter = FSMView.PIXELS_TO_FSM_UNITS; 

	Graphics2D g2 = (Graphics2D) g.create(); //creates a graphic2D
     
	int toX = (int) toPos.getX()  + unitSize / 4;
	int toY = (int) toPos.getY() + unitSize  / 20;
	int arrowLen = (int) Math.sqrt(169);

	AffineTransform at = AffineTransform.getTranslateInstance(toX,toY - 10);
       	at.concatenate(AffineTransform.getRotateInstance(0.00000121)); //makes the arrow 90degrees
	g2.transform(at);
	
	//draws the arrow at 0,0
	g2.drawOval(0, 0, unitSize/2, unitSize /2);
	g2.fillPolygon(new int[] {arrowLen, 
				  arrowLen-ARROW_SIZE, 
				  arrowLen-ARROW_SIZE, 
				  arrowLen}, 
	               new int[] {0, 
				  -ARROW_SIZE, 
				  ARROW_SIZE, 
				  0}, 
				  4);

    }


    /**
     * Draws arrow of transition to indicate direction
     * @param g, Graphics to draw on
     */
    private void drawArrow(Graphics g)
    {
    	int diameter = FSMView.PIXELS_TO_FSM_UNITS; 

		Graphics2D g2 = (Graphics2D) g.create(); //creates a graphic2D
		int fromX = (int) fromPos.getX() + diameter/2;
		int fromY = (int) fromPos.getY() + diameter/2;
		int toX = (int) toPos.getX() + diameter/2;
		int toY = (int) toPos.getY() + diameter/2;

		int xDist = toX - fromX;
		int yDist = toY - fromY;

		double angle = Math.atan2(yDist, xDist); //gets the angle in double 
		
		int arrowLen = (int) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));

		AffineTransform at = AffineTransform.getTranslateInstance(fromX,fromY);
	    at.concatenate(AffineTransform.getRotateInstance(angle));
		g2.transform(at);
		
		//draws the arrow at 0,0
	    g2.drawLine(0, 0, arrowLen, 0);
		g2.fillPolygon(new int[] {arrowLen, 
								  arrowLen-ARROW_SIZE, 
								  arrowLen-ARROW_SIZE, 
								  arrowLen}, 
					   new int[] {0, 
					   			  -ARROW_SIZE, 
					   			  ARROW_SIZE, 
					   			  0}, 
					   4);
    }

    /**
     * Draws label of transition
     * @param g, Graphics to draw on
     */
    private void drawLabel(Graphics g)
    {
    	int diameter = FSMView.PIXELS_TO_FSM_UNITS;

    	int fromX = (int) fromPos.getX() + diameter/2;
		int fromY = (int) fromPos.getY() + diameter/2;
		int toX = (int) toPos.getX() + diameter/2;
		int toY = (int) toPos.getY() + diameter/2;

		int labelX = (int) (fromX + toX)/2;
		int labelY = (int) (fromY + toY)/2;

		int hOffset = g.getFontMetrics().stringWidth(label);
		int vOffset = g.getFontMetrics().getHeight();

		//make labels not overlap for double transitions
		if(toY-fromY < 0 || (toY-fromY == 0 && toX-fromX < 0)){
			labelY += (int) 3*diameter/4-vOffset;
		}
		else{
			labelY += (int) diameter/4-vOffset;
			if(toX-fromX == 0)
			{
				labelX -= hOffset;
			}
		}

		g.drawString(label, labelX, labelY);

    }

    /**
     * @return String of transition
     */
    public String toString()
    {
    	return fromPos + " -> " + label + " -> " + toPos;
    }
}
