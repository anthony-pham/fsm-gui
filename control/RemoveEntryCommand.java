package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;

/*
 * RemoveEntryCommand extends FSMCommand and updates the model when a state goes from being an extry state to a non-entry state.
 */
public class RemoveEntryCommand extends EditFSMCommand
{
    /*
     * constructor
     */
    public RemoveEntryCommand(ViewableFSM model, EditMouseControl control)
    {
	   super(model, control);
	   this.message = "Click on non-entry state to remove entry and set clicked state to entry";
    }

   
    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
		this.model.removeEntry(FSMView.convertToFSMUnits(mouse));
    }

    /*
     * toString returns "Remove Entry"
     */
    public String toString()
    {
	return "Remove Entry";
    }
}
