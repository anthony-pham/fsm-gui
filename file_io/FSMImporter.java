package file_io;

import fsm.ViewableFSM;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public abstract class FSMImporter implements ActionListener
{
	private FSMLoader loader;

	public FSMImporter(FSMLoader loader){
		this.loader = loader;
	}

	public abstract ViewableFSM importFSM(String filename);

	public void actionPerformed(ActionEvent e)
	{
		loader.readFromFile(this);
	}

	public abstract String toString();
}