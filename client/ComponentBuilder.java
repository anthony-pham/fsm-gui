package client;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import fsm.ViewableFSM;
import control.*;
import view.FSMView;
import input_handling.FSMStringPasser;

import file_io.*;


/**
 *  Makes the buttons and the menu buttons
 */
public class ComponentBuilder
{
	private static int numEdits = 10;
	private static int numSimulates = 4;
	private EditFSMCommand[] edits;
	private SimulateFSMAction[] simulates;

	/**
	 * Generates listener commands for all edit and simulate options. MakeEdit and MakeSim will then make buttons for
	 * these shared commands.
	 */
    public ComponentBuilder(ViewableFSM model, FSMView view, EditMouseControl mouseControl, FSMStringPasser inputManager, InputBox simBox)
	{
		edits = new EditFSMCommand[numEdits];
		edits[0] = new AddStateCommand(model, mouseControl);
		edits[1] = new AddTransitionCommand(model, mouseControl);
		edits[5] = new MakeEntryCommand(model, mouseControl);
		edits[7] = new MakeAcceptCommand(model, mouseControl);
		edits[8] = new MakeNeutralCommand(model, mouseControl);
		edits[9] = new MakeRejectCommand(model, mouseControl);
		edits[2] = new MoveStateCommand(model, mouseControl);
		edits[3] = new RemoveStateCommand(model, mouseControl);
		edits[6] = new RemoveEntryCommand(model, mouseControl);
		edits[4] = new RemoveTransitionCommand(model, mouseControl);

		simulates = new SimulateFSMAction[numSimulates];
		simulates[0] = new StepAction(model, inputManager);
		simulates[3] = new ClearAction(model, inputManager);
		simulates[2] = new ResetAction(model, inputManager);
		simulates[1] = new InputAction(model, inputManager, simBox);

		for(int i = 0; i < edits.length; i++)
		{
			edits[i].addTextListener(view);
		}
	}

	public void addInteractorsTo(ModelManager m)
	{
		for(int i = 0; i < numEdits; i++)
		{
			m.addInteractor(edits[i]);
		}
		for(int i = 0; i < simulates.length; i++)
		{
			m.addInteractor(simulates[i]);
		}
	}

    /**
     * makeEdit makes an iterable of edit buttons, and adds the actionlistener for each button 
     */
    public Iterable<JButton> makeEdit()
    {
		ArrayList<JButton> toReturn = new ArrayList<JButton>();
		JButton toAdd;

		for(int i = 0; i < edits.length; i++)
		{
			toAdd = new JButton(edits[i].toString());
			toAdd.addActionListener(edits[i]);
			toReturn.add(toAdd);	  
		}

		return toReturn;
    }

    /**
     * makeSim makes an iterable of simulate buttons, and adds the actionListener to each button
     */
    public Iterable<JButton> makeSim()
    {
		ArrayList<JButton> toReturn = new ArrayList<JButton>();
		JButton toAdd;

		for(int i = 0; i < simulates.length; i++)
		{
			toAdd = new JButton(simulates[i].toString());
			toAdd.addActionListener(simulates[i]);
			toReturn.add(toAdd);	  
		}

		return toReturn;
    }

 	/**
     * makeMenu makes the menu with all the menu buttons that has the same actionLisnteners are the buttons.
     * Also makes file handling listeners and menu items.  
     */
    public JMenuBar makeMenu(ModelManager m, InputBox fileBox, FSMView view)
   

   {
   		JMenuBar toReturn = new JMenuBar();
   		JMenu file = new JMenu("File");
		toReturn.add(file);

		FSMSaver saver = new FSMSaver(m, fileBox);
		FSMLoader loader = new FSMLoader(m, fileBox);
		saver.addTextListener(view);
		loader.addTextListener(view);


		JMenu exportMenu = new JMenu("Export as");
		SerializableFSMExporter serExport = new SerializableFSMExporter(saver);

		JMenuItem exportSerItem = new JMenuItem(serExport.toString());
		exportSerItem.addActionListener(serExport);
		exportMenu.add(exportSerItem);

		LatexFSMExporter texExport = new LatexFSMExporter(saver);
		JMenuItem exportTexItem = new JMenuItem(texExport.toString());
		exportTexItem.addActionListener(texExport);
		exportMenu.add(exportTexItem);

		ImageFSMExporter imgExport = new ImageFSMExporter(saver, view);
		JMenuItem exportImgItem = new JMenuItem(imgExport.toString());
		exportImgItem.addActionListener(imgExport);
		exportMenu.add(exportImgItem);


		JMenu importMenu = new JMenu("Import from");
		SerializableFSMImporter serImport = new SerializableFSMImporter(loader);
		
		JMenuItem importSerItem = new JMenuItem(serImport.toString());
		importSerItem.addActionListener(serImport);
		importMenu.add(importSerItem);


		LatexFSMImporter texImport = new LatexFSMImporter(loader);
		JMenuItem importTexItem = new JMenuItem(texImport.toString());
		importTexItem.addActionListener(texImport);
		importMenu.add(importTexItem);


		file.add(importMenu);
		file.add(exportMenu);

		JMenu editMenu = new JMenu("Edit");
		toReturn.add(editMenu);

		for(int i = 0; i < edits.length; i++)
		{
			JMenuItem toAdd = new JMenuItem(edits[i].toString());
			toAdd.addActionListener(edits[i]);
			editMenu.add(toAdd);
		}

		JMenu simMenu = new JMenu("Simulate");
		toReturn.add(simMenu);

		for(int i = 0; i < simulates.length; i++)
		{
			JMenuItem toAdd = new JMenuItem(simulates[i].toString());
			toAdd.addActionListener(simulates[i]);
			simMenu.add(toAdd);
		}

		return toReturn;
   }

}
