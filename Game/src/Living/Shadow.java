package Living;

import java.util.HashMap;
import java.util.Map;

public class Shadow extends Living{
	private String animationFile;
	private static final Map<Integer, Integer[]> indextoDimensions;
	static {
		indextoDimensions = new HashMap<>();
		indextoDimensions.put(0, new Integer[] {24, 5});
		indextoDimensions.put(1, new Integer[] {33, 6});
		indextoDimensions.put(2, new Integer[] {46, 6});
		indextoDimensions.put(3, new Integer[] {58, 6});
		indextoDimensions.put(4, new Integer[] {69, 6});
		for(int i = 5; i < 26; i++)
			indextoDimensions.put(i, new Integer[] {69, 17});
		indextoDimensions.put(26, new Integer[] {69, 28});
		indextoDimensions.put(27, new Integer[] {69, 31});
		indextoDimensions.put(28, new Integer[] {69, 38});
		indextoDimensions.put(29, new Integer[] {69, 43});
		indextoDimensions.put(30, new Integer[] {69, 48});
		indextoDimensions.put(31, new Integer[] {78, 58});
		indextoDimensions.put(32, new Integer[] {80, 62});
		indextoDimensions.put(33, new Integer[] {80, 66});
		indextoDimensions.put(34, new Integer[] {0, 0});
		indextoDimensions.put(35, new Integer[] {0, 0});
		for(int i = 36; i < 40; i++) {
			indextoDimensions.put(i, new Integer[] {80, 66});
		}
	}
	public Shadow(int x, int y) {
		super(x, y);
		animationFile = "Boss/Shadow/shadow/00_shadow.png";
		super.init();
		setVelocity(8, 0);
		setDimensions(24, 5);
	}

	@Override
	public String getAnimationFile() {
		// TODO Auto-generated method stub
		return animationFile;
	}

	public void update() {
		
		int index = getAnimation().getCurrentIndex();
		Integer[] dimensions = indextoDimensions.get(index);
		setDimensions(dimensions[0], dimensions[1]);
		
		moveEntity();
		if ((getX() > 700 && getVelocityX() > 0)
			|| (getX() < 40 && getVelocityX() < 0)) {
			setVelocityX(-getVelocityX());
		}
		
		if (getAnimation().getCurrentIndex() > 26) {
			setVelocityX(0);
			
		}
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
