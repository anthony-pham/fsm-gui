package view;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JMenuBar;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.Color;
import java.awt.Graphics;

import fsm.ViewableFSM;
import fsm.State;
import fsm.StepState;
import fsm.StepTransition;
import fsm.FSMListener;
import fsm.Simulator;
import graph_utilities.Indexable;

/**
 * FSMView is the view components of our model-view-controller FSM Editor
 */
public class FSMView extends JFrame implements FSMListener, TextListener
{
    private static final String DISPLAY_MSG = "FSM Editor";
    public static final int PIXELS_TO_FSM_UNITS = 40;

    //pixel constants
    public static final Dimension  CANVAS_SIZE = new Dimension(620, 620);
    private static final Dimension  PANEL_SIZE = new Dimension(200, 620);
    private static final Dimension  MIN_PANEL = new Dimension(100, 100);
    private static final Dimension WINDOW_SIZE = new Dimension(980, 720);
    private static final Dimension DISPLAY_SIZE = new Dimension(980, 620);
    public static final Dimension TEXT_SIZE = new Dimension(980,100);
 
    private static final double DISPLAY_HEIGHT =
	CANVAS_SIZE.getHeight()/(TEXT_SIZE.getHeight() + CANVAS_SIZE.getHeight());

    private JPanel inputPanel;
    private FSMCanvas canvas;
    private TextDisplay textDisplay;
    private ViewableFSM model;
    private String currentText;
    
    public FSMView()
    {
		super(DISPLAY_MSG);

		currentText = "";

		Font myFont = new Font("Cambria Math", Font.PLAIN, 12);
		this.setFont(myFont);
		
		inputPanel = new JPanel();
		textDisplay = new TextDisplay();
		//textDisplay.setBackground(Color.white);
		canvas = new FSMCanvas();
		canvas.setBackground(Color.white);

		JSplitPane displayPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, canvas);

		displayPane.setMinimumSize(MIN_PANEL);
		
		displayPane.setOneTouchExpandable(false);		
		
		canvas.setPreferredSize(CANVAS_SIZE);

		inputPanel.setPreferredSize(PANEL_SIZE);

		displayPane.setDividerLocation(-1);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, displayPane, textDisplay);
		
		splitPane.setMinimumSize(MIN_PANEL);
	
		
		splitPane.setOneTouchExpandable(false);
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(DISPLAY_HEIGHT);		
		
		displayPane.setPreferredSize(DISPLAY_SIZE);
		textDisplay.setPreferredSize(TEXT_SIZE);

		splitPane.setPreferredSize(WINDOW_SIZE);

		splitPane.setDividerLocation(-1);

		this.getContentPane().add(splitPane);//add splitpane to frame
    }

    /**
     * Adds components to the edit panel
     * @param components, iterable of components to add
     */
    public void addComponents(Iterable<? extends Component> components)
    {
		for(Component c : components)
	  	{
		    inputPanel.add(c);
		}
    }

    /**
     * Sets menu bar of window
     * @param toSet, menubar of window
     */
    public void setJMenuBar(JMenuBar toSet)
    {
    	this.inputPanel.add(toSet);
    	super.setJMenuBar(toSet);
    }
    
    /**
     *
     *@overwrite JFrame
     */
    public void repaint()
    {
		super.repaint();
		//this.canvas.clearCanvas();
    }

    /**
     * Reads update from model and display the model
     */
    public void update()
    {
        if(this.model != null)
		{
            Iterable<StateDisplay> states = this.makeDisplayStates();
    		Iterable<TransitionDisplay> transitions = this.makeTransitions(states);
    		canvas.setDisplayStates(states);
    		canvas.setDisplayTransitions(transitions);
        }
        else
        {
            canvas.clearCanvas();
        }
	canvas.repaint();
    }

    /**
     * Helper Method
     * Constructs viewable states from the model
     * @return iterable of states to display
     */
    private Iterable<StateDisplay> makeDisplayStates()
    {
		Set<StateDisplay> toReturn = new HashSet<StateDisplay>();
		Point entry = this.model.getEntryPos();
		if (entry != null)
		{
		    Collection<StepState> current = this.model.getCurrentState();
		    for (Map.Entry pair : this.model.getLocationMap().entrySet())
		    {
		    	Map.Entry<Point, StepState> stateLoc = (Map.Entry<Point, StepState>) pair;
				Point location = stateLoc.getKey();
				StepState state = stateLoc.getValue();
				StateDisplay toDisplay = new StateDisplay(state, location, entry.equals(location), current.contains(state));		
	       		toReturn.add(toDisplay);	
		    }
		}
		return toReturn;
    }


    private Iterable<TransitionDisplay> makeTransitions(Iterable<StateDisplay> states)
    {
		Set<TransitionDisplay> toReturn = new HashSet<TransitionDisplay>();
		for(StateDisplay from : states)
	    {
			for(StateDisplay to : states)
		    {

				StepState fState = from.getState();
				StepState tState = to.getState();

				Iterable<StepTransition> transitions = model.getTransitions(fState,tState);

				if(transitions.iterator().hasNext())
				{
					TransitionDisplay toDisplay = 
							new TransitionDisplay(transitions, 
								from.getPos(), 
								to.getPos());
					toReturn.add(toDisplay);
				}
		    }
	    }

		return toReturn;

    }

    /**
     * @return canvas of this view
     */
    public FSMCanvas getCanvas()
    {
	return this.canvas;
    }

    public ViewableFSM getFSM()
    {
    	return this.model;
    }

    public void setFSM(ViewableFSM newFSM)
    {
        if (this.model != null){
            this.model.removeListener(this);
	    this.model.removeTextListener(this);
        }
        if(newFSM != null){
            newFSM.addListener(this);
	    newFSM.addTextListener(this);
        }
        this.model = newFSM;
    }

    public ViewableFSM removeFSM()
    {
        ViewableFSM toReturn = this.model;
        if(model != null){
            this.model.removeListener(this);
        }
        this.model = null;
        return toReturn;
    }
    
    public void simulate()
    {
	textDisplay.run(this);
    }

    public String getInput()
    {
	return currentText;
    }

    public void passInput(String newInput)
    {
	currentText = newInput;
	textDisplay.addText(currentText);
    }

    public boolean hasCurrentSimulator()
    {
	return true;
    }

    public Collection<Simulator> getCurrentSimulators()
    {
	return Collections.singleton((Simulator) textDisplay);
    }

    public void addSimulator(Simulator s)
    {
	throw new UnsupportedOperationException();
    }

    public void removeSimulator(Simulator s)
    {
	throw new UnsupportedOperationException();
    }

    public void clearText()
    {
	currentText = "";
	textDisplay.resetText();
    }


    public void setCurrentSimulators(Collection<Simulator> newCurrent) throws UnsupportedOperationException
    {
	throw new UnsupportedOperationException();
    }

    /**
     * @param pixels to convert to FSMUnits
     * @return pixels of display as FSMUnits
     */
    public static Point convertToFSMUnits(Point pixels)
    {
    	Point converted =  new Point(
    						(int) pixels.getX()/FSMView.PIXELS_TO_FSM_UNITS, 
    						(int) pixels.getY()/FSMView.PIXELS_TO_FSM_UNITS);
		return converted;
    }

    /**
     * @param FSMUnits to convert to pixels
     * @return FSMUnits as pixels
     */
    public static Point convertFromFSMUnits(Point fsmUnits)
    {
    	Point converted =  new Point(
    						(int) fsmUnits.getX
						()*FSMView.PIXELS_TO_FSM_UNITS, 
    						(int) fsmUnits.getY()*FSMView.PIXELS_TO_FSM_UNITS);
		return converted;
    }
}
