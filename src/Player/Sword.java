package Player;

import java.awt.image.BufferedImage;

import Entity.Entity;

public class Sword extends Entity {
    public Sword(int x, int y) {
        super(x, y);
        WIDTH = 10;
        HEIGHT = 60;
    }
    
    public Sword(Sword sword) {
    	super(sword);
    	WIDTH = 10;
    	HEIGHT = 60;
    }
    
    public BufferedImage imageChecker() {
    	return null;
    }


	@Override
	public String textSymbol() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getFile() {
		// TODO Auto-generated method stub
		return null;
	}
}
