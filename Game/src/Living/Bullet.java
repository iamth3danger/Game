package Living;

public class Bullet extends Living {
    private String animationFile;

    public Bullet(int x, int y) {
        super(x, y);
        this.animationFile = "Boss/Bullet/bullet/00_bullet.png";
        super.init();
    }

    @Override
    public String textSymbol() {
        // implement this method
        return "B";
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