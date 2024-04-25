import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

import Player.Player;
import Creature.*;

public class PlayerTest {

    private Player player;
    private Mushroom mushroom;
    private Goblin goblin;
    
    public PlayerTest() {
        this.player = new Player(100, 100);
        this.mushroom = new Mushroom(250, 609 - 55);
        this.goblin = new Goblin(250, 609 - 55);
    }

    @Test
    public void testIsOnTopOf_NotOnTop() {
        // Test when player is below mushroom
        player.setY(mushroom.getY() + mushroom.getHeight() + 1);
        assertFalse(player.isOnTopOf(mushroom));

        // Test when player is above mushroom but not touching
        player.setY(mushroom.getY() - player.getHeight() - 1);
        assertFalse(player.isOnTopOf(mushroom));

        // Test when player is to the left of mushroom
        player.setX(mushroom.getX() - player.getWidth() - 1);
        player.setY(mushroom.getY() - player.getHeight());
        assertFalse(player.isOnTopOf(mushroom));

        // Test when player is to the right of mushroom
        player.setX(mushroom.getX() + mushroom.getWidth() + 1);
        player.setY(mushroom.getY() - player.getHeight());
        assertFalse(player.isOnTopOf(mushroom));
    }
    
    
    @Test
    public void testIsLeftOf() {
        // Test when player is to the left of mushroom
        player.setX(mushroom.getX() - player.getWidth() - 1);
        assertTrue(player.isLeftOf(mushroom));

        // Test when player is to the right of mushroom
        player.setX(mushroom.getX() + mushroom.getWidth() + 1);
        assertFalse(player.isLeftOf(mushroom));

        // Test when player is above mushroom
        player.setX(mushroom.getX());
        player.setY(mushroom.getY() - player.getHeight() - 1);
        assertFalse(player.isLeftOf(mushroom));

        // Test when player is below mushroom
        player.setX(mushroom.getX());
        player.setY(mushroom.getY() + mushroom.getHeight() + 1);
        assertFalse(player.isLeftOf(mushroom));
    }
    
    @Test
    public void testIsRightOf() {
        // Test when player is to the right of mushroom
        player.setX(mushroom.getX() + mushroom.getWidth() + 1);
        assertTrue(player.isRightOf(mushroom));

        // Test when player is to the left of mushroom
        player.setX(mushroom.getX() - player.getWidth() - 1);
        assertFalse(player.isRightOf(mushroom));

        // Test when player is above mushroom
        player.setX(mushroom.getX());
        player.setY(mushroom.getY() - player.getHeight() - 1);
        assertFalse(player.isRightOf(mushroom));

        // Test when player is below mushroom
        player.setX(mushroom.getX());
        player.setY(mushroom.getY() + mushroom.getHeight() + 1);
        assertFalse(player.isRightOf(mushroom));
    }
    
    
    
    @Test
    public void testIsRightOf2() {
        // Test when player is to the right of mushroom
        player.setX(mushroom.getX() + mushroom.getWidth() + 1);
        assertTrue(player.isRightOf(mushroom));

        // Test when player is to the left of mushroom
        player.setX(mushroom.getX() - player.getWidth() - 1);
        assertFalse(player.isRightOf(mushroom));

        // Test when player is above mushroom
        player.setX(mushroom.getX());
        player.setY(mushroom.getY() - player.getHeight() - 1);
        assertFalse(player.isRightOf(mushroom));

        // Test when player is below mushroom
        player.setX(mushroom.getX());
        player.setY(mushroom.getY() + mushroom.getHeight() + 1);
        assertFalse(player.isRightOf(mushroom));

        // Test when player is on top of mushroom
        player.setX(mushroom.getX());
        player.setY(mushroom.getY() - player.getHeight());
        assertFalse(player.isRightOf(mushroom));
    }
    
    
    @Test
    public void testIsLeftOf_Goblin() {
        // Test when player is to the left of goblin
        player.setX(goblin.getX() - player.getWidth() - 1);
        assertTrue(player.isLeftOf(goblin));

        // Test when player is to the right of goblin
        player.setX(goblin.getX() + goblin.getWidth() + 1);
        assertFalse(player.isLeftOf(goblin));

        // Test when player is above goblin
        player.setX(goblin.getX());
        player.setY(goblin.getY() - player.getHeight() - 1);
        assertFalse(player.isLeftOf(goblin));

        // Test when player is below goblin
        player.setX(goblin.getX());
        player.setY(goblin.getY() + goblin.getHeight() + 1);
        assertFalse(player.isLeftOf(goblin));
    }

    @Test
    public void testIsRightOf_Goblin() {
        // Test when player is to the right of goblin
        player.setX(goblin.getX() + goblin.getWidth() + 1);
        assertTrue(player.isRightOf(goblin));

        // Test when player is to the left of goblin
        player.setX(goblin.getX() - player.getWidth() - 1);
        assertFalse(player.isRightOf(goblin));

        // Test when player is above goblin
        player.setX(goblin.getX());
        player.setY(goblin.getY() - player.getHeight() - 1);
        assertFalse(player.isRightOf(goblin));

        // Test when player is below goblin
        player.setX(goblin.getX());
        player.setY(goblin.getY() + goblin.getHeight() + 1);
        assertFalse(player.isRightOf(goblin));
    }

    @Test
    public void testIsOnTopOf_Goblin() {
        // Test when player is on top of goblin
        player.setX(goblin.getX());
        player.setY(goblin.getY() - player.getHeight());
        assertTrue(player.isOnTopOf(goblin));

        // Test when player is not on top of goblin
        player.setX(goblin.getX());
        player.setY(goblin.getY() + goblin.getHeight() + 1);
        assertFalse(player.isOnTopOf(goblin));
    }
    
    
    
    @Test
    public void testIsLeftOf_Goblin1() {
        // Test when player is to the left of goblin
        Goblin goblin = new Goblin(100, 100);
        player.setX(goblin.getX() - player.getWidth() - 1);
        assertTrue(player.isLeftOf(goblin));

        // Test when player is to the right of goblin
        player.setX(goblin.getX() + goblin.getWidth() + 1);
        assertFalse(player.isLeftOf(goblin));

        // Test when player is above goblin
        player.setX(goblin.getX());
        player.setY(goblin.getY() - player.getHeight() - 1);
        assertFalse(player.isLeftOf(goblin));

        // Test when player is below goblin
        player.setX(goblin.getX());
        player.setY(goblin.getY() + goblin.getHeight() + 1);
        assertFalse(player.isLeftOf(goblin));

        // Test when player is on top of goblin
        player.setX(goblin.getX());
        player.setY(goblin.getY() - player.getHeight());
        assertFalse(player.isLeftOf(goblin));
    }
    
    
    
}