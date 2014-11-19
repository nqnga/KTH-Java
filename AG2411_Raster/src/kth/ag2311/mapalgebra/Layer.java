package kth.ag2311.mapalgebra;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * What does Layer class use for?
 * 
 * @author Nga Nguyen
 *
 */
public class Layer {

	// //////////////////////////////////////////
	// Fields/Attributes
	//

	/**
	 * Name of a layer
	 */
	public String name;

	/**
	 * Path of data file
	 */
	public String path;

	/**
	 * Number of rows of the layer
	 */
	public int nRows;

	/**
	 * Number of columns of the layer
	 */
	public int nCols;

	/**
	 * Two double values representing the coordinates of the lower left corner
	 * of the layer
	 */
	public double[] origin = new double[2];

	/**
	 * Resolution of the layer
	 */
	public double resolution;

	/**
	 * Array of values the constitutes the layer in two dimensional nRows x
	 * nCols
	 */
	public double[][] values;

	/**
	 * no value - ArcGIS uses -9999 by default
	 */
	public double nullValue;

	/**
	 * max value of matrix values
	 */
	public double maxValue;

	/**
	 * min value of matrix values
	 */
	public double minValue;

	// //////////////////////////////////////////
	// Methods
	//

	/**
	 * Construction method of Layer class and load data
	 * 
	 * @param layerName
	 *            Name of layer
	 * @param inputFile
	 *            Path of input file
	 */
	public Layer(String layerName, String inputFile) {
		// Name this layer with layerName
		this.name = layerName;
		this.path = inputFile;

		System.out.println("Loading... " + inputFile);
		
		// use buffering, reading one line at a time
		// Exception may be thrown while reading (and writing) a file.
		BufferedReader bufReader = null; // obj for reading file

		// try to read content of fileName (path)
		try {
			// put data of filename to obj Reader
			bufReader = new BufferedReader(new FileReader(inputFile)); 

			String line = null;
			String[] words; // separate by a space

			// read #01 line - nCols int
			line = bufReader.readLine();
			if (line != null) {
				// split line into words separated by space
				words = line.split(" ");
				// get last word in array of words
				String numValue = words[words.length - 1];
				// try to convert String to Int
				nCols = Integer.parseInt(numValue);
			}

			// read #02 line - nRows int
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				nRows = Integer.parseInt(numValue);
			}

			// read #03 line - xllcorner double
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				origin[0] = Double.parseDouble(numValue);
			}

			// read #04 line - yllcorner double
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				origin[1] = Double.parseDouble(numValue);
			}

			// read #05 line - cellsize double
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				resolution = Double.parseDouble(numValue);
			}

			// read #06 line - NODATA_value int
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				nullValue = Double.parseDouble(numValue);
			}

			// IMPORTANT! Don't forget to create a matrix 2D of values
			values = new double[nRows][nCols];

			// continue read data
			for (int i = 0; i < nRows; i++) { // loop nRows
				line = bufReader.readLine();
				if (line != null) {

					// split line into words separated by space
					words = line.split(" ");

					for (int j = 0; j < nCols; j++) { // loop nCols
						// get value of word jth in words
						double value = Double.parseDouble(words[j]);

						// assign value to 2D-values
						values[i][j] = value;
					}
				}
			}

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				bufReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Loaded!");
	}

	/**
	 * Display all value of Layer class to console
	 * 
	 * @param allContentData
	 *            do you want to show all DATA, true=show, false=not show
	 * 
	 */
	public void print(boolean allContentData) {
		System.out.println("Layer:	" + name);
		System.out.println("Path:	" + path);
		System.out.println("---------------------------------------");

		System.out.println("ncols			" + nCols);
		System.out.println("nrows			" + nRows);
		System.out.println("xllcorner 		" + origin[0]);
		System.out.println("yllcorner 		" + origin[1]);
		System.out.println("cellsize 		" + resolution);
		System.out.println("NODATA_value		" + nullValue);

		if (allContentData) {
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					System.out.print(values[i][j] + " ");
				}
				System.out.println();
			}
		}
	}

	/**
	 * Save value of Layer class into text file The file format can be imported
	 * to ArcGIS
	 * 
	 * @param outputFile
	 *            Path of output file
	 */
	public void save(String outputFile) {
		System.out.println("Saving...");

		// use buffering, writing one line at a time
		// Exception may be thrown while reading (and writing) a file.
		BufferedWriter bufWriter = null;

		// try to write content into fileName (path)
		try {
			// use buffering, writing one line at a time
			// Exception may be thrown while reading (and writing) a file.
			bufWriter = new BufferedWriter(new FileWriter(outputFile));

			String line = null;

			// write #01 line - nCols int
			line = "ncols			" + nCols;
			bufWriter.write(line);
			bufWriter.newLine();

			// write #02 line - nRows int
			line = "nrows			" + nRows;
			bufWriter.write(line);
			bufWriter.newLine();

			// write #03 line - xllcorner double
			line = "xllcorner 		" + origin[0];
			bufWriter.write(line);
			bufWriter.newLine();

			// write #04 line - yllcorner double
			line = "yllcorner 		" + origin[1];
			bufWriter.write(line);
			bufWriter.newLine();

			// write #04 line - cellsize int
			line = "cellsize 		" + resolution;
			bufWriter.write(line);
			bufWriter.newLine();

			// write #06 line - NODATA_value int
			line = "NODATA_value		" + nullValue;
			bufWriter.write(line);
			bufWriter.newLine();

			// continue write data
			for (int i = 0; i < nRows; i++) { // loop nRows
				// create a line from each row of values
				// line must be empty before adding values
				line = "";

				// adding values in a row to line
				for (int j = 0; j < nCols; j++) { // loop nCols
					line += values[i][j] + " ";
				}

				// save it to buffer
				bufWriter.write(line);
				// make a break, and continue to saving a next row
				bufWriter.newLine();
			}

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Saved!");
		System.out.println("You can try to import to ArcGIS!");
	}

	/**
	 * Constant of max Gray Level
	 */
	private final static int maxGray = 255;
	
	/**
	 * 
	 * @return maximum value of image data
	 */
	public double getMax() {
		maxValue = Double.MIN_VALUE;
		for (int i = 0; i < nRows; i++) { // loop nRows
			for (int j = 0; j < nCols; j++) { // loop nCols
				double value = this.values[i][j];
				if (maxValue < value && value!=nullValue)
					maxValue = value;
			}
		}
		return maxValue;
	}
	
	/**
	 * 
	 * @return minimum value of image data
	 */
	public double getMin() {
		minValue = Double.MAX_VALUE;
		for (int i = 0; i < nRows; i++) { // loop nRows
			for (int j = 0; j < nCols; j++) { // loop nCols
				double value = this.values[i][j];
				if (minValue > value && value!=nullValue)
					minValue = value;
			}
		}
		return minValue;
	}
	
	/**
	 * show this Layer as an gray-scale image on the screen
	 */
	public void map() {
		BufferedImage image = new BufferedImage(nRows, nCols,
				BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();

		double grayscale = maxGray / (this.getMax() - this.getMin());
		
		// write data to raster
		int[] color = new int[3];
		for (int i = 0; i < nRows; i++) { // loop nRows
			for (int j = 0; j < nCols; j++) { // loop nCols
				// create color for this point
				if (values[i][j]==nullValue) {
					color[0] = 0; // Red
					color[1] = 0; // Green
					color[2] = 0; // Blue
				} else {
					int value = (int) (values[i][j] * grayscale);
					color[0] = value; // Red
					color[1] = value; // Green
					color[2] = value; // Blue
				}
				raster.setPixel(i, j, color);
			}
		}

		// show this image on the screen
		JFrame jframe = new JFrame();
		JLabel jlabel = new JLabel();
		ImageIcon ii = new ImageIcon(image);

		jlabel.setIcon(ii);
		jframe.add(jlabel);
		jframe.setSize(nCols + 25, nRows + 50);
		jframe.setVisible(true);

	}

	/**
	 * show this Layer as an color image on the screen using 24bit RGBA
	 * 
	 * @param interestingValues
	 *            list of interesting values
	 */
	public void map(double[] interestingValues) {
		BufferedImage image = new BufferedImage(nRows, nCols, BufferedImage.TYPE_INT_RGB); 
		WritableRaster raster = image.getRaster();
		
		double grayscale = maxGray / (this.getMax() - this.getMin());
		// create random color for each interesting values
	    Random rand = new Random();
		int numOfInterest = interestingValues.length;
		int[][] colorPanel = new int[numOfInterest][3];
		for (int k = 0; k<numOfInterest; k++) {
			colorPanel[k][0] = rand.nextInt(maxGray + 1);
			colorPanel[k][1] = rand.nextInt(maxGray + 1);
			colorPanel[k][2] = rand.nextInt(maxGray + 1);
		}
		
		// write data to raster
		int[] color = new int[3];
		for (int i = 0; i < nRows; i++) { // loop nRows
			for (int j = 0; j < nCols; j++) { // loop nCols
				// create color for this point
				if (values[i][j]==nullValue) {
					color[0] = 0; // Red
					color[1] = 0; // Green
					color[2] = 0; // Blue
				} else {
					// default color is grayscale
					double value = values[i][j] * grayscale;
					color[0] = (int) value; // Red
					color[1] = (int) value; // Green
					color[2] = (int) value; // Blue
				
					// get color for interesting value
					for (int k = 0; k<numOfInterest; k++) {
						if (interestingValues[k]==values[i][j]) {
							color[0] = colorPanel[k][0]; // Red
							color[1] = colorPanel[k][1]; // Green
							color[2] = colorPanel[k][2]; // Blue
							break;
						}
					}
				}
				raster.setPixel(i, j, color);
			}
		}
		
		// show this image on the screen
		JFrame jframe = new JFrame();
		JLabel jlabel = new JLabel();
		ImageIcon ii = new ImageIcon(image);
		
		jlabel.setIcon(ii);
		jframe.add(jlabel);
		jframe.setSize(nCols + 25, nRows + 50);
		jframe.setVisible(true);
		
	}
}
