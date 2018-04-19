package tech.ryanqyang;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class MLEnhanceAlgorithm extends BasicEnhanceAlgorithm{

    public MLEnhanceAlgorithm(File fileInput){
        super(fileInput);
    }


    /**
     * Replaces all detectable edges in the picture with green and everything else is white
     *
     *
     */
    public void edgeDetection(){
        extractFrames();

        for(int i = 0; i < fileLocations.size(); i ++){
            try {
                findEdgePixelDiff(i, getBufferedImageAt(fileLocations.get(i)));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Finds edges by using
     *
     * @param index
     * @param image
     */
    public void findEdgePixelDiff(int index, BufferedImage image){
        FrameData frame1 = new FrameData( index, image );

        for(int iX = 0; iX < frame1.getHeight(); iX++){
            for(int iY = 0; iY < frame1.getWidth() - 1; iY++){
                int pixelValue1 = frame1.getRGBValues().get(iX).get(iY);
                int pixelValue2 = frame1.getRGBValues().get(iX).get(iY + 1);
                image.setRGB(iX,iY, edgePixelValueAnalysis(pixelValue1, pixelValue2));
            }
        }
        cleanUpEdges(index, image);
        saveImage(image, this.fileLocations.get(index));
    }

    public BufferedImage cleanUpEdges(int index, BufferedImage image){
        FrameData frame1 = new FrameData( index, image );

        for(int iX = 0; iX < frame1.getHeight(); iX++){
            for(int iY = 0; iY < frame1.getWidth(); iY++){
                int pixelValue1 = frame1.getRGBValues().get(iX).get(iY);
                if(pixelValue1 == -1){
                    image.setRGB(iX, iY, 16777215);
                }else{
                    image.setRGB(iX, iY, 0);
                }
            }
        }
        return image;
    }

    /**
     * ML algorithm for generating image
     *
     * @param firstIndex
     * @param secondIndex
     * @return
     * @throws IOException
     */
    @Override
    public BufferedImage generateImage(int firstIndex, int secondIndex) throws IOException{

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
     * Gets two pixel values as integers (argb format) and returns green if the values are different
     * (basic edge detection)
     *
     * @param pixel1
     * @param pixel2
     * @return
     */
    public int edgePixelValueAnalysis(int pixel1, int pixel2){

        int a1 = (pixel1 >> 24) & 0xff;
        int r1 = (pixel1 >> 16) & 0xff;
        int g1 = (pixel1 >> 8) & 0xff;
        int b1 = (pixel1) & 0xff;

        int a2 = (pixel2 >> 24) & 0xff;
        int r2 = (pixel2 >> 16) & 0xff;
        int g2 = (pixel2 >> 8) & 0xff;
        int b2 = (pixel2) & 0xff;


        if(Math.abs(a1-a2) > 25|| Math.abs(r1-r2) > 25 || Math.abs(g1-g2) > 25 || Math.abs(b1-b2) > 25 ){
            return 6291200;
        }
        //return (( (a1+a2)/2 << 24 ) | ( (r1+r2)/2 << 16 ) | ( (g1+g2)/2 << 8 ) | (b1+b2)/2);
        return -1;
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


        if(Math.abs(a1-a2) > 30|| Math.abs(r1-r2) > 30 || Math.abs(g1-g2) > 30 || Math.abs(b1-b2) > 30 ){
            return 6291200;
        }
        //return (( (a1+a2)/2 << 24 ) | ( (r1+r2)/2 << 16 ) | ( (g1+g2)/2 << 8 ) | (b1+b2)/2);
        return pixel1;
    }
}
