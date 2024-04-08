
import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class Game {
    private Screen screen;
    private JFrame frame;
    private Player player;
    private Platform[] platforms;
    private Physics physics;
    
    public Game() {
        screen = new Screen();
        screen.background();
        frame = screen.getFrame();
        physics = screen.getPhysics();
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