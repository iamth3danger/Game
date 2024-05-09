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
    private boolean isOver = false;
    private long createTime;
    private FireBall[] fireballs;

    public Mage(int x, int y, FireBallListener listener) {
        super(x, y);
        this.listener = listener;
        this.animationFile = "Boss/Mage/Stage1/mageStage1/00_mageStage1.png";
        setWidth(40);
        setHeight(40);
        init();
        
        //animate();

        fireballs = new FireBall[8];
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI / 4 * i;
            int fireballX = (int) (getX() + (getWidth() / 2) + 50 * Math.cos(angle));
            int fireballY = (int) (getY() + (getHeight() / 2) + 50 * Math.sin(angle));
            fireballs[i] = new FireBall(fireballX, fireballY);
            listener.onFireBallCreated(fireballs[i]);
        }
        FireBall.findCenters(fireballs);
        for (FireBall fire : fireballs)
            fire.initAngle();
    }

    public void rotateFireballs() {
        for (FireBall fireball : fireballs) {
            fireball.rotate();
            if (System.currentTimeMillis() - fireball.getCreateTime() > 5000) {
                fireball.setVelocityX(0);
                fireball.setVelocityY(0);
            }
        }
    }

    private void init() {
        this.animation = new Animation(ImageLoader.loadImages(getAnimationFile()), 100, true, null);
    }

    public void update() {
        setX(getX() + getVelocityX());

        if (isOver) {
            setY(getY() + (getVelocityY() * getVelocityY()));
        } else {
            setY(getY() - (getVelocityY() * getVelocityY()));
        }

        if (getY() < 300) {
            isOver = true;
        }

        if (getY() > 450) {
            setY(450);
            isOver = false;
        }
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
	
	
	public void animate() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Create a new FireBall here
                FireBall fireBall = new FireBall(getX() + getWidth(), getY());
                fireBall.setVelocityX(2);
                listener.onFireBallCreated(fireBall);
            }
        }, 0, 1000); // 2000 milliseconds = 2 seconds
    }
	
}