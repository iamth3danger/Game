
package Creature;

import java.awt.image.BufferedImage;
import java.util.List;

import Tile.Platform;

public class Mushroom extends Creature{

	private String fileIdle;
	private String fileRun;
	private String file;
	private int IMAGESIZE;   
	
	
	public Mushroom(int x, int y) {
        super(x, y);
        
        fileIdle = "Mushroom/Run/00_Run.png";
        fileRun = "Mushroom/Run/00_Run.png";
        file = "Mushroom/Run/00_Run.png";
        IMAGESIZE = 8;
        setDimensions(40,40);
        super.init();
        
        noHealthImages = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            String fileName = "Mushroom/Death/0" + i + "_Death.png";
            noHealthImages[i] = loadImage(fileName);
        }
    }
	
	
	
	@Override
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
	
    @Override
    public String getIdleFile() {
        return fileIdle;
    }

    @Override
    public String getRunFile() {
        return fileRun;
    }

    public boolean getIsDying() {
    	return isDying;
    }
    @Override
    public String getFile() {
        return file;
    }

    @Override
    public int getImageSize() {
        return IMAGESIZE; // or whatever size is appropriate for a skeleton
    }

    @Override
    public String textSymbol() {
        return "M";
    }
}
