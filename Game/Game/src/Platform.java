
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Platform extends Entity {
    private final String file = "Tiles/Tile3.png";

    public Platform(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Platform (Platform platform) {
    	super(platform);
    }

    @Override
    public String getFile() {
        return file;
    }

    @Override
    public void imageChecker() {
        loadImage(file);
    }
}