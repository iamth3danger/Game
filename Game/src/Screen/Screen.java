package Screen;
import javax.imageio.ImageIO;
import javax.swing.*;

import Creature.Creature;
import Creature.Mushroom;
import Creature.Reaper;
import Entity.Entity;
import Game.Game;
import Player.Physics;
import Player.Player;
import Tile.Platform;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private Color continueColor = Color.WHITE;
    private Rectangle continueRect;
    private BufferedImage backgroundImage;
    
    
    public Screen(Game game) {
        this.entities = new ArrayList<Entity>(game.getEntities());
        if (this.entities == null) throw new NullPointerException("here");
        
        for (Entity entity : entities) {
        	if (entity instanceof Player) {
        		this.player = (Player) entity;
        	}
        }
     
        
        try {
        	backgroundImage = ImageIO.read(new File("background.png"));
        }
        catch(IOException e) {
        	 System.out.println("Error loading background image: " + e.getMessage());
             e.printStackTrace();
        }
        
        
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

                    // Draw "Continue" text
                    g.setColor(continueColor);
                    continueRect = new Rectangle(370, 380, 100, 40);
                    g.setFont(new Font("Arial", Font.BOLD, 30));
                    g.drawString("Continue", 370, 400);
                } else {
                	 if (backgroundImage != null) {
                         g.drawImage(backgroundImage, 0, 0, null); // Draw image at (0,0)
                     }
                    drawImage(g);
                }
            }

            @Override
            public void addMouseListener(MouseListener l) {
                super.addMouseListener(l);
            }

            @Override
            public void addMouseMotionListener(MouseMotionListener l) {
                super.addMouseMotionListener(l);
            }
        };

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                    if (continueRect != null && continueRect.contains(e.getPoint())) {
                        continueColor = Color.WHITE;
                    } else {
                        continueColor = Color.RED;
                    }
            
                    panel.repaint();
                
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (continueRect != null && continueRect.contains(e.getPoint())) {
                    // Restart the game
                
                   game.getPhysics().setGameOver(false);
                   game.reload();
                }
            }
        });
        
        panel.setBackground(new Color(0, 0, 0)); // #B92FC7
        frame.add(panel);
    }


    public void newLevel(Player player) {
    	this.player = player;
    }
  
    
    public void updateEntities(List<Entity> entities) {
        this.entities = entities;
    }
    
    public int getWidth() {
    	return frame.getWidth();
    }
    
    public int getHeight() {
    	return frame.getHeight();
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
                //TexturePaint texturePaint = new TexturePaint(entity.getImage(), new Rectangle(entity.getX(), entity.getY(), entity.getImage().getWidth(), entity.getImage().getHeight()));
                //g2d.setPaint(texturePaint);
                //g2d.fillRect(entity.getX(), entity.getY(), entity.getImage().getWidth(), entity.getImage().getHeight());
                if (entity instanceof Player || entity instanceof Platform) {
                	g2d.drawRect(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
                }
               
                	g2d.drawImage(entity.getImage(), entity.getX(), entity.getY(), entity.getImage().getWidth(), entity.getImage().getHeight(), null);
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