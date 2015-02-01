package asteroids;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author AP
 */
public class Explosion {
    
    private static final int VERSION_COUNT = 3;
    private static final int SPRITE_COUNT = 8;
    private static final int SPRITE_SIZE = 128;
    
    private BufferedImage[] images;
    private double posX;
    private double posY;
    private int activeImage;
    private int frameCount;
    private int radius;
    public volatile boolean alive;
    
    public Explosion(int radius, double posX, double posY) {
        this.alive = true;
        this.images = new BufferedImage[SPRITE_COUNT];
        this.frameCount = 0;
        this.radius = radius;
        this.posX = posX;
        this.posY = posY;
        Random ran = new Random();
        this.activeImage = 0;
        initSprites();
    }
    
    /**
     * Initiates sprites for the explosion
     */
    private void initSprites() {
        for(int i = 0; i < VERSION_COUNT; i++) {
            images[i]= Graphics.EXPLOSIONS.getSubimage(i*SPRITE_SIZE, 0, SPRITE_SIZE, SPRITE_SIZE);
        }
    }
    
    /**
     * Draws the next animation frame. If animation is over, sets alive to false.
     * @param g2 Graphics2D from the main GamePanel
     * @param interpolation 
     */
    public void draw(Graphics2D g2, double interpolation) {
        if(activeImage < SPRITE_COUNT) {
            frameCount++;
            g2.drawImage((images[activeImage]), (int)posX - radius, (int)posY - radius, null);
            if(frameCount % 4 == 0) {
                activeImage++; 
            }
        } else {
            alive = false;
        }
    }
    
}
