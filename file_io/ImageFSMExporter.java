package file_io;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import fsm.ViewableFSM;
import view.FSMView;
import view.FSMCanvas;

public class ImageFSMExporter extends FSMExporter
{
    FSMCanvas canvas;
    
    public ImageFSMExporter(FSMSaver s, FSMView view)
	{
		super(s);
		this.canvas = view.getCanvas();
	}

    public void exportFSM(ViewableFSM model, String filename)
	{
	    BufferedImage b = new BufferedImage((int) FSMView.CANVAS_SIZE.getWidth(), (int) FSMView.CANVAS_SIZE.getHeight(), BufferedImage.TYPE_INT_ARGB);

	    Graphics g = b.createGraphics();
	    canvas.paint(g);
	    g.dispose();
	    try{
		ImageIO.write(b,"png", new File(filename));
	    } catch (IOException r){
	    }
	}

	public String toString()
	{
		return "image (.png)";
	}
}
