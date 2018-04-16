package tech.ryanqyang;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class MLEnhanceAlgorithm extends BasicEnhanceAlgorithm{

    public MLEnhanceAlgorithm(File fileInput){
        super(fileInput);
    }

    /**
     * Takes two file locations of images and returns a generated image by converting generated pixel values into a
     * BufferedImage object
     *
     * @param firstIndex
     * @param secondIndex
     * @return
     * @throws IOException
     */
    @Override
    public BufferedImage compareFrames(int firstIndex, int secondIndex) throws IOException{
        this.BIFrame1 = getBufferedImageAt(this.fileLocations.get(firstIndex));
        this.BIFrame2 = getBufferedImageAt(this.fileLocations.get(secondIndex));
        FrameData frame1 = analyzeFrames(firstIndex, this.BIFrame1);
        FrameData frame2 = analyzeFrames(secondIndex, this.BIFrame2);

        ArrayList<ArrayList<Integer>> generated = new ArrayList<>();

        for(int iX = 0; iX < frame1.getHeight(); iX++){
            generated.add(iX, new ArrayList<Integer>());
            for(int iY = 0; iY < frame1.getWidth(); iY++){
                int pixelValue1 = frame1.getRGBValues().get(iX).get(iY);
                int pixelValue2 = frame2.getRGBValues().get(iX).get(iY);
                generated.get(iX).add(iY, averagePixelValues(pixelValue1, pixelValue2));
            }
        }
        return createImage(generated, frame1.getWidth(), frame1.getHeight());
    }

    /**
     * Given file location, return Buffered Image Object
     *
     * @exception IOException should never occur unless user modifies files while program is running
     * @param mediaLocation
     * @return
     */
    @Override
    public BufferedImage getBufferedImageAt( File mediaLocation ) throws IOException{
        return ImageIO.read( mediaLocation );
    }

    /**
     * Analyze image and generate data
     * Takes index of frame to be analyzed
     *
     * @param frameIndex
     */
    @Override
    public FrameData analyzeFrames( int frameIndex, BufferedImage image ){
        FrameData data = new FrameData(frameIndex, image);
        return data;
    }

    /**
     * Gets two pixel values as integers (argb format)
     * @see FrameData#storeImageData(BufferedImage image)
     *
     * @param pixel1
     * @param pixel2
     * @return
     */
    @Override
    public int averagePixelValues(int pixel1, int pixel2){

        int a1 = (pixel1 >> 24) & 0xff;
        int r1 = (pixel1 >> 16) & 0xff;
        int g1 = (pixel1 >> 8) & 0xff;
        int b1 = (pixel1) & 0xff;

        int a2 = (pixel2 >> 24) & 0xff;
        int r2 = (pixel2 >> 16) & 0xff;
        int g2 = (pixel2 >> 8) & 0xff;
        int b2 = (pixel2) & 0xff;

        //return (( (a1+a2)/2 << 24 ) | ( (r1+r2)/2 << 16 ) | ( (g1+g2)/2 << 8 ) | (b1+b2)/2);
        return 200;
    }

    /**
     * Turn 2d arraylist of pixel values into a BufferedImage object
     *
     * @param imageData
     * @param width
     * @param height
     */
    @Override
    public BufferedImage createImage(ArrayList<ArrayList<Integer>> imageData, int width, int height){
        BufferedImage generatedImage = new BufferedImage(width, height, TYPE_INT_RGB);
        for(int iX = 0; iX < width; iX++){
            for(int iY = 0; iY < height; iY++){
                generatedImage.setRGB(iX, iY, imageData.get(iX).get(iY));
            }
        }
        return generatedImage;
    }
}
