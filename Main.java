/**
 * @author Ryan Yang
 *
 */

package tech.ryanqyang;

import java.io.File;

public class Main {

    public static void main(String[] args) {
//        File image1 = new File("/Users/ryanyang/Desktop/frametest/in1/frame1.jpg");
//        File image2 = new File("/Users/ryanyang/Desktop/frametest/in1/frame2.jpg");
//        File image3 = new File("/Users/ryanyang/Desktop/frametest/in1/frame3.jpg");
//        File image4 = new File("/Users/ryanyang/Desktop/frametest/in1/frame4.jpg");
        File testGif = new File("/Users/ryanyang/Desktop/frametest/test1.gif");
        File outputFile = new File("/Users/ryanyang/Desktop/frametest/output");

        MediaLoader test = new MediaLoader(testGif, outputFile);

        //test.compareFrames( 0 , 1 );
        //BufferedImage imageGenerated = test.getFrames().get(1);

        //test.saveImage(imageGenerated);

    }
}
