package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;

/*
 * RemoveTransitionCommand extends FSMCommand and updates the model everytime a transition is removed.
 */
public class RemoveTransitionCommand extends EditFSMCommand
{
    Point storedPos;
    String storedInput;

    public RemoveTransitionCommand(ViewableFSM model, EditMouseControl mouseControl)
    {
    	super(model, mouseControl);
	this.message = "Click on the state where the transition is going from, then to in order to remove a transition";
    }

    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
    	if(this.hasStoredPos()) //it never gets into this
    	{
    		this.model.removeTransition(FSMView.convertToFSMUnits(storedPos), FSMView.convertToFSMUnits(mouse), storedInput);
    		this.clearStore();
    	}
    	else
    	{
    		this.setPos(mouse);
    		this.setInput(input);
    	}
	}

    public String toString()
    {
	return "Remove Transition";
    }

    private boolean hasStoredPos()
    {
    	return storedPos != null;
    }

    private void clearStore()
    {
    	this.storedPos = null;
    	this.storedInput = null;
    }

    private void setPos(Point pos)
    {
    	this.storedPos = pos;
    }

    private void setInput(String input)
    {
    	this.storedInput = input;
    }
}
