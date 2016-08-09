package control;

import fsm.ViewableFSM;
import input_handling.FSMStringPasser;

import java.awt.event.ActionEvent;

public class InputAction extends SimulateFSMAction
{
    InputBox box;
    public InputAction(ViewableFSM model, FSMStringPasser manager, InputBox box)
    {
    	super(model, manager);
	this.box = box;
    }

    public void actionPerformed(ActionEvent e)
    {
    	String s = box.pullInput();
    	manager.takeString(s);
    	this.manager.reset();
    	this.model.reset();
    }

    public String toString()
    {
        return "Input";
    }
}
