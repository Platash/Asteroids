package asteroids;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author AP
 * Static class, contains all the game graphics which are downloaded at the beginning.
 * Graphics are stored as final BufferedImage.
 */
public class Graphics {
    public static final BufferedImage[] STARS  = new BufferedImage[ObjectsController.STARS_COUNT];
    public static final BufferedImage[] MISTS = new BufferedImage[ObjectsController.MIST_COUNT];;
    public static final BufferedImage ASTEROIDS;
    public static final BufferedImage EXPLOSIONS;
    public static final BufferedImage SHIPS;
    public static final BufferedImage ROCKETS;
    public static final BufferedImage GAME_OVER;
    //Static relative path to graphics
    private static final String path = "graphics/";
    
    static {
        ASTEROIDS = downloadImage(path + ObjectsController.ASTEROID_NAME + "_0.png");
        EXPLOSIONS = downloadImage(path + ObjectsController.EXPLOSION_NAME + "_0.png");
        SHIPS = downloadImage(path + ObjectsController.SHIP_NAME + "_0.png");
        ROCKETS = downloadImage(path + ObjectsController.ROCKET_NAME + "_0.png");
        GAME_OVER = downloadImage(path + "game_over.png");
        
        for(int i = 0; i < ObjectsController.STARS_COUNT; i++) {
            STARS[i] = downloadImage(path + ObjectsController.STARS_NAME + "_" + i + ".png");
        }
        for(int i = 0; i < ObjectsController.MIST_COUNT; i++) {
            MISTS[i] = downloadImage(path + ObjectsController.MIST_NAME + "_" + i + ".png");
        }
    }
    
    /**
     * Downloads image using given path
     * @param path String 
     * @return BufferedImage downloaded image
     */
    public static BufferedImage downloadImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.printf("No %s file/n", path);
        }
        return img;
    }
}
