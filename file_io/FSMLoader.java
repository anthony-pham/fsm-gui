package file_io;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import client.ModelManager;
import fsm.ViewableFSM;
import control.InputBox;
import view.TextListener;

public class FSMLoader
{
	private ModelManager manager;
	private InputBox loadBox;
    private LinkedList<TextListener> textListeners;
    private String message;

	public FSMLoader(ModelManager m, InputBox loadBox)
	{
		manager = m;
		this.loadBox = loadBox;
		textListeners = new LinkedList<TextListener>();
		message = "Successful load from file";
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

	private String getFileName()
	{
		return loadBox.getText();
	}

	public void readFromFile(FSMImporter importer)
	{
		try{
			ViewableFSM model = importer.importFSM(getFileName());
			manager.setFSM(model);
		} catch(RuntimeException r){
		    message = r.getMessage();
		}
	      	this.updateTextListeners();
	}
}
