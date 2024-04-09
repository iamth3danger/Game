import java.awt.event.KeyEvent;
import java.util.*;

public class Physics {
    private Player player;
    private Platform[] platforms;
    private List<Platform> plats = new ArrayList<Platform>();
    private List<Entity> entities = new ArrayList<Entity>();
    
    private final int POSITIVE_V = 10;
    private final int NEGATIVE_V = -10;
    private final int FINAL_VELOCITY = 20;
    private  int GRAVITY = 1;
    private boolean isOnSomething = false;
    private int upKeyPressed = 0;
    
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
	                player.setVelocityY(NEGATIVE_V * 10);

	                if (player.getVelocityX() > 0) {
	                	player.setVelocityX(POSITIVE_V);
	                }
	                else if(player.getVelocityX() < 0) {
	                	player.setVelocityX(NEGATIVE_V);
	                }
	            }
	            else {
	            	player.setVelocityY(POSITIVE_V);
	                
	            }
	            break;
	        case KeyEvent.VK_DOWN:
	            // Do nothing
	            break;
	        case KeyEvent.VK_LEFT:
	            player.setVelocityX(NEGATIVE_V);
	            break;
	        case KeyEvent.VK_RIGHT:
	            player.setVelocityX(POSITIVE_V);
	            break;
	    }

	    // Update player position
	    x += player.getVelocityX();
	    y += player.getVelocityY();
	    player.setX(x);
	    player.setY(y);

	    // Check for collisions with platforms
	}

    
    public void keyReleased(KeyEvent e) {
    	 if (e.getKeyCode() == KeyEvent.VK_UP) {
    	        upKeyPressed = 0;
    	    }
        player.setVelocityX(0);
        player.setVelocityY(0);
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
    
    public void update() {
        gravity();

        // Check for collisions with platforms
       
        for (Entity entity : entities) {
            if (!(entity instanceof Player) && player.isCollidingWith(entity)) {
                // Handle the collision
                // For example, you can prevent the player from moving into the platform
            	isOnSomething = true;
               
            	if (player.isOnTopOf(entity)) {
            		System.out.println("On top!");
            	}
            	if (player.isRightOf(entity)){
            		System.out.println("Right!");
            		player.setX(entity.getX() - player.getWidth());
            	}
            	
            	if (player.isLeftOf(entity)) System.out.println("Left!");
            	if (player.isUnder(entity)) System.out.println("Under!");
            	
            	player.setVelocityY(0);
                player.setY(entity.getY() - player.getHeight());
            	
            }
        }
       
    }
    
    
    
    
    
    
    
    
    
    
    
}