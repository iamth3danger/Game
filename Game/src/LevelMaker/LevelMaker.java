package LevelMaker;
import javax.swing.*;

import Entity.Entity;
import Entity.EntityFactory;
import Entity.EntityType;
import Tile.Platform;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List; 
import java.awt.event.*;  
import java.io.*;

public class LevelMaker extends JPanel {
	private int yOffset = 0;
	private int xOffset = 0;
	private boolean saveScreenVisible = false;
    private List<Platform> platforms = new ArrayList<Platform>();
    private List<Entity> entities = new ArrayList<Entity>();
    private JButton yesButton;
    private JButton noButton;
    private Rectangle yesRect;
    private Rectangle noRect;
    private Color yesColor = Color.WHITE;
    private Color noColor = Color.WHITE;
    private JPopupMenu popupMenu = new JPopupMenu();
    private int menuCount = 0;
    private EntityType entityType = EntityType.PLATFORM;
    
    public LevelMaker() {
        setPreferredSize(new Dimension(800, 640));
        setBackground(Color.BLACK);

        String[] menuItems = {"Tile", "Platform", "Green Tile", "Red Tile", "Mushroom", "Reaper", "Goblin", "Flying Eye", "Skeleton"};

        for (String item : menuItems) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    entityType = EntityType.valueOf(item.toUpperCase().replace(" ", ""));
                }
            });
            popupMenu.add(menuItem);
        }
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!saveScreenVisible) {
                    int x = e.getX() - (e.getX() % 32) - xOffset;
                    int y = e.getY() - (e.getY() % 32) - yOffset;
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        addPlatform(x, y, entityType);
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                    	System.out.println("aaaa");
                        removePlatform(x, y);
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (saveScreenVisible) {
                    if (yesRect != null && yesRect.contains(e.getPoint())) {
                        yesColor = Color.BLUE;
                    } else {
                        yesColor = Color.WHITE;
                    }
                    if (noRect != null && noRect.contains(e.getPoint())) {
                        noColor = Color.BLUE;
                    } else {
                        noColor = Color.WHITE;
                    }
                    repaint();
                }
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!saveScreenVisible) {
                    if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                        int x = e.getX() - (e.getX() % 32) - xOffset;
                        int y = e.getY() - (e.getY() % 32) - yOffset;
                        boolean platformAlreadyExists = false;
                        for (Platform platform : platforms) {
                            if (platform.getX() == x && platform.getY() == y) {
                                platformAlreadyExists = true;
                                break;
                            }
                        }
                        if (!platformAlreadyExists) {
                            addPlatform(x, y, entityType);
                        }
                    } else if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0) {
                        int x = e.getX() - (e.getX() % 32) - xOffset;
                        int y = e.getY() - (e.getY() % 32) - yOffset;
                        removePlatform(x, y);
                    }
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (saveScreenVisible) {
                    if (yesRect != null && yesRect.contains(e.getPoint())) {
                    	saveLevelToFile(); 
                        saveScreenVisible = false;
                        repaint();
                    } else if (noRect != null && noRect.contains(e.getPoint())) {
                        saveScreenVisible = false;
                        repaint();
                    }
                }
            }
        });
        
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "show_menu");
        
        actionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (yOffset < 600) {
                    yOffset += 32; // adjust the offset as needed
                    repaint();
            	}
            }
        });

        actionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (yOffset > 0) {
                    yOffset -= 32; // adjust the offset as needed
                    repaint();
            	}
            }
        });

        actionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (xOffset < 0) {
	                xOffset += 32; // adjust the offset as needed
	                repaint();
            	}
            }
        });

        actionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xOffset -= 32; // adjust the offset as needed
                repaint();
            }
        });
        
        
        actionMap.put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saveScreenVisible) {
                    saveScreenVisible = false;
                } else {
                    saveScreenVisible = true;
                }
                repaint();
            }
        });
        
        actionMap.put("show_menu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
               menuCount++;
               if (menuCount % 2 != 0)
            	   popupMenu.setVisible(true);
               else
            	   popupMenu.setVisible(false);
            }
        }); 
        
    }
    

    private void addPlatform(int x, int y, EntityType entityType) {
        Entity platform = EntityFactory.createEntity(entityType, x, y);
        entities.add(platform);

        if (platform.getWidth() == 16) {
            Entity secondPlatform = EntityFactory.createEntity(entityType, x + 16, y);
            entities.add(secondPlatform);
        }

        repaint();
    }
    
    private void removePlatform(int x, int y) {
        List<Entity> entitiesToRemove = new ArrayList<Entity>();
        for (Entity entity : entities) {
            Rectangle bounds = entity.getBounds();
            if (x >= bounds.x && x <= bounds.x + bounds.width && y >= bounds.y && y <= bounds.y + bounds.height) {
                entitiesToRemove.add(entity);
            }
        }
        entities.removeAll(entitiesToRemove);
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (saveScreenVisible) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("SAVE?", 350, 300);
            g.setColor(yesColor);
            g.drawString("YES", 300, 350);
            g.setColor(noColor);
            g.drawString("NO", 400, 350);
            yesRect = new Rectangle(300, 320, 50, 20);
            noRect = new Rectangle(400, 320, 50, 20);
        } else {            
        	Graphics2D g2d = (Graphics2D) g.create();
            for (Entity entity : entities) {
                TexturePaint texturePaint = new TexturePaint(entity.getImage(), new Rectangle(entity.getX() + xOffset, entity.getY() + yOffset, entity.getWidth(), entity.getHeight()));
                g2d.setPaint(texturePaint);
                g2d.fillRect(entity.getX() + xOffset, entity.getY() + yOffset, entity.getImage().getWidth(), entity.getImage().getHeight());
            }
            //yesButton.setVisible(false);
           // noButton.setVisible(false);
        }
    }

    public void saveLevelToFile() {
        try {
            int maxX = 0;
            int maxY = 0;
            for (Entity entity : entities) {
                if (entity.getX() > maxX) {
                    maxX = entity.getX();
                }
                if (entity.getY() > maxY) {
                    maxY = entity.getY();
                }
            }
            System.out.println(platforms.size());
            maxX += 32; // add 32 to maxX and maxY to make sure the entire level is saved
            maxY += 32;

            StringBuilder level = new StringBuilder();
            for (int y = 0; y <= maxY; y += 32) {
                for (int x = 0; x <= maxX; x += 32) {
                    boolean platformFound = false;
                    for (Entity entity : entities) {
                        if (entity.getX() == x && entity.getY() == y) {
                            level.append(entity.textSymbol() + ",");
                            platformFound = true;
                            break;
                        }
                    }
                    if (!platformFound) {
                        level.append("-,");
                    }
                }
                level.append("\n");
            }

            try (FileWriter out = new FileWriter("level9.txt")) {
                out.write(level.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Level Maker");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new LevelMaker());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}