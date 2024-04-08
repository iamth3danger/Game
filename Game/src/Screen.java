import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Screen {
    private JFrame frame;
    private Player player;
    private Platform[] platforms;
    private Physics physics;
    
    public Screen() {
        frame = new JFrame("Color Screen");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.player = new Player(100, 100, 48, 60);

        // Create a line of platforms across the bottom of the screen
        int platformWidth = 32;
        int platformHeight = 32;
        int numPlatforms = frame.getWidth() / platformWidth;
        platforms = new Platform[numPlatforms];
        for (int i = 0; i < numPlatforms; i++) {
            int x = i * platformWidth;
            int y = frame.getHeight() - 3 * platformHeight;
            platforms[i] = new Platform(x, y, platformWidth, platformHeight);
        }
        
        physics = new Physics(player, platforms);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                physics.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                physics.keyReleased(e);
            }
        });

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawImage(g);
            }
        };
        panel.setBackground(new Color(0, 0, 0)); // #B92FC7
        frame.add(panel);
    }

    public Physics getPhysics() {
        return physics;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        if (player.getImage() != null) {
            TexturePaint texturePaint = new TexturePaint(player.getImage(), new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight()));
            g2d.setPaint(texturePaint);
            g2d.fillRect(player.getX(), player.getY(), player.getImage().getWidth(), player.getImage().getHeight());
        }

        // Draw the platforms
        for (Platform platform : platforms) {
            if (platform.getImage() != null) {
                TexturePaint texturePaint = new TexturePaint(platform.getImage(), new Rectangle(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight()));
                g2d.setPaint(texturePaint);
                g2d.fillRect(platform.getX(), platform.getY(), platform.getImage().getWidth(), platform.getImage().getHeight());
            }
        }
    }

    public void background() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}