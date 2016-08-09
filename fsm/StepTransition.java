package fsm;

import java.util.Collection;

/**
 * StepTransitions are transitions that are used
 * in a SimulatorFSM. May test if should cross and 
 * cross the transition
 * @author Tristan Johnson
 */
public class StepTransition 
				extends Transition<StepState> 
				implements java.io.Serializable
{
	public StepTransition(int index, String name, StepState source, StepState sink)
	{
		super(index, name, source, sink);
	} 	

	/** Assumes 0 index */
	public StepTransition(String name, StepState source, StepState sink)
	{
		super(name, source, sink);
	}

	/**
	 * @return true iff there is no label or the environment's current input matches the label of this transition
	 */
	public boolean shouldCross(Environment env)
	{
		return this.getName().isEmpty() || this.getName().equals(env.getInput());
	}

	/**
	 * Sets the environments current simulator from the sink to the source
	 * of this transition
	 * @throws RuntimeException if the sink is not the environments current
	 */
	public void crossTransition(Environment env)
	{
		if(env.hasCurrentSimulator()){
		    Collection<Simulator> currents = env.getCurrentSimulators();
		    env.removeSimulator(this.getSource());
		    env.addSimulator(this.getSink());
		}

		//if a transition were to perform more actions in the environment, it would happen here
	}
}
