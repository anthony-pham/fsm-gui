package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;

/*
 * MakeEntryCommand extends FSMCommand and updates the the model everytime a new state becomes the entry state.
 */
public class MakeEntryCommand extends EditFSMCommand
{
    /*
     * Constructor
     */
    public MakeEntryCommand(ViewableFSM model, EditMouseControl control)
    {
	super(model, control);
	this.message = "Click on state to make it the entry state";
    }

    /*
     * updateModel updates the model by setting a new entry state
     * @param mouse the Point that user wants to place the entry state
     * @param input the String that user has in the inputbox, it is ignored for this method
     */
    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
	this.model.setEntry(FSMView.convertToFSMUnits(mouse));
    }

    /*
     * toString returns makeEntry in a string format
     */
    public String toString()
    {
	return "Make Entry";
    }
}
