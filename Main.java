/**
 * @author Ryan Yang
 *
 */

package tech.ryanqyang;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        gifEnhanceBasicTest();
        //gifEnhanceMLTest();

    }


    /**
     * Runs a test on doubling the frame rate of a gif using the basic algorithm
     */
    static void gifEnhanceBasicTest(){
        File testFile = new File("/Users/ryanyang/Desktop/frametest/newtest/test1.gif");
        BasicEnhanceAlgorithm test = new BasicEnhanceAlgorithm( testFile );
        test.enhance();
    }

    /**
     * Runs a test on doubling the frame rate of a gif using the machine learning algorithm
     */
    static void gifEnhanceMLTest(){
        File testFile = new File("/Users/ryanyang/Desktop/frametest/newtest/test1.gif");
        MLEnhanceAlgorithm test = new MLEnhanceAlgorithm( testFile );
        test.enhance();
    }
}
