package hr.fer.zemris.java.gui.charts;

/**
 * Class represents x, y pair where y is value in x.
 * @author Vilim Staroveški
 *
 */
public class XYValue {

	/**
	 * X coordinate.
	 */
	private int x;
	
	/**
	 * Y coordinate. Value in x.
	 */
	private int y;
	
	/**
	 * Creates new {@link XYValue}.
	 * @param x X coordinate.
	 * @param y Y coordinate. Value in x.
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	/**
	 * Returns X coordinate.
	 * @return X coordinate.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Returns Y coordinate. Value in x.
	 * @return Y coordinate. Value in x.
	 */
	public int getY() {
		return y;
	}
}
