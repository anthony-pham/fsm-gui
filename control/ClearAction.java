package control;

import fsm.ViewableFSM;
import input_handling.FSMStringPasser;

import java.awt.event.ActionEvent;

public class ClearAction extends SimulateFSMAction
{
    public ClearAction(ViewableFSM model, FSMStringPasser manager)
    {
	super(model, manager);
    }

    public void actionPerformed(ActionEvent e)
    {
	this.manager.clear();
	this.model.reset();
    }

    public String toString()
    {
	return "Clear";
    }
}
