package asteroids;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author AP
 * 
 */
public class Asteroid extends FlyingObject{
    
    private static final int ASTER_RADIUS_MIN = 40;
    private static final int ASTER_RADIUS_MAX = 64;
    private static final int ASTER_SPEED_MIN = -10;
    private static final int ASTER_SPEED_MAX = 10;
    private static final int ASTER_ROT_SPEED_MIN = -8;
    private static final int ASTER_ROT_SPEED_MAX = 8;
    private static final int SPRITE_COUNT = 8;
    private static final int SPRITE_SIZE = 128;
    
    private final Random RANDOM = new Random();    
    
    /**
     *  Constructor
     */
    public Asteroid(int number) {
        super(number);
        this.images = new BufferedImage[SPRITE_COUNT];
        this.activeImage = 0;
        this.frameCount = 0;
        this.hp = 1;
        this.rotationSpeed = initRotationSpeed();
        this.collisions = new LinkedList<>();
        this.damage = 1;
        this.number = number;
        init();
    }
    
    /**
     * Initiates graphic sprites according to current radius
     */
    private void initSprites() {
        Random ran = new Random();
        int version = ran.nextInt(ObjectsController.ASTEROID_COUNT);
        for(int i = 0; i < SPRITE_COUNT; i++) {
            images[i] = scale(Graphics.ASTEROIDS.getSubimage(i*SPRITE_SIZE, 
                            version * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE));
        }
    }
    
    /**
     * 
     * @return BufferedImage to be drawn next (next rotation animation frame).
     */
    private BufferedImage rotate() {
        int temp = this.activeImage;
        this.frameCount ++;
        if(rotationSpeed < 0) {
            if(frameCount % rotationSpeed == 0) {
                frameCount = 0;
                if(activeImage > 0) {
                    activeImage--;
                } else {
                    activeImage = SPRITE_COUNT - 1;
                }
            } 
        } else if(rotationSpeed > 0) {
            if(frameCount % rotationSpeed == 0) {
                frameCount = 0;
                if(activeImage < SPRITE_COUNT - 1) {
                    activeImage++;
                } else {
                    activeImage = 0;
                }
            } 
        }
        return this.images[temp];
    }
    
    /**
     * Initiates new asteroid's start position, radius and speed
     * So that when asteroid goes off the screen, we just reusing it,
     * not creating a new object/thread
     */
    private void init() {
        this.setAlive(true);
        this.radius = initRadius();
        this.mass = this.radius/10;
        initSprites();
        Random ran = new Random();
        int random;
        random = ran.nextInt(4);
        switch (random) {
            case 0: initAsteroidLeft();
                break;
            case 1: initAsteroidRight();
                break;
            case 2: initAsteroidTop();
                break;
            case 3: initAsteroidBottom();
                break;
        }
        this.mass = radius/10;
    }
    
    /**
     * Scles image according to given radius
     * @param img BufferedImage to be scled
     * @return Scaled BufferedImage
     */
    private BufferedImage scale(BufferedImage img) {
            BufferedImage resizedImg = new BufferedImage(radius * 2, radius * 2, BufferedImage.TRANSLUCENT);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(img, 0, 0, radius * 2, radius * 2, null);
            g2.dispose();
            return resizedImg;
    }
    
    /**
     * Initiates random radius between min and max values.
     * @return int radius.
     */
    private int initRadius() {
        Random ran = new Random();
        return ran.nextInt(ASTER_RADIUS_MAX - ASTER_RADIUS_MIN) + ASTER_RADIUS_MIN;
    }
    
    /**
     * Initiates asteroid with a starting point at the left side of a screen.
     */
    private void initAsteroidLeft() {
        posX = 0 - radius;
        posY = getRandomDouble(0 - radius, Asteroids.ASTEROIDS.WIN_SIZE_Y + radius);
        posXPrev = posX;
        posYPrev = posY;
        velX = getRandomInt(1, ASTER_SPEED_MAX);
        velY = getRandomInt(ASTER_SPEED_MIN, ASTER_SPEED_MAX);
    }
    
    /**
     * Initiates asteroid with a starting point at the right side of a screen.
     */
    private void initAsteroidRight() {
        posX = Asteroids.ASTEROIDS.WIN_SIZE_X;
        posY = getRandomDouble(0 - radius, Asteroids.ASTEROIDS.WIN_SIZE_Y + radius);
        posXPrev = posX;
        posYPrev = posY;
        velX = getRandomInt(ASTER_SPEED_MIN, -1);
        velY = getRandomInt(ASTER_SPEED_MIN, ASTER_SPEED_MAX);
    }
    
    /**
     * Initiates asteroid with a starting point at the top of a screen.
     */
    private void initAsteroidTop() {
        posX = getRandomDouble(0 - radius, Asteroids.ASTEROIDS.WIN_SIZE_X + radius);
        posY = 0 - radius;
        posXPrev = posX;
        posYPrev = posY;
        velX = getRandomInt(ASTER_SPEED_MIN, ASTER_SPEED_MAX);
        velY = getRandomInt(1, ASTER_SPEED_MAX);
    }
    
    /**
     * Initiates asteroid with a starting point at the bottom of a screen.
     */
    private void initAsteroidBottom() {
        posX = getRandomDouble(0 - radius, Asteroids.ASTEROIDS.WIN_SIZE_X + radius);
        posY = Asteroids.ASTEROIDS.WIN_SIZE_Y + radius;
        posXPrev = posX;
        posYPrev = posY;
        velX = getRandomInt(ASTER_SPEED_MIN, ASTER_SPEED_MAX);
        velY = getRandomInt(ASTER_SPEED_MIN, -1);
    }
    
    /**
     * Initiates random rotation speed between min and max values
     * @return int rotation speed
     */
    private int initRotationSpeed() {
        int speed = getRandomInt(ASTER_ROT_SPEED_MIN, ASTER_ROT_SPEED_MAX);
        while(speed > -3 && speed < 3) {
            speed = getRandomInt(ASTER_ROT_SPEED_MIN, ASTER_ROT_SPEED_MAX);
        }
        return speed;
    }
    
    /**
     * Moves he asteroid according to current velocity X and Y.
     * @param acceleration int to be added to Y velocity if acceleration mode is on.
     */
    @Override
    public void move(int acceleration) {
        posYPrev = posY;
        posXPrev = posX;
        //If asteroid flies off the screen, it's being initiated with a new settings
        if(posX < - radius || posX > Asteroids.ASTEROIDS.WIN_SIZE_X + radius ||
            posY < - radius || posY > Asteroids.ASTEROIDS.WIN_SIZE_Y + radius) {
            init();
        } else {
            posX += velX;
            posY += velY + acceleration;
        }
    }
    
    /**
     * Calculates new velocity of the asteroid after collision, using Elastic Collision formula.
     * @param ufo second asteroid of the collision
     */
    @Override
    public void reactOnCollision(FlyingObject ufo) {
        int newVelX1 = (velX * (mass - ufo.getMass()) + 2 * ufo.getMass() * ufo.getVelX()) / (mass + ufo.getMass());
        int newVelY1 = (velY * (mass - ufo.getMass()) + 2 * ufo.getMass() * ufo.getVelY()) / (mass + ufo.getMass());
        int newVelX2 = (ufo.getVelX() * (ufo.getMass() - mass) + (2 * mass * velX)) / (mass + ufo.getMass());
        int newVelY2 = (ufo.getVelY() * (ufo.getMass() - mass) + (2 * mass * velY)) / (mass + ufo.getMass());
        
        velX = newVelX1;
        velY = newVelY1;
        ufo.setVelX(newVelX2);
        ufo.setVelY(newVelY2);
    }
    
    /**
     * sets alive variable to false, creates new Explosion at the current point
     * and calls init() function for the asteroid.
     */
    @Override
    public void kill() {
        this.setAlive(false);
        init();
    }
    
    /**
     * Draws object on the main JPanel GamePanel
     * @param g2 Graphics2D from the mail GamePanel
     * @param interpolation 
     */
    @Override
    public void draw(Graphics2D g2, double interpolation) {
        if(isAlive()) {
            int x = (int)((posX - posXPrev) * interpolation + posXPrev);
            int y = (int)((posY - posYPrev) * interpolation + posYPrev);
            g2.drawImage(this.rotate(), x - radius, y - radius, null);
        }
    }
    
    private double getRandomDouble(double min, double max) {
        return RANDOM.nextInt((int)Math.abs((max - min))) + min;
    }
    
    /**
     * Return random int from a given interval
     * @param min
     * @param max
     * @return random int from a given interval
     */
    private int getRandomInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }
    
}
