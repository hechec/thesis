package imageProcessing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ui.LoadingPanel;
import util.NetworkConfiguration;
import dialogs.LoadingDialog;

public class ImageProcessor 
{
	
	private static ImageProcessor instance = null;
	private BackgroundRemover bRemover = null;
	private FeatureExtractor fExtractor = null;
	
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;
	
	private BufferedImage cropped;
	
	public ImageProcessor() 
	{
		bRemover = BackgroundRemover.getInstance();
		fExtractor = FeatureExtractor.getInstance();
	}
	
	public static ImageProcessor getInstance() 
	{
		if( instance == null )
			instance = new ImageProcessor();
		return instance;
	}
	
	public BufferedImage process(BufferedImage image)
	{
		return BilinearInterpolation.resize(cropImage(removeBackground( BilinearInterpolation.resize(image, WIDTH, HEIGHT))), 64, 64);
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
	/*	    getters to FeatureExtractor        */ 
	/*******************************************/
	public double computeMeanRed(BufferedImage image) 
	{
		return fExtractor.computeMeanRed(image);
	}
	
	public double computeMeanGreen(BufferedImage image) 
	{
		return fExtractor.computeMeanGreen(image);
	}
	
	public double computeMeanRG(BufferedImage image) 
	{
		return fExtractor.computeMeanRG(image);
	}
	
	public double computeMeanA(BufferedImage image) 
	{
		return fExtractor.computeMeanA(image);
	}
	
	public double computeMeanHue(BufferedImage image) 
	{
		return fExtractor.computeMeanHue(image);
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
	
	public double[][] createInputVectorArray(ArrayList<BufferedImage> input_data, LoadingPanel loadingPanel) {
		int patternSize = input_data.size();
		double[][] inputArray = new double[patternSize][NetworkConfiguration.NUMBER_OF_INPUT];
		
		for( int i = 0; i < patternSize; i++ )  {
			BufferedImage extractedInput = process(input_data.get(i));
			inputArray[i] = getFeatures(extractedInput);
			//prog.setValue(patternSize+1+i);
			loadingPanel.increment();
		}
	
		return inputArray;
	}
	
	public double[][] createOutputVectorArray(ArrayList<Integer> output_data) {
		int patternSize = output_data.size();
		double[][] outputArray = new double[patternSize][NetworkConfiguration.NUMBER_OF_OUTPUT];
		
		// set the output node value of the expected class to 1
		for( int i = 0; i < patternSize; i++ ) 
			outputArray[i][output_data.get(i)-1] = 1.0;
		
		return outputArray;
	}
	
	
	public double[] getFeatures(BufferedImage extractedInput) {
		double[] features = new double[NetworkConfiguration.NUMBER_OF_INPUT];
		
		features[0] = computeMeanRG(extractedInput);
		features[1] = computeMeanRed(extractedInput);
		features[2] = computeMeanHue(extractedInput);
		features[3] = computeMeanGreen(extractedInput);
		features[4] = computeMeanA(extractedInput);		
		
		return features;
	}
	
}


/**
 package utilities;

import imageProcessing.Masking;
import imageProcessing.GrayScale;
import imageProcessing.MeanFilter;
import imageProcessing.OtsuThreshold;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import colorspace.CIELab;
import colorspace.HSI;
import colorspace.RGBChannel;
import dialogs.MyDialog;
import dialogs.LoadingDialog;


public class ImageHandler {
	
	private MyDialog dialog;
	private BufferedImage original;
	private BufferedImage blueImage;
	private BufferedImage filteredImage;
	private BufferedImage grayImage;
	private BufferedImage binaryImage;
	private BufferedImage segmentedImage;
	private BufferedImage normalizedImage;
	private BufferedImage resizedImage;
	
	public ImageHandler() {
	}
	

	public BufferedImage resize(BufferedImage original, int scaledWidth, int scaledHeight) {
		
		BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, original.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(original, 0, 0, scaledWidth, scaledHeight, null);
		g.setComposite(AlphaComposite.Src);
		g.dispose();	
		return resizedImage;
	
	} 
	
	public BufferedImage toBlue(BufferedImage original) {
		return RGBChannel.toRGBChannel(original, RGBChannel.BLUE);
		
	}
	
	public BufferedImage filter(BufferedImage original) {

		return MeanFilter.filter(original);
	
	}
	
	public BufferedImage toGray(BufferedImage original) {
		
		return GrayScale.toGray(original);
		
	}
	
	public BufferedImage binarize(BufferedImage original) {
		
		return OtsuThreshold.binarize(original);

	}
	
	public BufferedImage segment(BufferedImage original, BufferedImage mask) {
		
		return Masking.extract(original, mask);
		
	}

	public BufferedImage toRed(BufferedImage original) {
		
		return RGBChannel.toRGBChannel(original, RGBChannel.RED);
	
	}

	public BufferedImage toGreen(BufferedImage original) {

		return RGBChannel.toRGBChannel(original, RGBChannel.GREEN);
		
	}

	public BufferedImage convertToLab(BufferedImage original) {
	
		return CIELab.convertToLab(original);
		
	}

	public BufferedImage normalize(BufferedImage segmented) {
		int minX = segmented.getWidth(), maxX = 0, minY = segmented.getHeight(), maxY = 0;
		
		for( int x = 0; x < segmented.getWidth(); x++) 
			for( int y = 0; y < segmented.getHeight(); y++ ) {
				if( (segmented.getRGB(x, y) & 0x00FFFFFF) != 0  ) {
					if( x < minX  )
						minX = x;
					else if( x > maxX )
						maxX = x;
					if( y < minY )
						minY = y;
					else if( y > maxY)
						maxY = y;
				}
			}

		int w = maxX - minX;
		int h = maxY - minY;
		BufferedImage normalized = new BufferedImage(w, h, segmented.getType());
		for( int i = 0; i < h; i++ ) 
			for( int j = 0; j < w; j++ ) {
				normalized.setRGB(j, i, segmented.getRGB(minX+j, minY+i));
			}
		return normalized;
	}

	public BufferedImage extract(BufferedImage original) {
		this.original = original;
		blueImage = toBlue(original);
		filteredImage = filter(blueImage);
		grayImage = toGray(filteredImage);
		binaryImage = binarize(grayImage);
		segmentedImage = segment(original, binaryImage);
		normalizedImage = normalize(segmentedImage);
		resizedImage = resize(normalizedImage, 64, 64);
		
		return resizedImage;
		//return resize(normalize(segment(original, binarize(toGray(filter(toBlue(original)))))), 64, 64);
	}
	
	public BufferedImage getBlueImage() {
		return blueImage;
	}
	public BufferedImage getFilteredImage() {
		return filteredImage;
	}
	public BufferedImage getGrayImage() {
		return grayImage;
	}
	public BufferedImage getBinaryImage() {
		return binaryImage;
	}
	public BufferedImage getSegmentedImage() {
		return segmentedImage;
	}
	public BufferedImage getNormalizedImage() {
		return normalizedImage;
	}
	public BufferedImage getResizedImage() {
		return resizedImage;
	}
	
	public void showStepByStep() {
		dialog = new MyDialog();
		dialog.addImage(original);
		dialog.addImage(blueImage);
		dialog.addImage(filteredImage);
		dialog.addImage(grayImage);
		dialog.addImage(binaryImage);
		dialog.addImage(segmentedImage);
		dialog.addImage(normalizedImage);
		dialog.addImage(resizedImage);
		dialog.setVisible(true);
	}

	public double computeMeanRed(BufferedImage image) {
		BufferedImage redImage = RGBChannel.toRGBChannel(image, RGBChannel.RED);
		return getAverage(redImage);
	}

	public double computeMeanGreen(BufferedImage image) {
		BufferedImage greenImage = RGBChannel.toRGBChannel(image, RGBChannel.GREEN);
		return getAverage(greenImage);
	}
	
	public double getAverage(BufferedImage image) {
		BufferedImage grayImage = toGray(image);
		int sum = 0, ctr = 0;
		
		Raster r = grayImage.getData();
		
		for( int i = 0; i < grayImage.getHeight(); i++ ) 
			for( int j = 0; j < grayImage.getWidth(); j++ ) {
				
				//System.out.println( r.getSample(j, i, 0) +" - "+((grayImage.getRGB(j, i)) & 0xff) );
				
				if( r.getSample(j, i, 0) != 0 ) {
					sum += r.getSample(j, i, 0);
					//System.out.println((grayRed.getRGB(j, i)) & 0xff);
					ctr++;
				}
			}
		
		return ctr != 0 ? sum/ctr : 0;
	}

	public double computeMeanRG(BufferedImage image) {
		BufferedImage redGray =  toGray(RGBChannel.toRGBChannel(image, RGBChannel.RED));
		BufferedImage greenGray = toGray(RGBChannel.toRGBChannel(image, RGBChannel.GREEN));
		int sum = 0, ctr = 0;
		
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
					sum += ( (redGray.getRGB(j, i) & 0xff) - (greenGray.getRGB(j, i) & 0xff) );
					ctr++;
				}
			}
		//System.out.println("ctr "+ctr);
		return ctr != 0 ? sum/ctr : 0;
	}

	public double computeMeanA(BufferedImage image) {
		int sum = 0, ctr = 0, rgb, red, green, blue;
		double[] lab = new double[3];
		
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
					rgb = image.getRGB(j, i);
					red =  (rgb >> 16) & 0xFF;
					green = (rgb >> 8) & 0xFF;
					blue = rgb & 0xFF;
					lab = CIELab.RGBtoLAB(red, green, blue);
					sum += lab[1];
					ctr++;
				}
			}
		
		return ctr != 0 ? sum/ctr : 0;
	}

	public double computeMeanHue(BufferedImage image) {
		int rgb, red, green, blue, sum = 0, ctr = 0;
		
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
					rgb = image.getRGB(j, i);
					red =  (rgb >> 16) & 0xFF;
					green = (rgb >> 8) & 0xFF;
					blue = rgb & 0xFF;
					sum += HSI.getHue(red, green, blue);
					ctr++;
				}
			}
		return ctr != 0 ? sum/ctr : 0;
	}
	
	public double[][] createInputVectorArray(ArrayList<BufferedImage> input_data, LoadingDialog prog) {
		int patternSize = input_data.size();
		double[][] inputArray = new double[patternSize][NetworkConfiguration.NUMBER_OF_INPUT];
		
		for( int i = 0; i < patternSize; i++ )  {
			BufferedImage extractedInput = extract(input_data.get(i));
			inputArray[i] = getFeatures(extractedInput);
			//prog.setValue(patternSize+1+i);
			prog.increment();
		}
	
		return inputArray;
	}
	
	public double[][] createOutputVectorArray(ArrayList<Integer> output_data) {
		int patternSize = output_data.size();
		double[][] outputArray = new double[patternSize][NetworkConfiguration.NUMBER_OF_OUTPUT];
		
		// set the output node value of the expected class to 1
		for( int i = 0; i < patternSize; i++ ) 
			outputArray[i][output_data.get(i)-1] = 1.0;
		
		return outputArray;
	}
	
	
	public double[] getFeatures(BufferedImage extractedInput) {
		double[] features = new double[NetworkConfiguration.NUMBER_OF_INPUT];
		
		features[0] = computeMeanRed(extractedInput);
		features[1] = computeMeanGreen(extractedInput);
		features[2] = computeMeanRG(extractedInput);
		features[3] = computeMeanHue(extractedInput);
		features[4] = computeMeanA(extractedInput);
		
		return features;
	}
	
}
 
*/