package Creature;
public class Skeleton extends Creature {
    private String fileIdle;
	private String fileRun;
	private String file;
	private String fileDeath;
	private String fileAttack;
	
	private int IMAGESIZE;   
	
	public Skeleton(int x, int y, int[] blockDimensions) {
        super(x, y, blockDimensions);
        
        fileIdle = "Skeleton/Idle/00_Idlecropped.png";
        fileRun = "Skeleton/Walk/00_Walkcropped.png";
        file = "Skeleton/Idle/00_Idlecropped.png";
        fileDeath = "Skeleton/Death/00_Death.png";
        fileAttack = "Skeleton/Attack/00_Attack.png";
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
    public String getDeathFile() {
    	return fileDeath;
    }
    
    @Override
    public int getImageSize() {
        return IMAGESIZE; // or whatever size is appropriate for a skeleton
    }

    @Override
    public String textSymbol() {
        return "S";
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