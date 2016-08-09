package input_handling;

import fsm.ViewableFSM;
import fsm.FSMInteractor;

public class FSMStringPasser extends StringManager implements FSMInteractor
{
	public FSMStringPasser(ViewableFSM fsm)
    {
    	super(fsm);
    }

    public ViewableFSM getFSM()
    {
    	return (ViewableFSM) this.getEnv();
    }

    public void setFSM(ViewableFSM fsm)
    {
    	super.setEnv(fsm);
    }

    public ViewableFSM removeFSM()
    {
        return (ViewableFSM) super.removeEnv();
    }
}