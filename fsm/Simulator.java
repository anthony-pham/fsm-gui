package fsm;

public interface Simulator
{	
	/**
     * Simulates the environment, assuming this simulator
     * is the current.
     * @param env the environment the state runs in 
     * @author Tristan Johnson
     */
    public void run(Environment env);
}