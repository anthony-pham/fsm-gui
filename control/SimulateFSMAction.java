package control;

import java.awt.event.ActionListener;

import fsm.ViewableFSM;
import fsm.FSMInteractor;
import input_handling.FSMStringPasser;

public abstract class SimulateFSMAction implements ActionListener, FSMInteractor
{
    protected ViewableFSM model;
    protected FSMStringPasser manager;

    protected SimulateFSMAction(ViewableFSM model, FSMStringPasser manager)
    {
    	this.model = model;
    	this.manager = manager;
    }

    public ViewableFSM getFSM()
    {
        return this.model;
    }

   public void setFSM(ViewableFSM newFSM)
   {
        model = newFSM;
   }

   public ViewableFSM removeFSM()
   {
        ViewableFSM toReturn = model;
        this.model = null;
        return toReturn;
   }
}
