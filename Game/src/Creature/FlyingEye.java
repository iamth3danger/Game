package Creature;

import java.util.List;

import Tile.Platform;

public class FlyingEye extends Creature{
	private String fileIdle;
	private String fileRun;
	private String file;
	private String fileDeath;
	private String fileAttack;
	
	private int IMAGESIZE;   
	
	public FlyingEye(int x, int y) {
        super(x, y);
        
        fileIdle = "Flying eye/Flight/00_Flight.png";
        fileRun = "Flying eye/Flight/00_Flight.png";
        file = "Flying eye/Flight/00_Flight.png";
        fileDeath = "Flying eye/Death/00_Death.png";
        fileAttack = "Flying eye/Attack/00_Attack.png";
        IMAGESIZE = 8;
        setVelocityX(-2);
        setDimensions(40, 30);
        setFlying(true);
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
    public String getDeathFile() {
    	return fileDeath;
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

	@Override
	protected int getHealthImageSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String getAttackFile() {
		// TODO Auto-generated method stub
		return fileAttack;
	}

	@Override
	protected int getXModifier() {
		// TODO Auto-generated method stub
		return getX();
	}

	@Override
	protected int getReverseXModifier() {
		// TODO Auto-generated method stub
		return getX();
	}
	
	

}
