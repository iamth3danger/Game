import java.awt.*;

public class Player extends Entity {
	private final String file = "Idle1.png";
    private final String fileIdle = "Sprites/Idle/Idle1.png";
    private final String fileRun = "Sprites/Run/Run1.png";

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Player(Player player) {
    	super(player);
    }
    
    public String getFile() {
        return fileIdle;
    }
    
    public String getFileRun() {
        return "Sprites/Moving/Moving1.png";
    }

    public String getFileIdle() {
        return "Sprites/Idle/Idle1.png";
    }
    
    public boolean isCollidingWith(Entity other) {
        Rectangle playerRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle otherRect = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());
        return playerRect.intersects(otherRect);
    }
    
}