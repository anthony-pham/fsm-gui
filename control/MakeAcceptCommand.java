package control;

import fsm.ViewableFSM;
import view.FSMView;

import java.awt.Point;

/*
 * MakeAcceptCommand extends FSMCommand and updates the model everytime a state becomes an accept state.
 */
public class MakeAcceptCommand extends EditFSMCommand
{
    public MakeAcceptCommand(ViewableFSM model, EditMouseControl control)
    {
        super(model, control);
	this.message = "Click on state to change neutrality to accept";
    }


    public void editModel(Point mouse, String input)
    {
	this.updateTextListeners();
        this.model.setAccept(FSMView.convertToFSMUnits(mouse));
    }

    public String toString()
    {
	return "Make Accept";
    }
}
