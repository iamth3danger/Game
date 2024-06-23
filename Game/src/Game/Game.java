package Game;


import java.awt.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

import Boss.FireBallListener;
import Boss.*;
import Creature.Creature;
import Creature.Reaper;
import DataStructures.Grid;
import Entity.Entity;
import Entity.EntityFactory;
import ImageHandler.TextConverter;
import Living.FireBall;
import Living.Living;
import Living.Shadow;
import Player.Physics;
import Player.Player;
import Screen.Screen;
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
    private List<Living> living = new ArrayList<Living>();
    private List<Moving> moves = new ArrayList<Moving>();
    private List<Creature> creaturesToAdd = new ArrayList<Creature>();
    private boolean running = true;
    private FireBall flame;
    private Thread gameThread;
    private FireBall[] fireballs = new FireBall[8];
    private Grid grid;
    private Shadow shadow;
    
    public Game(){
    	
    	this.player = new Player(100, 100);
    	//this.atttk = new ReaperAttack(400, 400);
    	entities.add(player);
    	
    	 /*
         for (int i = 0; i < 8; i++) {
             double angle = Math.PI / 4 * i;
             int x = (int) (100 + 50 * Math.cos(angle));
             int y = (int) (200 + 50 * Math.sin(angle));
             fireballs[i] = new FireBall(x, y);
             entities.add(fireballs[i]);
         }
         FireBall.findCenters(fireballs);
         for (FireBall fire : fireballs)
        	 fire.initAngle();
         // Now you can call the rotate method on each fireball
         */
    	this.mage = new Mage(100, 370, this);
    	mage.setVelocity(1, 2);
    	entities.add(mage);
    	
    	//this.flame = new FireBall(mage.getX() + mage.getWidth(), mage.getY());
    	//this.flame = new FireBall(100, 400);
    	//flame.setVelocity(1, 1);
    	//entities.add(flame);
    	//entities.add(atttk);
        // Create a 2D array of strings
    	String[][] level = TextConverter.convertTextFileToArray("level7.txt");
    	
    	for (int i = 0; i < level.length; i++) {
    	    for (int j = 0; j < level[i].length; j++) {
    	        if (!level[i][j].equals("-")) {
    	            int x = j * 32;
    	            int y = i * 32;
    	            Entity entity = EntityFactory.createEntity(level[i][j], x, y);
    	            
    	            
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
    	            // Check if the width of the entity is 16
    	            if (entity.getWidth() == 16) {
    	                // Create another entity 16 units to the right
    	                Entity secondEntity = EntityFactory.createEntity(level[i][j], x + 16, y);
    	                entities.add(secondEntity);
    	                
    	                if (secondEntity instanceof Moving) {
        	            	moves.add((Moving) secondEntity);
        	            }
    	            }
    	        }
    	    }
    	}
    	
        screen = new Screen(this);
       
        physics = new Physics(entities);
        screen.background();
        frame = screen.getFrame();
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
            //creaturesToAdd.clear(); // Clear the list at the beginning of each tick
            while (delta >= 1) {
                physics.update();
                mage.update();
                
                //flame.update();
                updateEntities();
                for (Iterator<Creature> iterator = creatures.iterator(); iterator.hasNext();) {
                    Creature creature = iterator.next();
                    creature.animate(plats);
                    
                    if (creature.getIsDying()) {
                        entities.remove(creature);
                        iterator.remove();
                    }
                    
                    
                    
                    
                }
                
                for (Iterator<Living> iterator = living.iterator(); iterator.hasNext();) {
                	Living living = iterator.next();
                		if (living.isBanished()) {
                			entities.remove(living);
                			iterator.remove();
                  	}
                }
                // Add the new creatures to the main list
                //creatures.addAll(creaturesToAdd);

                for (Moving move : moves) {
                    move.animate();
                }
                
              
                delta--;
            }

            // Check if the game is over
            if (physics.isGameOver()) {
                // Handle game over logic here
                break;
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

    public void stop() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void restart() {
        // Reset the game state
    	
        entities.clear();
        creatures.clear();
        plats.clear();
        moves.clear();
        
        // Create a new player
        this.player = new Player(100, 100);
        entities.add(player);

     // Load the level again
        String[][] level = TextConverter.convertTextFileToArray("level.txt");

        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[i].length; j++) {
                if (!level[i][j].equals("-")) {
                    int x = j * 32;
                    int y = i * 32;
                    Entity entity = EntityFactory.createEntity(level[i][j], x, y);

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
                        Entity secondEntity = EntityFactory.createEntity(level[i][j], x + 16, y);
                        entities.add(secondEntity);

                        if (secondEntity instanceof Moving) {
                            moves.add((Moving) secondEntity);
                        }
                    }
                }
            }
        }

        screen = new Screen(this);
        physics = new Physics(entities);
        screen.background();
        frame = screen.getFrame();
        running = true;
        run();
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
    public void onFireBallCreated(FireBall fireBall) {
        entities.add(fireBall);
        living.add(fireBall);
    }
    
    @Override
    public void onShadowCreated(Shadow shadow) {
    	entities.add(shadow);
    	living.add(shadow);
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}