package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;

   /**
    * RemoveStateCommand extends FSMCommand and updates the model everytime a state is removed.
    */
public class RemoveStateCommand extends EditFSMCommand
{
    /**
     * constructor
     */
    public RemoveStateCommand(ViewableFSM model, EditMouseControl control)
    {
        super(model, control);
	this.message = "Click on state to remove";
    }

    /**
     * editModel removes the state from the model 
     * @param mouse the point that the user clicked
     * @param input the String from inputBox
     */
    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
    	try{
    	    this.model.removeState(FSMView.convertToFSMUnits(mouse));
    	} catch (RuntimeException e) {
    	    //Write in FSMView a public method to print messages to user on canvas
    	    //Print Cannot Remove Entry
    	}
    }
    
    /**
     * toString prints Remove State in a string
     */  
    public String toString()
    {
	return "Remove State";
    }
}
