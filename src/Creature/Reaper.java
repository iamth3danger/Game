package Creature;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Entity.Entity;

public class Reaper extends Creature {
    private int startingPositionX;
    
    private String fileIdle;
   	private String fileRun;
   	private String file;

    private int IMAGESIZE;
    
    public Reaper(int x, int y) {
        super(x, y);
        WIDTH = 54; // Set width to 54 pixels
        HEIGHT = 54; // Set height to 54 pixels

        file = "DeathBringer/Idle/00_Idle.png";
        fileIdle = "DeathBringer/Walk/00_Walk.png";
        fileRun = "DeathBringer/Walk/00_Walk.png";
        IMAGESIZE = 8;
        setDimensions(54, 54);
        super.init();
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
    public int getImageSize() {
        return IMAGESIZE; // or whatever size is appropriate for a skeleton
    }

  
}