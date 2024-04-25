import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Entity {
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage image;
    private int indexRun = 1;
    private int indexIdle = 1;
    private int velocityX = 0;
    private int velocityY = 0;
    private final String file = "Idle1.png";
    private final String fileIdle = "Sprites/Idle/Idle1.png";
    private final String fileRun = "Sprites/Run/Run1.png";
    
    
    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        //this.file = getFile();
        imageChecker();
    }

    public Entity(Entity entity) {
    	this.x = entity.getX();
    	this.y = entity.getY();
    	this.width = entity.getWidth();
    	this.height = entity.getHeight();
    	
    	entity.imageChecker();
    }
    
    public int getLeft() {
        return x;
    }

    public int getRight() {
        return x + width;
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
        imageChecker();
    }
    
    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocity) {
        this.velocityY = velocity;
    }
    
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTop() {
        return y;
    }

    public int getBottom() {
        return y + height;
    }
    
    public BufferedImage getImage() {
        return image;
    }

    public void imageChecker() {
    	
        if (getVelocityX() == 0) {
        	String file = "Sprites/Idle/Idle" + indexIdle + ".png";
            loadImage(file);
            indexIdle++;
            if (indexIdle == 11) indexIdle = 1;
        } else if (getVelocityX() > 0){
        	String file = "Sprites/Run/Run" + indexRun + ".png";
            loadImage(file);
            indexRun++;
            if (indexRun == 9) indexRun = 1;
        }
        else {
        	String file = "Sprites/Run/Run" + indexRun + "Flipped.png";
            loadImage(file);
            indexRun++;
            if (indexRun == 9) indexRun = 1;
        }
    }

    public void loadImage(String file) {
        try {
            image = ImageIO.read(new File(file));
        } catch (IOException e) {
        	System.out.println(file);
            e.printStackTrace();
        }
    }


    
    public abstract String getFile();
}