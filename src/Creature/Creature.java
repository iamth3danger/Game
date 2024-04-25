package Creature;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Entity.*;
import Tile.Platform;

public abstract class Creature extends Entity {
    protected int startingPositionX;
    protected BufferedImage[] idleImages = new BufferedImage[getImageSize()];
    protected BufferedImage[] runImages = new BufferedImage[getImageSize()];
    protected BufferedImage[] runImagesFlipped = new BufferedImage[getImageSize()];
    protected BufferedImage idleImage = null;
    protected int indexIdle = 1;
    protected int indexRun = 1;
    protected int indexRunFlipped = 1;
    protected int health = 1;
    private BufferedImage[] noHealthImages;
	private int indexNoHealth = 0;
	private boolean isDying = false;
   private String fileIdle = "Goblin/Idle/00_Idle.png";
   private String fileRun = "Goblin/Run/00_Run.png";
    private String file = "Goblin/Run/00_Run.png";
    


    public Creature(int x, int y) {
        super(x, y);
 
    }

    protected void init() {
    	startingPositionX = getX();
    	indexIdle = 1;
    	indexRun = 1;
        this.setVelocityX(-2);
        
        loadIdleImages();
        loadRunImages();
        loadRunFlipped();
        
        for (BufferedImage image : runImages) {
            if (image == null) {
            	System.out.println(runImages.length);
                throw new NullPointerException("One or more images in 'runImages' array is null.");
            }
        }

        for (BufferedImage image : runImagesFlipped) {
            if (image == null) {
                throw new NullPointerException("One or more images in 'runImagesFlipped' array is null.");
            }
        }
    }

    @Override
    public String textSymbol() {
        return "C";
    }

    public void animate(List<Platform> platforms) {
        // Apply gravity
        setVelocityY(getVelocityY() + 2); // You can adjust the gravity value as needed

        // Move the creature
        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());

        // Check for collisions with platforms
        for (Platform platform : platforms) {
            if (isCollidingWith(platform)) {
                // If colliding, set Y velocity to zero
                setVelocityY(0);

                // Also, move the creature out of the platform to prevent sticking
                int newY = platform.getY() - getHeight();
                setY(newY);
            }
        }

        // Your original boundary check
        if (getX() <= startingPositionX - 100 || getX() >= startingPositionX + 100) {
            setVelocityX(-getVelocityX()); 
        }
    }
    
    public void animate1() {
        if (getX() <= startingPositionX - 100 || getX() >= startingPositionX + 100) {
            setVelocityX(-getVelocityX()); 
        }
        
        setX(getX() + getVelocityX());
    }
    
    public boolean isCollidingWith(Entity other) {
        Rectangle playerRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle otherRect = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());
        return playerRect.intersects(otherRect);
    }
    
    @Override
	public BufferedImage imageChecker() {
	    if (getHealth() == 0) {
	        int frameCount = indexNoHealth / 5000;
	        indexNoHealth++;
	        if (frameCount == noHealthImages.length - 1) { 
	        	isDying = true; 
	        	return noHealthImages[(frameCount - 1) % noHealthImages.length];
	        	}
	        return noHealthImages[frameCount % noHealthImages.length];
	    } else if (getVelocityX() == 0) {
	        return idleImage; 
	    } else if (getVelocityX() < 0){
	        int frameCount = indexRun / 500;
	        indexRun++;
	        return runImages[frameCount % runImages.length];
	    }
	    else {
	        int frameCount = indexRunFlipped / 500;
	        indexRunFlipped++;
	        return runImagesFlipped[frameCount % runImagesFlipped.length];
	    }
	}

    protected void loadIdleImages() {
    	idleImage = loadImage(getIdleFile());
    }
    
    
    protected void loadRunImages() {
	    	runImages = new BufferedImage[getImageSize()];
	        for (int i = 0; i < getImageSize(); i++) {
	        	String[] fileSplit = getRunHealthFile().split("_");
	            String fileName = fileSplit[0].substring(0, fileSplit[0].length() - 1) + i + "_" + fileSplit[1];
	            runImages[i] = loadImage(fileName);
	        }
	    }
	
    protected void loadDeathImages() {
    	runImages = new BufferedImage[getImageSize()];
        for (int i = 0; i < getHealthImageSize(); i++) {
        	String[] fileSplit = getRunFile().split("_");
            String fileName = fileSplit[0].substring(0, fileSplit[0].length() - 1) + i + "_" + fileSplit[1];
            runImages[i] = loadImage(fileName);
        }
    }
    public int getHealth() {
    	return health;
    }
    
    public void setHealth(int health) {
    	this.health = health;
    }
    protected void setDimensions(int width, int height) {
    	setWidth(width);
    	setHeight(height);
    }
    
    //public abstract int getWidth();
   // public abstract int getHeight();
    protected abstract int getHealthImageSize();
    protected abstract int getImageSize();
    public abstract String getIdleFile();
    public abstract String getRunFile(); 
    
    protected void loadRunFlipped() {
	    	runImagesFlipped = new BufferedImage[getImageSize()];
	        for (int i = 0; i < getImageSize(); i++) {
	        	String[] fileSplit = getRunFile().split("_");
	            String fileName = fileSplit[0].substring(0, fileSplit[0].length() - 1) + i + "_flipped_" + fileSplit[1];
	            runImagesFlipped[i] = loadImage(fileName);
	        }
	    }


    
   
    public String getFile() {
    	return this.file;
    }
}