import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import sun.tools.tree.ThisExpression;


public class CodeMarkAssist extends JFrame implements MouseListener, MouseMotionListener, MouseWheelListener
{
	Container contentPane;
	
	File rootDir;
	
	JSplitPane mainSplit;
	JSplitPane sideSplit;
	
	// settings
	private File lastDirectory;
	private boolean askForDirectoryAtStartup = false;

	private SpringLayout layout;
	
	public CodeMarkAssist() throws Exception
	{
		loadSettings();

		if(this.askForDirectoryAtStartup)
		{
			JFileChooser chooser = new JFileChooser(this.lastDirectory);
			//chooser.setCurrentDirectory(new File("."));
			chooser.setDialogTitle("Select root directory of submissions...");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				rootDir = chooser.getSelectedFile();
				System.out.println(rootDir);
			}
			else
				throw new Exception("You must select a directory.");
		}
		else
		{
			this.rootDir = this.lastDirectory;
		}
		
		//layout = new SpringLayout();
		contentPane = this.getContentPane();
		//contentPane.setLayout(layout);
		
		JFileColumn jfc = new JFileColumn(this.rootDir);
		JPanel jp = new JPanel();
		
		mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jfc, jp);
		contentPane.add(mainSplit);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		
		//contentPane.add(new CustomPanel());

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 400);
		//this.pack();
		this.setVisible(true);
	}

	private void loadSettings()
	{
		// TODO Actually write this to load from a file
		//this.lastDirectory = new File("/Users/rprince/Desktop/Rikki-CW1");
		this.lastDirectory = new File("/");
	}

	// MOUSE LISTENER
	public void mouseClicked(MouseEvent e)
	{

	}

	public void mouseEntered(MouseEvent e)
	{

	}

	public void mouseExited(MouseEvent e)
	{

	}

	public void mousePressed(MouseEvent e)
	{

	}

	public void mouseReleased(MouseEvent e)
	{

	}

	// MOUSE MOVED LISTENER
	public void mouseDragged(MouseEvent e)
	{

	}

	public void mouseMoved(MouseEvent e)
	{

	}

	// MOUSE WHEEL LISTENER
	public void mouseWheelMoved(MouseWheelEvent e)
	{

	}

	public static void main(String[] args) throws Exception
	{
		new CodeMarkAssist();
	}
}
