package fsm;

/**
 * A stepstate is an IndexState that is also a Simulator.
 * Must act within a SimulatorFSM
 */
public class StepState 
				extends IndexState 
				implements Simulator, java.io.Serializable
{
	private static final String REJ_NAME = "IMPLIED_REJECT";
	protected static final StepState IMP_REJECT = new StepState(REJ_NAME, State.REJECT);


	private StepState(String name, State.Neutrality n)
	{
		super(name, n);
	}

	/**
	 * Constructs a new StepState with the given name. Starts with 0 index, updated through setter
	 * @throws IllegalArumentException if the given name is "IMPLIED_REJECT", which is reserved for the static
	 */
	public StepState(String name){
		super(name);
	}

	/** 
	 * Sets the name of the state.
	 * @throws IllegalArumentException if the given name is "IMPLIED_REJECT", which is reserved for the static
	 */
	public void rename(String name)
	{
		super.rename(name);
	}

	/**
	 * Executes itself as the current state. Will look for a StepTransition
	 * that it should cross, and then cross it (update SimulatorFSM's current to the
	 * end of the transition)
	 * @throws RuntimeException if the given environment is not a SimulatorFSM or the 
	 * 							given transition to follow does not start from this
	 */
	public void run(Environment env)
	{
		if(!(env instanceof SimulatorFSM))
		{
			throw new RuntimeException("StepStates must simulate in SimulatorFSM");
		}

		StepTransition toFollow = ((SimulatorFSM) env).toFollow();

		if(toFollow.getSource() != this){
			throw new RuntimeException("Must step from current");
		}

		toFollow.crossTransition(env);
		// if there were more complex environment changes associated with a state
		// they would occur here
	}
}