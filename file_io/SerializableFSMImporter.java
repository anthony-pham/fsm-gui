package file_io;

import fsm.ViewableFSM;

public class SerializableFSMImporter extends FSMImporter
{
	public SerializableFSMImporter(FSMLoader l){
		super(l);
	}

	public ViewableFSM importFSM(String filename)
	{
		try{
			return SerializableImporter.<ViewableFSM>read(filename);
		} catch(RuntimeException r){
			throw new RuntimeException(r);
		}
	}

	public String toString()
	{
		return "binary (.bin)";
	}
}