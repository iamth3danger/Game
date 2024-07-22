package Tile;
import java.awt.image.BufferedImage;

import Entity.Entity;

public abstract class Moving extends Entity{
	 protected int startingPositionX;
	 private final String file = "Tiles/Tile4.png";
	    private BufferedImage image = null;
	    public Moving(int x, int y) {
	        super(x, y);
	        
	    }

	    public Moving (Moving move) {
	    	super(move);
	    }

	    @Override
	    public String getFile() {
	        return file;
	    }

	    @Override
	    public BufferedImage imageChecker() {
	        return image;
	    }
	    
	    protected abstract int getStartingPos();
	    
	    public void animate() {
	        //System.out.println(getVelocityX());
	        if (getX() <= getStartingPos() - 100 || getX() >= getStartingPos() + 100) {
	            setVelocityX(-getVelocityX()); 
	        }
	        
	        setX(getX() + getVelocityX());
	    }
	    
	    public void updatePos() {
	    	moveEntity();
	    }

	    public abstract String textSymbol();
	    	
}
