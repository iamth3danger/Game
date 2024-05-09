package Screen;
import javax.swing.*;

import Entity.Entity;
import Game.Game;
import Player.Physics;
import Player.Player;
import Tile.Platform;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Screen {
    private JFrame frame;
    private Player player;
    //private Platform[] platforms;
    private Physics physics;
    private Platform[][] platforms;
    private List<Platform> plats = new ArrayList<Platform>();
    private List<Entity> entities = new ArrayList<Entity>();
    private boolean isGameOverVisible = false;
    
    
    public Screen(Game game) {
    	this.entities = new ArrayList(game.getEntities());
    	if (this.entities == null) throw new NullPointerException("here");
    	this.player = (Player) this.entities.get(0);
        frame = new JFrame("Color Screen");
        frame.setSize(800, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    isGameOverVisible = !isGameOverVisible;
                    frame.repaint();
                } else {
                    game.getPhysics().keyPressed(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                game.getPhysics().keyReleased(e);
            }
        });

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (game.getPhysics().isGameOver()) {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
                    g.setColor(Color.RED);
                    g.setFont(new Font("Arial", Font.BOLD, 50));
                    g.drawString("Game Over", 350, 300);
                } else {
                    drawImage(g);
                }
            }
        };
        panel.setBackground(new Color(0, 0, 0)); // #B92FC7
        frame.add(panel);
    }



  
    
    public void updateEntities(List<Entity> entities) {
        this.entities = entities;
    }
    
    public void drawImage4(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Calculate the offset to keep the player in the center of the screen
        int offsetX = (frame.getWidth() - player.getWidth()) / 2 - player.getX();
        int offsetY = -32; // Set offsetY to -32

        // Adjust offsetY to center the player when it's at the top of the screen
        if (player.getY() < frame.getHeight() / 8) {
            offsetY = (frame.getHeight() - player.getHeight()) / 2 - player.getY();
            System.out.println(offsetY);
        }

        
        // Ensure that the screen stops moving in the Y direction when the player's y position plus the distance to the bottom of the screen is greater than 640
        int maxYOffset = 0 - player.getY() - player.getHeight();
        if (offsetY < maxYOffset) {
            offsetY = maxYOffset;
        }

        // Translate the graphics context
        g2d.translate(offsetX, offsetY);

        // Draw the platforms
        for (Entity entity : entities) {
            if (entity.getImage() != null) {
                TexturePaint texturePaint = new TexturePaint(entity.getImage(), new Rectangle(entity.getX(), entity.getY(), entity.getImage().getWidth(), entity.getImage().getHeight()));
                g2d.setPaint(texturePaint);
                g2d.fillRect(entity.getX(), entity.getY(), entity.getImage().getWidth(), entity.getImage().getHeight());
            }
        }
    }
    
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Calculate the offset to keep the player in the center of the screen
        int offsetX = (frame.getWidth() - player.getWidth()) / 2 - player.getX();
        int offsetY = -32; // Set offsetY to -32

        // Adjust offsetY to center the player when it's at the top of the screen
        if (player.getY() < offsetY + frame.getHeight() / 12) {
            offsetY = (frame.getHeight() - player.getHeight()) / 2 - player.getY();
        }

        // Ensure that the screen stops moving in the Y direction when the player's y position plus the distance to the bottom of the screen is greater than 640
        int maxYOffset = 0 - player.getY() - player.getHeight();
        if (offsetY < maxYOffset) {
            offsetY = maxYOffset;
        }

        // Translate the graphics context
        g2d.translate(offsetX, offsetY);

        // Draw the platforms
        for (Entity entity : entities) {
            if (entity.getImage() != null) {
                TexturePaint texturePaint = new TexturePaint(entity.getImage(), new Rectangle(entity.getX(), entity.getY(), entity.getImage().getWidth(), entity.getImage().getHeight()));
                g2d.setPaint(texturePaint);
                g2d.fillRect(entity.getX(), entity.getY(), entity.getImage().getWidth(), entity.getImage().getHeight());
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