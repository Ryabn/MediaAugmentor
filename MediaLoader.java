package tech.ryanqyang;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.image.BufferedImage.*;

public class MediaLoader {

    private File mediaFile;
    private ArrayList<BufferedImage> frames;

    /**
     * General constructor that takes in a media file and splits it into frames, if possible
     *
     * @param input
     */
    public MediaLoader(File input){
        this.mediaFile = input;
        try {
            splitIntoFrames();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * constructor used for testing frame generator algorithm
     * takes in 2 images and inserts one between
     *
     * @param image1
     * @param image2
     */
    public MediaLoader(File image1, File image2){
        this.frames = new ArrayList<>();
        try{
            frames.add(ImageIO.read(image1));
            frames.add(ImageIO.read(image2));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<BufferedImage> getFrames() {
        return frames;
    }

    /**
     * Take a media input filter it into images
     *
     */
    public void splitIntoFrames() throws Exception{
        //if gif
        splitGif();

//        try(mediaFile.canRead()){
//
//        }catch(IOException e){
//
//        }
    }
    public void splitGif(){

    }

    public void compareFrames(int firstIndex, int secondIndex){
        FrameData frame1 = analyzeFrames(firstIndex, this.frames.get(firstIndex));
        FrameData frame2 = analyzeFrames(secondIndex, this.frames.get(secondIndex));


        ArrayList<ArrayList<Integer>> generated = new ArrayList<>();

        for(int iX = 0; iX < frame1.getHeight(); iX++){
            generated.add(iX, new ArrayList<Integer>());
            for(int iY = 0; iY < frame1.getWidth(); iY++){
                int pixelValue1 = frame1.getRGBValues().get(iX).get(iY);
                int pixelValue2 = frame2.getRGBValues().get(iX).get(iY);
                generated.get(iX).add(iY, averagePixelValues(pixelValue1, pixelValue2));
            }
        }
        BufferedImage created = createImage(generated, frame1.getWidth(), frame1.getHeight());
    }

    /**
     * Gets two pixel values as integers (argb format)
     * @see FrameData storeImageData method
     *
     * @param pixel1
     * @param pixel2
     * @return
     */
    public int averagePixelValues(int pixel1, int pixel2){

        int a1 = (pixel1 >> 24) & 0xff;
        int r1 = (pixel1 >> 16) & 0xff;
        int g1 = (pixel1 >> 8) & 0xff;
        int b1 = (pixel1) & 0xff;

        int a2 = (pixel2 >> 24) & 0xff;
        int r2 = (pixel2 >> 16) & 0xff;
        int g2 = (pixel2 >> 8) & 0xff;
        int b2 = (pixel2) & 0xff;

        return (( (a1+a2)/2 << 24 ) | ( (r1+r2)/2 << 16 ) | ( (g1+g2)/2 << 8 ) | (b1+b2)/2);
    }

    /**
     * Turn 2d arraylist of pixel values into a BufferedImage object
     *
     * @param imageData
     * @param width
     * @param height
     */
    public BufferedImage createImage(ArrayList<ArrayList<Integer>> imageData, int width, int height){
        BufferedImage generatedImage = new BufferedImage(width, height, TYPE_INT_RGB);
        for(int iX = 0; iX < width; iX++){

            for(int iY = 0; iY < height; iY++){
                generatedImage.setRGB(iX, iY, imageData.get(iX).get(iY));
            }
        }
        return generatedImage;
    }

    /**
     * Analyze image and generate data
     * Takes index of frame to be analyzed
     *
     * @param frameIndex
     */
    public FrameData analyzeFrames( int frameIndex, BufferedImage image ){
        FrameData data = new FrameData(frameIndex, image);

        return data;
    }

    /**
     * Mainly used for testing
     * Saves an image into the output folder (mostly to see the generated image without having to develop
     * a ui to see the outputs)
     *
     * @param generatedImage
     */
    public void saveImage( BufferedImage generatedImage ){
        File outputFile = new File("/Users/ryanyang/Desktop/frametest/output/test.jpg");

        try{
            ImageIO.write(generatedImage, "jpg", outputFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}