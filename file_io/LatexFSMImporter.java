package file_io;

import fsm.ViewableFSM;

public class LatexFSMImporter extends FSMImporter
{
	public LatexFSMImporter(FSMLoader l){
		super(l);
	}


	//not implemented :(
	public ViewableFSM importFSM(String filename)
	{
		return null;
	}

	public String toString()
	{
		return "LaTeX (.tex)";
	}
}