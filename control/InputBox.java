package control;

import javax.swing.JTextField;

public class InputBox extends JTextField
{
    public String pullInput()
    {
	String toReturn = this.getText();
	this.clear();
	return toReturn;
    }

    public void clear()
    {
	this.setText("");
    }
}
