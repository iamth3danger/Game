package Tile;
import java.awt.image.BufferedImage;

public class RedTile extends Moving{

 protected int startingPositionX;
 
 private final String file = "Tiles/Tile6.png";
    private BufferedImage image = null;
    public RedTile(int x, int y) {
        super(x, y);
        this.WIDTH = 16;
        this.HEIGHT = 32;
        startingPositionX = x;
        setVelocityX(1);
        image = loadImage(file);
    }
    
    @Override
    public BufferedImage imageChecker() {
        return image;
    }
	    
    protected int getStartingPos() {
    	return startingPositionX;
    }
    
	public String textSymbol() {
		return "^";
	}
	
}
