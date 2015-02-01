package asteroids;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author AP
 */
public class Rocket extends FlyingObject {
    
    private final static int ROCKET_SPEED = 20;
    private final static int SPRITE_SIZE = 32;
    private final static int SPRITE_COUNT = 1;
    
    public Rocket(int number, double startPosX, double startPosY, double finX, double finY) {
        super(number);
        this.images = new BufferedImage[SPRITE_COUNT];
        this.activeImage = 0;
        this.damage = 2;
        this.radius = 16;
        this.posX = startPosX;
        this.posY = startPosY;
        this.posXPrev = startPosX;
        this.posYPrev = startPosY;
        
        double distX = finX - startPosX;
        double distY = finY - startPosY;
        double dist = Math.sqrt(distX * distX + distY * distY);
        this.velX = (int) ((distX / dist) * ROCKET_SPEED);
        this.velY = (int) ((distY / dist) * ROCKET_SPEED);
        
        initSprites();
    }

    /**
     * Initiates rocket graphic sprites. In current wersion there's only one sprite
     * due to luck of time for creating sophisticated graphics.
     */
    private void initSprites() {
        for(int i = 0; i < SPRITE_COUNT; i++) {
            images[i] = Graphics.ROCKETS.getSubimage(i*SPRITE_SIZE, 0, SPRITE_SIZE, SPRITE_SIZE);
        }
    }
    
    
    /**
     * Moves the rocket
     * @param acceleration 
     */
    @Override
    public void move(int acceleration) {
        posYPrev = posY;
        posXPrev = posX;
        //If rocket flies off the screen, alive is set to false 
        //and later object is being deleted.
        if(posX < - radius || posX > Asteroids.ASTEROIDS.WIN_SIZE_X + radius ||
            posY < - radius || posY > Asteroids.ASTEROIDS.WIN_SIZE_Y + radius) {
            alive = false;
        } else {
            posX += velX;
            posY += velY + acceleration;
        }
    }
    
    /**
     * Checks for collisions with the asteroid
     * @param ufo Asteroid to be checked for collision
     * @return true in case of collision, false otherwise.
     */
    @Override
    public boolean checkCollision(FlyingObject ufo) {
        double distance = (posX - ufo.getPosX()) * (posX - ufo.getPosX()) +
                            (posY - ufo.getPosY()) * (posY - ufo.getPosY());
                
        if ((int)distance < ((radius + ufo.getRadius())) * (radius + ufo.getRadius())) {
            return true;
        } 
       return false; 
    }
    
    @Override
    public void kill() {
        alive = false;
    }
    
    public void draw(Graphics2D g2, double interpolation) {
        if(alive) {
            int x = (int)((posX - posXPrev) * interpolation + posXPrev);
            int y = (int)((posY - posYPrev) * interpolation + posYPrev);
            g2.drawImage(images[activeImage], x - radius, y - radius, null);
        }
    }

    @Override
    public void reactOnCollision(FlyingObject ufo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
