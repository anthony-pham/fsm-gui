package file_io;

import fsm.ViewableFSM;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public abstract class FSMExporter implements ActionListener
{
	private FSMSaver saver;

	public FSMExporter(FSMSaver saver){
		this.saver = saver;
	}

	public abstract void exportFSM(ViewableFSM fsm, String filename);

	public void actionPerformed(ActionEvent e)
	{
		saver.writeToFile(this);
	}

	public abstract String toString();
}