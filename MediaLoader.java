package tech.ryanqyang;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

            System.out.print(frames.toString());

        }catch(IOException e){

            e.printStackTrace();

        }
    }

    /**
     * Take a media input filter it into images
     *
     */
    public void splitIntoFrames() throws Exception{

//        try(mediaFile.canRead()){
//
//        }catch(IOException e){
//
//        }
    }

    public void compareFrames(int firstIndex, int secondIndex){
        FrameData frame1 = analyzeFrames(firstIndex);
        FrameData frame2 = analyzeFrames(secondIndex);


    }

    /**
     * Analyze image and generate data
     * Takes index of frame to be analyzed
     *
     * @param frameIndex
     */
    public FrameData analyzeFrames( int frameIndex ){
        FrameData data = new FrameData(frameIndex);


        return data;
    }
}
