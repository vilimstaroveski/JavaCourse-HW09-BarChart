package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class represents a bar chart. Bar chart is chart in first quadrant 
 * with y values of each x value is represented with a column with height 
 * y.
 * @author Vilim Staroveški
 *
 */
public class BarChart {

	/**
	 * List of presented values.
	 */
	private List<XYValue> values;
	
	/**
	 * Description of x axis.
	 */
	private String xDes;
	
	/**
	 * Description of y axsis.
	 */
	private String yDes;
	
	/**
	 * The smallest y value that is shown on this chart.
	 */
	private int yMin;
	
	/**
	 * The greatest y value that is shown on this chart.
	 */
	private int yMax;
	
	/**
	 * Step for y values.
	 */
	private int step;
	
	/**
	 * Creates new {@link BarChart}.
	 * @param values List of presented values.
	 * @param xDescription Description of x axis.
	 * @param yDescription Description of y axsis.
	 * @param yMin The smallest y value that is shown on this chart.
	 * @param yMax The greatest y value that is shown on this chart.
	 * @param step Step for y values.
	 */
	public BarChart(List<XYValue> values, String xDescription,
			String yDescription, int yMin, int yMax, int step) {
		
		this.values = values;
		this.xDes = xDescription;
		this.yDes = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.step = step;
	}

	/**
	 * Returns List of presented values.
	 * @return List of presented values.
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Returns Description of x axis.
	 * @return Description of x axis.
	 */
	public String getxDes() {
		return xDes;
	}

	/**
	 * Returns  Description of y axsis.
	 * @return  Description of y axsis.
	 */
	public String getyDes() {
		return yDes;
	}

	/**
	 * Returns  The smallest y value that is shown on this chart.
	 * @return The smallest y value that is shown on this chart.
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Returns The greatest y value that is shown on this chart.
	 * @return The greatest y value that is shown on this chart.
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Returns Step for y values.
	 * @return Step for y values.
	 */
	public int getStep() {
		return step;
	}
}
