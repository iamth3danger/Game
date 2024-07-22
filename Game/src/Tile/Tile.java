package Tile;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Entity;

public class Tile extends Entity {
    private final String file = "Tiles/Tile4.png";
    private BufferedImage image = null;
    public Tile(int x, int y) {
        super(x, y);
        this.WIDTH = 32;
        this.HEIGHT = 32;
        image = loadImage(file);
    }

    public Tile (Tile tile) {
    	super(tile);
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
    	return "*";
    }
}