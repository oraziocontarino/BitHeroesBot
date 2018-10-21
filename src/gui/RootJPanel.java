package gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import be.BitHeroesBot;
import lib.CustomRobot;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;

public class RootJPanel extends ViewElement implements ActionListener {
	private Dimension nodeSize;
	private final static int PADDING = 5;
	private final static int COORDS_ELEMENT_HEIGHT = 20;
	private CustomRobot customRobot;
	private Point topLeftCorner;
	private Point bottomRightCorner;
	private JLabel[] coordsLabels;
	private JButton coordsButton;
	private JButton runBot;
	private JComboBox<String> missionList;
	public RootJPanel(Dimension nodeSize) throws AWTException {
		super(nodeSize);
		this.nodeWidth = (int) rootNodeSize.getWidth();
		this.nodeHeight = (int) (rootNodeSize.getHeight());
		this.nodeSize = new Dimension(nodeWidth, nodeHeight);
		this.setMinimumSize(nodeSize);
		this.setSize(nodeSize);
		this.setMaximumSize(nodeSize);
		this.setBackground(MaterialColors.INDIGO_50);
		this.setLocation(0,0);
		this.customRobot = CustomRobot.getInstance();
		setCoordsSection();
		setRunBotSection();
		setSelectMissionSection();
	}
	
	private void setSelectMissionSection() {
		Dimension selectMissionSize = new Dimension(nodeWidth-(PADDING*4), COORDS_ELEMENT_HEIGHT);
		List<String> missionlistLabels= new ArrayList<String>();
		
		for(int i = 1; i <= 4; i++) {
			for(int j = 1; j <= 4; j++) {
				missionlistLabels.add("Z"+i+"D"+j);
			}
		}

		missionList = new JComboBox<String>(missionlistLabels.toArray(new String[0]));
		missionList.setMinimumSize(selectMissionSize);
		missionList.setSize(selectMissionSize);
		missionList.setMaximumSize(selectMissionSize);
		missionList.setLocation(PADDING, (COORDS_ELEMENT_HEIGHT+PADDING)*5);
		this.add(missionList);
	}
	
	private void setRunBotSection() {
		Dimension runBotButtonSize = new Dimension(nodeWidth-(PADDING*4), COORDS_ELEMENT_HEIGHT);
		this.runBot = new JButton("Run Bot");
				
		runBot.setMinimumSize(runBotButtonSize);
		runBot.setSize(runBotButtonSize);
		runBot.setMaximumSize(runBotButtonSize);
		runBot.setLocation(PADDING, (COORDS_ELEMENT_HEIGHT+PADDING)*4);
		// on hover, button will change to a light gray
		MaterialUIMovement.add (coordsButton, MaterialColors.GRAY_50);
		runBot.setEnabled(false);
		this.add(runBot);
		runBot.addActionListener(this);
	}
	
	private void setCoordsSection() {		
		this.coordsLabels = new JLabel[2];
		setCoordsLabels();
		setRecalculateCoords();
	}

	private void setRecalculateCoords() {
		Dimension recalculateCoordsButtonSize = new Dimension(nodeWidth-(PADDING*4), COORDS_ELEMENT_HEIGHT);
		this.coordsButton = new JButton("Recalculate coords");		
				
		coordsButton.setMinimumSize(recalculateCoordsButtonSize);
		coordsButton.setSize(recalculateCoordsButtonSize);
		coordsButton.setMaximumSize(recalculateCoordsButtonSize);
		coordsButton.setLocation(PADDING, (COORDS_ELEMENT_HEIGHT+PADDING)*3);
		// on hover, button will change to a light gray
		MaterialUIMovement.add (coordsButton, MaterialColors.GRAY_50);
		this.add(coordsButton);
		coordsButton.addActionListener(this);
	}

	private void setCoordsLabels() {
		Dimension size = new Dimension(this.nodeWidth, COORDS_ELEMENT_HEIGHT);

		coordsLabels[0] = new JLabel("");	
		coordsLabels[0].setMinimumSize(size);
		coordsLabels[0].setSize(size);
		coordsLabels[0].setMaximumSize(size);
		coordsLabels[0].setLocation(PADDING, 0);

		coordsLabels[1] = new JLabel("");	
		coordsLabels[1].setMinimumSize(size);
		coordsLabels[1].setSize(size);
		coordsLabels[1].setMaximumSize(size);
		coordsLabels[1].setLocation(PADDING, PADDING+COORDS_ELEMENT_HEIGHT);

		this.add(coordsLabels[0]);
		this.add(coordsLabels[1]);
	}
	
	public Point[] detectGamePoistion(){
        System.out.println("Loading game position...");
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int width = (int) screenSize.getWidth();
    	int height = (int) screenSize.getHeight();
    	Color borderColor = new Color(0,0,0);
    	int tolerance = 0;
    	Point[] coords = new Point[2];
    	int patternSize = 10;
    	
        BufferedImage image = this.customRobot.createScreenCapture(new Rectangle(0, 0, width, height));
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
            	if(coords[0] == null){
            		coords[0] = calculateTopLeftMatrix(patternSize, x, y, borderColor, tolerance, image);
            	}else if(coords[1] == null){
            		coords[1] = calculateBottomRightMatrix(patternSize, x, y, borderColor, tolerance, image);
            	}
            }
        }
        /*
        if(coords[0] != null) {
        	System.out.println("Found topLeft! x:"+coords[0].x+"; y:"+coords[0].y);
        }
        if(coords[1] != null) {
        	System.out.println("Found bottomRight! x:"+coords[1].x+"; y:"+coords[1].y);
        }
        */
        System.out.println("Done!");
        return coords;
       }
    
    private Point calculateTopLeftMatrix(int patternSize, int x, int y, Color borderColor, int tolerance, BufferedImage image) {
    	Boolean[][] topLeftCorner = new Boolean[patternSize][patternSize];
    	Point coords = null;
    	try {
    		for(int i = 0; i < patternSize; i++) {
    			for(int j = 0; j < patternSize; j++) {
    				if(i == 0) {
    					//i=0, first row is black!
    					topLeftCorner[i][j] = this.customRobot.comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);
    				}else if(j == 0) {	
    					//i>0, first element is black!
						topLeftCorner[i][j] = this.customRobot.comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);	
    				}else {
    					//i>0, j>0, should not find black
						topLeftCorner[i][j] = !this.customRobot.comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);
					}
    			}
    		}
    		
    		boolean success = true;
    		for(int i = 0; i < patternSize; i++) {
    			for(int j = 0; j < patternSize; j++) {
    				success = success && topLeftCorner[i][j];
    			}
    		}
    		
    		if(success) {
    			coords = new Point(x,y);
    		}
    	}catch(Exception e) {
    		//Out of bound, ignore it
    	}
    	return coords;
    }
    
    private Point calculateBottomRightMatrix(int patternSize, int x, int y, Color borderColor, int tolerance, BufferedImage image) {
    	Boolean[][] bottomRightCorner = new Boolean[patternSize][patternSize];
    	Point coords = null;
    	int badPixel = 0;
    	try {
    		for(int i = 0; i < patternSize; i++) {
    			for(int j = 0; j < patternSize; j++) {
    				if(i == patternSize - 1) {
    					//i = patternSize-1, last row is black!
    					bottomRightCorner[i][j] = this.customRobot.comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);
    				}else if(j == patternSize - 1) {	
    					//i < patternSize, last element is black!
    					bottomRightCorner[i][j] = this.customRobot.comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);	
    				}else {
    					//i < patternSize, j < patternSize, should not find black
    					if(this.customRobot.comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance)) {
    						badPixel ++;
    					}
    					bottomRightCorner[i][j] = true;
    				}
    			}
    		}
    		
    		boolean success = true;
    		for(int i = 0; i < patternSize; i++) {
    			for(int j = 0; j < patternSize; j++) {
    				success = success && bottomRightCorner[i][j];
    			}
    		}
    		
    		if(success && badPixel < 4) {
    			coords = new Point(x+patternSize,y+patternSize);
    			//System.out.println("BAD PIXEL:"+badPixel);
    		}
    	}catch(Exception e) {
    		//Out of bound, ignore it
    	}
    	return coords;
    }

    public void recalculateCoords() {
    	try {
			coordsButton.setEnabled(false);
			runBot.setEnabled(false);
			coordsLabels[0].setText("Top-Left corner: (loading...)");
			coordsLabels[1].setText("Bottom-Right corner: (loading...)");
			
	    	this.paintImmediately(0, 0, nodeWidth, nodeHeight);
	        
	    	Point[] coords = detectGamePoistion();
	        topLeftCorner = coords[0];
	        bottomRightCorner = coords[1];
			coordsLabels[0].setText("Top-Left corner: ("+topLeftCorner.x+", "+topLeftCorner.y+")");
			coordsLabels[1].setText("Bottom-Right corner: ("+bottomRightCorner.x+", "+bottomRightCorner.y+")");	
			coordsButton.setEnabled(true);
			runBot.setEnabled(true);
    	}catch(Exception e) {
    		coordsLabels[0].setText("Top-Left corner: (error)");
    		coordsLabels[1].setText("Bottom-Right corner: (error)");
    		
        	this.paintImmediately(0, 0, nodeWidth, nodeHeight);

    		coordsButton.setEnabled(true);
    		runBot.setEnabled(false);
    	}
    }
    
    public void runBot() {
		try {
			coordsButton.setEnabled(false);
			runBot.setEnabled(false);
			this.paintImmediately(0, 0, nodeWidth, nodeHeight);
			
	    	Point[] coords = {topLeftCorner, bottomRightCorner};
	    	String missionKey = missionList.getSelectedItem().toString();
	    	BitHeroesBot.getInstance(coords).changeMission(missionKey);
			BitHeroesBot.getInstance(coords).run();
    		coordsButton.setEnabled(true);
    		runBot.setEnabled(false);
    		this.paintImmediately(0, 0, nodeWidth, nodeHeight);
		} catch (InterruptedException | AWTException e1) {
    		coordsLabels[0].setText("Top-Left corner: (error)");
    		coordsLabels[1].setText("Bottom-Right corner: (error)");
    		
        	this.paintImmediately(0, 0, nodeWidth, nodeHeight);

    		coordsButton.setEnabled(true);
    		runBot.setEnabled(false);
		}
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand().equals(coordsButton.getActionCommand()) && coordsButton.isEnabled()) {
    		recalculateCoords();
    	}else if(e.getActionCommand().equals(runBot.getActionCommand()) && runBot.isEnabled()) {
    		runBot();
    	}
    	
    }
}
