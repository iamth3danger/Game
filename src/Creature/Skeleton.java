package Creature;
public class Skeleton extends Creature {
    private String fileIdle;
	private String fileRun;
	private String file;
	private int IMAGESIZE;   
	
	public Skeleton(int x, int y) {
        super(x, y);
        
        fileIdle = "Skeleton/Idle/00_Idlecropped.png";
        fileRun = "Skeleton/Walk/00_Walkcropped.png";
        file = "Skeleton/Idle/00_Idlecropped.png";
        IMAGESIZE = 3;
        
        super.init();
    }
	
    @Override
    public String getIdleFile() {
        return fileIdle;
    }

    @Override
    public String getRunFile() {
        return fileRun;
    }

    
    @Override
    public String getFile() {
        return file;
    }

    @Override
    public int getImageSize() {
        return IMAGESIZE; // or whatever size is appropriate for a skeleton
    }

    @Override
    public String textSymbol() {
        return "S";
    }
}