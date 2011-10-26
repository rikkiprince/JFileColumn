import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;

public class JFileColumn extends JPanel implements MouseListener, ListSelectionListener, ListCellRenderer
{
	private File rootDir;
	private File currentSelection;
	
	private Container contentPane;
	private LayoutManager layout;

	public JFileColumn(String root)
	{
		this(new File(root));
	}
	
	public JFileColumn(File dir)
	{
		if(dir.isDirectory())
			this.rootDir = dir;
		else
			this.rootDir = dir.getParentFile();
		
		this.contentPane = this;
		layout = new SpringLayout();
		//layout = new FlowLayout();
		
		//File[] files = this.rootDir.listFiles();
		//JList list = new JList(files);
		//list.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		//list.addMouseListener(this);
		//list.addListSelectionListener(this);
		JList list = initialiseJList(this.rootDir);
		contentPane.add(list);
		//this.setLayout(new BorderLayout());
		
		((SpringLayout)layout).putConstraint(SpringLayout.EAST, contentPane, 5, SpringLayout.EAST, list);
		((SpringLayout)layout).putConstraint(SpringLayout.WEST, contentPane, 5, SpringLayout.WEST, list);
		((SpringLayout)layout).putConstraint(SpringLayout.NORTH, contentPane, 5, SpringLayout.NORTH, list);
		((SpringLayout)layout).putConstraint(SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, list);

		this.setLayout(layout);
	}

	
	public void mouseClicked(MouseEvent e)
	{
		//System.out.println(e);
	}
	public void mouseEntered(MouseEvent arg0)
	{ }
	public void mouseExited(MouseEvent arg0)
	{ }
	public void mousePressed(MouseEvent arg0)
	{ }
	public void mouseReleased(MouseEvent arg0)
	{ }

	
	JList lastList = null;
	File lastFile = null;

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		Object source = e.getSource();
		
		if(source instanceof javax.swing.JList)
		{
			JList list = (JList)source;
			Object value = list.getSelectedValue();
			Container parentContainer = list.getParent();
			
			if(value instanceof java.io.File)
			{
				File selected = (File)value;
				
				this.currentSelection = selected;
				
				if(list != lastList || selected != lastFile)
				{
					if(selected.isDirectory())
					{
						// Need to figure out way of replacing this JList with a JSplitPane
						// containing current JList on left and new JList on right.
						JList sub = initialiseJList(selected);
						if(parentContainer instanceof JSplitPane)
						{
							//System.out.println("Inside of another pane");
							if(list == ((JSplitPane)parentContainer).getLeftComponent())
							{
								System.out.println("in left of jsplitpane");
								JSplitPane outer = (JSplitPane)parentContainer;
								//JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, sub);
								//mainSplit.setBorder(null);
								outer.setRightComponent(sub);
							}
							else if(list == ((JSplitPane)parentContainer).getRightComponent())
							{
								System.out.println("in right of jsplitpane");
								JSplitPane outer = (JSplitPane)parentContainer;
								JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, sub);
								mainSplit.setBorder(null);
								outer.setRightComponent(mainSplit);
							}
							else
							{
								System.out.println("whoops, not in nothing");
							}
							
							//if(parentContainer.getParent() instanceof JSplitPane)
							/*{
								JSplitPane outer = (JSplitPane)parentContainer;
								JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, sub);
								mainSplit.setBorder(null);
								outer.setRightComponent(mainSplit);
							}*/
						}
						else
						{
							System.out.println("Top level");
							JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, sub);
							mainSplit.setBorder(null);
							parentContainer.remove(list);
							parentContainer.add(mainSplit);
						}
						//c.validate();
						this.validate();
					}
					
					lastFile = selected;
				}
				lastList = list;
			}
		}
	}
	
	private JList initialiseJList(File dir)
	{
		File[] files = dir.listFiles();
		JList list = new JList(files);
		list.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		list.addListSelectionListener(this);
		
		list.setCellRenderer(this);
		
		return list;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean callHasFocus)
	{
		JLabel cell = new JLabel();
		cell.setOpaque(true);
		
		if(value instanceof java.io.File)
		{
			File f = (File)value;
			cell.setText(f.getName());
		}
		else
			cell.setText(value.toString());
		
		Color background;
		Color foreground;
		
		// check if this cell represents the current DnD drop location
		JList.DropLocation dropLocation = list.getDropLocation();
		if (dropLocation != null
				&& !dropLocation.isInsert()
				&& dropLocation.getIndex() == index)	// check if this cell is selected
		{

			background = Color.BLUE;
			foreground = Color.WHITE;
		}
		else if (isSelected)
		{
			/*if(list == this.lastList)
			{
				background = Color.GRAY;
				foreground = Color.BLACK;
			}
			else*/
			{
				background = Color.BLUE;
				foreground = Color.WHITE;
			}
		}
		else	// unselected, and not the DnD drop location
		{
			background = Color.WHITE;
			foreground = Color.BLACK;
		}
		
		
		cell.setBackground(background);
		cell.setForeground(foreground);
		
		return cell;
	}
}
