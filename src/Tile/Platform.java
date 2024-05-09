package Tile;
import java.awt.image.BufferedImage;

import Entity.Entity;

public class Platform extends Entity {
    private final String file = "Tiles/Tile3.png";
   // public static final int WIDTH = 32; // Set platform width here
   // public static final int HEIGHT = 32; // Set platform height here
    private BufferedImage image = null;
    
    public Platform(int x, int y) {
        super(x, y);
        
        this.WIDTH = 32;
        this.HEIGHT = 32;
        
        this.image = loadImage(getFile());
    }

    public Platform (Platform platform) {
    	super(platform);
    }

    @Override
    public String getFile() {
        return file;
    }

    @Override
    public BufferedImage imageChecker() {
        return image;
    }
    
    @Override
    public String textSymbol() {
    	return "#";
    }
}