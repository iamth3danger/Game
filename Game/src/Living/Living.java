package Living;

import Entity.Entity;
import ImageHandler.ImageLoader;

import java.awt.image.BufferedImage;

import Creature.Animation;

public abstract class Living extends Entity {
    private Animation animation;
    private String animationFile;

    public Living(int x, int y) {
        super(x, y);
    }

    protected void init() {
        this.animation = new Animation(ImageLoader.loadImages(getAnimationFile()), 100, true, null);
    }

    public void update() {
        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());
    }

    @Override
    public BufferedImage imageChecker() {
        return animation.getCurrentImage();
    }

    public abstract String getAnimationFile();
    
}