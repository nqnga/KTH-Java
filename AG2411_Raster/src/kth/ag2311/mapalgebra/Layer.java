package kth.ag2311.mapalgebra;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	public double originX;
	public double originY;

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
				originX = Double.parseDouble(numValue);
			}

			// read #04 line - yllcorner double
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				originY = Double.parseDouble(numValue);
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
			minValue = Double.MAX_VALUE;
			maxValue = Double.MIN_VALUE;

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
						if (minValue > value)
							minValue = value;
						if (maxValue < value)
							maxValue = value;
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
	 * Construction method of Layer class with input fields
	 * 
	 * @param layerName
	 *            name of Layer
	 * @param nrow
	 *            Number of rows
	 * @param ncol
	 *            Number of col
	 * @param oX
	 *            the Origin of X
	 * @param oY
	 *            the Origin of Y
	 * @param res
	 *            resolution of layer
	 * @param nullVal
	 *            NODATA value
	 */
	public Layer(String layerName, int nrow, int ncol, double oX, double oY,
			double res, double nullVal) {
		this.name = layerName;
		this.nRows = nrow;
		this.nCols = ncol;
		this.originX = oX;
		this.originY = oY;
		this.resolution = res;
		this.nullValue = nullVal;

		// IMPORTANT! Don't forget to create a matrix 2D of values
		values = new double[nRows][nCols];

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
		System.out.println("xllcorner 		" + originX);
		System.out.println("yllcorner 		" + originY);
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
			line = "xllcorner 		" + originX;
			bufWriter.write(line);
			bufWriter.newLine();

			// write #04 line - yllcorner double
			line = "yllcorner 		" + originY;
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
	 * Default RGB for NODATA cells
	 */
	private final static int[] nullGray = {0,0,0} ;
	
	/**
	 * show this Layer as an gray scale image on the screen
	 */
	
	public void map() {
		BufferedImage image = new BufferedImage(nRows, nCols,
				BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();
		int[] color = new int[3];

		// write data to raster
		for (int i = 0; i < nRows; i++) { // loop nRows
			for (int j = 0; j < nCols; j++) { // loop nCols
				// create color for this point
				if (values[i][j] == nullValue) {
					color[0] = nullGray[0]; // Red
					color[1] = nullGray[1]; // Green
					color[2] = nullGray[2]; // Blue
				} else {
					int grayscale = (int) Math.floor(values[i][j] - minValue);
					color[0] = grayscale; // Red
					color[1] = grayscale; // Green
					color[2] = grayscale; // Blue
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
		jframe.setSize(nRows, nCols);
		jframe.setVisible(true);

	}
	/**
	 * Default RGBA for NODATA cells
	 */
	private final static double[] nullColor = {0f,0f,0f,1f} ;

	/**
	 * show this Layer as an color image on the screen using 24bit RGBA
	 * 
	 * @param colorScheme
	 *            value of 24bit RGBA
	 */
	public void map(double[] colorScheme) {
		BufferedImage image = new BufferedImage(nRows, nCols,
				BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();
		double[] attr = new double[4];

		// write data to raster
		for (int i = 0; i < nRows; i++) { // loop nRows
			for (int j = 0; j < nCols; j++) { // loop nCols
				// create color for this point
				double value = values[i][j];
				if (value == nullValue) {
					attr[0] = nullColor[0]; // read
					attr[1] = nullColor[1]; // green
					attr[2] = nullColor[2]; // blue
					attr[3] = nullColor[3]; // alpha
				} else {
					attr[0] = (colorScheme[0] * value) / 255f; // read
					attr[1] = (colorScheme[1] * value) / 255f; // green
					attr[2] = (colorScheme[2] * value) / 255f; // blue
					attr[3] = colorScheme[3] / 255f; // alpha
				}
				raster.setPixel(i, j, attr);
			}
		}

		// show this image on the screen
		JFrame jframe = new JFrame();
		JLabel jlabel = new JLabel();
		ImageIcon ii = new ImageIcon(image);

		jlabel.setIcon(ii);
		jframe.add(jlabel);
		jframe.setSize(nRows, nCols);
		jframe.setVisible(true);

	}

	/**
	 * Create a layer with values equal sum of this.values + inLayer.values
	 * 
	 * @param inLayer
	 *            obj of inLayer
	 * @param outLayerName
	 *            name of outLayer
	 * @return obj of ourLayer
	 */
	public Layer localSum(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, originX,
				originY, resolution, nullValue);

		for (int i = 0; i < nRows; i++) { // loop nRows
			for (int j = 0; j < nCols; j++) { // loop nCols
				if ((this.values[i][j] == this.nullValue)
						|| (inLayer.values[i][j] == inLayer.nullValue)) {
					
					outLayer.values[i][j] = outLayer.nullValue;
				
				} else {
					
					outLayer.values[i][j] = this.values[i][j]
							+ inLayer.values[i][j];
				}
			}
		}
		
		return outLayer;
	}
	
	
	private ArrayList<Integer> getNeighborhood(int idx, int radius, boolean square) {
		ArrayList<Integer> neighbors = new ArrayList<Integer>();

		int sizeOfFilter = radius*2 + 1;
		
		// create deltaX and deltaY
		int[][] deltaX = new int[sizeOfFilter][sizeOfFilter];
		int[][] deltaY = new int[sizeOfFilter][sizeOfFilter];

		// create mask layer
		int[][] mask = new int[sizeOfFilter][sizeOfFilter];
		
		
		return neighbors;
	}
	
	
	public Layer focalVariety(int radius, boolean square, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, originX,
				originY, resolution, nullValue);

		for (int i = 0; i < nRows; i++) { // loop nRows
			for (int j = 0; j < nCols; j++) { // loop nCols
				// calculate index of cell
				int index = i * nCols + j;
				ArrayList<Integer> neighbors = getNeighborhood(index, radius, square);
				
				// get number of neighbors, if it is empty then continue
				int numOfNeighbors = 0;
				if (neighbors.isEmpty()) { 
					continue;
				} else {
					numOfNeighbors = neighbors.size();
				}
				
				// calculate sum of neighbors
				int sum = 0;
				for (int k=0; k<numOfNeighbors; k++ ) {
					int value = neighbors.get(k);
					if (value != this.nullValue) 
						sum = sum + value;
					// else ignore !!!
				}
				
				// assign to cell in outLayer
				outLayer.values[i][j] = sum;

			}
		}


		
		
		return outLayer;
	}
	
	
	public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, originX,
				originY, resolution, nullValue);

		
		return outLayer;
	}
	
	

}