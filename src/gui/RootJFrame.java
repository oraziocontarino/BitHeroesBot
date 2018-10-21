package gui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mdlaf.MaterialLookAndFeel;
import mdlaf.utils.MaterialColors;

public class RootJFrame extends JFrame{
	private final static int NODE_WIDTH = 400;
	private final static int NODE_HEIGHT = 500;
	private static JFrame frame;
	private static Dimension nodeSize;
	private static RootJPanel rootJPanel;
	
	public RootJFrame() throws AWTException {
		super("BitHeroes Bot");
		try {
			UIManager.setLookAndFeel (new MaterialLookAndFeel());
		}
		catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace ();
		}
		
		nodeSize = new Dimension(NODE_WIDTH, NODE_HEIGHT);
		rootJPanel = new RootJPanel(nodeSize);
		this.setResizable(false);
		this.setMinimumSize(nodeSize);
		this.setSize(nodeSize);
		this.setMaximumSize(nodeSize);
		this.setBackground(MaterialColors.BLUE_GRAY_900);
		this.setLocation(0,0);

		this.add(rootJPanel);
		this.setLocationRelativeTo(null);  // *** this will center your app ***
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		rootJPanel.recalculateCoords();
	}
	


}
