package Creature;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Entity.Entity;

public class Reaper extends Creature {
    private int startingPositionX;
    private String fileIdle;
    private String fileRun;
    private String file;
    private String fileDeath;
    private String fileAttack;
    private int IMAGESIZE;
    private ArrayList<BufferedImage> attackImages;
    private ArrayList<BufferedImage>  runImages;
    private ArrayList<BufferedImage>  noHealthImages;
    private BufferedImage idleImage;
    private int indexAttack = 0;
    private int indexRun = 0;
    private int indexNoHealth = 0;
    private boolean isAttacking = false;
    private boolean isDying = false;

    public Reaper(int x, int y) {
        super(x, y);
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
    	if (getVelocityX() < 0)
    		return getX() - 50;
    	else {
    		return getX() - 138;
    	}
    }

    @Override
	protected int getReverseXModifier() {
		// TODO Auto-generated method stub
    	
    	if (getVelocityX() < 0)
    		return getX() + 50;
    	else {
    		return getX() + 50;
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