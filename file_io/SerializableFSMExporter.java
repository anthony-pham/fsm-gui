package file_io;

import fsm.ViewableFSM;

public class SerializableFSMExporter extends FSMExporter
{
	public SerializableFSMExporter(FSMSaver s)
	{
		super(s);
	}

	public void exportFSM(ViewableFSM model, String filename)
	{
		try{
			SerializableExporter.<ViewableFSM>write(model, filename);
		} catch (RuntimeException r){
			throw new RuntimeException(r);
		}
	}

	public String toString()
	{
		return "binary (.bin)";
	}
}