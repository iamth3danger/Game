package Creature;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Goblin extends Creature {

	private String fileIdle;
    private String fileRun;
    private String file = "Goblin/Run/00_Run.png";
    private int IMAGESIZE = 8;
    
    public Goblin(int x, int y) {
        super(x, y);
        fileIdle = "Goblin/Idle/00_Idle.png";
        fileRun = "Goblin/Run/00_Run.png";
        file = "Goblin/Run/00_Run.png";
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
    public String textSymbol() {
        return "G";
    }
}