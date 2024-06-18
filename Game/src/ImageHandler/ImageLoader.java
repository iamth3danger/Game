package ImageHandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageLoader {
    public static ArrayList<BufferedImage> loadImages(String filename) {
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        int i = 0;
        String[] fileSplit = filename.split("_");
        String fileName = fileSplit[0].substring(0, fileSplit[0].length() - 1) + i + "_" + fileSplit[1];
        File file = new File(fileName);
        while (file.exists()) {
            images.add(loadImage(fileName));
            i++;
            if (i < 10)
            	fileName = fileSplit[0].substring(0, fileSplit[0].length() - 1) + i + "_" + fileSplit[1];
            else
            	fileName = fileSplit[0].substring(0, fileSplit[0].length() - 2) + i + "_" + fileSplit[1];
            file = new File(fileName);
        }
        return images;
    }

    public static BufferedImage loadImage(String file) {
        try {
            return ImageIO.read(new File(file));
        } catch (IOException e) {
            System.out.println("Error loading " + file);
            e.printStackTrace();
            return null;
        }
    }
    
    public static ArrayList<BufferedImage> loadFlippedImages(String filename) {
    	ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    	int i = 0;
    	String[] fileSplit = filename.split("_");
    	String fileName = fileSplit[0].substring(0, fileSplit[0].length() - 1) + i + "_flipped_" + fileSplit[1];
        File file = new File(fileName);
        while (file.exists()) {
        	images.add(loadImage(fileName));
        	i++;
        	if (i < 10)
        		fileName = fileSplit[0].substring(0, fileSplit[0].length() - 1) + i + "_flipped_" + fileSplit[1];
        	else 
        		fileName = fileSplit[0].substring(0, fileSplit[0].length() - 2) + i + "_flipped_" + fileSplit[1];
        	
        	file = new File(fileName);
        }
        
    	return images;
    }
}