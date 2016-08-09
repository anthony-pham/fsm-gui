package control;

import fsm.ViewableFSM;
import input_handling.FSMStringPasser;

import java.awt.event.ActionEvent;

public class ResetAction extends SimulateFSMAction
{
    public ResetAction(ViewableFSM model, FSMStringPasser manager)
    {
    	super(model, manager);
    }

    public void actionPerformed(ActionEvent e)
    {
    	this.manager.reset();
    	this.model.reset();
    }

    public String toString()
    {
    	return "Reset";
    }
}
