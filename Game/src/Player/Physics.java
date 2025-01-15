package Player;
import java.awt.event.KeyEvent;
import java.util.*;

import Boss.Mage;
import Creature.Creature;
import Creature.Mushroom;
import Creature.Reaper;
import DataStructures.Grid;
import Entity.Entity;
import Living.Door;
import Living.FireBall;
import Living.Lightning;
import Living.Living;
import Tile.GreenTile;
import Tile.Platform;
import Tile.RedTile;

public class Physics {
    private Player player;
    private Platform[] platforms;
    private List<Platform> plats = new ArrayList<Platform>();
    private List<Entity> entities = new ArrayList<Entity>();
    private int count = 0;
    private final int POSITIVE_V = 10;
    private final int NEGATIVE_V = -10;
    private final int FINAL_VELOCITY = 10;
    private  int GRAVITY = 1;
    private boolean isOnSomething = false;
    private int upKeyPressed = 0;
    private boolean gameOver = false;
    private boolean inDoor = false;
    private boolean nextLevel = false;
    private long invinsibilityClock;
    
    private Grid grid;
    
    public Physics(Player player, Platform[] platforms) {
        this.player = player;
        this.platforms = platforms;
    }
    
    public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

//	public Physics(Player player, List<Platform> platforms) {
//        this.player = player;
//        this.plats = platforms;
//    }
    
    public Physics(List<Entity> entities) {
    	this.entities = new ArrayList<Entity>(entities);
    	 for (Entity entity : entities) {
         	if (entity instanceof Player) {
         		this.player = (Player) entity;
         	}
         }
    }
    
    public Physics(Player player, List<Entity> entities) {
    	this.entities = new ArrayList<Entity>(entities);
        this.player = player;
    }
    
    public Physics(Grid grid, Player player) {
    	this.grid = grid;
        this.player = (Player) player;
    }
    
    public Physics(Physics physics) {
        this.player = physics.getPlayer();
        this.platforms = physics.getPlatforms();
    }
    public Player getPlayer() {
		return new Player(player);
	}
    
	public Platform[] getPlatforms() {
		return platforms;
	}

	public void keyPressed(KeyEvent e) {
	    switch (e.getKeyCode()) {
	        case KeyEvent.VK_UP:
	        	
	        	if(inDoor) {
	            	inDoor = false;
	            	nextLevel = true;
	            }
	        	else {
	        		player.jump();
	        	}
	            break;
	        case KeyEvent.VK_LEFT:
	            player.moveLeft();
	            break;
	        case KeyEvent.VK_RIGHT:
	            player.moveRight();
	            break;
	        case KeyEvent.VK_DOWN:
	        	System.out.println(player.getX() + " " + player.getY());
	        	break;
	        case KeyEvent.VK_C:
	            player.attack();
	            break;
	        case KeyEvent.VK_A:
	        	nextLevel = true;
	    }
	    // Update player position
	  
	}
	

	    // Check for collisions with platforms
	

	public boolean toNextLevel() {
		return nextLevel;
	}
	
	public void inNextLevel() {
		nextLevel = false;
	}
    
	public void keyReleased(KeyEvent e) {
	    switch (e.getKeyCode()) {
	        case KeyEvent.VK_UP:
	            player.stopJump();
	            break;
	        case KeyEvent.VK_LEFT:
	        case KeyEvent.VK_RIGHT:
	            player.stopMoving();
	            break;
	    }
	}
    
    public void gravity() {
    	
    	int x = player.getX();
        int y = player.getY();
        
        
    	player.setVelocityY(player.getVelocityY() + GRAVITY);
        if (player.getVelocityY() > FINAL_VELOCITY) {
            player.setVelocityY(FINAL_VELOCITY);
    
        y += player.getVelocityY();
        }
        player.setX(x);
        player.setY(y);
    }
    
    public void updateEntities(List<Entity> entities) {
        this.entities = entities;
    }
    
    public boolean collision() {
    	for (Entity entity : entities) {
    		if (!(entity instanceof Player) && player.isCollidingWith(entity)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private void setCollidable() {
    	invinsibilityClock = System.currentTimeMillis();
    	player.setCollidable(false);
    }
    
    public void update() {
    	//System.out.println(entities.size());
        gravity();
        player.updateSword();
        // Update player position
        int newX = player.getX() + player.getVelocityX();
        int newY = player.getY() + player.getVelocityY();
        
        if(!player.isCollidable()) {
        	if(System.currentTimeMillis() - invinsibilityClock >= 1000) {
        		player.setCollidable(true);
        		player.noLongerHurt();
        	}
        }

        player.setInCollision(collision());

        for (Entity entity : entities) {

            if (player.getSword() != null) {
                if ((entity instanceof Creature) && player.getSword().isCollidingWith(entity) && entity.isCollidable()) {

                	Creature creature = (Creature) entity;
                    creature.setHealth(0);
                }
                if ((entity instanceof Mage) && player.getSword().isCollidingWith(entity) && entity.isCollidable()) {

                	Mage mage = (Mage) entity;
                	if(!mage.isHurt())
                		mage.takeHit();
                }
                
            }
            if (!(entity instanceof Player) && player.isCollidingWith(entity) && entity.isCollidable() ) {
            	

            	isOnSomething = true;
                
                if(entity instanceof Door) {
                	inDoor = true;
                	continue;
                }
                
                if(!player.isOnTopOf(entity) && player.isCollidable() && player.getSword() != null) {
                	if(entity instanceof Creature || entity instanceof Living) {
                		player.takeHit();
                		setCollidable();
                	}
                }

                if (player.isOnTopOf(entity) && !(entity instanceof Living || entity instanceof Mage)) {

                	newY = entity.getY() - player.getHeight();
                    if (entity instanceof GreenTile) {
                    	GreenTile tile = (GreenTile) entity;
                    	tile.drop();
                    }
                    
                    if (entity instanceof Creature) {
                    	Creature creature = (Creature) entity;
                    	player.jump();
                    	creature.setHealth(0);
                    }
                }
                else if (player.isRightOf(entity) && !(entity instanceof Living || entity instanceof Mage)) {
                    newX = entity.getX() + entity.getWidth();
                }
                else if (player.isLeftOf(entity) && !(entity instanceof Living || entity instanceof Mage)) {
                    newX = entity.getX() - player.getWidth() + entity.getVelocityX();
                   
                }
                else if (player.isUnder(entity) && !(entity instanceof Living || entity instanceof Mage)) {
                    newY = entity.getY() + player.getHeight();
                }
            }
            
           
        }

       


        player.setX(newX);
        player.setY(newY);

        if (player.getY() > 579 || player.getHealth() == 0) {
//            System.out.println(player.getVelocityY());
//            System.out.println(player.isInCollision());
            gameOver = true;
        	//player.setX(100);
        	//player.setY(300);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
    

    
    
    
    
}