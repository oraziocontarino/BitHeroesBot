package global;

import java.awt.Color;

public class GameDimension {
	private double width;
	private double height;
	private Color color;
	
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
