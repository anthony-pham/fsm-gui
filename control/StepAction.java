package control;

import fsm.ViewableFSM;
import input_handling.FSMStringPasser;

import java.awt.event.ActionEvent;

public class StepAction extends SimulateFSMAction
{
    public StepAction(ViewableFSM model, FSMStringPasser manager)
    {
    	super(model, manager);
    }

    public void actionPerformed(ActionEvent e)
    {
	this.manager.nextInput();
    	this.model.simulate();
    }

    public String toString()
    {
        return "Step";
    }
}
