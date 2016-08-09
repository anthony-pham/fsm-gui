package fsm;

/**
 * An FSMListener is called to update itself by a ViewableFSM.
 */
public interface FSMListener extends FSMInteractor
{
	public void update();
}