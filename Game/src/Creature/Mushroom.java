
package Creature;

import java.awt.image.BufferedImage;
import java.util.List;

import Tile.Platform;

public class Mushroom extends Creature{

	private String fileIdle;
	private String fileRun;
	private String fileDeath;
	private String fileAttack;
	private String file;
	private int IMAGESIZE;   
	
	
	public Mushroom(int x, int y, int[] blockDimensions) {
        super(x, y, blockDimensions);
        
        fileIdle = "Mushroom/Run/00_Run.png";
        fileRun = "Mushroom/Run/00_Run.png";
        file = "Mushroom/Run/00_Run.png";
        fileDeath = "Mushroom/Death/00_Death.png";
        fileAttack = "Mushroom/Attack/00_Attack.png";
        IMAGESIZE = 8;
        setDimensions(40,35);
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
    public String getDeathFile() {
    	return fileDeath;
    }

    @Override
    public String textSymbol() {
        return "M";
    }


    @Override
	public String getAttackFile() {
		// TODO Auto-generated method stub
		return fileAttack;
	}

	@Override
	protected int getHealthImageSize() {
		// TODO Auto-generated method stub
		return 0;
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
