package input_handling;

import java.util.LinkedList;

import fsm.Environment;
import view.TextListener;

public class StringManager implements java.io.Serializable
{
    private String[] input;
    private int currentIndex;
    private Environment env;
    private LinkedList<TextListener> textListeners;
    
    public StringManager(Environment e)
    {
	this.env = e;
	input = null;
	currentIndex = 0;
	textListeners = new LinkedList<TextListener>();
    }

    public void updateTextListeners()
    {
	for (TextListener tl: textListeners)
	    {
		tl.passInput(this.getDisplayString());
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

    private String getDisplayString()
    {
	String toReturn = "";
	if(input != null)
	    {
		for(int i = 1; i < input.length; i++)
		    {
			if(i == currentIndex)
			    {
				toReturn += "*" + input[i] + "*,";
			    }
			else
			    {
				toReturn += input[i] + ",";
			    }
		    }
		if(!toReturn.isEmpty())
		    {
			toReturn = toReturn.substring(0,toReturn.length()-1);
		    }
	    }
	else
	    {
		toReturn += "NO INPUT!!!!!!!!!!!!!!!!  8(";
	    }
	return toReturn;
    }

    public void reset()
    {
	currentIndex = 0;
	this.updateTextListeners();
    }

    public void takeString(String s)
    {
	input = StringHandler.takeString(s);
	this.updateTextListeners();
    }

    public void nextInput()
    {
	if (input != null)
	    {
		if (currentIndex < input.length)
		    {
			env.passInput(input[currentIndex++]);
		    }
		else
		    {
			env.passInput(null);
			this.reset();
		    }
	    }
	this.updateTextListeners();
    }

    public void clear()
    {
	input = null;
	currentIndex = 0;
	this.clearTextListeners();
	this.simulateListeners();
    }

    public void simulateListeners()
    {
	for (TextListener tl: textListeners)
	    {
		tl.simulate();
	    }
    }

    public Environment getEnv()
    {
    	return this.env;
    }

    public void setEnv(Environment newEnv)
    {
    	this.env = newEnv;
    }

    public Environment removeEnv()
    {
        Environment toReturn = this.env;
        this.env = null;
        return toReturn;
    }
}
