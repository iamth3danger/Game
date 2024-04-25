
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class Game {
    private Screen screen;
    private JFrame frame;
    private Player player;
    private Platform[] platforms;
    private Physics physics;
    private List<Platform> plats = new ArrayList<Platform>();
    private List<Entity> entities = new ArrayList<Entity>();
    
    public Game() {
    	this.player = new Player(100, 100, 48, 60);
    	entities.add(player);
        // Create a 2D array of strings
        String[][] level = TextConverter.convertTextFileToArray("Levels/level.txt");

    
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 125; j++) {
                if (level[i][j].equals("#")) {
                    int x = j * 32;
                    int y = i * 32;
                   entities.add( new Platform(x, y, 32, 32));
                    plats.add(new Platform(x, y, 32, 32));
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
                delta--;
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