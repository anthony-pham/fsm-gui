package control;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import fsm.ViewableFSM;
import fsm.FSMInteractor;
import view.TextListener;

public abstract class EditFSMCommand implements ActionListener, FSMInteractor
{
    
    protected ViewableFSM model;
    protected EditMouseControl control;
    protected LinkedList<TextListener> textListeners;
    protected String message;

    protected EditFSMCommand(ViewableFSM model, EditMouseControl control)
    {
    	this.model = model;
    	this.control = control;
	textListeners = new LinkedList<TextListener>();
	this.message = "";
    }

    public abstract void editModel(Point position, String input);

	
    public void addTextListener(TextListener tl)
    {
	this.textListeners.add(tl);
    }

    public void removeTextListener(TextListener tl)
    {
	this.textListeners.remove(tl);
    }

    public void updateTextListeners()
    {
	for(TextListener tl : textListeners)
	{
	    tl.passInput(this.message);
	    tl.simulate();
	}
    }

    public void clearTextListeners()
    {
	for(TextListener tl : textListeners)
	{
	    tl.clearText();
	}
    }
    

    public ViewableFSM getFSM()
    {
        return model;
    }

    public void setFSM(ViewableFSM newFSM)
    {
        model = newFSM;
    }

    public ViewableFSM removeFSM()
    {
        ViewableFSM toReturn = model;
        model = null;
        return toReturn;
    }

    public void actionPerformed(ActionEvent e)
    {
	this.updateTextListeners();
    	this.control.setEditCommand(this);
    }
}
