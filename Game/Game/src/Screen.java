import javax.swing.*;
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
    
    public Screen(Game game) {
    	this.entities = new ArrayList<Entity>(game.getEntities());
    	if (this.entities == null) throw new NullPointerException("here");
    	this.player = (Player) this.entities.get(0);
        frame = new JFrame("Color Screen");
        frame.setSize(800, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                game.getPhysics().keyPressed(e);
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
                drawImage(g);
            }
        };
        panel.setBackground(new Color(0, 0, 0)); // #B92FC7
        frame.add(panel);
    }


  
    
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Calculate the offset to keep the player in the center of the screen
        int offsetX = (frame.getWidth() - player.getWidth()) / 2 - player.getX();
        int offsetY = (frame.getHeight() - player.getHeight()) / 2 - player.getY();

        // Ensure that the screen stops moving in the Y direction when the player's y position plus the distance to the bottom of the screen is greater than 640
        int maxYOffset = 0 - player.getY() - player.getHeight();
        if (offsetY > maxYOffset) {
            offsetY = maxYOffset;
        }

        // Translate the graphics context
        g2d.translate(offsetX, -32);

        // Draw the platforms
       
        for (Entity entity : entities) {
            if (entity.getImage() != null) {
                TexturePaint texturePaint = new TexturePaint(entity.getImage(), new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight()));
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