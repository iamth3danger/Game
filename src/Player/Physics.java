package Player;
import java.awt.event.KeyEvent;
import java.util.*;

import Creature.Creature;
import Entity.Entity;
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
    private final int FINAL_VELOCITY = 20;
    private  int GRAVITY = 1;
    private boolean isOnSomething = false;
    private int upKeyPressed = 0;
    private boolean gameOver = false;
    
    public Physics(Player player, Platform[] platforms) {
        this.player = player;
        this.platforms = platforms;
    }
    
    public Physics(Player player, List<Platform> platforms) {
        this.player = player;
        this.plats = platforms;
    }
    
    public Physics(List<Entity> entities) {
    	this.entities = new ArrayList<Entity>(entities);
        this.player = (Player) this.entities.get(0);
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
	    int x = player.getX();
	    int y = player.getY();
	    switch (e.getKeyCode()) {
	        case KeyEvent.VK_UP:
	            upKeyPressed++;
	            if (upKeyPressed == 1 && player.getVelocityY() >= 0) {
	                player.setVelocityY(NEGATIVE_V * 3);
	            }
	            else {
	                player.setVelocityY(POSITIVE_V);
	            }
	            break;
	        case KeyEvent.VK_DOWN:
	            System.out.println(player.getX() + " " + player.getY());
	            break;
	        case KeyEvent.VK_LEFT:
	            player.setVelocityX(NEGATIVE_V);
	            break;
	        case KeyEvent.VK_RIGHT:
	            player.setVelocityX(POSITIVE_V);
	            break;
	        case KeyEvent.VK_C:
	            player.setAttacking(true);
	            break;
	    }

	    // Update player position
	  

	    // Check for collisions with platforms
	}

    
	public void keyReleased(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_UP) {
	        upKeyPressed = 0;
	        player.setVelocityY(0);
	    } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
	        player.setVelocityX(0);
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
    
    public void update() {
        gravity();
        player.updateSword();
        // Update player position
        int newX = player.getX() + player.getVelocityX();
        int newY = player.getY() + player.getVelocityY();

        // Check for collisions with platforms
        for (Entity entity : entities) {
        	if (player.getSword() != null) {
        		if ((entity instanceof Creature) && player.getSword().isCollidingWith(entity)) {
        			//System.out.println("yea!");
        			Creature creature = (Creature) entity;
        			creature.setHealth(0);
        		}
        	}
            if (!(entity instanceof Player) && player.isCollidingWith(entity)) {
                // Handle the collision
                isOnSomething = true;

                // Check if player is on top of entity
                if (player.isOnTopOf(entity)) {
                    // Don't move the player vertically
                    newY = entity.getY() - player.getHeight();
           
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

        // Update player position only if it's not on top of an entity
        player.setX(newX);
        player.setY(newY);

        if (player.getY() > 700) {
            gameOver = true;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
    

    
    
    
    
    
    
    
    
    
    
}