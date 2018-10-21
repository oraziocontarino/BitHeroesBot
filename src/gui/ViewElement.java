package gui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class ViewElement extends JPanel {
	protected int nodeWidth;
	protected int nodeHeight;
	protected Dimension rootNodeSize;
	protected Dimension nodeSize;
	
	public ViewElement(Dimension rootNodeSize) {
		super();
		this.rootNodeSize = rootNodeSize;
		this.setLayout(null);
	}

}
