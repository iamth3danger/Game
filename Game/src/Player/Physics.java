package Player;
import java.awt.event.KeyEvent;
import java.util.*;

import Creature.Creature;
import Creature.Mushroom;
import Creature.Reaper;
import DataStructures.Grid;
import Entity.Entity;
import Living.Door;
import Living.Lightning;
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
    
    
    public void update() {
        gravity();
        player.updateSword();
        // Update player position
        int newX = player.getX() + player.getVelocityX();
        int newY = player.getY() + player.getVelocityY();
        

        player.setInCollision(collision());
        // Check for collisions with platforms
        for (Entity entity : entities) {
            if (player.getSword() != null) {
                if ((entity instanceof Creature) && player.getSword().isCollidingWith(entity) && entity.isCollidable()) {
                    //System.out.println("yea!");
                    Creature creature = (Creature) entity;
                    creature.setHealth(0);
                }
                
                if (entity instanceof Lightning) {
                	System.out.println("lightlight");
                }
            }
            if (!(entity instanceof Player) && player.isCollidingWith(entity)) {
                // Handle the collision
                isOnSomething = true;
                
                if(entity instanceof Door) {
                	inDoor = true;
                	continue;
                }

                // Check if player is on top of entity
                if (player.isOnTopOf(entity)) {
                    // Don't move the player vertically
                    newY = entity.getY() - player.getHeight();
                    if (entity instanceof GreenTile) {
                    	GreenTile tile = (GreenTile) entity;
                    	tile.drop();
                    }
                }

                // Check if player is right of entity
                else if (player.isRightOf(entity)) {
                    //System.out.println("Right!");
                    newX = entity.getX() + entity.getWidth();
                }

                // Check if player is left of entity
                else if (player.isLeftOf(entity)) {
                    //System.out.println("Left!");
                    newX = entity.getX() - player.getWidth() + entity.getVelocityX();
                   
                }

                // Check if player is under entity
                else if (player.isUnder(entity)) {
                   // System.out.println("Under!");
                    newY = entity.getY() + player.getHeight();
                }
            }
            
           
        }

       


        player.setX(newX);
        player.setY(newY);

        if (player.getY() > 579) {
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