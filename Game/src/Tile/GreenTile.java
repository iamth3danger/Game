package Tile;
import java.awt.image.BufferedImage;

import Game.Game;

public class GreenTile extends Moving{

 protected int startingPositionX;
 
 private final String file = "Tiles/Tile7.png";
    private BufferedImage image = null;
    public GreenTile(int x, int y) {
        super(x, y);
        this.WIDTH = 16;
        this.HEIGHT = 32;
        startingPositionX = x;
        setVelocityX(0);
        image = loadImage(getFile());
    }
	    
    @Override 
    public String getFile() {
    	return file;
    }
    
	public String textSymbol() {
		return "%";
	}
	
	protected int getStartingPos() {
    	return startingPositionX;
    }
	
	 @Override
    public BufferedImage imageChecker() {
        return image;
    }
	 
	 
	
	 public void drop() {
         setVelocityY(getVelocityY() + 3);
         setY(getVelocityY() + getY());
	 }
}
