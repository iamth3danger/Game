package Boss;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import Creature.Animation;
import Entity.Entity;
import ImageHandler.ImageLoader;
import Living.*;
public class Mage extends Entity {
    private String animationFile;
    private Animation animation;
    private Timer timer;
    private AttackListener listener;
    private long createTime;
    private FireBallAnimation fireballAnimation;
    private Shadow shadow;
    private boolean shadowMade = false;
    private ShadowAttack shadowAttack;
    private AttackType currentAttack;
    private AttackType[] attackList = {AttackType.FIREBALL, AttackType.SHADOW};
    private int attackCounter = 0;
    private Attack attack;
    
    public Mage(int x, int y, AttackListener listener) {
        super(x, y);
        this.listener = listener;
        this.animationFile = "Boss/Mage/Stage1/mageStage1/00_mageStage1.png";
        setWidth(40);
        setHeight(40);
        init();
        //animate();
        currentAttack = AttackType.FIREBALL;
        attack = AttackFactory.createAttack(currentAttack, this);
        createTime = System.currentTimeMillis();
    }

   
    public AttackListener getListener() {
    	return listener;
    }
    private void init() {
        this.animation = new Animation(ImageLoader.loadImages(getAnimationFile()), 100, true, null);
    }

    public void update() {
    	attack.animation();
    	if (attack.isCompleted()) {
    		attackCounter++;
    		currentAttack = attackList[attackCounter % attackList.length];
    		setCurrentAttack(currentAttack);
    	}
    }
    
    private void setCurrentAttack(AttackType variation) {
    	attack = AttackFactory.createAttack(variation, this);
    }
    
    @Override
    public BufferedImage imageChecker() {
        return animation.getCurrentImage();
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

	
}