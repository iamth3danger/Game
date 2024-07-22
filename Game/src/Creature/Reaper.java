package Creature;

public class Reaper extends Creature {
    private String fileIdle;
    private String fileRun;
    private String file;
    private String fileDeath;
    private String fileAttack;
    private int IMAGESIZE;


    public Reaper(int x, int y, int[] blockDimensions) {
        super(x, y, blockDimensions);
        //WIDTH = 54; // Set width to 54 pixels
        //HEIGHT = 54; // Set height to 54 pixels

        file = "DeathBringer/Idle/00_Idle.png";
        fileIdle = "DeathBringer/Walk/00_Walk.png";
        fileRun = "DeathBringer/Walk/00_Walk.png";
        fileDeath = "DeathBringer/Death/00_Death.png";
        fileAttack = "DeathBringer/Attack/00_Attack.png";
        IMAGESIZE = 8;
        setDimensions(54, 93);
        super.init();
       
    }

    @Override
    protected int getXModifier() {
    	if (getVelocityX() < 0) {
    		return getX() - 50;
    	}
    	else {
    		return getX();
    	}
    }

    @Override
	protected int getReverseXModifier() {
		// TODO Auto-generated method stub
    	
    	if (getVelocityX() < 0)
    		return getX() + 50;
    	else {
    		return getX();
    	}
	}
    
    @Override
    public String textSymbol() {
        return "R";
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
    public String getDeathFile() {
        return fileDeath;
    }

    @Override
    public int getImageSize() {
        return IMAGESIZE; // or whatever size is appropriate for a skeleton
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

	
}