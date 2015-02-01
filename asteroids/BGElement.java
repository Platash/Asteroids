package asteroids;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Background element. All it does is moving via Y vector with a given velocity.
 * @author AP
 */
public class BGElement {

    private int velX;
    private int velY;
    private double posX;
    private double posY;
    private double posXPrev;
    private double posYPrev;
    private int activeImage1;
    private int activeImage2;
    private double posX2;
    private double posY2;
    private double posX2Prev;
    private double posY2Prev;
    public Random random = new Random();
    public BufferedImage[] images;
    
    public BGElement(double posX, double posY, int velX, int velY, int count) {
        this.velX = velX;
        this.velY = velY;
        this.posX = posX;
        this.posY = posY;
        this.posXPrev = posX;
        this.posYPrev = posY;
        this.posX2 = posX;
        this.posX2Prev = posX;
        this.posY2 = posY - Asteroids.ASTEROIDS.WIN_SIZE_Y;
        this.posY2Prev = posY - Asteroids.ASTEROIDS.WIN_SIZE_Y;
        this.activeImage1 = random.nextInt(count);
        this.activeImage2 = random.nextInt(count);
    }
    
    public void draw(Graphics2D g2, double interpolation) {
        int bgX = (int)((posX - posXPrev) * interpolation + posXPrev);
         
        int bgY = (int)((posY - posYPrev) * interpolation + posYPrev);
         
        int bgX2 = (int)((posX2 - posX2Prev) * interpolation + posX2Prev);

        int bgY2 = (int)((posY2 - posY2Prev) * interpolation + posY2Prev);

        g2.drawImage(getImages()[activeImage1], bgX, bgY, null);
        g2.drawImage(getImages()[activeImage2], bgX2, bgY2, null);
    }
    
    public void move(int acceleration) {
        velX += acceleration;
        velY += acceleration;
        posYPrev = posY;
        posY2Prev = posY2;
        posY += velY;
        posY2 += velY;
        
        if (posY > Asteroids.ASTEROIDS.WIN_SIZE_Y){
            setPosY(- Asteroids.ASTEROIDS.WIN_SIZE_Y + posY2);
            
            posYPrev = posY - velY ;
            setActiveImage1(random.nextInt(getImages().length));
        }
        
        if (posY2 > Asteroids.ASTEROIDS.WIN_SIZE_Y){
            
            setPosY2(- Asteroids.ASTEROIDS.WIN_SIZE_Y + posY);
            posY2Prev = posY2 - velY ;
            setActiveImage2(random.nextInt(getImages().length));
        }
        velX -= acceleration;
        velY -= acceleration;
    }
    
    public void setImages(BufferedImage[] aImages) {
        setImages(aImages);
    }

    public BufferedImage[] getImages() {
        return images;
    }
    
    public BufferedImage getImage1() {
        return getImages()[activeImage1];
    }
    
    public BufferedImage getImage2() {
        return getImages()[activeImage2];
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public int getActiveImage1() {
        return activeImage1;
    }

    public int getActiveImage2() {
        return activeImage2;
    }

    public double getPosX2() {
        return posX2;
    }

    public double getPosY2() {
        return posY2;
    }

    public double getPosXPrev() {
        return posXPrev;
    }

    public double getPosYPrev() {
        return posYPrev;
    }

    public double getPosX2Prev() {
        return posX2Prev;
    }

    public double getPosY2Prev() {
        return posY2Prev;
    }

    public void setPosXPrev(double posXPrev) {
        this.posXPrev = posXPrev;
    }

    public void setPosYPrev(double posYPrev) {
        this.posYPrev = posYPrev;
    }

    public void setPosX2Prev(double posX2Prev) {
        this.posX2Prev = posX2Prev;
    }

    public void setPosY2Prev(double posY2Prev) {
        this.posY2Prev = posY2Prev;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setActiveImage1(int activeImage1) {
        this.activeImage1 = activeImage1;
    }

    public void setActiveImage2(int activeImage2) {
        this.activeImage2 = activeImage2;
    }

    public void setPosX2(double posX2) {
        this.posX2 = posX2;
    }

    public void setPosY2(double posY2) {
        this.posY2 = posY2;
    }
    
}
