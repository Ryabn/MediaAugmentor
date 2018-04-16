/**
 * @author Ryan Yang
 *
 */

package tech.ryanqyang;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FrameData {

    private int frameIndex;
    private int height;
    private int width;
    private ArrayList<ArrayList<Integer>> RGBValues;

    /**
     * constructor for image data
     *
     * @param frameIndex
     * @param image
     */
    public FrameData(int frameIndex, BufferedImage image){
        this.frameIndex = frameIndex;
        this.height = image.getHeight();
        this.width = image.getWidth();
        storeImageData(image);
    }

    public FrameData(){

    }

    /**
     * getters for data members
     *
     */
    public int getFrameIndex() {
        return this.frameIndex;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<ArrayList<Integer>> getRGBValues() {
        return RGBValues;
    }

    /**
     * Loops all pixels of image and stores it inside 2d arraylist data member
     *
     * .getRGB returns an integer for the color
     * to get rgb values, should convert the value to binary (32 bit signed int)
     * ignore the first 8 bits of data and group next 24 bits into 8
     *
     * first 8 bits = r
     * second 8 bits = g
     * last 8 bits = b
     *
     * convert bits into hex values to get hex code
     *
     * When working with higher resolution images, do not use getRGB because converting it to a hex code and back
     * is extremely inefficient. Better to get the pixel data yourself and store into an array for comparison
     *
     */
    public void storeImageData(BufferedImage image){
        RGBValues = new ArrayList<>();
        for(int iX = 0; iX < image.getHeight(); iX++){
            RGBValues.add( new ArrayList<Integer>());
            for(int iY = 0; iY < image.getWidth(); iY++){
                RGBValues.get(iX).add(iY, image.getRGB(iX, iY));
            }
        }
    }
}
