package fsm;

import java.util.Collection;

/**
 * @param I the input type
 */
public interface Environment<I>
{
	public void simulate();

	public I getInput();

	public void passInput(I newInput);

	public boolean hasCurrentSimulator();

	public Collection<Simulator> getCurrentSimulators();

    public void addSimulator(Simulator s);

    public void removeSimulator(Simulator s);

	public void setCurrentSimulators(Collection<Simulator> newCurrent);
}
