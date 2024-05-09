package Living;

public class FlameBall extends Living {
    private String animationFile = "Boss/FlameBall/flameball/00_flameball.png";

    public FlameBall(int x, int y) {
        super(x, y);
        animationFile = "Boss/FlameBall/flameball/00_flameball.png";
        super.init();
    }

   

    @Override
    public String textSymbol() {
        // implement this method
        return "F";
    }

    @Override
    public String getFile() {
        // implement this method
        return animationFile;
    }



	@Override
	public String getAnimationFile() {
		// TODO Auto-generated method stub
		return animationFile;
	}
}
