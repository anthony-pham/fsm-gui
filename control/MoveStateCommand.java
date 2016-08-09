package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;

/*
 * MoveStateCommand extends FSMCommand and updates the model everytime a state changes location.
 */
public class MoveStateCommand extends EditFSMCommand
{
    Point storedPos;

    /*
     * constructor 
     */
    public MoveStateCommand(ViewableFSM model, EditMouseControl control)
    {
	super(model, control);
	this.message = "Click on state and then location desired to move to";
    }

    /*
     * updateModel updates the model on moving the state to its new location
     * @param mouse the point where the mouse puts
     * @param input the user has in the input box, it is not used explicitly in this method
     */
    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
    	if(this.hasStoredPos())
    	{
            System.out.println("Acting on model");
    		this.model.moveState(FSMView.convertToFSMUnits(storedPos), FSMView.convertToFSMUnits(mouse));
    		this.clearStore();
    	}
    	else
    	{
    		this.setPos(mouse);
    	}
	}

    private boolean hasStoredPos()
    {
    	return storedPos != null;
    }

    private void clearStore()
    {
    	this.storedPos = null;
    }

    private void setPos(Point pos)
    {
    	this.storedPos = pos;
    }

    public String toString()
    {
	return "Move State";
    }
}
