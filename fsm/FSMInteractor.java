package fsm;

public interface FSMInteractor extends java.io.Serializable
{
	public ViewableFSM getFSM();

	public void setFSM(ViewableFSM newFSM);

	public ViewableFSM removeFSM();
}