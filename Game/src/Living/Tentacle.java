package Living;

public class Tentacle extends Living {
    private String animationFile = "Boss/Tentacles/tentacles/00_tentacles.png";

    public Tentacle(int x, int y) {
        super(x, y);
        animationFile = "Boss/Tentacles/tentacles/00_tentacles.png";
        super.init();
    }

    
    @Override
    public String textSymbol() {
        // implement this method
        return "T";
    }

    @Override
    public String getFile() {
        // implement this method
        return animationFile;
    }

	@Override
	public String getAnimationFile() {
		return animationFile;
	}
}