package fsm;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/**
 * A simulator FSM is a LabelGraphFSM that is also an Environment
 * Simulate() moves from the current state across any transitions matching
 * the given input, setting the new current to be the end of the transition.
 * If it is simulating, can access the state to follow if there is one.
 * @author Tristan Johnson
 */
public class SimulatorFSM<I>
				extends LabelGraphFSM<StepState, StepTransition>
				implements Environment<I>, java.io.Serializable
{
	private Set<StepState> currentStates;
	private I lastInput;
	private StepTransition toFollow;
	private State.Neutrality result;

	/**
	 * new empty SimulatorFSM
	 */
	public SimulatorFSM()
	{
	    currentStates = new HashSet<StepState>();
		lastInput = null;
		toFollow = null;
		result = null;
	}

	/**
	 * If there is no input there is a result. If there is a current, result is accept, otherwise it is reject.
	 * If there is input and no current, current becomes entry. If there is current and no valid transitions, 
	 * go to implied reject state. If there is a valid transition, cross it and current becomes the end of the 
	 * transition.
	 */
	public void simulate()
	{
		if(lastInput == null)
		{
		    if(this.hasCurrentSimulator())
			   {
			       if(this.hasAcceptCurrent())
				   {
				       result = State.ACCEPT;
				   }
			       else
				   {
				       result = State.REJECT;
				   }
			       this.setCurrentSimulators(null);
			   }
		}
		else
		{
		    if(this.hasCurrentSimulator())
		    {
			Collection<StepState> currentCopy = (Collection<StepState>) ((HashSet) currentStates).clone();
			for(StepState currentState: currentCopy)
			    {
				if(currentState.getNeutrality() != State.REJECT)
				    {
					boolean followed = false;
					for(StepTransition t : this.getOutgoing(currentState))
					    {
						if(t.shouldCross(this))//want to follow this transition
						    {
							this.toFollow = t;
							currentState.run(this);
							this.toFollow = null;
							followed = true;
						    }
					    }
					if(!followed)
					    {
						currentStates.remove(currentState);
						currentStates.add(StepState.IMP_REJECT);
					    }
				    }
			    }
		    }
		    else
			{
			    this.currentStates.clear();
			    this.currentStates.add(this.getEntry());
			}
		}
	}

	/**
	 * @return true iff input is null and current is not null (i.e. getResult() is not null)
	 */
	public boolean hasResult()
	{
		return result != null;
	}

	/**
	 * @return State.ACCEPT if there is an ACCEPT current state, State.REJECT otherwise. null if still input
	 */
	public State.Neutrality getResult()
	{
		return result;
	}

	/**
	 * @return the StepTransition to cross, if it exists. null if it doesn't
	 */
	protected StepTransition toFollow()
	{
		return toFollow;
	}

	public boolean hasCurrentSimulator()
	{
	    return !getCurrentSimulators().isEmpty();
	}

	public Collection<Simulator> getCurrentSimulators()
	{
	    return (Collection) currentStates;
	}

    public Collection<StepState> getCurrentState()
    {
	return currentStates;
    }

	/**
	 * @return true iff there is a current state with ACCEPT neutrality
	 */
	private boolean hasAcceptCurrent()
	{
	    for(StepState current: currentStates)
		{
		    if(current.getNeutrality() == State.ACCEPT)
			{
			    return true;
			}
		}
	    return false;
	}

	public void setCurrentSimulators(Collection<Simulator> newCurrent)
	{
	    Set<StepState> newCur = new HashSet<StepState>();
	    if(newCurrent != null)
		{
		    for(Simulator newCurElement : newCurrent)
			{
			    if(newCurElement != null)
				{
		    
				    if(!(newCurrent instanceof StepState)){
					throw new RuntimeException("SimulatorFSM takes StepState");
				    }

				    if(!this.hasState((StepState) newCurElement)){
					throw new RuntimeException("Current state must be in FSM");
				    }
				}
			    newCur.add((StepState) newCurElement);
			}
		}
	    this.currentStates = newCur;
	}

    public void addSimulator(Simulator s)
    {
	currentStates.add((StepState) s);
    }

    public void removeSimulator(Simulator s)
    {
	if (!(s instanceof StepState))
	    {
		throw new RuntimeException("Simulator takes StepState");
	    }
	currentStates.remove((StepState) s);
    }

	public I getInput()
	{
		return lastInput;
	}

	public void passInput(I input)
	{
		lastInput = input;
		if(input != null)
		{
			result = null;
		}
	}

	/**
	 * Resets the simulator aspect of the SimulatorFSM
	 * After call, no currentSimulator (state) no input, no result, no transition to follow
	 */
	public void reset()
	{
	    this.setCurrentSimulators(null);
		lastInput = null;
		toFollow = null;
		result = null;
	}
}
