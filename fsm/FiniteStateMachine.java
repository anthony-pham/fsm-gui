package fsm;

import java.util.Map;

public interface FiniteStateMachine<S extends State,T extends Transition<S>>
{
	public void addState(S newState);

	public Iterable<S> getStates();

	public int getNumTransitions(S source, S sink);

	public int getEdgeIndex(T transition);

	public boolean hasState(S state);

    /**
     * removes the given state from the FSM, if it is not the entry state. throws an exception if the given state is entry
     */
    public void removeState(S state);

    /**
     * Removes the entry state from the fsm, and sets the entry state to be the given state. If the given state is not
     * in the fsm, throws a runtime exception
     * If the entry is the only state left, newEntry may be arbitrary.
     */
    public void removeEntry(S newEntry);

	public void addTransition(T transition);

	public boolean hasTransition(T transition);

	public void removeTransition(T transition);

	public Iterable<T> getTransitions(S from, S to);

	public Iterable<T> getOutgoing(S source);

	public void setAccept(S state);

	public void setReject(S state);

	public void neutralizeState(S state);

	public void setEntry(S state);

	public S getPointer(S state);
}
