package global;

public class GameDimension {
	private double width;
	private double height;
	
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
	
}
