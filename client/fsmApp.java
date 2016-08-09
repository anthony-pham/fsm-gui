package client;

import java.util.Collections;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JButton;

import fsm.ViewableFSM;
import view.FSMView;
import control.EditMouseControl;
import control.InputBox;

//************************************
import control.*;
import java.awt.Point;
//************************************

import input_handling.FSMStringPasser;
import control.SimulateFSMAction;

public class fsmApp
{
    public static void main(String [] args)
    {
	//	JFrame frame = new JFrame(DISPLAY_MSG);
	//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits app when closing window
	
	ViewableFSM model = new ViewableFSM();
	ModelManager manager = new ModelManager();

	FSMStringPasser passer = new FSMStringPasser(model);
	manager.addInteractor(passer);

	InputBox fileBox = new InputBox();
	fileBox.setColumns(15);
	InputBox editBox = new InputBox();
	editBox.setColumns(15);
	InputBox simBox = new InputBox();
	simBox.setColumns(15);

	FSMView view = new FSMView();
	manager.addInteractor(view);
	passer.addTextListener(view);

	view.addComponents(Collections.singleton(fileBox));

	EditMouseControl mouseControl = new EditMouseControl(view.getCanvas(), editBox);

	ComponentBuilder b = new ComponentBuilder(model, view, mouseControl, passer, simBox);
	
	b.addInteractorsTo(manager);

	//use the constructed FSM for all interactors
	manager.setFSM(model);

	//set up window
	Iterable<JButton> editButtons = b.makeEdit();
	view.addComponents(editButtons);

	view.addComponents(Collections.singleton(editBox));
	view.setJMenuBar(b.makeMenu(manager, fileBox, view));

	Iterable<JButton> simButtons = b.makeSim();
	view.addComponents(simButtons);
	view.addComponents(Collections.singleton(simBox));

	view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	view.pack();
	view.setVisible(true);

	//testing simulate
	/*
	SimulateFSMAction step = new StepAction(model, passer);
	SimulateFSMAction clear = new ClearAction(model, passer);
	SimulateFSMAction reset = new ResetAction(model, passer);

	Point A = new Point(2,2);
	Point B = new Point(2,4);
	Point C = new Point(4,2);
	
	model.addState(A, "A");
	model.addState(B, "B");
	model.addState(C, "C");

	model.addTransition(A,B, "");
	model.addTransition(B,C, "");
	model.addTransition(C,A, "");

	model.setReject(B);
	
	step.actionPerformed(null);
	step.actionPerformed(null);
	step.actionPerformed(null);
	step.actionPerformed(null);
	step.actionPerformed(null);
	*/
    }    
}
