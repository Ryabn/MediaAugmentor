package tech.ryanqyang;

import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        File image1 = new File("/Users/ryanyang/Desktop/frametest/frame1.jpg");
        File image2 = new File("/Users/ryanyang/Desktop/frametest/frame2.jpg");

        MediaLoader test = new MediaLoader(image1, image2);


        BufferedImage imageGenerated = test.getFrames().get(0);

        test.saveImage(imageGenerated);

    }
}
