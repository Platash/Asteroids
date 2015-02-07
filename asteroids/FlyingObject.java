package asteroids;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author AP
 */
public abstract class FlyingObject {
    
    protected int velX;
    protected int velY;
    protected double posX;
    protected double posY;
    protected double posXPrev;
    protected double posYPrev;
    protected LinkedList<Integer> collisions;
    protected int radius;
    protected int hp;
    protected int rotationSpeed;
    protected int activeImage;
    protected int frameCount;
    protected int mass;
    protected BufferedImage[] images;
    protected int number;
    protected boolean alive;
    protected int damage;
    
    public FlyingObject(int number) {
        this.number = number;
        this.alive = true;
    }
    
    /**
     * Adds info about another asteroid, with which current asteroid 
     * is colliding at the moment
     * @param ast second Asteroid, colliding with the current one.
     */
    public void addCollision(FlyingObject ufo) {
        collisions.add(ufo.getNumber());
    }
    
    /**
     * Removes info about asteroid, when it is out of collision.
     * @param ast second asteroid
     */
    public void removeCollision(FlyingObject ufo) {
        collisions.removeFirstOccurrence(ufo.getNumber());
    }
    
    
     /**
     * Checks for collisions with other asteroid using Pythagorean theorem
     * A^2 + B^2 = C^2.
     * If collision is taking place, info about another asteroid is being added to 
     * the LinkedList<String> collisions variable. Later it is being removed.
     * @param ufo second asteroid to check for collision with.
     * @return true if there is a collision, false if there is no collision
     */
    public boolean checkCollision(FlyingObject ufo) {
        double distanceSq = (posX - ufo.getPosX()) * (posX - ufo.getPosX()) +
                            (posY - ufo.getPosY()) * (posY - ufo.getPosY());
        //As asteroids and the ship are not perfectly round, I use /2 here, 
        //so they could overlap and make all the game look a bit nicer.
        if ((int)distanceSq <= ((radius + ufo.getRadius()) * (radius + ufo.getRadius()) / 2) 
                                && !collisions.contains(ufo.getNumber())) {
            addCollision(ufo);
            ufo.addCollision(this);
            return true;
        } else if(distanceSq > ((radius + ufo.getRadius()) * (radius + ufo.getRadius())) && 
                                collisions.contains(ufo.getNumber())) {
            removeCollision(ufo);
            ufo.removeCollision(this);
        }
       return false; 
    }
    
    public abstract void draw(Graphics2D g2, double interpolation);
    
    public abstract void move(int acceleration);
    
    public abstract void reactOnCollision(FlyingObject ufo);
    
    public abstract void kill();
    
    public int getNumber () {
        return this.number;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosXPrev() {
        return posXPrev;
    }

    public void setPosXPrev(double posXPrev) {
        this.posXPrev = posXPrev;
    }

    public double getPosYPrev() {
        return posYPrev;
    }

    public void setPosYPrev(double posYPrev) {
        this.posYPrev = posYPrev;
    }

    public LinkedList<Integer> getCollisions() {
        return collisions;
    }

    public void setCollisions(LinkedList<Integer> collisions) {
        this.collisions = collisions;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public int getActiveImage() {
        return activeImage;
    }

    public void setActiveImage(int activeImage) {
        this.activeImage = activeImage;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public BufferedImage[] getImages() {
        return images;
    }

    public void setImages(BufferedImage[] images) {
        this.images = images;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    
}
