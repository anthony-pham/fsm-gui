package control;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;

import view.FSMCanvas;

public class EditMouseControl implements MouseListener
{
    EditFSMCommand editCommand;
    InputBox editBox;
    boolean canAct;

	public EditMouseControl(FSMCanvas canvas, InputBox box)
	{
		this.editCommand = null;
		this.editBox = box;
		this.editBox.clear();
		this.canAct = false;

		canvas.addMouseListener(this);
	}

	public void setEditCommand(EditFSMCommand c)
	{
		this.editCommand = c;
	}

	public void mouseClicked(MouseEvent e)
	{
		if(this.canAct && this.editCommand != null)
		{
			Point mousePos = e.getPoint();
			String input = this.editBox.pullInput();
			this.editCommand.editModel(mousePos, input);
		}
	}

	public void mouseEntered(MouseEvent e)
	{
	    this.canAct = true;
	}

    public void mouseExited(MouseEvent e)
    {
		this.canAct = false;
    }

    public void mouseReleased(MouseEvent e){}
    
    public void mousePressed(MouseEvent e){}
}
