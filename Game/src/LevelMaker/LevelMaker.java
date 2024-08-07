package LevelMaker;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import Creature.Creature;
import Creature.Reaper;
import Entity.Entity;
import Entity.EntityFactory;
import Entity.EntityType;
import ImageHandler.TextConverter;
import Living.Living;
import Player.Physics;
import Player.Player;
import Tile.Moving;
import Tile.Platform;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
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
    private Thread gameThread;
    private WindowMode gameMode = WindowMode.EDIT;
    private Physics physics;
    private Player player;
    private String levelFile;
    
    public LevelMaker() {
        setPreferredSize(new Dimension(800, 640));
        setBackground(Color.BLACK);
        levelFile = "level9.txt";
        
        if(new File(levelFile).exists()) {
        	loadLevel(levelFile);
        }

        String[] menuItems = {"Door", "Platform", "Green Tile", "Red Tile", "Mushroom", "Reaper", "Goblin", "Flying Eye", "Skeleton", "FireBall"};
        
        JButton YVelocityUp = new BasicArrowButton(BasicArrowButton.NORTH);
        JButton YVelocityDown = new BasicArrowButton(BasicArrowButton.SOUTH);
        JButton XVelocityUp = new BasicArrowButton(BasicArrowButton.EAST);
        JButton XVelocityDown = new BasicArrowButton(BasicArrowButton.WEST);
        
        YVelocityUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setJUMP_V(player.getJUMP_V() - 1);	
			}
        	
        });
        
        YVelocityDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setJUMP_V(player.getJUMP_V() + 1);	
			}
        	
        });
        
        XVelocityUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setX_DIRECTION_V(player.getX_DIRECTION_V() + 1);	
			}
        	
        });
        
        XVelocityDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setX_DIRECTION_V(player.getX_DIRECTION_V() - 1);	
			}
        	
        });
        
        YVelocityUp.setVisible(false);
        YVelocityDown.setVisible(false);
        XVelocityUp.setVisible(false);
        XVelocityDown.setVisible(false);
        add(YVelocityUp);
        add(YVelocityDown);
        add(XVelocityUp);
        add(XVelocityDown);

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
        
        if (gameMode == WindowMode.GAME) {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                physics.keyPressed(e);
              
            }

            @Override
            public void keyReleased(KeyEvent e) {
                physics.keyReleased(e);
            }
        });
        }
        
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "show_menu");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "player");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "edit");
        
        
        

			actionMap.put("up", new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (gameMode == WindowMode.EDIT) {
						if (yOffset < 600) {
							yOffset += 32; // adjust the offset as needed
							repaint();
						}
					}
					else {
						player.jump();
					}
				}
	
			});
			
			
	        actionMap.put("upReleased", new AbstractAction() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		if (gameMode == WindowMode.GAME) {
	        			player.stopJump();
	        		}
	        	}
	        });
	        
	        actionMap.put("down", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	if (gameMode == WindowMode.EDIT) {
		            	if (yOffset > 0) {
		                    yOffset -= 32; // adjust the offset as needed
		                    repaint();
		            	}
	            	}
	            	else {
	            		System.out.println("Jumping Velocity " + player.getJUMP_V() + " X Velocity " + player.getX_DIRECTION_V());
	            	}
	            }
	        });
	
	        actionMap.put("left", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	if (gameMode == WindowMode.EDIT) {
		            	if (xOffset < 0) {
			                xOffset += 32; // adjust the offset as needed
			                repaint();
		            	}
	            	}
	            	else {
	            		//xOffset = (getWidth() - player.getWidth()) / 2 - player.getX();
	            		player.moveLeft();
	            	}
	            }
	        });
	        
	        
	        actionMap.put("leftReleased", new AbstractAction() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		if (gameMode == WindowMode.GAME) {
	        			System.out.println("stopped");
	        			System.out.println(player.getX());
	        			player.stopMoving();
	        		}
	        	}
	        });
	
	        actionMap.put("right", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	if (gameMode == WindowMode.EDIT) {
		                xOffset -= 32; // adjust the offset as needed
		                repaint();
	            	}
	            	else {
	            		//xOffset = (getWidth() - player.getWidth()) / 2 - player.getX();
	            		player.moveRight();
	            	}
	            }
	        });
	        
	        
	        actionMap.put("rightReleased", new AbstractAction() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		if (gameMode == WindowMode.GAME) {
	        			player.stopMoving();
	        		}
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
        
        actionMap.put("player", new AbstractAction() {
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		player = new Player(100, 200);
        		physics = new Physics(player, entities);
        		entities.add(player);
        		gameMode = WindowMode.GAME;
        		YVelocityUp.setVisible(true);
                YVelocityDown.setVisible(true);
                XVelocityUp.setVisible(true);
                XVelocityDown.setVisible(true);
        		run();
        	}
        });
        
        actionMap.put("edit", new AbstractAction() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		gameMode = WindowMode.EDIT;
        		if (player != null) {
        			entities.remove(player);
        		}
        		player = null;
        		YVelocityUp.setVisible(false);
                YVelocityDown.setVisible(false);
                XVelocityUp.setVisible(false);
                XVelocityDown.setVisible(false);
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
            	
            	if (entity.getImage() == null)
            		System.out.println("null instance of entity : " + entity.getFile() + " image");
            	
                TexturePaint texturePaint = new TexturePaint(entity.getImage(), new Rectangle(entity.getX() + xOffset, entity.getY() + yOffset, entity.getImage().getWidth(), entity.getImage().getHeight()));
                g2d.setPaint(texturePaint);
                g2d.fillRect(entity.getX() + xOffset, entity.getY() + yOffset, entity.getImage().getWidth(), entity.getImage().getHeight());
            }
            //yesButton.setVisible(false);
           // noButton.setVisible(false);
        }
    }
    
    private void loadLevel(String file) {
    	String[][] level = TextConverter.convertTextFileToArray(file);
    	
    	for (int i = 0; i < level.length; i++) {
    	    for (int j = 0; j < level[i].length; j++) {
    	        if (!level[i][j].equals("-")) {
    	            int x = j * 32;
    	            int y = i * 32;
    	            Entity entity = EntityFactory.createEntity(level[i][j], x, y, null);
    	            
    	            entities.add(entity);
    	           
        	            }
    	            }
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

            try (FileWriter out = new FileWriter(levelFile)) {
                out.write(level.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
	public void run() {
		gameThread = new Thread(() -> {
			long lastTime = System.nanoTime();
			double amountOfTicks = 60.0;
			double ns = 1000000000 / amountOfTicks;
			double delta = 0;
			long timer = System.currentTimeMillis();
			int frames = 0;

			while (gameMode == WindowMode.GAME) {
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				// creaturesToAdd.clear(); // Clear the list at the beginning of each tick
				while (delta >= 1) {
					physics.update();
					xOffset = (getWidth() - player.getWidth()) / 2 - player.getX();

					if (player.getY() > 700) {
						player.setX(100);
						player.setY(200);
					}
					// Repaint the frame
					repaint();

					if (System.currentTimeMillis() - timer > 1000) {
						timer += 1000;
						// System.out.println("FPS: " + frames);
						frames = 0;
					}
					
					delta--;
				}
			}
		});
		gameThread.start();
	}
    
    private enum WindowMode {
    	GAME,
    	EDIT
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