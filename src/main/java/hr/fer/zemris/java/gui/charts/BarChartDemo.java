package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Program that shows {@link BarChart} defined by text file. Program takes 
 * one argument: path to file which defines a bar chart.
 * File must be in form:
 * Line1 : description for x axsis
 * Line2 : description for y axsis
 * Line3 : values for chart, where x and y value is separeted by ',' and 
 * 		   every pair of values is separeted with space from other pairs.
 * Line4 : minimum y that will be shown. (Positive number)
 * Line5 : maximum y that will be shown. (Positive number greater than minimum y)
 * Line6 : step for shown y values.
 * @author Vilim Staroveški
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 5151434034409506855L;
	
	/**
	 * Bar chart that will be drawn.
	 */
	private BarChart barChart;
	
	/**
	 * Path to bar chart file.
	 */
	private static String pathToFile;

	/**
	 * Creates new {@link BarChartDemo}
	 * @param barChart {@link BarChart} representation of bar chart from file.
	 */
	public BarChartDemo(BarChart barChart) {
		this.barChart = barChart;
		this.setLocation(150, 150);
		this.setSize(600, 400);
		this.setTitle("Chart viewer");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Initialises GUI of this program.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		
		BarChartComponent component = new BarChartComponent(barChart);
		getContentPane().add(component, BorderLayout.CENTER);
		getContentPane().setBackground(Color.WHITE);
		JLabel title = new JLabel("Source file: "+pathToFile, SwingConstants.CENTER);
		getContentPane().add(title, BorderLayout.NORTH);
		
	}

	/**
	 * Method called on program start.
	 * @param args command line arguments. Program expects one argument:
	 * path to file which defines a bar chart.
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.err.println("Wrong number of arguments!");
			System.exit(1);
		}
		pathToFile = args[0];
		
		BarChart barChart = barChartFromFile(pathToFile);
		
		SwingUtilities.invokeLater( () -> {
			JFrame demo = new BarChartDemo(barChart);
			demo.setVisible(true);
		});
	}

	/**
	 * Parser for file which defines a bar chart.
	 * @param pathToFile path to file which defines a bar chart.
	 * @return {@link BarChart} representation of file.
	 */
	private static BarChart barChartFromFile(String pathToFile) {
		
		String xDescription = null;
		String yDescription= null;
		int yMin = 0;
		int yMax = 0;
		int step = 0;
		List<XYValue> values = null;
		try (BufferedReader fileReader = new BufferedReader( 
											new InputStreamReader( 
												new BufferedInputStream( 
													new FileInputStream(
														pathToFile)),"UTF-8"))) {
			
			xDescription = fileReader.readLine();
			yDescription = fileReader.readLine();
			values = parseToListOfValues(fileReader.readLine());
			yMin = Integer.parseInt(fileReader.readLine());
			yMax = Integer.parseInt(fileReader.readLine());
			step = Integer.parseInt(fileReader.readLine());
			
		} catch(NumberFormatException e) {
			System.err.println("Invalid line in file! Cannot read values.");
			System.exit(1);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The Character Encoding is not supported for this file.");
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.err.println("File not found!");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("There was an unknown IO error occured! Exiting the program.");
			System.exit(1);
		}
		
		return new BarChart(values, xDescription, yDescription, yMin, yMax, step);
	}

	/**
	 * Parses line into a list of {@link XYValue}'s.
	 * @param fileLine line containing values.
	 * @return list list of {@link XYValue}'s.
	 * @throws NumberFormatException if there are unparsable parts in given line. 
	 * It can be that line contains letters, or float values that are not supported by this program.
	 */
	private static List<XYValue> parseToListOfValues(String fileLine) throws NumberFormatException {
		
		List<XYValue> values = new ArrayList<XYValue>();
		String[] points = fileLine.split(" ");
		for(String point : points) {
			
			String [] coordinates = point.split(",");
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			values.add(new XYValue(x, y));
		}
		
		return values;
	}
	
}
