package Game;


import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;

import Boss.AttackListener;
import Boss.Mage;
import Creature.Creature;
import Creature.Reaper;
import DataStructures.Grid;
import Entity.Entity;
import Entity.EntityFactory;
import ImageHandler.TextConverter;
import Living.Door;
import Living.FireBall;
import Living.Living;
import Living.Shadow;
import Player.Physics;
import Player.Player;
import Screen.Screen;
import Tile.Block;
import Tile.GreenTile;
import Tile.Moving;
import Tile.Platform;

public class Game implements AttackListener {
    private Screen screen;
    private JFrame frame;
    public Player player;
    private Physics physics;
    private Mage mage;
    private List<Platform> plats = new ArrayList<Platform>();
    private List<Entity> entities = new CopyOnWriteArrayList<Entity>();
    private List<Creature> creatures = new ArrayList<Creature>();
    private List<Living> liveList = new ArrayList<Living>();
    private List<Moving> moves = new ArrayList<Moving>();
    private List<Creature> creaturesToAdd = new ArrayList<Creature>();
    private boolean running = true;
    private FireBall flame;
    private Thread gameThread;
    private FireBall[] fireballs = new FireBall[8];
    private Grid grid;
    private Shadow shadow;
    private CurrentLevel currentLevel;
    private ArrayList<Block> blocks = new ArrayList<Block>();
    
    public Game(){
    	currentLevel = CurrentLevel.LEVEL1;
    	
    	loadLevel("level9.txt");
    	    	
    	
        screen = new Screen(this);
       
        physics = new Physics(entities);
        screen.background();
        frame = screen.getFrame();
        System.out.println(entities.size());
    }

    public CurrentLevel getCurrentLevel() {
		return currentLevel;
	}

	public void run() {
    	gameThread = new Thread(() -> {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

       
        while (running) {
        	
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
               physics.update();
               if(currentLevel == CurrentLevel.BOSSLEVEL) {
               		mage.update();
               }
            
                updateEntities();
                for (Iterator<Creature> iterator = creatures.iterator(); iterator.hasNext();) {
                    Creature creature = iterator.next();
                    creature.animate(plats);
                    
                    if (creature.getIsDying()) {
                        entities.remove(creature);
                        iterator.remove();
                    }
                }
                
                for (Iterator<Living> iterator = liveList.iterator(); iterator.hasNext();) {
                	Living living = iterator.next();
                		if (living.isBanished()) {
                			entities.remove(living);
                			iterator.remove();
                  	}
                }
                for (Moving move : moves) {
                    move.animate();
                    if (move instanceof GreenTile)
                    	move.update();
                }
                delta--;
            }

           
           

            if (physics.toNextLevel()) {
            	loadLevel2();
            	physics.inNextLevel();
            }
            // Repaint the frame
            frame.repaint();

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
       });
    	gameThread.start();
    }

   
	public void reload() {
		if (getCurrentLevel() != CurrentLevel.BOSSLEVEL)
			loadLevel(LevelFactory.levelMaker(getCurrentLevel()));
		else
			loadLevel2();
		screen.newLevel(player);
		System.out.println(entities.size());
	}
    
    
    
    private void loadLevel(String levelName) {
    	
    	entities.clear();
    	moves.clear();
    	plats.clear();
    	creatures.clear();
    	
    	String[][] level = TextConverter.convertTextFileToArray(levelName);
    	
    	for (int i = 0; i < level.length; i++) {
    		ArrayList<Platform> platforms = new ArrayList<Platform>();
    		
    	    for (int j = 0; j < level[i].length; j++) {
    	    	if(level[i][j].equals("#")) {
    	    		int x = j * 32;
    	            int y = i * 32;
    	    		platforms.add((Platform) EntityFactory.createEntity(level[i][j], x, y, null));
    	    	}
    	    	else if (level[i][j].equals("-") && !platforms.isEmpty()) {
    	            blocks.add(new Block(platforms.toArray(new Platform[0])));
    	            platforms.clear();
    	    	}
    	    		
    	    }
    	    
    	    if (!platforms.isEmpty()) {
    	        blocks.add(new Block(platforms.toArray(new Platform[0])));
    	    }
    	    
    	}
    	
    	
    	for (int i = 0; i < level.length; i++) {
    	    for (int j = 0; j < level[i].length; j++) {
    	        if (!level[i][j].equals("-")) {
    	            int x = j * 32;
    	            int y = i * 32;
    	            int[] blockDimensions = null;
    	            String[] creatureStrings = {"M", "R", "G", "E", "S"};
    	            for (String str : creatureStrings)
    	            	if(level[i][j].equals(str))
    	            		blockDimensions = Block.findNearestBlock(blocks, x);
    	            
    	            
    	            Entity entity = EntityFactory.createEntity(level[i][j], x, y, blockDimensions);
    	            
    	            
    	            if (entity instanceof Reaper) {
    	            	entity.setY(entity.getY() - 180);
    	            }
    	            
    	            entities.add(entity);
    	            if (entity instanceof Creature) {
                        creatures.add((Creature) entity);
                    }
    	            
    	            if (entity instanceof Platform) {
                        plats.add((Platform) entity);
                    }
    	            
    	            if (entity instanceof Moving) {
    	            	moves.add((Moving) entity);
    	            }
    	            if (entity.getWidth() == 16) {
    	                Entity secondEntity = EntityFactory.createEntity(level[i][j], x + 16, y, null);
    	                entities.add(secondEntity);
    	                
    	                if (secondEntity instanceof Moving) {
        	            	moves.add((Moving) secondEntity);
        	            }
    	            }
    	        }
    	    }
    	}
    	
    	this.player = new Player(300, 100);
    	entities.add(player);
    	physics = new Physics(entities);
    	

    }
    
    public void loadLevel2() {
    	loadLevel("level7.txt");
    	updateEntities();
    	player.setX(100);
    	player.setY(300);
    	this.mage = new Mage(150, 250, this, player);
    	mage.setVelocity(0, 0);
    	entities.add(mage);
    	currentLevel = CurrentLevel.BOSSLEVEL;
    	screen.newLevel(player);
    }
    
    public void updateEntities() {
        screen.updateEntities(entities);
        physics.updateEntities(entities);
    }
    
    public Physics getPhysics() {
        return physics;
    }
    
    public List<Entity> getEntities() {
		return new ArrayList<Entity>(entities);
	}
    
    public void render() {
        Graphics g = frame.getGraphics();
        screen.drawImage(g);
        g.dispose();
    }


    @Override
    public void onLivingCreated(Living living) {
        entities.add(living);
        liveList.add(living);
    }
    

    
    
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}