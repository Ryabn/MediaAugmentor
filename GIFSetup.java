package tech.ryanqyang;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class GIFSetup implements Splittable{
    private File inputFile;
    private int frameCount;
    private int delay = 10;

    public GIFSetup(File inputFile){
        this.inputFile = inputFile;
    }

    @Override
    public int getFrameCount() {
        return frameCount;
    }
    @Override
    public int getDelay() {
        return delay;
    }

    /**
     * Taken from
     * https://stackoverflow.com/questions/8933893/convert-each-animated-gif-frame-to-a-separate-bufferedimage/17269591#17269591
     *
     * Info on what meta data exists in gif and what to extract
     * http://www.matthewflickinger.com/lab/whatsinagif/bits_and_bytes.asp
     *
     * @param outputLocation
     */
    @Override
    public void splitIntoFrames(File outputLocation){
        try {
            String[] imageatt = new String[]{
                    "imageLeftPosition",
                    "imageTopPosition",
                    "imageWidth",
                    "imageHeight"
            };

            ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream ciis = ImageIO.createImageInputStream(this.inputFile);
            reader.setInput(ciis, false);

            int noi = reader.getNumImages(true);
            this.frameCount = noi;
            BufferedImage master = null;

            for (int i = 0; i < noi; i++) {
                BufferedImage image = reader.read(i);
                IIOMetadata metadata = reader.getImageMetadata(i);



                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");



                NodeList children = tree.getChildNodes();

                if(children.item(1).getNodeName().equals("GraphicControlExtension")) {
                    NamedNodeMap attr = children.item(1).getAttributes();
                    for (int k = 0; k < attr.getLength(); k++) {
                        if(attr.item(k).getNodeName().equals("delayTime")) {
                            this.delay = Integer.parseInt(attr.item(k).getNodeValue());
                        }
                    }
                }

                for (int j = 0; j < children.getLength(); j++) {


                    Node nodeItem = children.item(j);


                    if(nodeItem.getNodeName().equals("ImageDescriptor")){
                        Map<String, Integer> imageAttr = new HashMap<String, Integer>();

                        for (int k = 0; k < imageatt.length; k++) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            Node attnode = attr.getNamedItem(imageatt[k]);
                            imageAttr.put(imageatt[k], Integer.valueOf(attnode.getNodeValue()));
                        }
                        if(i==0){
                            master = new BufferedImage(
                                    imageAttr.get("imageWidth"),
                                    imageAttr.get("imageHeight"),
                                    BufferedImage.TYPE_INT_RGB
                            );
                        }
                        master.getGraphics().drawImage(
                                image,
                                imageAttr.get("imageLeftPosition"),
                                imageAttr.get("imageTopPosition"),
                                null
                        );
                    }
                }
                ImageIO.write(master, "JPG", new File( outputLocation.getAbsolutePath() + "/" + (i*2) + ".jpg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
