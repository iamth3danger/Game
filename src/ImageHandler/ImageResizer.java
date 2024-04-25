package ImageHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {
    public File resizeImage(String filename) throws IOException {
        // Load image from file
        BufferedImage original = ImageIO.read(new File(filename));

        // Find non-clear area bounds
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = 0, maxY = 0;
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                Color color = new Color(original.getRGB(x, y));
                if (!isClear(color)) { // Assuming clear means alpha channel value is zero
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }

        // Crop and save resized image
        String[] parts = filename.split("\\.");
        String newName = parts[0] + "_resized." + parts[1];
        BufferedImage cropped = original.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
        ImageIO.write(cropped, "png", new File(newName)); // Use your preferred format

        return new File(newName);
    }

    public static int findCropX1(BufferedImage original) {
    	 int imageAt = 0;
    	for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                if (original.getRGB(x, y) != 0) {
                	return x;
                }
            }
        }
    	return imageAt;
    }
    
    public static int findCropY1(BufferedImage original) {
   	 int imageAt = 0;
   	for (int y = 0; y < original.getHeight(); y++) {
           for (int x = 0; x < original.getWidth(); x++) {
               if (original.getRGB(x, y) != 0) {
               	return y;
               }
           }
       }
   	return imageAt;
   }
    
    
    public static int findCropYEnd1(BufferedImage original) {
      	 int imageAt = 0;
      	for (int y = original.getHeight() - 1; y > 0; y--) {
              for (int x = 0; x < original.getWidth(); x++) {
                  if (original.getRGB(x, y) != 0) {
                  	return y;
                  }
              }
          }
      	return imageAt;
      }
    
    public static int findCropXEnd1(BufferedImage original) {
   	 int imageAt = 0;
   	for (int x = original.getWidth() - 1; x > 0; x--) {
           for (int y = 0; y < original.getHeight(); y++) {
               if (original.getRGB(x, y) != 0) {
               	return x;
               }
           }
       }
   	return imageAt;
   }
    
    private static boolean isClear(Color color) {
        return color.getAlpha() == 0;
    }
    
    
    
    public static int findCropX(BufferedImage original) {
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int pixel = original.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                if (alpha > 0) {
                    return x;
                }
            }
        }
        return 0;
    }

    public static int findCropY(BufferedImage original) {
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int pixel = original.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                if (alpha > 0) {
                    return y;
                }
            }
        }
        return 0;
    }

    public static int findCropYEnd(BufferedImage original) {
        for (int y = original.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < original.getWidth(); x++) {
                int pixel = original.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                if (alpha > 0) {
                    return y;
                }
            }
        }
        return 0;
    }

    public static int findCropXEnd(BufferedImage original) {
        for (int x = original.getWidth() - 1; x >= 0; x--) {
            for (int y = 0; y < original.getHeight(); y++) {
                int pixel = original.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                if (alpha > 0) {
                    return x;
                }
            }
        }
        return 0;
    }
    
    public static BufferedImage getImage(String filename) throws IOException{
    	try {
    		return ImageIO.read(new File(filename));
    	} catch(IOException e) {
    		System.out.println("No image found!");
    		return null;
    	}
    }
    public static void cropImage(String filename, int number) throws IOException {
    	BufferedImage original = getImage(filename);
    	 int xStart = original.getWidth();
    	 int yStart = 0;
    	 int xEnd = 0;
    	 int yEnd = original.getHeight();
    	 
    	 String[] fileSplit = filename.split("_");
    	 String baseName = fileSplit[0].substring(0, fileSplit[0].length() - 1);
    	for (int i = 0; i < number; i++) {
    		 String currentFilename = baseName + i + "_" + fileSplit[1];
    	     original = getImage(currentFilename);

	        
	        
	        final int x = findCropX(original); 
	        final int y = findCropY(original);
	        final int xF = findCropXEnd(original);
	        final int yF = findCropYEnd(original);
	        
	        yStart = Math.max(yF, yStart);
	        xStart = Math.min(x, xStart);
	        yEnd = Math.min(y, yEnd);
	        xEnd = Math.max(xF, xEnd);
	        System.out.println("Edges " +  findCropXEnd(original));
    	}
        //System.out.println(yStart + " " + xEnd + " " + yEnd);
        int widthToCrop = Math.min(xEnd - xStart, original.getWidth());
        int heightToCrop = yStart - yEnd;

        System.out.println(widthToCrop);
        System.out.println(heightToCrop);
        for (int i = 0; i < number; i++) {

            String fileName = fileSplit[0].substring(0, fileSplit[0].length() - 1) + i + "_" + fileSplit[1];
            //System.out.println(fileName);
	        original = getImage(fileName);
	        String[] parts = fileName.split("\\.");
		    String newName = parts[0] + "cropped." + parts[1];
	        BufferedImage cropped = original.getSubimage(xStart, yEnd, widthToCrop, heightToCrop);
	        ImageIO.write(cropped, "png", new File(fileName)); 

        }
    }
    
    
    public static void cropSingleImage(String filename) throws IOException {
        BufferedImage original = getImage(filename);

        final int x = findCropX(original); 
        final int y = findCropY(original);
        final int xF = findCropXEnd(original);
        final int yF = findCropYEnd(original);

        
        int widthToCrop = xF - x;
        int heightToCrop = yF - y;
        System.out.println("Single " + xF);
        String[] parts = filename.split("\\.");
        String newName = parts[0] + "cropped." + parts[1];
        BufferedImage cropped = original.getSubimage(x, y, widthToCrop, heightToCrop);
        ImageIO.write(cropped, "png", new File(newName));
    }
    
    
    public static void printPixelVisibility(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            int width = image.getWidth();
            int height = image.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    int alpha = (pixel >> 24) & 0xff; // get alpha value
                    System.out.print(alpha + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
    	String filename = "Mushroom/Death/00_Death.png";
    	//ImageResizer size = new ImageResizer();
    	//size.resizeImage(filename);
    	//cropSingleImage("Sprites/Attack1/02_Attack1.png");
    	ImageResizer.cropImage(filename, 4);
    	printPixelVisibility("Mushroom/Death/00_Death.png");
    }
}
