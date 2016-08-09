package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;



/**
 * MakeRejectCommand extends FSMCommand and updates the model everytime a state becomes a reject state.
 */
public class MakeRejectCommand extends EditFSMCommand
{
    /*
     * default constructor
     */
    public MakeRejectCommand(ViewableFSM model, EditMouseControl control)
    {
        super(model, control);
	this.message = "Click on state to change neutrality to reject";
    }

/**
* updateModel updates the model on the change to reject state
* @param mouse the Point that the mouse clicks on 
* @param input the String that user inputs in the input box, the input is not used in this method
*/
    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
        this.model.setReject(FSMView.convertToFSMUnits(mouse));
    }

    /*
     * toString returns "Make Reject"
     */
    public String toString()
    {
	return "Make Reject";
    }
}
