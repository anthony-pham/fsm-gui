package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;

/*
 * MakeNeutralCommand extends FSMCommand and updates the model everytime a state changes state to become a neutral state.
 */
public class MakeNeutralCommand extends EditFSMCommand
{
    /*
     *  contructor
     */
    public MakeNeutralCommand(ViewableFSM model, EditMouseControl control)
    {
        super(model, control);
	this.message = "Click on state to change neutrality to neutral";
    }
    
    /*
     * updateModel updates the model with setting state neutral 
     */
    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
        this.model.setNeutral(FSMView.convertToFSMUnits(mouse));
    }

    /*
     * toString returns a human readable statement to print out "Make Neutral"
     */
    public String toString()
    {
	return "Make Neutral";
    }
}
