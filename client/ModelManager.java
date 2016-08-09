package client;

import java.util.LinkedList;

import fsm.ViewableFSM;
import fsm.FSMInteractor;
import fsm.FSMListener;

public class ModelManager implements java.io.Serializable, FSMInteractor
{
	private ViewableFSM modelFSM;
	private LinkedList<FSMInteractor> interactors;

	public ModelManager()
	{
		this.modelFSM = null;
		interactors = new LinkedList<FSMInteractor>();
	}

	public void addInteractor(FSMInteractor i)
	{
		interactors.add(i);
	}

	public Iterable<FSMInteractor> removeAllInteractors()
	{
		Iterable<FSMInteractor> toReturn = interactors;
		interactors = new LinkedList<FSMInteractor>();
		return toReturn;
	}

	public void addAllInteractors(Iterable<FSMInteractor> interactors)
	{
		for(FSMInteractor i : interactors)
		{
			this.interactors.add(i);
		}
	}

	public void removeInteractor(FSMInteractor i)
	{
		interactors.remove(i);
	}

	/**
	 * @return the FSM for the app this manages
	 */
	public ViewableFSM getFSM()
	{
		return this.modelFSM;
	}

	/**
	 * Sets the FSM to the newFSM for all FSMInteractors in the app this manages
	 */
	public void setFSM(ViewableFSM newFSM)
	{
		for(FSMInteractor i : interactors)
		{
			i.setFSM(newFSM);
		}

		this.modelFSM = newFSM;
		this.modelFSM.updateListeners();
	}

	/**
	 * Removes the FSM for all Interactors in the app this manages
	 * @return the removed FSM
	 */
	public ViewableFSM removeFSM()
	{
		ViewableFSM toReturn = this.modelFSM;
		
		for(FSMInteractor i : interactors)
		{
			i.removeFSM();
		}

		toReturn.removeAllTextListeners();

		this.modelFSM = null;
		return toReturn;
	}
}
