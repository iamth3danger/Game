package Boss;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import Creature.Animation;
import Entity.Entity;
import ImageHandler.ImageLoader;
import Living.FireBall;
import Living.FlameBall;

public class Mage extends Entity {
    private String animationFile;
    private Animation animation;
    private Timer timer;
    private FireBallListener listener;
    private long createTime;
    private FireBallAnimation fireballAnimation;

    public Mage(int x, int y, FireBallListener listener) {
        super(x, y);
        this.listener = listener;
        this.animationFile = "Boss/Mage/Stage1/mageStage1/00_mageStage1.png";
        setWidth(40);
        setHeight(40);
        this.fireballAnimation = new FireBallAnimation(this, listener);
        init();
        
        //animate();

        createTime = System.currentTimeMillis();
    }

   
    private void init() {
        this.animation = new Animation(ImageLoader.loadImages(getAnimationFile()), 100, true, null);
    }

    public void update() {
    	fireballAnimation.animation();	   	
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