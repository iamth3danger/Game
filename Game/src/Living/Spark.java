package Living;

import java.awt.image.BufferedImage;

import Creature.Animation;
import ImageHandler.ImageLoader;

public class Spark extends Living{
	private String animationFileA;
	private String animationFileB;
	
	private Animation smallSpark;
	private Animation bigSpark;


	private CurrentImage current = CurrentImage.BIG;

	public Spark(int x, int y) {
		super(x, y);
		this.animationFileA = "Boss/SmallSpark/SmallSpark/00_smallspark.png";
		this.animationFileB = "Boss/Spark/Spark/00_spark.png";
		this.setVelocity(2,	0);
		init();
	}

	@Override
	protected void init() {
		this.smallSpark = new Animation(ImageLoader.loadImages(getAnimationFiles()[0]), 100, true, null);
		this.bigSpark = new Animation(ImageLoader.loadImages(getAnimationFiles()[1]), 100, true, null);
	}
	
	@Override
	public String getAnimationFile() {
		// TODO Auto-generated method stub
		return animationFileA;
	}
	
	public String[] getAnimationFiles() {
		String[] animationFiles = {animationFileA, animationFileB};
		return animationFiles;
	}

	@Override
	public BufferedImage imageChecker() {
		if(current == CurrentImage.SMALL)
			return smallSpark.getCurrentImage();
		else
			return bigSpark.getCurrentImage();
	}
	
	public void ground() {
		banish(true);
	}
	
	@Override
	public String textSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFile() {
		// TODO Auto-generated method stub
		return animationFileA;
	}


	public CurrentImage getCurrent() {
		return current;
	}
	public void setCurrent(CurrentImage current) {
		this.current = current;
	}
	
	
}
