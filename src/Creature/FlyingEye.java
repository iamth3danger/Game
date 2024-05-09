package Creature;

import java.util.List;

import Tile.Platform;

public class FlyingEye extends Creature{
	private String fileIdle;
	private String fileRun;
	private String file;
	private int IMAGESIZE;   
	
	public FlyingEye(int x, int y) {
        super(x, y);
        
        fileIdle = "Flying eye/Flight/00_Flight.png";
        fileRun = "Flying eye/Flight/00_Flight.png";
        file = "Flying eye/Flight/00_Flight.png";
        IMAGESIZE = 8;
        setDimensions(40, 31);
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
    public void animate(List<Platform> platforms) {
        if (getX() <= startingPositionX - 100 || getX() >= startingPositionX + 100) {
            setVelocityX(-getVelocityX()); 
        }
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
        return "E";
    }

}
