package Living;

public class Shadow extends Living{
	private String animationFile;
	
	public Shadow(int x, int y) {
		super(x, y);
		animationFile = "Boss/Shadow/shadow/00_shadow.png";
		super.init();
		setVelocity(8, 0);
	}

	@Override
	public String getAnimationFile() {
		// TODO Auto-generated method stub
		return animationFile;
	}

	public void update() {
		
		moveEntity();
		if ((getX() > 700 && getVelocityX() > 0)
			|| (getX() < 40 && getVelocityX() < 0)) {
			setVelocityX(-getVelocityX());
		}
		
		if (getAnimation().getCurrentIndex() > 26)
			setVelocityX(0);
	}
	
	@Override
	public String textSymbol() {
		// TODO Auto-generated method stub
		return "(";
	}

	public void light() {
		banish(true);
	}
	
	@Override
	public String getFile() {
		// TODO Auto-generated method stub
		return animationFile;
	}

}
