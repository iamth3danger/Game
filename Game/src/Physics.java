import java.awt.event.KeyEvent;

public class Physics {
    private Player player;
    private Platform[] platforms;

    private final int POSITIVE_V = 10;
    private final int NEGATIVE_V = -10;
    private final int FINAL_VELOCITY = 20;
    private  int GRAVITY = 1;
    private boolean isOnSomething = false;
    
    public Physics(Player player, Platform[] platforms) {
        this.player = player;
        this.platforms = platforms;
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
                player.setVelocityY(NEGATIVE_V);
                y += player.getVelocityY();
                break;
            case KeyEvent.VK_DOWN:
                player.setVelocityY(POSITIVE_V);
                y += player.getVelocityY();
                break;
            case KeyEvent.VK_LEFT:
                player.setVelocityX(NEGATIVE_V);
                x += player.getVelocityX();
                break;
            case KeyEvent.VK_RIGHT:
                player.setVelocityX(POSITIVE_V);
                x += player.getVelocityX();
                break;
        }
        player.setX(x);
        player.setY(y);

        // Check for collisions with platforms
        
    }

    
    public void keyReleased(KeyEvent e) {
        player.setVelocityX(0);
        player.setVelocityY(0);
    }
    
    
    public void update() {
        int x = player.getX();
        int y = player.getY();

        player.setVelocityY(player.getVelocityY() + GRAVITY);
        if (player.getVelocityY() > FINAL_VELOCITY) {
            player.setVelocityY(FINAL_VELOCITY);
    
        y += player.getVelocityY();
        }
        player.setX(x);
        player.setY(y);

        // Check for collisions with platforms
        for (Platform platform : platforms) {
            if (player.isCollidingWith(platform)) {
                // Handle the collision
                // For example, you can prevent the player from moving into the platform
            	isOnSomething = true;
                //player.setX(x - player.getVelocity());
            	player.setVelocityY(0);
                player.setY(y - 10);
            }
        }
    }
}