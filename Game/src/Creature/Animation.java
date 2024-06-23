package Creature;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    private ArrayList<BufferedImage> images;
    private int currentIndex;
    private int delay;
    private long lastTime;
    private boolean isLooping;
    private Runnable onAnimationComplete;

    public Animation(ArrayList<BufferedImage> images, int delay, boolean isLooping, Runnable onAnimationComplete) {
        this.images = images;
        this.delay = delay;
        this.isLooping = isLooping;
        this.onAnimationComplete = onAnimationComplete;
        this.currentIndex = 0;
        this.lastTime = System.currentTimeMillis();
    }

    public BufferedImage getCurrentImage() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= delay) {
            currentIndex = (currentIndex + 1) % images.size();
            lastTime = currentTime;
            if (!isLooping && currentIndex == 0) {
                onAnimationComplete.run();
            }
        }
        return images.get(currentIndex);
    }

    public boolean isFinished() {
        return !isLooping && currentIndex == images.size() - 1 && System.currentTimeMillis() - lastTime >= delay;
    }

    public boolean hasFinished() {
        return !isLooping && currentIndex >= images.size() - 1;
    }
    
    public boolean atLastIndex() {
    	return currentIndex >= images.size() - 1;
    }
    
    public int getCurrentIndex() {
    	return currentIndex;
    }
    
    public int getSize() {
    	return images.size();
    }
    
    public void reset() {
        currentIndex = 0;
        lastTime = System.currentTimeMillis();
    }
}