package tech.ryanqyang;

import com.sun.imageio.plugins.gif.GIFImageWriter;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.awt.image.GifImageDecoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class GIFSetup implements Splittable{
    private File inputFile;

    public GIFSetup(File inputFile){
        this.inputFile = inputFile;
    }

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
            BufferedImage master = null;

            for (int i = 0; i < noi; i++) {
                BufferedImage image = reader.read(i);
                IIOMetadata metadata = reader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

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
                            master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                        }
                        master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
                    }
                }
                ImageIO.write(master, "JPG", new File( outputLocation.getAbsolutePath() + "/" + i + ".jpg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
