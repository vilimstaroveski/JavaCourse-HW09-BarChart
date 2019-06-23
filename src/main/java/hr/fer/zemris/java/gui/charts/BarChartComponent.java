package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * Class represents a component that draws {@link BarChart}.
 * @author Vilim Staroveški
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -2290688286165650352L;

	/**
	 * Chart this component draws.
	 */
	private BarChart chart;
	
	/**
	 * Creates new {@link BarChartComponent}
	 * @param chart Chart this component draws.
	 */
	public BarChartComponent(BarChart chart) {

		this.chart = chart;
		Border current = this.getBorder();
		Border empty = new EmptyBorder(5, 5, 5, 5);
		if (current == null) {
		    this.setBorder(empty);
		}
		else {
		    this.setBorder(new CompoundBorder(empty, current));
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		FontMetrics fm = g.getFontMetrics();
		Insets insets = getInsets();
		Dimension size = getSize();
		g2d.setColor(Color.BLACK);

		//drawing first frame; contains x and y axis descriptions
		Rectangle frameForDrawing = new Rectangle(insets.left, insets.top, 
													size.width-insets.right-insets.left, 
													size.height-insets.top-insets.bottom);
		
//		g2d.drawLine(frameForDrawing.x, frameForDrawing.y, frameForDrawing.x + frameForDrawing.width, frameForDrawing.y);
		
		AffineTransform defaultAt = g2d.getTransform();
		g2d.setTransform(defaultAt);
		
		String xDescription = chart.getxDes();
		int lengthOfX = fm.stringWidth(xDescription);
		g2d.drawString(xDescription, frameForDrawing.x + frameForDrawing.width/2 - lengthOfX/2, frameForDrawing.y + frameForDrawing.height);
		
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g2d.setTransform(at);
		
		String yDescription = chart.getyDes();
		int lengthOfY = fm.stringWidth(yDescription);
		g2d.drawString(yDescription, frameForDrawing.y - frameForDrawing.height/2 - lengthOfY/2, fm.getHeight());
		g2d.setTransform(defaultAt);
		
		//drawing second frame, that is inside first frame; contains numbers for axis
		int insetsForSecondFrame = fm.getHeight();
		Rectangle secondFrame = new Rectangle(frameForDrawing.x + insetsForSecondFrame, frameForDrawing.y + insetsForSecondFrame, 
												frameForDrawing.width - 2*insetsForSecondFrame, 
												frameForDrawing.height - 2*insetsForSecondFrame);
		
//		g2d.drawLine(secondFrame.x, secondFrame.y, secondFrame.width + secondFrame.x, secondFrame.y);
//		g2d.drawLine(secondFrame.x, secondFrame.y, secondFrame.x, secondFrame.height + secondFrame.y);
//		g2d.drawLine(secondFrame.x, secondFrame.height + secondFrame.y, secondFrame.x + secondFrame.width, secondFrame.height + secondFrame.y);
		
		
		int widthOfYMax = fm.stringWidth(String.valueOf(chart.getyMax()));
		int widthOfYMin = fm.stringWidth(String.valueOf(chart.getyMin()));
		int maxWidth = Math.max(widthOfYMax, widthOfYMin);
		int yValues = (chart.getyMax()-chart.getyMin()) / chart.getStep() + 1;
		//drawing third frame
		int insetForThirdFrameBottomTop = fm.getHeight();
		int insetsForThirdFrameLR = maxWidth + 5;
		Rectangle thirdFrame = new Rectangle(secondFrame.x + insetsForThirdFrameLR, 
												secondFrame.y + insetForThirdFrameBottomTop, 
												secondFrame.width - 2*insetsForThirdFrameLR, 
												secondFrame.height - 2*insetForThirdFrameBottomTop);
		
//		g2d.drawLine(thirdFrame.x, thirdFrame.y, thirdFrame.width + thirdFrame.x, thirdFrame.y);
//		g2d.drawLine(thirdFrame.x, thirdFrame.y, thirdFrame.x, thirdFrame.height + thirdFrame.y);
//		g2d.drawLine(thirdFrame.x, thirdFrame.height + thirdFrame.y, thirdFrame.x + thirdFrame.width, thirdFrame.height + thirdFrame.y);
		
		 g2d.setFont(g2d.getFont().deriveFont(Font.BOLD));
		
		int spaceBetweenVertical = (thirdFrame.height + fm.getHeight()) / yValues - fm.getHeight();
		int[] valuesOnY = new int[yValues];
		for(int i = 0; i < yValues; i++) {
			valuesOnY[i] = i*chart.getStep();
		}
		for(int i = 0; i < yValues; i++) {
			g2d.drawString(String.valueOf(valuesOnY[i]), 
					secondFrame.x + (maxWidth - fm.stringWidth(String.valueOf(valuesOnY[i]))), 
					thirdFrame.y + thirdFrame.height + fm.getHeight()/2 - i*(fm.getHeight() + spaceBetweenVertical));
		}
		
		int[] valuesOnX = new int[chart.getValues().size()];
		for(int i = 0; i < chart.getValues().size(); i++) {
			valuesOnX[i] = chart.getValues().get(i).getX();
		}
		Arrays.sort(valuesOnX);
		int availableSpaceForXValue = thirdFrame.width / valuesOnX.length;
		for(int i = 0; i < valuesOnX.length; i++) {
			int offset = (availableSpaceForXValue - fm.stringWidth(String.valueOf(valuesOnX[i]))) /2;
			g2d.drawString(String.valueOf(valuesOnX[i]), 
					thirdFrame.x + i*availableSpaceForXValue + offset, 
					thirdFrame.y+thirdFrame.height + fm.getHeight());
		}
		
		//setting the actual chart picture
		//frame is 'thirdFrame'
		for(int i = 0; i < yValues ; i++) {
			if(i == 0) {
				g2d.setColor(Color.GRAY);
			}
			else {
				g2d.setColor(Color.YELLOW);
			}
			g2d.drawLine(thirdFrame.x - 3, 
						 thirdFrame.y + thirdFrame.height - i*(fm.getHeight() + spaceBetweenVertical) + 3,
						 thirdFrame.x + thirdFrame.width, 
						 thirdFrame.y + thirdFrame.height - i*(fm.getHeight() + spaceBetweenVertical) + 3);
		}
		for(int i = 0; i <= valuesOnX.length; i++) {
			if(i == 0) {
				g2d.setColor(Color.GRAY);
			}
			else {
				g2d.setColor(Color.YELLOW);
			}
			g2d.drawLine(thirdFrame.x + i*availableSpaceForXValue, 
						 thirdFrame.y + thirdFrame.height + 3, 
						 thirdFrame.x + i*availableSpaceForXValue, 
						 thirdFrame.y);
		}
		g2d.setColor(Color.ORANGE);
		int i = 0;
		XYValue values[] = new XYValue[chart.getValues().size()];
		for(XYValue value : chart.getValues()) {
			int heightOfColumn = (int) Math.round((double)value.getY() / chart.getStep() * (fm.getHeight() + spaceBetweenVertical));
			Rectangle rect = new Rectangle(thirdFrame.x + i*availableSpaceForXValue, 
											thirdFrame.y + thirdFrame.height - heightOfColumn + 2, 
											availableSpaceForXValue - 2, 
											heightOfColumn);
			g2d.draw(rect);
			g2d.fill(rect);
			values[i] = value;
			i++;
		}
		
		g2d.setColor(Color.LIGHT_GRAY);
		for(i = 0; i < values.length; i++) {
			boolean hasShadow = false;
			int diference = 0;
			if(i == values.length - 1) {
				hasShadow = true;
				diference = values[i].getY();
			}
			else if(values[i].getY() > values[i+1].getY()) {
				hasShadow = true;
				diference = values[i].getY() - values[i+1].getY();
			}
			
			if(hasShadow) {
				
				int heightOfColumn = (int) Math.round((double)values[i].getY() / chart.getStep() * (fm.getHeight() + spaceBetweenVertical));
				int diferenceHeight = (int) Math.round((double)diference / chart.getStep() * (fm.getHeight() + spaceBetweenVertical));
				Rectangle rect = new Rectangle(thirdFrame.x + (i+1)*availableSpaceForXValue, 
												thirdFrame.y + thirdFrame.height - heightOfColumn + 5, 
												2, 
												diferenceHeight - 5);
				g2d.draw(rect);
				g2d.fill(rect);
			}
		}
	}
}
