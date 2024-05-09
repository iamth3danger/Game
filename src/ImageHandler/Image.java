package ImageHandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image {
    public BufferedImage imgFlipper(BufferedImage image) {
        BufferedImage flippedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                flippedImage.setRGB(image.getWidth() - x - 1, y, image.getRGB(x, y));
            }
        }
        return flippedImage;
    }

    public static void main(String[] args) {
        try {
            Image image = new Image();
            for (int i = 0; i < 8; i++) {
            BufferedImage originalImage = ImageIO.read(new File("Sprites/Attack1/0" + i + "_Attack1.png"));
            BufferedImage flippedImage = image.imgFlipper(originalImage);
            ImageIO.write(flippedImage, "png", new File("Sprites/Attack1/0" + i + "_flipped_Attack1.png"));
            System.out.println("flipped");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

