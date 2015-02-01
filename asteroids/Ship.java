
package asteroids;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author AP
 */
public class Ship extends FlyingObject{
    
    private static final int SPRITE_SIZE = 128;
    private static final int SPRITE_COUNT = 1;
    public static final int MAX_VELOCITY = 6;
    private static final int VELOCITY = 3;
    
    public Ship(int num) {
        super(num);
        this.alive = true;
        this.images = new BufferedImage[SPRITE_COUNT];
        this.activeImage = 0;
        this.frameCount = 0;
        this.hp = 10;
        this.radius = 64;
        this.mass = radius/10;
        this.collisions = new LinkedList<>();
        this.posX = Asteroids.ASTEROIDS.WIN_SIZE_X / 2;
        this.posY = Asteroids.ASTEROIDS.WIN_SIZE_Y / 2;
        this.posXPrev = posX;
        this.posYPrev = posY;
        this.velX = 0;
        this.velY = 0;
        initSprites();
    }
    
    private void initSprites() {
        for(int i = 0; i < SPRITE_COUNT; i++) {
            images[i] = Graphics.SHIPS.getSubimage(i*SPRITE_SIZE, 0, SPRITE_SIZE, SPRITE_SIZE);
        }
    }
    
    @Override
    public void move(int acceleration) {
        posYPrev = posY;
        posXPrev = posX;
        if(posX < radius) {
            velX = VELOCITY;
        }
        if(posX > Asteroids.ASTEROIDS.WIN_SIZE_X - radius) {
            velX = - VELOCITY;
        }
        if(posY < radius) {
            velY = VELOCITY;
        }
        if(posY > Asteroids.ASTEROIDS.WIN_SIZE_Y - radius) {
            velY = - VELOCITY;
        }
        posX += velX;
        posY += velY;
    }
    
    
    @Override
    public void reactOnCollision(FlyingObject ufo) {
        if(alive) {
            int velXTemp = 1;
            int velYTemp = 1;
            if(velX != 0) {
                velXTemp = velX;
            } 
            if(velY != 0) {
                velYTemp = velY;
            }
            int newVelX1 = (velXTemp * (mass - ufo.getMass()) + 2 * ufo.getMass() * ufo.getVelX()) 
                    / (mass + ufo.getMass());
            int newVelY1 = ((velYTemp) * (mass - ufo.getMass()) + 2 * ufo.getMass() * ufo.getVelY()) 
                    / (mass + ufo.getMass());
            int newVelX2 = (ufo.getVelX() * (ufo.getMass() - mass) + (2 * mass * velXTemp)) 
                    / (mass + ufo.getMass());
            int newVelY2 = (ufo.getVelY() * (ufo.getMass() - mass) + (2 * mass * velYTemp)) 
                    / (mass + ufo.getMass());

            hp -= ufo.getDamage();
            if(hp <= 0) {
                kill();
                return;
            }
            velX = newVelX1;
            velY = newVelY1;
            ufo.setVelX(newVelX2);
            ufo.setVelY(newVelY2);
        }
        
    }
    
    public void draw(Graphics2D g2, double interpolation) {
        if(alive) {
            int x = (int)((posX - posXPrev) * interpolation + posXPrev);
            int y = (int)((posY - posYPrev) * interpolation + posYPrev);
            g2.drawImage(images[activeImage], x - radius, y - radius, null);
        }
    }
    
    @Override
    public void kill(){
        alive = false;
    }
    
}
