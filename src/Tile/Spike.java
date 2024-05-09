package Tile;
import java.awt.image.BufferedImage;

import Entity.Entity;

public class Spike extends Entity {
    private final String file = "Tiles/Tile5.png";
    private  BufferedImage image = null;
    
    public Spike(int x, int y) {
        super(x, y);
        this.WIDTH = 32;
        this.HEIGHT = 32;
        
        image = loadImage(file);
    }

    public Spike (Spike spike) {
    	super(spike);
    }

    @Override
    public String getFile() {
        return file;
    }

    @Override
    public BufferedImage imageChecker() {
        return this.getImage();
    }
    
    @Override
    public String textSymbol() {
    	return "$";
    }
}