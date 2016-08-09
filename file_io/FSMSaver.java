package file_io;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import client.ModelManager;
import fsm.ViewableFSM;
import control.InputBox;
import view.TextListener;

public class FSMSaver
{
	private ModelManager manager;
	private InputBox saveBox;
    private LinkedList<TextListener> textListeners;
    private String message;

	public FSMSaver(ModelManager m, InputBox saveBox)
	{
		manager = m;
		this.saveBox = saveBox;
		textListeners = new LinkedList<TextListener>();
		message = "Successful save to file";
	}

    public void updateTextListeners()
    {
	for (TextListener tl: textListeners)
	    {
		tl.passInput(this.message);
		tl.simulate();
	    }
    }

    public void addTextListener(TextListener tl)
    {
	this.textListeners.add(tl);
    }

    public void removeTextListener(TextListener tl)
    {
	this.textListeners.remove(tl);
    }

    public void clearTextListeners()
    {
	for(TextListener tl : textListeners)
	{
	    tl.clearText();
	}
    }

	public String getFileName()
	{
		return saveBox.getText();
	}

	public void writeToFile(FSMExporter exporter)
	{
		ViewableFSM fsm = manager.removeFSM();
		try{
			exporter.exportFSM(fsm, getFileName());
		} catch (RuntimeException r){
		    message = r.getMessage();
		}
		manager.setFSM(fsm);
		this.updateTextListeners();
	}
}
