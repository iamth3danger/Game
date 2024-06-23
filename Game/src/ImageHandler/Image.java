package ImageHandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image {
    public BufferedImage imgFlipper(BufferedImage image) {
        BufferedImage flippedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                flippedImage.setRGB(image.getWidth() - x - 1, y, image.getRGB(x, y));
            }
        }
        return flippedImage;
    }

    public static void ImageCopier2(String filename, int imgToCopy, int copies, int imgNumber) throws IOException {

        // split the input file name into parts
         String[] fileSplit = filename.split("_");

         /*
         for (int i = imgToCopy; i < imgToCopy + copies; i++) {
             int splitter;
  
              //check whether number is single digit or double digit
             if (i < 10) {
                 splitter = fileSplit[0].length() - 1;
             } else {
                 splitter = fileSplit[0].length() - 2;
             }
  
           // construct new file name
             String newFileName = fileSplit[0].substring(0, splitter) + i + "_" + fileSplit[1];
  
            //read the original image
             BufferedImage originalImage = ImageIO.read(new File(filename));
  
            // write the image to a new file
             ImageIO.write(originalImage, "png", new File(newFileName));
         }
*/
       // shift the files which are greater than imgNumber
         for (int i = imgToCopy; i <= imgNumber; i++) {
             int splitter;
             if (i < 10) {
            	 splitter = fileSplit[0].length() - 1;
             } else {
            	 splitter = fileSplit[0].length() - 2;
             }
  
             String oldFileName = fileSplit[0].substring(0, splitter) + i + "_" + fileSplit[1];
             File oldFile = new File(oldFileName);
             System.out.println(oldFileName);
             if (oldFile.exists()) {
                 String newFileName = fileSplit[0].substring(0, splitter) + (i + copies) + "_" + fileSplit[1];
                 File newFile = new File(newFileName);
                 oldFile.renameTo(newFile);
             }
         }
     }
 
    
    
    public static void ImageCopier(String filename, int imgToCopy, int copies, int imgNumber) throws IOException{
    	String[] fileSplit = filename.split("_");
        String fileName = "";
    	for (int i = imgNumber; i > imgToCopy; i--) {
    		int splitter;
    		if (i < 10) {
    			splitter = fileSplit[0].length() - 1;
    		}
    		else 
    			splitter = fileSplit[0].length() - 2;
    		
    		
    		File oldFile = new File(fileSplit[0].substring(0, splitter) + i + "_" + fileSplit[1]);
    		
    		if (i + copies < 10) {
    			splitter = fileSplit[0].length() - 1;
    		}
    		else 
    			splitter = fileSplit[0].length() - 2;
    		
    		File newFile = new File(fileSplit[0].substring(0, splitter) + (i + copies) + "_" + fileSplit[1]);
    		oldFile.renameTo(newFile);
    	}
    	
    	
    	
    	int splitter;
		if (imgToCopy < 10) {
			splitter = fileSplit[0].length() - 1;
		}
		else 
			splitter = fileSplit[0].length() - 2;
		
    	String copyFile = fileSplit[0].substring(0, splitter) + imgToCopy + "_" + fileSplit[1];
    	
    	BufferedImage image = ImageIO.read(new File(copyFile));
    	
    	for (int i = 1; i <= copies; i++) {
    		if (i + imgToCopy < 10) {
    			splitter = fileSplit[0].length() - 1;
    		}
    		else 
    			splitter = fileSplit[0].length() - 2;
    		
    		copyFile = fileSplit[0].substring(0, splitter) + (i + imgToCopy) + "_" + fileSplit[1];
    		System.out.println(copyFile);
    		ImageIO.write(image, "png", new File(copyFile));
    	}
    
    }
    public static void main(String[] args) throws IOException {
    	
    	String fileName = "Boss/Shadow/shadow/00_shadow.png";
    	Image.ImageCopier(fileName, 5, 20, 19);
    	/*
        try {
            Image image = new Image();
            for (int i = 0; i < 15; i++) {
            BufferedImage originalImage = ImageIO.read(new File("DeathBringer/Attack/0" + i + "_flipped_Attack.png"));
            BufferedImage flippedImage = image.imgFlipper(originalImage);
            ImageIO.write(flippedImage, "png", new File("DeathBringer/Attack/0" + i + "_flipped_Attack.png"));
            System.out.println("flipped");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       */
    	
    	/*
    	
    	try {
            Image image = new Image();
            for (int i = 1; i < 10; i++) {
            BufferedImage originalImage = ImageIO.read(new File("DeathBringer/Walk/Bringer-of-Death_Walk_" + i + ".png"));
            //BufferedImage flippedImage = image.imgFlipper(originalImage);

            ImageIO.write(originalImage, "png", new File("DeathBringer/Walk/0" + (i - 1) + "_Walk.png"));
            System.out.println("flipped");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}


