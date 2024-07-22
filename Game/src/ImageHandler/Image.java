package ImageHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Image {
    public static BufferedImage imgFlipper(BufferedImage image) {
        BufferedImage flippedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                flippedImage.setRGB(image.getWidth() - x - 1, y, image.getRGB(x, y));
            }
        }
        return flippedImage;
    }

   public static void flipAllImages(String filename) {
	   String fileName = filename;
	   String[] fileSplit = filename.split("_");
	   File file = new File(fileName);
	   int i = 0;
	   
	   while(file.exists()) {
		   try {
			   int split = splitter(fileSplit[0].length(), i);
			   fileName = fileSplit[0].substring(0, split) + (i) + "_" + fileSplit[1];
			   file = new File(fileName);
			   BufferedImage flippedImage = imgFlipper(ImageIO.read(file));
			   String flipped = fileSplit[0].substring(0, split) + (i) + "_flipped_" + fileSplit[1];
			   ImageIO.write(flippedImage, "png", new File(flipped));
			   i++;
		   }
		   catch(IOException e) {
			   e.printStackTrace();
		   }
	   }
   }
   public static void ImageRenamer(String filename, String newFileName) {
	   String[] oldFileSplit = filename.split("\\d");
	   String[] newFileSplit = newFileName.split("_");
	   
	   File oldFile = new File(filename);
	   File newFile = new File(newFileName);
	 
	   int i = 0;
	   System.out.println(oldFile.toString());
	   if(!oldFile.exists()) System.out.println("File not found!");
	   while(oldFile.exists()) {
		   oldFile.renameTo(newFile);
		   i++;
		   int splitter = splitter(newFileSplit[0].length(), i);
		   System.out.println(oldFileSplit[0] + (i + 1) + oldFileSplit[1]);
		   newFile = new File(newFileSplit[0].substring(0, splitter) + (i) + "_" + newFileSplit[1]);
		   oldFile = new File(oldFileSplit[0] + (i + 1) + oldFileSplit[1]);
	   }
   }
 
   public static void renameFilesAfterDeleting(String filename, int numFiles) {
	   String[] fileSplit = filename.split("_");
	   String fileName = filename;
	   String nextName;
	   for (int i = 0; i <= numFiles; i++) {
		   int split = splitter(fileSplit[0].length(), i);
		   fileName = fileSplit[0].substring(0, split) + (i) + "_" + fileSplit[1];
		   File file = new File(fileName);
		   File newFile;
		   if(file.exists()) {
			   continue;
		   }
		   else {
			   int j = i;
			   int splut = splitter(fileSplit[0].length(), j);
			   nextName = fileSplit[0].substring(0, splut) + (j) + "_" + fileSplit[1];
			   newFile = new File(nextName);
			   while(!newFile.exists() && j < numFiles + 1) {
				   splut = splitter(fileSplit[0].length(), j);
				   nextName = fileSplit[0].substring(0, splut) + (j) + "_" + fileSplit[1];
				   newFile = new File(nextName);
				   System.out.println(newFile.toString());
				   j++;
			   }
			   int splitter = splitter(fileSplit[0].length(), i);
			   newFile.renameTo(new File(fileSplit[0].substring(0, splitter) + (i) + "_" + fileSplit[1]));
		   }
		   
		   
	   }
   }
    public static void ImageCombinerAllFiles(String filename, int combNumber) {
    	
    	BufferedImage originalImage;
    	String fileName = filename;
    	String[] fileSplit = filename.split("_");

    	File file = new File(fileName);
    	int i = 0;
		try {
			while(file.exists()) {
				originalImage = ImageIO.read(new File(fileName));
				BufferedImage combined = new BufferedImage(originalImage.getWidth(), combNumber * originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g = combined.createGraphics();
				for (int j = 0; j < combNumber; j++)
					g.drawImage(originalImage, 0, j * originalImage.getHeight(), originalImage.getWidth(), originalImage.getHeight(), null);
				String newFile = fileSplit[0].substring(0, fileSplit[0].length() - 1) + (i + 10) + "_" + fileSplit[1];
				ImageIO.write(combined, "png", new File(newFile));
				fileName = fileSplit[0].substring(0, fileSplit[0].length() - 1) + i + "_" + fileSplit[1];
				file = new File(fileName);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
    }
    
    public static void CombineTwoImages(String topFile, String bottomFile) throws IOException{
    	BufferedImage topImage = ImageIO.read(new File(topFile));
    	BufferedImage bottomImage = ImageIO.read(new File(bottomFile));
    	
    	BufferedImage combined = new BufferedImage(topImage.getWidth(), topImage.getHeight() + bottomImage.getHeight(), BufferedImage.TYPE_INT_RGB);
    	Graphics2D g = combined.createGraphics();
    	g.drawImage(topImage, 0, 0, topImage.getWidth(), topImage.getHeight(), null);
    	g.drawImage(bottomImage, 0, topImage.getHeight(), bottomImage.getWidth(), bottomImage.getHeight(), null);
    	
    	ImageIO.write(combined, "png", new File(bottomFile));
    }
    
    public static void ImageCombiner(String filename, int combNumber) throws IOException{
    	BufferedImage originalImage;
    	String fileName = filename;
    	
    	originalImage = ImageIO.read(new File(fileName));
		BufferedImage combined = new BufferedImage(originalImage.getWidth(), combNumber * originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = combined.createGraphics();
		for (int j = 0; j < combNumber; j++)
			g.drawImage(originalImage, 0, j * originalImage.getHeight(), originalImage.getWidth(), originalImage.getHeight(), null);
		ImageIO.write(combined, "png", new File(fileName));

    }
    public static int splitter(int strlength, int i) {
    	if(i < 10)
    		return strlength - 1;
    	else return strlength - 2;
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
    	
    	String fileName = "Sprites/Jump/Jump/00_Jump.png";
    	String newFile = "Boss/SmallSpark/SmallSpark/00_smallspark.png";
    	
    	Image.flipAllImages(fileName);
    	
    }
}


