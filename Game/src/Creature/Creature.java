package Creature;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Entity.*;
import ImageHandler.ImageLoader;
import Player.PlayerLastMove;
import Tile.Platform;

public abstract class Creature extends Entity {
    protected int startingPositionX;
    //protected BufferedImage[] idleImages = new BufferedImage[getImageSize()];
    //protected BufferedImage[] runImages = new BufferedImage[getImageSize()];
    //protected BufferedImage[] runImagesFlipped = new BufferedImage[getImageSize()];
    
    
    
    private Animation idleAnimation;
    private Animation runAnimation;
    private Animation runFlippedAnimation;
    private Animation noHealthAnimation;
    private Animation attackAnimation;
    private Animation attackFlippedAnimation;
    
    
    private BufferedImage idleImage = null;
    private int indexIdle = 1;
    private int indexRun = 1;
    private int indexRunFlipped = 1;
    private int health = 1;
    //private BufferedImage[] noHealthImages;
	private int indexNoHealth = 0;
	private boolean isDying = false;
   private String fileIdle = "Goblin/Idle/00_Idle.png";
   private String fileRun = "Goblin/Run/00_Run.png";
    private String file = "Goblin/Run/00_Run.png";
    private String fileDeath = "Goblin/Death/00_Death.png";
    private boolean isAttacking = false;
    private int indexAttack = 1;
    private long lastAttackTime = 0;
    private boolean isFlying = false;
    private ImageType currentImage;
    
    
    
    public boolean isFlying() {
		return isFlying;
	}

	public void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}




	private SideFacing side = SideFacing.RIGHT;
    
    public Creature(int x, int y) {
        super(x, y);
 
    }

    protected void init() {
    	startingPositionX = getX();
    	indexIdle = 1;
    	indexRun = 1;
        this.setVelocityX(-2);
        
        loadIdleImages();
        loadAnimations();
        
    }


    private void loadAnimations() {
        idleAnimation = new Animation(ImageLoader.loadImages(getIdleFile()), 100, true, null);
        runAnimation = new Animation(ImageLoader.loadImages(getRunFile()), 120, true, null);
        runFlippedAnimation = new Animation(ImageLoader.loadFlippedImages(getRunFile()), 120, true, null);
        noHealthAnimation = new Animation(ImageLoader.loadImages(getDeathFile()), 100, false, new Runnable() {
            @Override
            public void run() {
                isDying = true;
            }
        });
        attackAnimation = new Animation(ImageLoader.loadImages(getAttackFile()), 250, false, new Runnable() {
            @Override
            public void run() {
                isAttacking = false;
                setVelocityX(2); // Restore original velocity after attack is finished
                setX(getReverseXModifier());
            }
        });
        attackFlippedAnimation = new Animation(ImageLoader.loadFlippedImages(getAttackFile()), 250, false, new Runnable() {
            @Override
            public void run() {
                isAttacking = false;
                setVelocityX(-2); // Restore original velocity after attack is finished
                setX(getReverseXModifier());
            }
        });
    }
    
	@Override
    public String textSymbol() {
        return "C";
    }

	private enum AnimationState {
	    APPLY_GRAVITY,
	    MOVE,
	    CHECK_COLLISIONS,
	    CHECK_ATTACK,
	    CHECK_BOUNDARIES
	}
	
	private enum SideFacing {
		LEFT,
		RIGHT
	}

	public void animate(List<Platform> platforms)  {
	    for (AnimationState state : AnimationState.values()) {
	        switch (state) {
	            case APPLY_GRAVITY:
	                applyGravity();
	                break;
	            case MOVE:
	                move();
	                break;
	            case CHECK_COLLISIONS:
	                checkCollisions(platforms);
	                break;
	            case CHECK_ATTACK:
	                checkAttack();
	                break;
	            case CHECK_BOUNDARIES:
	                checkBoundaries();
	                break;
	        }
	    }
	}

	private void applyGravity() {
	    if (!isFlying()) {
	        setVelocityY(getVelocityY() + 2); // You can adjust the gravity value as needed
	    }
	}

	private void move() {
		 if (getVelocityX() < 0) {
	        	side = SideFacing.LEFT;
	        }
	        else {
	        	side = SideFacing.RIGHT;
	        }
	    setX(getX() + getVelocityX());
	    setY(getY() + getVelocityY());
	}

	private void checkCollisions(List<Platform> platforms) {
	    for (Platform platform : platforms) {
	        if (isCollidingWith(platform)) {
	            handleCollision(platform);
	        }
	    }
	}

	private void handleCollision(Platform platform) {
	    setVelocityY(0);
	    int newY = platform.getY() - getHeight();
	    setY(newY);
	}

	private void checkAttack() {
	    long currentTime = System.currentTimeMillis();
	 //   if (!isAttacking && currentTime - lastAttackTime >= 2000) { // 2000 milliseconds = 2 seconds
	        if ((getVelocityX() < 0 && Math.abs(getX() - (startingPositionX - 50)) < 4) || (getVelocityX() > 0 && Math.abs(getX() - (startingPositionX + 50)) < 4)) {
	            attacking();
	            lastAttackTime = currentTime;
	        }
	//    }
	}

	private void checkBoundaries() {
	    if (getX() <= startingPositionX - 100 || getX() >= startingPositionX + 100) {
	        setVelocityX(-getVelocityX()); 
	       
	    }
	}
    

	public boolean isCollidingWith(Entity other) {
        Rectangle playerRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle otherRect = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());
        return playerRect.intersects(otherRect);
    }
    
	@Override
	public BufferedImage imageChecker() {
	    if (isAttacking && getHealth() > 0) {
	        if (currentImage == ImageType.RUN || currentImage == ImageType.ATTACK) {
	        	currentImage = ImageType.RUN;
	            setVelocityX(0);
	            BufferedImage image = attackAnimation.getCurrentImage();
	            if (attackAnimation.atLastIndex()) {
	                isAttacking = false;
	                setVelocityX(2); // Restore original velocity after attack is finished
	                setX(getReverseXModifier());
	            }
	            return image;
	        } else if (currentImage == ImageType.RUNFLIPPED || currentImage == ImageType.ATTACKFLIPPED){
	        	currentImage = ImageType.RUNFLIPPED;
	            setVelocityX(0);
	            BufferedImage image = attackFlippedAnimation.getCurrentImage();
	            if (attackFlippedAnimation.atLastIndex()) {
	                isAttacking = false;
	                setVelocityX(-2); // Restore original velocity after attack is finished
	                setX(getReverseXModifier());
	            }
	            return image;
	        } else {
	        	return null;
	        }
	    } else if (getHealth() == 0) {
	        setCollidable(false);
	        System.out.println(isCollidable());
	        currentImage = ImageType.DEATH;
	        return noHealthAnimation.getCurrentImage();
	    } else if (getVelocityX() < 0) {
	    	currentImage = ImageType.RUNFLIPPED;
	        return runFlippedAnimation.getCurrentImage();
	    } else if (getVelocityX() > 0) {
	    	currentImage = ImageType.RUN;
	        return runAnimation.getCurrentImage();
	    } else {
	    	currentImage = ImageType.IDLE;
	        return idleAnimation.getCurrentImage();
	    }
	}

	private enum ImageType{
		RUN,
		RUNFLIPPED,
		ATTACK,
		ATTACKFLIPPED,
		DEATH,
		IDLE
	}

	protected void attacking() {
	    isAttacking = true;
	    setX(getXModifier());
	}
    
    protected void loadIdleImages() {
    	idleImage = loadImage(getIdleFile());
    }
    

    
    public boolean isAttacking() {
    	return isAttacking;
    }
    
    public int getHealth() {
    	return health;
    }
    
    public String getRunHealthFile() {
    	return fileDeath;
    }
    public void setHealth(int health) {
    	this.health = health;
    }
    protected void setDimensions(int width, int height) {
    	setWidth(width);
    	setHeight(height);
    }
    
    public boolean getIsDying() {
    	return isDying;
    }
    
    protected abstract int getHealthImageSize();
    protected abstract int getImageSize();
    public abstract String getIdleFile();
    public abstract String getRunFile(); 
    public abstract String getDeathFile(); 
    public abstract String getAttackFile();
    protected abstract int getXModifier();
    protected abstract int getReverseXModifier();
	

    
   
    public String getFile() {
    	return this.file;
    }
}