package imageprocessing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import utilities.GlobalVariables;
import views.ExperimentPane;
import views.ProgressPane;

public class ImageProcessor
{
	
	private static ImageProcessor instance = null;
	private BackgroundRemover bRemover = null;
	
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;
	
	private BufferedImage cropped;
	
	public ImageProcessor() 
	{
		bRemover = BackgroundRemover.getInstance();
	}
	
	public static ImageProcessor getInstance() 
	{
		if( instance == null )
			instance = new ImageProcessor();
		return instance;
	}
	
	public BufferedImage process(BufferedImage image, int width, int height)
	{
		return BilinearInterpolation.resize(cropImage(removeBackground( BilinearInterpolation.resize(image, width, height))), 64, 64);
		//return resizeImage(cropImage(removeBackground(resizeImage(image, WIDTH, HEIGHT))), 64, 64);
	}
	
	public BufferedImage resizeImage(BufferedImage image, int scaledWidth, int scaledHeight) 
	{
		BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, image.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
		g.setComposite(AlphaComposite.Src);
		g.dispose();	
		return resizedImage;
	}
	
	public BufferedImage removeBackground(BufferedImage image)
	{
		return bRemover.removeBackground(image);
	}
	
	public BufferedImage cropImage(BufferedImage image)
	{
		cropped = Cropper.crop(image);
		return cropped;
	}
	
	public BufferedImage getCropped()
	{
		return cropped;
	}
	
	
	/*******************************************/
	/*		getters to BackgroundRemover       */ 
	/*******************************************/
	public BufferedImage getBlueChannel() 
	{
		return bRemover.getBlueChannel();
	}

	public BufferedImage getFilteredBlue() 
	{
		return bRemover.getFilteredBlue();
	}

	public BufferedImage getGrayscale() 
	{
		return bRemover.getGrayscale();
	}
	
	public BufferedImage getBinaryMask() 
	{
		return bRemover.getBinaryMask();
	}

	public BufferedImage getSegmented() {
		return bRemover.getSegmented();
	}
	
	/**
	 * creates a vector of features for each image 
	 * @param input_data
	 * @param progressPane
	 * @return
	 */
	public double[][] createInputVectorArray(ArrayList<BufferedImage> input_data, ProgressPane progressPane) {
		int patternSize = input_data.size();
		double[][] inputArray = new double[patternSize][GlobalVariables.NUMBER_OF_INPUT];
		
		for( int i = 0; i < patternSize; i++ )  {
			BufferedImage extractedInput = process(input_data.get(i), 200, 200);
			inputArray[i] = getFeatures(extractedInput);
			//prog.setValue(patternSize+1+i);
			progressPane.incrementBar();
		}
	
		return inputArray;
	}
	
	/**
	 * creates a vector and sets the corresponding class index to 1, other to 0  
	 * @param output_data
	 * @return 
	 */
	public double[][] createOutputVector(ArrayList<Integer> output_data) {
		int patternSize = output_data.size();
		double[][] outputArray = new double[patternSize][GlobalVariables.NUMBER_OF_OUTPUT];
		
		// set the output node value of the expected class to 1
		for( int i = 0; i < patternSize; i++ ) 
			outputArray[i][output_data.get(i)-1] = 1.0;
		
		return outputArray;
	}
	
	/**
	 * extracts color features of an image 
	 * @param processedImage
	 * @return feature vector
	 */
	public double[] getFeatures(BufferedImage processedImage) {
		double[] features = new double[GlobalVariables.NUMBER_OF_INPUT];
		
		if(GlobalVariables.MODE == GlobalVariables.EXPERIMENTATION_3) {
			ArrayList<String> featuresList = ExperimentPane.getInstance().getFeatures();
			
			for( int i = 0; i < featuresList.size(); i++ ) 
				features[i] = extractFeature(featuresList.get(i), processedImage);
			
		} else {
			features[0] = FeatureExtractor.computeMeanRG(processedImage);
			features[1] = FeatureExtractor.computeMeanRed(processedImage);
			features[2] = FeatureExtractor.computeMeanHue(processedImage);
			features[3] = FeatureExtractor.computeMeanGreen(processedImage);
			features[4] = FeatureExtractor.computeMeanA(processedImage);
		}
		
		return features;
		//features[0] = FeatureExtractor.computeMeanRG(processedImage);
		//features[1] = FeatureExtractor.computeMeanRed(processedImage);
		//features[2] = FeatureExtractor.computeMeanHue(processedImage);
		//features[3] = FeatureExtractor.computeMeanGreen(processedImage);
		//features[4] = FeatureExtractor.computeMeanA(processedImage);
		
		//1
		//features[0] = FeatureExtractor.computeMeanA(processedImage);
		
		//2
		//features[0] = FeatureExtractor.computeMeanRG(processedImage);
		//features[1] = FeatureExtractor.computeMeanHue(processedImage);
		
		//3
		/*features[0] = FeatureExtractor.computeMeanRed(processedImage);
		features[1] = FeatureExtractor.computeMeanGreen(processedImage);
		features[2] = FeatureExtractor.computeMeanBlue(processedImage);
		*/
	}

	private double extractFeature(String string, BufferedImage processedImage) 
	{
		double colorValue = 0;
		switch (string) {
			case "Red":
					colorValue = FeatureExtractor.computeMeanRed(processedImage);
					break;
			case "Green":
					colorValue = FeatureExtractor.computeMeanGreen(processedImage);
					break;
			case "Blue":
					colorValue = FeatureExtractor.computeMeanBlue(processedImage);
					break;
			case "R-G":
					colorValue = FeatureExtractor.computeMeanRG(processedImage);
					break;
			case "Hue":
					colorValue = FeatureExtractor.computeMeanHue(processedImage);
					break;	
			case "a*":
					colorValue = FeatureExtractor.computeMeanA(processedImage);
					break;
		}
		return colorValue;
	}
}
