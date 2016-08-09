package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;

/*
 * AddStateCommand extends FSMCommand that updates the model with any states that gets added.
 */
public class AddStateCommand extends EditFSMCommand
{
    /* 
     * Contructor
     */
    public AddStateCommand(ViewableFSM model, EditMouseControl control)
    {
	super(model, control);
	this.message = "Click on the canvas to add a new state";
    }
    
    /*
     * updateModel updates the model with the state that gets added
     * @param mouse the point that the user wants to add the state
     * @param input the String for the label of the state
     */
    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
    	this.model.addState(FSMView.convertToFSMUnits(mouse), input);
    }

    /*
     * toString returns AddState in a readable string format
     */
    public String toString()
    {
	return "Add State";
    }
}
