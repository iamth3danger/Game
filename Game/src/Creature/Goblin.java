package Creature;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Goblin extends Creature {

	private String fileIdle;
    private String fileRun;
    private String file = "Goblin/Run/00_Run.png";
    private String fileDeath;
    private String fileAttack;
    private int IMAGESIZE = 8;
    
    public Goblin(int x, int y, int[] blockDimensions) {
        super(x, y, blockDimensions);
        fileIdle = "Goblin/Idle/00_Idle.png";
        fileRun = "Goblin/Run/00_Run.png";
        file = "Goblin/Run/00_Run.png";
        fileDeath = "Goblin/Death/00_Death.png";
        fileAttack = "Goblin/Attack/00_Attack.png";
        
        //WIDTH = 37; // Set width to 54 pixels
        //HEIGHT = 38;
        IMAGESIZE = 8;
        setDimensions(40,40);
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
    public int getImageSize() {
        return IMAGESIZE; // or whatever size is appropriate for a skeleton
    }
    
    @Override
    public String getFile() {
        return file;
    }
    
    
    @Override 
    public String getDeathFile() {
    	return fileDeath;
    }
    
    @Override
    public String textSymbol() {
        return "G";
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