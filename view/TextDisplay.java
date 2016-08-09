package view;

import fsm.Simulator;
import fsm.Environment;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.FontMetrics;

/**
 * TextDisplay shows text to the user that includes instructions for using our GUI and info
 * about the simulation of the current FSM
 */
public class TextDisplay extends JPanel implements Simulator
{
    
    String text;

    /**
     * Constructor
     */
    public TextDisplay()
    {
    	text = "";
    }


    public void run(Environment e)
    {
	this.repaint();
    }

    public void addText(String newText)
    {
	this.text = newText;
    }

    public void resetText()
    {
	this.text = "";
    }

    
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
	
	FontMetrics f = g.getFontMetrics();
	int textHeight = f.getHeight();
	
	int w = (int) FSMView.TEXT_SIZE.getWidth();
	
	g.drawString(text, (w-f.stringWidth(text))/2, textHeight);
	
	this.resetText();
    }
}
