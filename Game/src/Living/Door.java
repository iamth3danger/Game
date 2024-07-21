package Living;

import java.awt.image.BufferedImage;

public class Door extends Living{
	private String animationFile;
	private BufferedImage image;
	
	public Door(int x, int y){
		super(x, y);
		animationFile = "Door/Idle/00_Door.png";
		image = loadImage(animationFile);
		setWidth(84);
		setHeight(128);
		super.init();
	}

	@Override
	public String getAnimationFile() {
		// TODO Auto-generated method stub
		return animationFile;
	}

	@Override
	public String textSymbol() {
		// TODO Auto-generated method stub
		return "D";
	}
	
	 @Override
    public BufferedImage imageChecker() {
        return image;
    }

	@Override
	public String getFile() {
		// TODO Auto-generated method stub
		return animationFile;
	}
}
