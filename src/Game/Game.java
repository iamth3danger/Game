package Game;


import javax.swing.*;

import Creature.Creature;
import Creature.FlyingEye;
import Creature.Goblin;
import Creature.Mushroom;
import Creature.Skeleton;
import Entity.Entity;
import Entity.EntityFactory;
import ImageHandler.TextConverter;
import Player.Physics;
import Player.Player;
import Screen.Screen;
import Tile.Moving;
import Tile.Platform;
import Tile.RedTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class Game {
    private Screen screen;
    private JFrame frame;
    public Player player;
    private Skeleton skeleton;
    private Goblin goblin;
    private Mushroom mushroom;
    private Platform[] platforms;
    private FlyingEye fly;
    private Physics physics;
    private RedTile tile;
    private List<Platform> plats = new ArrayList<Platform>();
    private List<Entity> entities = new ArrayList<Entity>();
    private List<Creature> creatures = new ArrayList<Creature>();
    private List<Moving> moves = new ArrayList<Moving>();
    
    public Game() {
    	this.player = new Player(100, 100);
    
    	entities.add(player);
    
        // Create a 2D array of strings
    	String[][] level = TextConverter.convertTextFileToArray("level.txt");
    	String[][] level2 = TextConverter.convertTextFileToArray("level.txt");
    	
    	for (int i = 0; i < level.length; i++) {
    	    for (int j = 0; j < level[i].length; j++) {
    	        if (!level[i][j].equals("-")) {
    	            int x = j * 32;
    	            int y = i * 32;
    	            Entity entity = EntityFactory.createEntity(level[i][j], x, y);
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
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                physics.update();
                updateEntities();
                for (Iterator<Creature> iterator = creatures.iterator(); iterator.hasNext();) {
                    Creature creature = iterator.next();
                    creature.animate(plats);
                    
                    if (creature instanceof Mushroom) {
                    	Mushroom mush = (Mushroom) creature;
                    	if (mush.getIsDying()) {
	                        entities.remove(creature);
	                        iterator.remove();
                    	}
                    }
                }
                
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

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}