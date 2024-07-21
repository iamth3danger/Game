package Tile;

import Entity.Entity;

public class Block {
	Platform[] platBlock;
	Moving[] moveBlock;
	int[] minMax;
	
	public Block(Platform [] platform) {
		this.platBlock = platform;
		this.minMax = findMinMax(platform);
	}
	
	public Block(Moving [] moving) {
		this.moveBlock = moving;
		this.minMax = findMinMax(moving);
	}
	
	private int[] findMinMax(Platform [] platform) {
		int min = platform[0].getX();
		int max = 0;
		
		for (Platform plat : platform) {
			if(min > plat.getX())
				min = plat.getX();
			if(max < plat.getX())
				max = plat.getX();
		}
		
		int[] minMax = {min, max};
		return minMax;
	}
	
	private int[] findMinMax(Moving[] moving) {
		int min = moving[0].getX();
		int max = 0;
		
		for (Moving move : moving) {
			if(min > move.getX())
				min = move.getX();
			if(max < move.getX())
				max = move.getX();
		}
		
		int[] minMax = {min, max};
		return minMax;
	}

	public void update() {
		
	}
	public int[] getMinMax() {
		return minMax;
	}
}
