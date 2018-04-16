/**
 * @author Ryan Yang
 *
 */

package tech.ryanqyang;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.image.BufferedImage.*;

public class MediaLoader {
    private File inputFile;
    private File outputFile;
    private BufferedImage BIFrame1;
    private BufferedImage BIFrame2;
    private ArrayList<File> fileLocations;

    /**
     * MediaLoader constructor
     *
     * @param inputFile
     */
    public MediaLoader(File inputFile){
        this.inputFile = inputFile;
    }

    /**
     * Creates an output folder in the same directory as test and will do all file manipulation in here
     * and final finished video will be placed here
     */
    public void createOutputFolder(){
        String folderLocation = this.inputFile.getParentFile().getAbsolutePath();
        this.outputFile = new File(folderLocation + "/output");
        this.outputFile.mkdir();
    }

    /**
     * Runs entire enhancement algorithm based off of user submitted info stored in data members
     *
     * loops through compareFrames to generate inbetween frames for entire media file
     *      Calls averagePixelValues to generate inbetween
     *      Calls createImage to generate BufferedImage object
     *      Calls insertFrame to add it to entire frame collection and moves up index
     *      Move to next index and repeat
     * Finally calls saveFile to save generatedMedia to users system
     *
     * @see #compareFrames(int, int)
     * @see #averagePixelValues(int, int)
     * @see #createImage(ArrayList, int, int)
     * @see #insertFrame(BufferedImage, int)
     * @see #saveFile()
     */
    public void enhance(){
        extractFrames();
        try {
            for (int i = 0; i < fileLocations.size() - 1; i += 2) {
                insertFrame(compareFrames(i, i + 1), i);
            }
        }catch(IOException e){
            System.err.println("Something happened while I was working! Try again");
            e.printStackTrace();
        }
        saveFile();
    }

    /**
     * User call extractFrames first before enhancing
     * filter out input media types
     */
    public void extractFrames(){
        createOutputFolder();
        try{
            GIFSetup gifFile = new GIFSetup(inputFile);
            splitFrames(gifFile);
        }catch(IllegalArgumentException e){
            System.err.println("File type not supported");
            e.printStackTrace();
        }
    }

    public void splitFrames(Splittable mediaToSplit){
        mediaToSplit.splitIntoFrames(outputFile);
    }

    /**
     * Takes a generated frame and saves it to output file and inserts its location to arraylist
     *
     * @param generated
     * @param previousFrameIndex
     */
    public void insertFrame(BufferedImage generated, int previousFrameIndex){
        String fileOutput = this.outputFile.getAbsolutePath() + "/altFrame" + previousFrameIndex;
        File outputFilePath = new File(fileOutput);
        saveImage(generated, outputFilePath);

        this.fileLocations.add(previousFrameIndex + 1, outputFilePath);
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
    public BufferedImage getBufferedImageAt( File mediaLocation ) throws IOException{
        return ImageIO.read( mediaLocation );
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
     * Gets two pixel values as integers (argb format)
     * @see FrameData#storeImageData(BufferedImage image)
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
     * Wraps up entire project by taking entire folder of generated images and converting it back into original media
     * type (gif, mp4, etc.)
     */
    public void saveFile(){
        //ImageIO.write(generatedImage, "gif", this.outputFile);
        for(File frameToDelete : fileLocations){
            if(frameToDelete.delete()){
                System.out.print(" . ");
            }else{
                System.err.println("File did not delete: " + frameToDelete.getAbsolutePath());
            }
        }
    }

    /**
     * Used while processing images, saves an image into the output folder
     *
     * @param generatedImage
     */
    public void saveImage( BufferedImage generatedImage, File filePath ){
        try{
            ImageIO.write(generatedImage, "jpg", filePath);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}


//    /**
//     * MediaLoader getter and setter methods
//     *
//     */
//    public ArrayList<File> getFileLocations() {
//        return fileLocations;
//    }
//    public File getInputFile() {
//        return inputFile;
//    }
//    public File getOutputFile() {
//        return outputFile;
//    }
//    public void setInputFile(File inputFile) {
//        this.inputFile = inputFile;
//    }
//    public void setOutputFile(File outputFile) {
//        this.outputFile = outputFile;
//    }