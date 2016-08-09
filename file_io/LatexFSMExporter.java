package file_io;

import fsm.ViewableFSM;
import fsm.StepState;
import fsm.StepTransition;
import java.util.Map;
import java.io.*;
import java.awt.Point;

public class LatexFSMExporter extends FSMExporter
{

	private static final String RADIUS = ".5";
	private static final String BEGIN = 
	"\\documentclass{article}\n\\usepackage{tikz}\n\t\\usetikzlibrary{arrows}\n\\begin{document}\n\n\\begin{center}\n\t\\begin{tikzpicture}[scale="+RADIUS+", state/.style={fill=white!20}, trans/.style={thick,->}]\n\n";

	private static final String END = 
	"\t\\end{tikzpicture}\n\\end{center}\n\\end{document}";
	public LatexFSMExporter(FSMSaver s)
	{
		super(s);
	}
	private String makePair(Point p)
	{
		return "("+p.getX()+","+p.getY()+")";
	}

	private Point selfLoopShift(Point center)
	{
		return new Point((int) center.getX()+1, (int) center.getY()+1);
	}

	private String makeDocument(ViewableFSM model)
	{
		String toReturn = BEGIN;
		Map<StepState, Point> locations = model.getStateMap();
		for(StepState s : locations.keySet())
		{
			Point loc = locations.get(s);
			String name = s.getName();
			if(name.isEmpty())
			{
				toReturn += "\t\\draw[state] " + makePair(loc) + " circle (1);\n";
			}
			else
			{
				toReturn += "\t\\draw[state] " + makePair(loc) + " circle (1) node[below] {"+ name +"};\n";
			}
		}

		for(StepTransition t : model.getTransitions())
		{
			String name = t.getName();
			StepState source = t.getSource();
			StepState sink = t.getSink();
			if(source.equals(sink))
			{
				if(name.isEmpty())
				{
					toReturn += "\t\\draw[trans] "+makePair(selfLoopShift(locations.get(t.getSource())))+" arc[x radius=1, y radius = 1, start angle = 0, end angle = 360];\n";
				}
				else
				{
					toReturn += "\t\\draw[trans] "+makePair(selfLoopShift(locations.get(t.getSource())))+" arc[x radius=1, y radius = 1, start angle = 0, end angle = 360]"+" node[above, right] {"+ name +"};\n";
				}
			}
			else
			{
				if(name.isEmpty())
				{
					toReturn += "\t\\draw[trans] "+makePair(locations.get(source))+" -- "+makePair(locations.get(source))+";\n";
				}
				{
					toReturn += "\t\\draw[trans] "+makePair(locations.get(source))+" -- "+makePair(locations.get(sink))+" node[above, right] {"+ name +"};\n";
				}
			}
		}
		return toReturn + END;
	}

	public void exportFSM(ViewableFSM model, String fileName)
	{
		BufferedWriter writer = null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
	          			new FileOutputStream(fileName)));
		    writer.write(makeDocument(model));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
		    try {
		    	writer.close();
		    } catch (Exception ex){
		    	throw new RuntimeException(ex);
		    }
		}
	}

	public String toString()
	{
		return "LaTeX (.tex)";
	}
}