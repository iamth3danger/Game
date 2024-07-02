package Living;

public class Lightning extends Living{
	private String animationFile;
	private int originalX;
	private int originalY;
	
	public Lightning(int x, int y) {
		super(x, y);
		animationFile = "Boss/Lightning/Lightning/00_Lightning.png";
		super.init();
		originalX = x;
		originalY = y;
		setHeight(144);
	}
	
	
	@Override
	public String getAnimationFile() {
		// TODO Auto-generated method stub
		return animationFile;
	}

	public void update() {
		if(getAnimation().getCurrentIndex() < 10) {
			//int multiplier = 4 - getAnimation().getCurrentIndex();
			setY(originalY - 8 * getHeight());
		}
		else setY(originalY);
	}
	
	@Override
	public String textSymbol() {
		// TODO Auto-generated method stub
		return "L";
	}

	public void stricken() {
		banish(true);
	}
	
	@Override
	public String getFile() {
		// TODO Auto-generated method stub
		return animationFile;
	}
	
}
