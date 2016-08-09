package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;

public class AddTransitionCommand extends EditFSMCommand
{
    Point storedPos;
    String storedInput;


   /**
    * Constructor
    */
    public AddTransitionCommand(ViewableFSM model, EditMouseControl control)
    {
	super(model, control);
	this.message = "Click a state where a new transition is going from and then a state where the transition is going to";
    }

   /**
    * editModel adds the transition to the model
    * @param mouse the Point that the user clicks
    * @param input the String that lables the transition
    */    
    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
    	if(this.hasStoredPos())
    	{
    		this.model.addTransition(FSMView.convertToFSMUnits(storedPos), FSMView.convertToFSMUnits(mouse), storedInput);
    		this.clearStore();
    	}
    	else
    	{
    		this.setPos(mouse);
    		this.setInput(input);
    	}
    }

   /**
    * hasStoredPos checks if there is the user clicked the canvas for the initial state
    */    
    private boolean hasStoredPos()
    {
    	return storedPos != null;
    }

   /**
    * clearStore clears the stored information
    */
    private void clearStore()
    {
    	this.storedPos = null;
    	this.storedInput = null;
    }

   /**
    * setPos sets the storedPos
    * @param pos the Point that the pos
    */
    private void setPos(Point pos)
    {
    	this.storedPos = pos;
    }

    /**
     * setInput sets the input for the Transition
     */
    private void setInput(String input)
    {
    	this.storedInput = input;
    }

   /**
    * toString returns "Add Transition"
    */    
    public String toString()
    {
	return "Add Transition";
    }
}
