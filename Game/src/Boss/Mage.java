package Boss;

import java.awt.image.BufferedImage;

import Creature.Animation;
import Entity.Entity;
import ImageHandler.ImageLoader;
import Player.Player;
public class Mage extends Entity {
    private String animationFile;
    private Animation animation;
    private Animation animationFlipped;
    private AttackListener listener;
    private long createTime;
    private long movementTimer;
    private boolean onLeftSide = true;
    private int leftPoint = 200;
    private int rightPoint = 600;
    private int centerX = getX() - 25;
    private int centerY = getY();
    private int radius = 25;
    private double angle = 0;
    
    private AttackType currentAttack;
    private AttackType[] attackList = {AttackType.SPARK};
    private int attackCounter = 0;
    private Attack attack;
    private Player player;
    public Mage(int x, int y, AttackListener listener, Player player) {
        super(x, y);
        this.listener = listener;
        this.animationFile = "Boss/Mage/Stage1/mageStage1/00_mageStage1.png";
        setWidth(40);
        setHeight(40);
        init();
        this.player = player;
        
        currentAttack = AttackType.SPARK;
        attack = AttackFactory.createAttack(currentAttack, this);
        createTime = System.currentTimeMillis();
        movementTimer = System.currentTimeMillis();

    }

   
    public AttackListener getListener() {
    	return listener;
    }
    private void init() {
        this.animation = new Animation(ImageLoader.loadImages(getAnimationFile()), 100, true, null);
        this.animationFlipped = new Animation(ImageLoader.loadFlippedImages(getAnimationFile()), 100, true, null);
    }

    public void update() {

    	//if (currentAttack == AttackType.SPARK) {
	    	 double newX = centerX + radius * Math.cos(5 * Math.PI * angle);
	         double newY = centerY + (2 * radius) * Math.sin(7 * Math.PI *angle);
	    	
	         setX((int) newX);
	         setY((int) newY);
	         
	         angle += .004;
	         
	         centerX += getVelocityX();
	         
	         sideSwitcher();
	    	
	    	attack.animation();
	
	    	if (attack.isCompleted()) {
	    		attackCounter++;
	    		currentAttack = attackList[attackCounter % attackList.length];
	    		setCurrentAttack(currentAttack);
	    	}
    	//}
   // 	else {
    		//System.out.println();
    	//}
    }
    
    private void sideSwitcher() {
    	if(sixSecondsPassed() && onLeftSide) {
    		setVelocityX(4);
    		if(atRightPoint()) {
    			setVelocityX(0);
    			movementTimer = System.currentTimeMillis();
    			onLeftSide = false;
    		}
    	
    	}
    	else if(sixSecondsPassed() && !onLeftSide) {
    		setVelocityX(-4);
    		if(atLeftPoint()) {
    			setVelocityX(0);
    			movementTimer = System.currentTimeMillis();
    			onLeftSide = true;
    		}
    	}
    }

    private boolean atLeftPoint() {
    	if(Math.abs(leftPoint - getX()) < 10)
    		return true;
    	else
    		return false;
    }
    private boolean sixSecondsPassed() {
    	if(System.currentTimeMillis() - movementTimer > 6000)
    		return true;
    	else
    		return false;
    }
    
    private boolean atRightPoint() {
    	if(Math.abs(rightPoint - getX()) < 10)
    		return true;
    	else
    		return false;
    }
    private void setCurrentAttack(AttackType variation) {
    	attack = AttackFactory.createAttack(variation, this);
    }
    
    

	@Override
	public String textSymbol() {
		// TODO Auto-generated method stub
		return "M";
	}

	@Override
	public String getFile() {
		// TODO Auto-generated method stub
		return "Boss/Mage/mageStage1/00_mageStage1.png";
	}
	
	public String getAnimationFile() {
		return animationFile;
	}
	
	public long getTime() {
		return createTime;
	}
	
	public void updateTime() {
		createTime = System.currentTimeMillis();
	}

	@Override
	public BufferedImage imageChecker() {
		BufferedImage currentImage = null;
		if(onLeftSide) {
			currentImage = animation.getCurrentImage();
		}
		else {
			currentImage = animationFlipped.getCurrentImage();
		}
		return currentImage;
	}
	
	
	
	
	
}