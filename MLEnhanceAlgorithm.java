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


        if(Math.abs(a1-a2) > 50|| Math.abs(r1-r2) > 50 || Math.abs(g1-g2) > 50 || Math.abs(b1-b2) > 50 ){
            return 6291200;
        }
        //return (( (a1+a2)/2 << 24 ) | ( (r1+r2)/2 << 16 ) | ( (g1+g2)/2 << 8 ) | (b1+b2)/2);
        return pixel1;
    }
}
