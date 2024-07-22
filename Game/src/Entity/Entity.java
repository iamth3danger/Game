package Entity;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Tile.Platform;
import Tile.Spike;
import Tile.Tile;

public abstract class Entity {
    private int x;
    private int y;
    public int WIDTH; // Set platform width here
    public int HEIGHT; // Set platform height here
    private BufferedImage image;
    //private int indexRun = 1;
  //  private int indexIdle = 1;
    private int velocityX = 0;
    private int velocityY = 0;
    private boolean isCollidable = true;
    
    
    public boolean isCollidable() {
		return isCollidable;
	}

	public void setCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}

	public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Entity(Entity entity) {
    	this.x = entity.getX();
    	this.y = entity.getY();
    	this.WIDTH = entity.getWidth();
    	this.HEIGHT = entity.getHeight();
    	
    	entity.imageChecker();
    }
    
   
    
    public void update() {
    	moveEntity();
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public int getLeft() {
        return x;
    }

    public int getRight() {
        return x + WIDTH;
    }

    public int getX() {
		return x;
	}

    public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocity) {
        this.velocityX = velocity;
        
    }
    
    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocity) {
        this.velocityY = velocity;
    }
    
	public int getWidth() {
		return WIDTH;
	}

	public void setWidth(int width) {
		this.WIDTH = width;
	}
	
	public int getHeight() {
		return HEIGHT;
	}

	
	public void setHeight(int height) {
		HEIGHT = height;
	}
	
	protected void setDimensions(int width, int height) {
		setWidth(width);
		setHeight(height);
	}

	public void setVelocity(int x, int y) {
		velocityX = x;
		velocityY = y;
	}
	
	public int getTop() {
        return y;
    }

    public int getBottom() {
        return y + HEIGHT;
    }
    
    public BufferedImage getImage() {
        return imageChecker();
    }

    public abstract BufferedImage imageChecker();
    
    public boolean isCollidingWith(Entity other) {
        Rectangle playerRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle otherRect = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());
        return playerRect.intersects(otherRect);
    }

    public BufferedImage loadImage(String file) {
        try {
            return ImageIO.read(new File(file));
        } catch (IOException e) {
            System.out.println("Error loading " + file);
            e.printStackTrace();
            return null;
        }
    }
  
    public void moveEntity() {
    	setX(getX() + getVelocityX());
    	setY(getY() + getVelocityY());
    }
    
    public abstract String textSymbol();
    
    public abstract String getFile();
}