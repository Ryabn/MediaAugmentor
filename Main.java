/**
 * @author Ryan Yang
 *
 */

package tech.ryanqyang;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //gifEnhanceBasicTest();
        //gifEnhanceMLTest();

        File testFile = new File("/Users/ryanyang/Desktop/Workspace/CS003B/MediaAugmentor/src/tech/ryanqyang/assets/basicGifTest2.gif");
        MLEnhanceAlgorithm test = new MLEnhanceAlgorithm( testFile );

        //test.enhance();
        test.edgeDetection();
//        test.extractFrames();
//        try {
//            File output = new File("/Users/ryanyang/Desktop/Workspace/CS003B/MediaAugmentor/src/tech/ryanqyang/assets/output/testpicture.jpg");
//            test.saveImage( test.generateImage( 0, 2 ), output );
//        } catch ( IOException e ) {
//            e.printStackTrace();
//        }
    }

    /**
     * Runs a test on doubling the frame rate of a gif using the basic algorithm
     */
    static void gifEnhanceBasicTest(){
        File testFile =
                new File("/Users/ryanyang/Desktop/Workspace/CS003B/MediaAugmentor/src/tech/ryanqyang/assets/basicGifTest2.gif");
        BasicEnhanceAlgorithm test = new BasicEnhanceAlgorithm( testFile );
        test.enhance();
    }

    /**
     * Runs a test on doubling the frame rate of a gif using the machine learning algorithm
     */
    static void gifEnhanceMLTest(){
        File testFile = new File("/Users/ryanyang/Desktop/Workspace/CS003B/MediaAugmentor/src/tech/ryanqyang/assets/basicGifTest2.gif");
        MLEnhanceAlgorithm test = new MLEnhanceAlgorithm( testFile );
        test.enhance();
    }
}
