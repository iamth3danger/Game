package Player;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Entity;
import ImageHandler.ImageLoader;

public class Player extends Entity {
	private final String file = "Idle1.png";
    private final String fileIdle = "Sprites/Idle/Idle1.png";
    private final String fileRun = "Sprites/Run/Run1.png";
    private int indexRun = 0;
    private int indexRunFlipped = 0;
    private int indexIdle = 0;
    private BufferedImage[] idleImages;
    private BufferedImage[] idleImagesFlipped;
    private BufferedImage[] runImages;
    private BufferedImage[] runImagesFlipped;
    private BufferedImage[] jumpImages;
    private BufferedImage[] jumpImagesFlipped;
    private BufferedImage[] fallImages;
    private BufferedImage[] fallImagesFlipped;
    private BufferedImage hitImage;
    
    private boolean isAttacking = false;
    private int indexAttack = 0;
    private BufferedImage[] attackImages;
    private BufferedImage[] attackImagesFlipped;
    private Sword sword;
    private int POSITIVE_V = 8;
    private int NEGATIVE_V = -8;
    private int JUMP_V = -19;
    private int X_DIRECTION_V = 8;
    private int health = 10000;
    private boolean isHurt = false;
    
    private CurrentAnimation currentAnimation = CurrentAnimation.IDLE;
    
    public int getX_DIRECTION_V() {
		return X_DIRECTION_V;
	}

	public void setX_DIRECTION_V(int x_DIRECTION_V) {
		X_DIRECTION_V = x_DIRECTION_V;
	}

	private PlayerLastMove move = PlayerLastMove.RIGHT;
    private boolean isInCollision = false;
    private int upKeyPressed = 0;
    
    public boolean isInCollision() {
		return isInCollision;
	}

	public void setInCollision(boolean isInCollision) {
		this.isInCollision = isInCollision;
	}

	public Player(int x, int y) {
        super(x, y);
        init();
    }

    public int getJUMP_V() {
		return JUMP_V;
	}

	public void setJUMP_V(int jUMP_V) {
		JUMP_V = jUMP_V;
	}

	private void init() {
        WIDTH = 45;
        HEIGHT = 60;

        hitImage = ImageLoader.loadImage("Hit/HitFrame.png");
        
        jumpImages = ImageLoader.loadImages("Sprites/Jump/Jump/00_Jump.png").toArray(new BufferedImage[0]);
        jumpImagesFlipped = ImageLoader.loadFlippedImages("Sprites/Jump/Jump/00_Jump.png").toArray(new BufferedImage[0]);
        
        fallImages = ImageLoader.loadImages("Sprites/Fall/00_Fall.png").toArray(new BufferedImage[0]);
        fallImagesFlipped = ImageLoader.loadFlippedImages("Sprites/Fall/00_Fall.png").toArray(new BufferedImage[0]);
        
        idleImages = new BufferedImage[10];
        for (int i = 0; i < 10; i++) {
            try {
                idleImages[i] = ImageIO.read(new File("Sprites/Idle/Idle" + (i+1) + ".png"));
            } catch (IOException e) {
                System.out.println("Error loading image: Sprites/Idle/Idle" + (i+1) + ".png");
                e.printStackTrace();
            }
        }
        
        idleImagesFlipped = new BufferedImage[10];
        for (int i = 0; i < 10; i++) {
            try {
                idleImagesFlipped[i] = ImageIO.read(new File("Sprites/Idle/Idle" + (i+1) + "Flipped.png"));
            } catch (IOException e) {
                System.out.println("Error loading image: Sprites/Idle/Idle" + (i+1) + "Flipped.png");
                e.printStackTrace();
            }
        }

        // Load run images
        runImages = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            try {
                runImages[i] = ImageIO.read(new File("Sprites/Run/Run" + (i+1) + ".png"));
            } catch (IOException e) {
                System.out.println("Error loading image: Sprites/Run/Run" + (i+1) + ".png");
                e.printStackTrace();
            }
        }
        
        runImagesFlipped = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            try {
                runImagesFlipped[i] = ImageIO.read(new File("Sprites/Run/Run" + (i+1) + "Flipped.png"));
            } catch (IOException e) {
                System.out.println("Error loading image: Sprites/Run/Run" + (i+1) + "Flipped.png");
                e.printStackTrace();
            }
        }
        
        
        attackImages = new BufferedImage[7];
        for (int i = 0; i < 7; i++) {
            try {
                attackImages[i] = ImageIO.read(new File("Sprites/Attack1/" + String.format("%02d", i) + "_Attack1.png"));
            } catch (IOException e) {
                System.out.println("Error loading image: Sprites/Attack1/" + String.format("%02d", i) + "_Attack1.png");
                e.printStackTrace();
            }
        }
        
        attackImagesFlipped = new BufferedImage[7];
        for (int i = 0; i < 7; i++) {
            try {
                attackImagesFlipped[i] = ImageIO.read(new File("Sprites/Attack1/" + String.format("%02d", i) + "_flipped_Attack1.png"));
            } catch (IOException e) {
                System.out.println("Error loading image: Sprites/Attack1/" + String.format("%02d", i) + "_flipped_Attack1.png");
                e.printStackTrace();
            }
        }
    }
    

    public void updateSword() {
        if (isAttacking) {
            makeSword();
        } else {
            destroySword();
        }
    }
    
    public Player(Player player) {
    	super(player);
    }
    
    public String getFile() {
        return fileIdle;
    }
    
    public String getFileRun() {
        return "Sprites/Moving/Moving1.png";
    }

    public String getFileIdle() {
        return "Sprites/Idle/Idle1.png";
    }
    
    public Sword getSword() {
    	if (sword == null) return null;
    	else return new Sword(sword);
    }
      
    public String textSymbol() {
    	return ":";
    }
    public boolean isOnTopOf(Entity other) {
    	return getY() + getHeight() >= other.getY() && getY() < other.getY() - (getHeight() - other.getHeight())
                && getX() <= other.getX() + other.getWidth() && getX() + getWidth() >= other.getX();
    }
    
    public boolean isRightOf(Entity other) { 
        return (getX() > other.getX()) && !(isOnTopOf(other));
    }

    public boolean isLeftOf(Entity other) {
        return (getX() < other.getX()) && !(isOnTopOf(other));
    }
    
    public void setAttacking(boolean attacking) {
        this.isAttacking = attacking;
    }

    public void makeSword() {
        if (sword == null && move == PlayerLastMove.RIGHT) {
            sword = new Sword(getX() + getWidth(), getY()); // Create a new sword in front of the player
        }
        else if (sword == null && move == PlayerLastMove.LEFT) {
        	sword = new Sword(getX() - 10, getY()); // Create a new sword in behind the player
        }
    }

    public void destroySword() {
        sword = null; // Destroy the sword
    }


    @Override
    public BufferedImage imageChecker() {
    	
    	if (isAttacking) {
            int frameCount = indexAttack / 50;
            indexAttack++;
            if (frameCount >= attackImages.length) {
                isAttacking = false;
                indexAttack = 0;
            }
            if(isHurt && frameCount % 2 == 1)
            	return hitImage;
            if (move == PlayerLastMove.RIGHT)
            	return attackImages[frameCount % attackImages.length];
            else
            	return attackImagesFlipped[frameCount % attackImages.length];
        } else if (getVelocityX() == 0 && getVelocityY() > 0) {
        	int frameCount = indexIdle / 150;
            indexIdle++;
            if(isHurt && frameCount % 2 == 1)
            	return hitImage;
            if (move == PlayerLastMove.RIGHT)
            	return idleImages[frameCount % runImages.length];
            else {
            	return idleImagesFlipped[frameCount % runImages.length];
            }
        }
            else if(getVelocityY() < 0) {
        	 indexIdle++;
        	 int frameCount = indexIdle / 150;
        	 if(isHurt && frameCount % 2 == 1)
             	return hitImage;
             if (move == PlayerLastMove.RIGHT)
             	return jumpImages[frameCount % jumpImages.length];
             else {
             	return jumpImagesFlipped[frameCount % jumpImages.length];
             }
            }
    	
    	
        else if (getVelocityX() > 0){
            // Return a different run image every few frames to slow down the animation.
            int frameCount = indexRun / 50;
            indexRun++;
            if(isHurt && frameCount % 2 == 1)
            	return hitImage;
            return runImages[frameCount % runImages.length];
        }
        else {
            int frameCount = indexRunFlipped / 50;
            indexRunFlipped++;
            if(isHurt && frameCount % 2 == 1)
            	return hitImage;
            return runImagesFlipped[frameCount % runImagesFlipped.length];
        }
    }

    public boolean isUnder(Entity other) {
        Rectangle playerRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle otherRect = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());
        return playerRect.getY() >= otherRect.getY() + otherRect.getHeight() && playerRect.intersects(otherRect);
    }
    
    public void setMove(PlayerLastMove move) {
    	this.move = move;
    }
    
    public PlayerLastMove getMove() {
    	return move;
    }
    
    public void jump() {
        upKeyPressed++;
        if (upKeyPressed == 1 && getVelocityY() >= 0 && isInCollision()) {
            setVelocityY(JUMP_V);
        } else {
            setVelocityY(JUMP_V);
        }
    }
    
    private enum CurrentAnimation{
    	RUNNING,
    	JUMPING,
    	IDLE,
    	ATTACKING,
    	FALLING;
    }

    public void moveLeft() {
        setMove(PlayerLastMove.LEFT);
        setVelocityX(-1 * X_DIRECTION_V);
    }

    public void moveRight() {
        setMove(PlayerLastMove.RIGHT);
        setVelocityX(X_DIRECTION_V);
    }

    public void attack() {
        setAttacking(true);
    }

    public void stopJump() {
        upKeyPressed = 0;
        setVelocityY(0);
    }

    public void stopMoving() {
        setVelocityX(0);
    }
    
    public int getHealth() {
    	return health;
    }
    
    public void takeHit() {
    	isHurt = true;
    	health--;
    	
    }
    
    
    
    public void noLongerHurt() {
    	isHurt = false;
    }
}